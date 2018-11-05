package org.communis.javawebintro.service;

import org.communis.javawebintro.dto.UserPasswordWrapper;
import org.communis.javawebintro.dto.UserWrapper;
import org.communis.javawebintro.exception.ServerException;

public interface PersonalService {
    Long edit(UserWrapper userWrapper) throws ServerException;

    Long changePassword(UserPasswordWrapper passwordWrapper) throws ServerException;
}
