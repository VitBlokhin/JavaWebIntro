package org.communis.javawebintro.controller.view;

import org.communis.javawebintro.dto.ArticleWrapper;
import org.communis.javawebintro.dto.CategoryWrapper;
import org.communis.javawebintro.dto.UserWrapper;
import org.communis.javawebintro.dto.filters.ArticleFilterWrapper;
import org.communis.javawebintro.enums.ArticleStatus;
import org.communis.javawebintro.enums.ArticleType;
import org.communis.javawebintro.exception.ServerException;
import org.communis.javawebintro.service.ArticleService;
import org.communis.javawebintro.service.CategoryService;
import org.communis.javawebintro.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "articles")
public class ArticleController {
    private final String ARTICLE_VIEWS_PATH = "article/";

    private final ArticleService articleService;
    private final CategoryService categoryService;
    private final UserService userService;

    @Autowired
    public ArticleController(ArticleService articleService, CategoryService categoryService, UserService userService) {
        this.articleService = articleService;
        this.categoryService = categoryService;
        this.userService = userService;
    }

    @RequestMapping(value = {""}, method = RequestMethod.GET)
    public ModelAndView getIndexPage(ArticleFilterWrapper articleFilter) throws ServerException {
        ModelAndView indexPage = new ModelAndView(ARTICLE_VIEWS_PATH + "list");
        articleFilter.setType(ArticleType.PUBLIC);
        articleFilter.setStatus(ArticleStatus.ACTIVE);
        indexPage.addObject("filter", articleFilter);
        indexPage.addObject("page", articleService.getPage(articleFilter));
        return indexPage;
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ModelAndView addPage() throws ServerException {
        ModelAndView addPage = new ModelAndView(ARTICLE_VIEWS_PATH + "add");
        addPage.addObject("categories", categoryService.getAllActive());
        addPage.addObject("article", new ArticleWrapper());
        return addPage;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ModelAndView getArticle(@PathVariable("id") Long id) throws ServerException {
        ArticleWrapper articleWrapper = articleService.getById(id);
        if(articleWrapper.getType() == ArticleType.PRIVATE){
            UserWrapper userWrapper = new UserWrapper(userService.getCurrentUser());
            if(!userWrapper.getId().equals(articleWrapper.getAuthor().getId())) {
                throw new AccessDeniedException("Доступ к чужим личным заметкам запрещен");
            }
        }

        ModelAndView articlePage = new ModelAndView(ARTICLE_VIEWS_PATH + "view");
        articlePage.addObject("article", articleWrapper);
        return articlePage;
    }

    @RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
    public ModelAndView getEdit(@PathVariable("id") Long id) throws ServerException {
        ArticleWrapper articleWrapper = articleService.getById(id);
        UserWrapper userWrapper = new UserWrapper(userService.getCurrentUser());

        if(!userWrapper.getId().equals(articleWrapper.getAuthor().getId())) {
            throw new AccessDeniedException("Доступ к редактированию чужих заметок запрещен");
        }

        ModelAndView articlePage = new ModelAndView(ARTICLE_VIEWS_PATH + "edit");
        articlePage.addObject("article", articleWrapper);
        articlePage.addObject("categories", categoryService.getAllActive());
        articlePage.addObject("user", userWrapper);
        return articlePage;
    }


    @RequestMapping(value = "/my", method = RequestMethod.GET)
    public ModelAndView getPersonalArticles(ArticleFilterWrapper articleFilter) throws ServerException {
        ModelAndView articlesPage = new ModelAndView("article/my");
        articleFilter.setAuthorId(userService.getCurrentUser().getId());
        articlesPage.addObject("filter", articleFilter);
        articlesPage.addObject("page", articleService.getPage(articleFilter));
        return articlesPage;
    }
}
