package org.communis.javawebintro.service;

import org.communis.javawebintro.dto.ArticleWrapper;
import org.communis.javawebintro.dto.PageWrapper;
import org.communis.javawebintro.dto.filters.ArticleFilterWrapper;
import org.communis.javawebintro.exception.ServerException;

public interface ArticleService {
    PageWrapper<ArticleWrapper> getPage(ArticleFilterWrapper filter) throws ServerException;
    Long create(ArticleWrapper articleWrapper) throws ServerException;
    ArticleWrapper getById(Long id) throws ServerException;
    Long update(ArticleWrapper articleWrapper) throws ServerException;
    void delete(Long id) throws ServerException;
}
