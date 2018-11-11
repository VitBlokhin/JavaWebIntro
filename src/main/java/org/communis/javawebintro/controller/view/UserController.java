package org.communis.javawebintro.controller.view;

import lombok.extern.log4j.Log4j2;
import org.communis.javawebintro.dto.UserWrapper;
import org.communis.javawebintro.dto.filters.UserFilterWrapper;
import org.communis.javawebintro.enums.UserRole;
import org.communis.javawebintro.enums.UserStatus;
import org.communis.javawebintro.exception.InvalidDataException;
import org.communis.javawebintro.exception.ServerException;
import org.communis.javawebintro.exception.error.ErrorCodeConstants;
import org.communis.javawebintro.exception.error.ErrorInformationBuilder;
import org.communis.javawebintro.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Log4j2
@Controller
@RequestMapping(value = "admin/users")
public class UserController {
    private final String USER_VIEWS_PATH = "admin/user/";

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "")
    public ModelAndView list(UserFilterWrapper userFilterWrapper) throws ServerException {
        ModelAndView usersPage = new ModelAndView(USER_VIEWS_PATH + "list");
        usersPage.addObject("filter", userFilterWrapper);
        usersPage.addObject("page", userService.getPage(userFilterWrapper));
        prepareUserModelAndView(usersPage);
        return usersPage;
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ModelAndView addPage() {
        ModelAndView addPage = new ModelAndView(USER_VIEWS_PATH + "add");
        addPage.addObject("user", new UserWrapper());
        prepareUserModelAndView(addPage);
        return addPage;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ModelAndView editPage(@PathVariable("id") Long id) throws ServerException {
        ModelAndView editPage = new ModelAndView(USER_VIEWS_PATH + "edit");
        editPage.addObject("user", userService.getById(id));
        prepareUserModelAndView(editPage);
        return editPage;
    }

    private void prepareUserModelAndView(ModelAndView modelAndView) {
        modelAndView.addObject("statuses", UserStatus.values());
        modelAndView.addObject("roles", UserRole.values());
    }
}
