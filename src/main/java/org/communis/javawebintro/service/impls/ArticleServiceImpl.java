package org.communis.javawebintro.service.impls;

import org.communis.javawebintro.dto.ArticleWrapper;
import org.communis.javawebintro.dto.filters.ArticleFilterWrapper;
import org.communis.javawebintro.entity.Article;
import org.communis.javawebintro.enums.ArticleStatus;
import org.communis.javawebintro.enums.ArticleType;
import org.communis.javawebintro.exception.ServerException;
import org.communis.javawebintro.exception.error.ErrorCodeConstants;
import org.communis.javawebintro.exception.error.ErrorInformationBuilder;
import org.communis.javawebintro.repository.ArticleRepository;
import org.communis.javawebintro.repository.CategoryRepository;
import org.communis.javawebintro.repository.specifications.ArticleSpecification;
import org.communis.javawebintro.service.ArticleService;
import org.communis.javawebintro.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Objects;

@Service("articleService")
@Transactional(rollbackFor = ServerException.class)
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;
    private final CategoryRepository categoryRepository;
    private final UserService userService;

    @Autowired
    public ArticleServiceImpl(ArticleRepository articleRepository,
                              CategoryRepository categoryRepository,
                              UserService userService) {
        this.articleRepository = articleRepository;
        this.categoryRepository = categoryRepository;
        this.userService = userService;
    }

    @Override
    public Page<ArticleWrapper> getPage(ArticleFilterWrapper filter) throws ServerException {
        try {
            int pageNumber = (filter.getPage() < 1) ? 0 : filter.getPage();
            int pageSize = filter.getSize();

            Sort sortBy = new Sort(new Sort.Order(Sort.Direction.DESC, "dateCreate"));
            Pageable pageable = new PageRequest(pageNumber, pageSize, sortBy);

            return articleRepository.findAll(ArticleSpecification.build(filter), pageable).map(ArticleWrapper::new);
        } catch (Exception ex) {
            throw new ServerException(ErrorInformationBuilder.build(ErrorCodeConstants.ARTICLE_LIST_ERROR), ex);
        }
    }

    @Override
    public Long create(ArticleWrapper articleWrapper) throws ServerException {
        try {
            validateArticle(articleWrapper);

            final Article article = new Article();
            articleWrapper.fromWrapper(article);

            categoryRepository.findById(articleWrapper.getCategory().getId()).ifPresent(article::setCategory);

            //article.setAuthorId(userService.getCurrentUser().getId());
            article.setAuthor(userService.getCurrentUser());

            article.setStatus(ArticleStatus.ACTIVE);
            article.setType(ArticleType.PRIVATE);
            article.setDateCreate(new Date());
            articleRepository.save(article);

            return article.getId();
        } catch (ServerException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ServerException(ErrorInformationBuilder.build(ErrorCodeConstants.ARTICLE_ADD_ERROR), ex);
        }
    }

    @Override
    public ArticleWrapper getById(Long id) throws ServerException {
        return new ArticleWrapper(getArticle(id));
    }

    @Override
    public Long update(ArticleWrapper articleWrapper) throws ServerException {
        try {
            validateArticle(articleWrapper);

            Article article = getArticle(articleWrapper.getId());
            if (!Objects.equals(userService.getCurrentUser().getId(), article.getAuthor().getId())) {
                throw new ServerException(ErrorInformationBuilder.build(ErrorCodeConstants.ARTICLE_WRONG_AUTHOR));
            }

            articleWrapper.fromWrapper(article);
            categoryRepository.findById(articleWrapper.getCategory().getId()).ifPresent(article::setCategory);

            article = articleRepository.save(article);

            return article.getId();
        } catch (ServerException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ServerException(ErrorInformationBuilder.build(ErrorCodeConstants.ARTICLE_UPDATE_ERROR), ex);
        }
    }

    @Override
    public void delete(Long id) throws ServerException {
        try {
            Article article = getArticle(id);

            Date date = new Date();
            article.setDateClose(date);
            article.setStatus(ArticleStatus.DELETED);

            articleRepository.save(article);
        } catch (Exception ex) {
            throw new ServerException(ErrorInformationBuilder.build(ErrorCodeConstants.ARTICLE_DELETE_ERROR), ex);
        }
    }

    @Override
    public Long setPublic(Long id) throws ServerException {
        try {
            Article article = getArticle(id);

            article.setType(ArticleType.PUBLIC);
            articleRepository.save(article);

            return article.getId();
        } catch (Exception ex) {
            throw new ServerException(ErrorInformationBuilder.build(ErrorCodeConstants.ARTICLE_SHOW_ERROR), ex);
        }
    }

    @Override
    public Long setPrivate(Long id) throws ServerException {
        try {
            Article article = getArticle(id);

            article.setType(ArticleType.PRIVATE);
            articleRepository.save(article);

            return article.getId();
        } catch (Exception ex) {
            throw new ServerException(ErrorInformationBuilder.build(ErrorCodeConstants.ARTICLE_HIDE_ERROR), ex);
        }
    }

    @Override
    public Long block(Long id) throws ServerException {
        try {
            Article article = getArticle(id);

            article.setStatus(ArticleStatus.BLOCKED);
            articleRepository.save(article);

            return article.getId();
        } catch (Exception ex) {
            throw new ServerException(ErrorInformationBuilder.build(ErrorCodeConstants.ARTICLE_BLOCK_ERROR), ex);
        }
    }

    @Override
    public Long unblock(Long id) throws ServerException {
        try {
            Article article = getArticle(id);

            article.setStatus(ArticleStatus.ACTIVE);
            articleRepository.save(article);

            return article.getId();
        } catch (Exception ex) {
            throw new ServerException(ErrorInformationBuilder.build(ErrorCodeConstants.ARTICLE_UNBLOCK_ERROR), ex);
        }
    }

    private Article getArticle(Long id) throws ServerException {
        return articleRepository.findById(id)
                .orElseThrow(() -> new ServerException(ErrorInformationBuilder.build(ErrorCodeConstants.ARTICLE_INFO_ERROR)));
    }

    private boolean validateArticle(ArticleWrapper articleWrapper) throws ServerException {
        if (articleRepository.findFirstByTitleAndContent(articleWrapper.getTitle(), articleWrapper.getContent())
                .isPresent()) {
            throw new ServerException(ErrorInformationBuilder.build(ErrorCodeConstants.ARTICLE_ALREADY_EXIST));
        }

        if (!categoryRepository.findById(articleWrapper.getCategory().getId()).isPresent()) {
            throw new ServerException(ErrorInformationBuilder.build(ErrorCodeConstants.ARTICLE_CATEGORY_NOT_FOUND));
        }

        return true;
    }

    private boolean checkAuthor(ArticleWrapper articleWrapper) throws ServerException {
        Article article = getArticle(articleWrapper.getId());
        if (!Objects.equals(userService.getCurrentUser().getId(), article.getAuthor().getId())) {
            throw new ServerException(ErrorInformationBuilder.build(ErrorCodeConstants.ARTICLE_WRONG_AUTHOR));
        }
        return true;
    }
}
