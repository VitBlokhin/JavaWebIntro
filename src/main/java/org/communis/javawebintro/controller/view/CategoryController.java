package org.communis.javawebintro.controller.view;

import org.communis.javawebintro.dto.filters.ArticleFilterWrapper;
import org.communis.javawebintro.dto.filters.ObjectFilter;
import org.communis.javawebintro.enums.ArticleStatus;
import org.communis.javawebintro.exception.ServerException;
import org.communis.javawebintro.service.ArticleService;
import org.communis.javawebintro.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "categories")
public class CategoryController {
    private final String CATEGORY_VIEWS_PATH = "category/";

    private final CategoryService categoryService;

    private final ArticleService articleService;

    @Autowired
    public CategoryController(CategoryService categoryService,
                              ArticleService articleService) {
        this.categoryService = categoryService;
        this.articleService = articleService;
    }

    @RequestMapping(value = "")
    public ModelAndView list(ObjectFilter filterWrapper) throws ServerException {
        ModelAndView categoriesPage = new ModelAndView(CATEGORY_VIEWS_PATH + "list");
        categoriesPage.addObject("filter", filterWrapper);
        categoriesPage.addObject("page", categoryService.getPage(filterWrapper));
        return categoriesPage;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ModelAndView getCategory(@PathVariable("id") Long id, ArticleFilterWrapper articleFilter) throws ServerException {
        ModelAndView categoriesPage = new ModelAndView(CATEGORY_VIEWS_PATH + "view");
        articleFilter.setCategoryId(id);
        //articleFilter.setStatus(ArticleStatus.SHOWN);
        categoriesPage.addObject("filter", articleFilter);
        categoriesPage.addObject("category", categoryService.getById(id));
        categoriesPage.addObject("page", articleService.getPage(articleFilter));
        return categoriesPage;
    }
}
