package org.communis.javawebintro.dto.filters;

import org.communis.javawebintro.enums.ArticleStatus;

public class ArticleFilterWrapper extends ObjectFilter {
    private Long categoryId;
    private Long authorId;
    private ArticleStatus status;

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public ArticleStatus getStatus() {
        return status;
    }

    public void setStatus(ArticleStatus status) {
        this.status = status;
    }
}
