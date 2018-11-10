package org.communis.javawebintro.service.impls;

import org.communis.javawebintro.dto.CategoryWrapper;
import org.communis.javawebintro.dto.PageWrapper;
import org.communis.javawebintro.dto.filters.ObjectFilter;
import org.communis.javawebintro.entity.Category;
import org.communis.javawebintro.enums.ArticleStatus;
import org.communis.javawebintro.exception.ServerException;
import org.communis.javawebintro.exception.error.ErrorCodeConstants;
import org.communis.javawebintro.exception.error.ErrorInformationBuilder;
import org.communis.javawebintro.repository.CategoryRepository;
import org.communis.javawebintro.repository.specifications.CategorySpecification;
import org.communis.javawebintro.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service("categoryService")
@Transactional(rollbackFor = ServerException.class)
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public PageWrapper<CategoryWrapper> getPage(ObjectFilter filter) throws ServerException {
        try {
            int pageNumber, pageSize;
            pageNumber = filter.getPage() - 1;
            pageNumber = pageNumber < 0 ? 0 : pageNumber;
            pageSize = filter.getSize();

            Sort sortBy = new Sort(new Sort.Order(Sort.Direction.ASC, "name"));
            Pageable pageable = new PageRequest(pageNumber, pageSize, sortBy);

            return new PageWrapper<>(categoryRepository.findAll(CategorySpecification.build(filter), pageable), CategoryWrapper::new);
        } catch (Exception ex) {
            throw new ServerException(ErrorInformationBuilder.build(ErrorCodeConstants.USER_LIST_ERROR), ex);
        }
    }

    @Override
    public Long create(CategoryWrapper categoryWrapper) throws ServerException {
        try {
            Category category = new Category();
            categoryWrapper.fromWrapper(category);
            if (categoryRepository.findFirstByName(category.getName()).isPresent()) {
                throw new ServerException(ErrorInformationBuilder.build(ErrorCodeConstants.CATEGORY_ALREADY_EXIST));
            }

            category.setDateCreate(new Date());
            category = categoryRepository.save(category);

            return category.getId();
        } catch (ServerException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ServerException(ErrorInformationBuilder.build(ErrorCodeConstants.CATEGORY_ADD_ERROR), ex);
        }
    }

    @Override
    public CategoryWrapper getById(Long id) throws ServerException {
        return new CategoryWrapper(getCategory(id));
    }

    @Override
    public Long update(CategoryWrapper categoryWrapper) throws ServerException {
        try {
            Category category = getCategory(categoryWrapper.getId());
            categoryWrapper.fromWrapper(category);
            category = categoryRepository.save(category);

            return category.getId();
        } catch (ServerException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ServerException(ErrorInformationBuilder.build(ErrorCodeConstants.CATEGORY_UPDATE_ERROR), ex);
        }
    }

    @Override
    public void delete(Long id) throws ServerException {
        try {
            Category category = getCategory(id);

            Date date = new Date();
            category.setDateClose(date);

            category.getArticles().forEach(a -> {
                // TODO: убрать категорию из обьявления или скрыть объявление?
                //a.setStatus(ArticleStatus.HIDDEN);
            });

            categoryRepository.save(category);
        } catch (ServerException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ServerException(ErrorInformationBuilder.build(ErrorCodeConstants.CATEGORY_DELETE_ERROR), ex);
        }
    }

    private Category getCategory(Long id) throws ServerException {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ServerException(ErrorInformationBuilder.build(ErrorCodeConstants.CATEGORY_INFO_ERROR)));
    }
}
