package org.communis.javawebintro.controller.rest;

import org.communis.javawebintro.dto.ArticleWrapper;
import org.communis.javawebintro.enums.ArticleStatus;
import org.communis.javawebintro.exception.InvalidDataException;
import org.communis.javawebintro.exception.NotFoundException;
import org.communis.javawebintro.exception.ServerException;
import org.communis.javawebintro.exception.error.ErrorCodeConstants;
import org.communis.javawebintro.exception.error.ErrorInformationBuilder;
import org.communis.javawebintro.service.ArticleService;
import org.communis.javawebintro.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "rest/articles")
public class ArticleRestController {

    private final ArticleService articleService;
    private final UserService userService;

    @Autowired
    public ArticleRestController(ArticleService articleService,
                                 UserService userService) {
        this.articleService = articleService;
        this.userService = userService;
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public String add(@Valid ArticleWrapper articleWrapper, BindingResult bindingResult)
            throws InvalidDataException, ServerException {
        if (bindingResult.hasErrors()) {
            throw new InvalidDataException(ErrorInformationBuilder.build(ErrorCodeConstants.DATA_VALIDATE_ERROR));
        }
        articleWrapper.setStatus(ArticleStatus.NEW);
        articleWrapper.setAuthorId(userService.getCurrentUser().getId());
        return articleService.create(articleWrapper).toString();
    }

    @RequestMapping(value = "", method = RequestMethod.PATCH)
    public String edit(@Valid ArticleWrapper articleWrapper, BindingResult bindingResult)
            throws InvalidDataException, NotFoundException, ServerException {
        if (bindingResult.hasErrors()) {
            throw new InvalidDataException(ErrorInformationBuilder.build(ErrorCodeConstants.DATA_VALIDATE_ERROR));
        }
        return articleService.update(articleWrapper).toString();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") Long id)
            throws NotFoundException, ServerException {

        articleService.delete(id);
    }
}
