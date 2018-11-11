package org.communis.javawebintro.service.impls;

import org.communis.javawebintro.dto.ArticleWrapper;
import org.communis.javawebintro.dto.PageWrapper;
import org.communis.javawebintro.dto.filters.ArticleFilterWrapper;
import org.communis.javawebintro.entity.Article;
import org.communis.javawebintro.enums.ArticleStatus;
import org.communis.javawebintro.exception.ServerException;
import org.communis.javawebintro.exception.error.ErrorCodeConstants;
import org.communis.javawebintro.exception.error.ErrorInformationBuilder;
import org.communis.javawebintro.repository.ArticleRepository;
import org.communis.javawebintro.repository.CategoryRepository;
import org.communis.javawebintro.repository.specifications.ArticleSpecification;
import org.communis.javawebintro.service.ArticleService;
import org.communis.javawebintro.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service("articleService")
@Transactional(rollbackFor = ServerException.class)
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;
    private final CategoryRepository categoryRepository;
    //private final UserRepository userRepository;
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
    public PageWrapper<ArticleWrapper> getPage(ArticleFilterWrapper filter) throws ServerException {
        try {
            int pageNumber, pageSize;
            pageNumber = filter.getPage() - 1;
            pageNumber = pageNumber < 0 ? 0 : pageNumber;
            pageSize = filter.getSize();

            Sort sortBy = new Sort(new Sort.Order(Sort.Direction.DESC, "dateCreate"));
            Pageable pageable = new PageRequest(pageNumber, pageSize, sortBy);

            return new PageWrapper<>(articleRepository.findAll(ArticleSpecification.build(filter), pageable), ArticleWrapper::new);
        } catch (Exception ex) {
            throw new ServerException(ErrorInformationBuilder.build(ErrorCodeConstants.CATEGORY_LIST_ERROR), ex);
        }
    }

    @Override
    public Long create(ArticleWrapper articleWrapper) throws ServerException {
        try {
            final Article article = new Article();
            articleWrapper.fromWrapper(article);
            if (articleRepository.findFirstByTitleAndContent(article.getTitle(), article.getContent()).isPresent()) {
                throw new ServerException(ErrorInformationBuilder.build(ErrorCodeConstants.ARTICLE_ALREADY_EXIST));
            }

            //categoryRepository.findById(articleWrapper.getCategoryId()).ifPresent(c -> c.addArticle(article));

            //article.setCategoryId(
            categoryRepository.findById(articleWrapper.getCategoryId()).ifPresent(c -> article.setCategoryId(c.getId()));
//                    .orElseThrow(() -> new ServerException(
//                                    ErrorInformationBuilder.build(ErrorCodeConstants.ARTICLE_CATEGORY_NOT_FOUND)
//                            ));
            //);

            article.setAuthorId(userService.getCurrentUser().getId());

            article.setStatus(ArticleStatus.NEW);
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
            Article article = getArticle(articleWrapper.getId());
            articleWrapper.fromWrapper(article);
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

            articleRepository.save(article);
        } catch (ServerException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ServerException(ErrorInformationBuilder.build(ErrorCodeConstants.ARTICLE_DELETE_ERROR), ex);
        }
    }

    private Article getArticle(Long id) throws ServerException {
        return articleRepository.findById(id)
                .orElseThrow(() -> new ServerException(ErrorInformationBuilder.build(ErrorCodeConstants.ARTICLE_INFO_ERROR)));
    }
}
