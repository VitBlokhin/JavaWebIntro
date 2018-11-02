package org.communis.javawebintro.service;

import org.communis.javawebintro.dto.PageWrapper;
import org.communis.javawebintro.dto.UserPasswordWrapper;
import org.communis.javawebintro.dto.UserWrapper;
import org.communis.javawebintro.dto.filters.UserFilterWrapper;
import org.communis.javawebintro.entity.User;
import org.communis.javawebintro.exception.ServerException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    PageWrapper<UserWrapper> getPage(UserFilterWrapper filter) throws ServerException;
    void create(UserWrapper userWrapper) throws ServerException;
    UserWrapper getById(Long id) throws ServerException;
    void update(UserWrapper userWrapper) throws ServerException;
    void delete(Long id) throws ServerException;

    //
    void block(Long id) throws ServerException;
    void unblock(Long id) throws ServerException;

    User getCurrentUser() throws ServerException;
    void changePassword(UserPasswordWrapper passwordWrapper) throws ServerException;
    void updateLastOnlineDate(Long id);
}
