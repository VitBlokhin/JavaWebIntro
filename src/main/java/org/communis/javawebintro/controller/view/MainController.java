package org.communis.javawebintro.controller.view;

import org.communis.javawebintro.dto.filters.ArticleFilterWrapper;
import org.communis.javawebintro.exception.ServerException;
import org.communis.javawebintro.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController {

    private final ArticleService articleService;

    @Autowired
    public MainController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @RequestMapping(value = {"/badbrowser"}, method = RequestMethod.GET)
    public String badBrowser() {
        return "errors/badBrowser";
    }

    @RequestMapping(value = {"/", "/index"}, method = RequestMethod.GET)
    public ModelAndView getIndexPage(ArticleFilterWrapper articleFilter) throws ServerException {
        ModelAndView indexPage = new ModelAndView("index");
        indexPage.addObject("filter", articleFilter);
        indexPage.addObject("page", articleService.getPage(articleFilter));
        return indexPage;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }

}
