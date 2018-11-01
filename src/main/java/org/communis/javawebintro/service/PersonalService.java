package org.communis.javawebintro.service;

import org.communis.javawebintro.dto.UserPasswordWrapper;
import org.communis.javawebintro.dto.UserWrapper;
import org.communis.javawebintro.exception.ServerException;

public interface PersonalService {
    void edit(UserWrapper userWrapper) throws ServerException;

    void changePassword(UserPasswordWrapper passwordWrapper) throws ServerException;
}
