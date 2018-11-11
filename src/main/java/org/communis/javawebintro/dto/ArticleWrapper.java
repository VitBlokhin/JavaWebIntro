package org.communis.javawebintro.dto;

import lombok.Data;
import org.communis.javawebintro.entity.Article;
import org.communis.javawebintro.enums.ArticleStatus;

import java.io.Serializable;
import java.util.Date;

@Data
public class ArticleWrapper implements ObjectWrapper<Article>, Serializable {
    private Long id;

    private String title;

    private String content;

    private Date dateCreate;

    private Date dateClose;

    private ArticleStatus status;

    private Long categoryId;

    private Long authorId;

    public ArticleWrapper() {
    }

    public ArticleWrapper(Article item) {
        toWrapper(item);
    }

    @Override
    public void toWrapper(Article item) {
        if (item != null) {
            id = item.getId();
            title = item.getTitle();
            content = item.getContent();
            dateCreate = item.getDateCreate();
            dateClose = item.getDateClose();
            status = item.getStatus();
            //authorId = item.getAuthor().getId();
            //categoryId = item.getCategory().getId();
            authorId = item.getAuthorId();
            categoryId = item.getCategoryId();
        }
    }

    @Override
    public void fromWrapper(Article item) {
        if (item != null) {
            item.setTitle(title);
            item.setContent(content);
            //item.setStatus(status);
        }
    }

    public boolean isShown(){
        return status == ArticleStatus.SHOWN;
    }
}
