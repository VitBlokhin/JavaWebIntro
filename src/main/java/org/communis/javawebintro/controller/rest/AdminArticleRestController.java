package org.communis.javawebintro.controller.rest;

import org.communis.javawebintro.exception.InvalidDataException;
import org.communis.javawebintro.exception.NotFoundException;
import org.communis.javawebintro.exception.ServerException;
import org.communis.javawebintro.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "rest/admin/articles")
public class AdminArticleRestController {

    private final ArticleService articleService;

    @Autowired
    public AdminArticleRestController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @RequestMapping(value = "/{id}/show", method = RequestMethod.POST)
    public String show(@PathVariable("id") Long id)
            throws InvalidDataException, ServerException, NotFoundException {

        return articleService.show(id).toString();
    }

    @RequestMapping(value = "/{id}/hide", method = RequestMethod.POST)
    public String hide(@PathVariable("id") Long id)
            throws InvalidDataException, ServerException, NotFoundException {

        return articleService.hide(id).toString();
    }

    @RequestMapping(value = "/{id}/block", method = RequestMethod.POST)
    public String block(@PathVariable("id") Long id)
            throws InvalidDataException, ServerException, NotFoundException {

        return articleService.block(id).toString();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") Long id)
            throws NotFoundException, ServerException {

        articleService.delete(id);
    }
}
