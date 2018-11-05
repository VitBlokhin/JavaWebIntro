package org.communis.javawebintro.controller.rest;

import org.communis.javawebintro.dto.UserPasswordWrapper;
import org.communis.javawebintro.dto.UserWrapper;
import org.communis.javawebintro.exception.InvalidDataException;
import org.communis.javawebintro.exception.NotFoundException;
import org.communis.javawebintro.exception.ServerException;
import org.communis.javawebintro.exception.error.ErrorCodeConstants;
import org.communis.javawebintro.exception.error.ErrorInformationBuilder;
import org.communis.javawebintro.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "rest/admin/users")
public class UserRestController {
    private final UserService userService;

    @Autowired
    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(@Valid UserWrapper userWrapper, BindingResult bindingResult)
            throws InvalidDataException, ServerException {
        if (bindingResult.hasErrors()) {
            throw new InvalidDataException(ErrorInformationBuilder.build(ErrorCodeConstants.DATA_VALIDATE_ERROR));
        }
        return userService.create(userWrapper).toString();
    }

    @RequestMapping(value = "", method = RequestMethod.PATCH)
    public String edit(@Valid UserWrapper userWrapper, BindingResult bindingResult)
            throws InvalidDataException, NotFoundException, ServerException {
        if (bindingResult.hasErrors()) {
            throw new InvalidDataException(ErrorInformationBuilder.build(ErrorCodeConstants.DATA_VALIDATE_ERROR));
        }
        return userService.update(userWrapper).toString();
    }

    @RequestMapping(value = "/password", method = RequestMethod.PATCH)
    public String changePassword(@Valid UserPasswordWrapper passwordWrapper, BindingResult bindingResult)
            throws InvalidDataException, NotFoundException, ServerException {
        if (bindingResult.hasErrors()) {
            throw new InvalidDataException(ErrorInformationBuilder.build(ErrorCodeConstants.DATA_VALIDATE_ERROR));
        }

        return userService.changePassword(passwordWrapper).toString();
    }

    @RequestMapping(value = "/{id}/block", method = RequestMethod.POST)
    public String block(@PathVariable("id") Long id)
            throws InvalidDataException, ServerException, NotFoundException {

        return userService.block(id).toString();
    }

    @RequestMapping(value = "/{id}/unblock", method = RequestMethod.POST)
    public String unblock(@PathVariable("id") Long id)
            throws NotFoundException, ServerException {

        return userService.unblock(id).toString();
    }

    @RequestMapping(value = "/{id}/delete", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") Long id)
            throws NotFoundException, ServerException {

        userService.delete(id);
    }
}
