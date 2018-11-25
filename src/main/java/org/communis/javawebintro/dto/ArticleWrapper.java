package org.communis.javawebintro.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.communis.javawebintro.entity.Article;
import org.communis.javawebintro.enums.ArticleStatus;
import org.communis.javawebintro.enums.ArticleType;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Data
public class ArticleWrapper implements ObjectWrapper<Article>, Serializable {
    private Long id;

    @NotEmpty
    private String title;

    @NotEmpty
    private String content;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private Date dateCreate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private Date dateClose;

    private ArticleStatus status;

    private ArticleType type;

//    @NotNull
//    private Long categoryId;

    @NotNull
    private CategoryWrapper category;

    private AuthorWrapper author;

    //private Long authorId;

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
            type = item.getType();
            //authorId = item.getAuthor().getId();
            //categoryId = item.getCategory().getId();
            category = new CategoryWrapper(item.getCategory());
            author = new AuthorWrapper(item.getAuthor());
            //categoryId = item.getCategoryId();
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
}
