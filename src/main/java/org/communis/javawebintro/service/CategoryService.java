package org.communis.javawebintro.service;

import org.communis.javawebintro.dto.CategoryWrapper;
import org.communis.javawebintro.dto.PageWrapper;
import org.communis.javawebintro.dto.filters.ObjectFilter;
import org.communis.javawebintro.exception.ServerException;

public interface CategoryService {
    PageWrapper<CategoryWrapper> getPage(ObjectFilter filter) throws ServerException;
    Long create(CategoryWrapper categoryWrapper) throws ServerException;
    CategoryWrapper getById(Long id) throws ServerException;
    Long update(CategoryWrapper categoryWrapper) throws ServerException;
    void delete(Long id) throws ServerException;
}
