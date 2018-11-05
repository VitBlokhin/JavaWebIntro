package org.communis.javawebintro.service;

import org.communis.javawebintro.dto.PageWrapper;
import org.communis.javawebintro.dto.UserPasswordWrapper;
import org.communis.javawebintro.dto.UserWrapper;
import org.communis.javawebintro.dto.filters.UserFilterWrapper;
import org.communis.javawebintro.entity.User;
import org.communis.javawebintro.exception.ServerException;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    PageWrapper<UserWrapper> getPage(UserFilterWrapper filter) throws ServerException;
    Long create(UserWrapper userWrapper) throws ServerException;
    UserWrapper getById(Long id) throws ServerException;
    Long update(UserWrapper userWrapper) throws ServerException;
    void delete(Long id) throws ServerException;

    //
    Long block(Long id) throws ServerException;
    Long unblock(Long id) throws ServerException;

    User getCurrentUser() throws ServerException;
    Long changePassword(UserPasswordWrapper passwordWrapper) throws ServerException;
    void updateLastOnlineDate(Long id);
}
