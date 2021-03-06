package org.communis.javawebintro.controller.rest;

import org.communis.javawebintro.dto.ArticleWrapper;
import org.communis.javawebintro.dto.UserWrapper;
import org.communis.javawebintro.dto.filters.ArticleFilterWrapper;
import org.communis.javawebintro.enums.ArticleStatus;
import org.communis.javawebintro.enums.ArticleType;
import org.communis.javawebintro.exception.InvalidDataException;
import org.communis.javawebintro.exception.NotFoundException;
import org.communis.javawebintro.exception.ServerException;
import org.communis.javawebintro.exception.error.ErrorCodeConstants;
import org.communis.javawebintro.exception.error.ErrorInformationBuilder;
import org.communis.javawebintro.service.ArticleService;
import org.communis.javawebintro.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.AccessDeniedException;
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
    public ArticleRestController(ArticleService articleService, UserService userService) {
        this.articleService = articleService;
        this.userService = userService;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public Page<ArticleWrapper> list(ArticleFilterWrapper articleFilter)
            throws InvalidDataException, ServerException {
        articleFilter.setType(ArticleType.PUBLIC);
        articleFilter.setStatus(ArticleStatus.ACTIVE);
        return articleService.getPage(articleFilter);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ArticleWrapper get(@PathVariable("id") Long id) throws ServerException {
        ArticleWrapper articleWrapper = articleService.getById(id);
        if(articleWrapper.getType() == ArticleType.PRIVATE){
            UserWrapper userWrapper = new UserWrapper(userService.getCurrentUser());
            if(!userWrapper.getId().equals(articleWrapper.getAuthor().getId())) {
                throw new AccessDeniedException("Доступ к чужим личным заметкам запрещен");
            }
        }
        return articleService.getById(id);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public String add(@Valid ArticleWrapper articleWrapper, BindingResult bindingResult)
            throws InvalidDataException, ServerException {
        if (bindingResult.hasErrors()) {
            throw new InvalidDataException(ErrorInformationBuilder.build(ErrorCodeConstants.DATA_VALIDATE_ERROR));
        }
        //articleWrapper.setStatus(ArticleStatus.ACTIVE);
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

    @RequestMapping(value = "/{id}/show", method = RequestMethod.POST)
    public String setPublic(@PathVariable("id") Long id)
            throws InvalidDataException, ServerException, NotFoundException {

        return articleService.setPublic(id).toString();
    }

    @RequestMapping(value = "/{id}/hide", method = RequestMethod.POST)
    public String setPrivate(@PathVariable("id") Long id)
            throws InvalidDataException, ServerException, NotFoundException {

        return articleService.setPrivate(id).toString();
    }
}
