package org.communis.javawebintro.service.impls;

import org.communis.javawebintro.dto.UserPasswordWrapper;
import org.communis.javawebintro.dto.UserWrapper;
import org.communis.javawebintro.entity.User;
import org.communis.javawebintro.exception.ServerException;
import org.communis.javawebintro.exception.error.ErrorCodeConstants;
import org.communis.javawebintro.exception.error.ErrorInformationBuilder;
import org.communis.javawebintro.repository.UserRepository;
import org.communis.javawebintro.service.PersonalService;
import org.communis.javawebintro.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("personalService")
@Transactional(rollbackFor = ServerException.class)
public class PersonalServiceImpl implements PersonalService{

    private final UserService userService;

    @Autowired
    public PersonalServiceImpl(UserService userService) {
        this.userService = userService;
    }

    /**
     * Изменяет информацию о текущем пользователе
     *
     * @param userWrapper новая информация о пользователе
     */
    public Long edit(UserWrapper userWrapper) throws ServerException {
        try {
            User currentUser = userService.getCurrentUser();
            userWrapper.setId(currentUser.getId());     // если в данных был передан неверный id
            userWrapper.setRole(currentUser.getRole());
            userWrapper.setStatus(currentUser.getStatus());
            userWrapper.fromWrapper(currentUser);

            return userService.update(userWrapper);
        } catch (Exception ex) {
            throw new ServerException(ErrorInformationBuilder.build(ErrorCodeConstants.USER_UPDATE_ERROR), ex);
        }
    }

    /**
     * Изменяет пароль текущего пользователя
     *
     * @param passwordWrapper инфомарция о пароле пользователя
     */
    public Long changePassword(UserPasswordWrapper passwordWrapper) throws ServerException {
        try {
            User currentUser = userService.getCurrentUser();
            passwordWrapper.setId(currentUser.getId());

            return userService.changePassword(passwordWrapper);
        } catch (Exception ex) {
            throw new ServerException(ErrorInformationBuilder.build(ErrorCodeConstants.USER_UPDATE_ERROR), ex);
        }
    }
}
