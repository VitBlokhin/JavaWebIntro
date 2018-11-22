package org.communis.javawebintro.service;

import org.communis.javawebintro.dto.CategoryWrapper;
import org.communis.javawebintro.dto.filters.ObjectFilter;
import org.communis.javawebintro.exception.ServerException;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CategoryService {
    Page<CategoryWrapper> getPage(ObjectFilter filter) throws ServerException;
    Long create(CategoryWrapper categoryWrapper) throws ServerException;
    CategoryWrapper getById(Long id) throws ServerException;
    Long update(CategoryWrapper categoryWrapper) throws ServerException;
    void delete(Long id) throws ServerException;

    List<CategoryWrapper> getAllActive() throws ServerException;
}
