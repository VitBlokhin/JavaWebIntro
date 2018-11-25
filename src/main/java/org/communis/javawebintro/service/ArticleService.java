package org.communis.javawebintro.service;

import org.communis.javawebintro.dto.ArticleWrapper;
import org.communis.javawebintro.dto.filters.ArticleFilterWrapper;
import org.communis.javawebintro.exception.ServerException;
import org.springframework.data.domain.Page;

public interface ArticleService {
    Page<ArticleWrapper> getPage(ArticleFilterWrapper filter) throws ServerException;
    Long create(ArticleWrapper articleWrapper) throws ServerException;
    ArticleWrapper getById(Long id) throws ServerException;
    Long update(ArticleWrapper articleWrapper) throws ServerException;
    void delete(Long id) throws ServerException;

    Long setPublic(Long id) throws ServerException;
    Long setPrivate(Long id) throws ServerException;
    Long block(Long id) throws ServerException;
    Long unblock(Long id) throws ServerException;
}
