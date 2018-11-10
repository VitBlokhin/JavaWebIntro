package org.communis.javawebintro.controller.view;

import lombok.extern.log4j.Log4j2;
import org.communis.javawebintro.dto.CategoryWrapper;
import org.communis.javawebintro.dto.filters.ObjectFilter;
import org.communis.javawebintro.exception.ServerException;
import org.communis.javawebintro.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Log4j2
@Controller
@RequestMapping(value = "admin/categories")
public class AdminCategoryController {
    private final String CATEGORY_VIEWS_PATH = "admin/category/";

    private final CategoryService categoryService;

    @Autowired
    public AdminCategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @RequestMapping(value = "")
    public ModelAndView list(ObjectFilter filterWrapper) throws ServerException {
        ModelAndView usersPage = new ModelAndView(CATEGORY_VIEWS_PATH + "list");
        usersPage.addObject("filter", filterWrapper);
        usersPage.addObject("page", categoryService.getPage(filterWrapper));
        return usersPage;
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ModelAndView addPage() {
        ModelAndView addPage = new ModelAndView(CATEGORY_VIEWS_PATH + "add");
        addPage.addObject("category", new CategoryWrapper());
        return addPage;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ModelAndView editPage(@PathVariable("id") Long id) throws ServerException {
        ModelAndView editPage = new ModelAndView(CATEGORY_VIEWS_PATH + "edit");
        editPage.addObject("category", categoryService.getById(id));
        return editPage;
    }
}
