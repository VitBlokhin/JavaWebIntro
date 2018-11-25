package org.communis.javawebintro.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.communis.javawebintro.enums.ArticleStatus;
import org.communis.javawebintro.enums.ArticleType;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "articles")
public class Article {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "CONTENT")
    private String content;

    @Column(name = "DATE_CREATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreate;

    @Column(name = "DATE_CLOSE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateClose;

    @Column(name = "STATUS")
    @Enumerated(EnumType.STRING)
    private ArticleStatus status;

    @Column(name = "TYPE")
    @Enumerated(EnumType.STRING)
    private ArticleType type;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CATEGORY_ID", referencedColumnName = "ID")
    private Category category;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AUTHOR_ID", referencedColumnName = "ID")
    private User author;

//    @Getter(AccessLevel.NONE)
//    @Setter(AccessLevel.NONE)
//    @Column(name = "AUTHOR_ID")
//    private Long authorId;

//    @Getter(AccessLevel.NONE)
//    @Setter(AccessLevel.NONE)
//    @Column(name = "CATEGORY_ID")
//    private Long categoryId;

//    public Long getAuthorId() {
//        return authorId;
//    }
//
//    public void setAuthorId(Long authorId) {
//        this.authorId = authorId;
//    }

//    public Long getCategoryId() {
//        return categoryId;
//    }
//
//    public void setCategoryId(Long categoryId) {
//        this.categoryId = categoryId;
//    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
        category.addArticle(this);
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
        author.addArticle(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Article article = (Article) o;

        if (id != null ? !id.equals(article.id) : article.id != null) return false;
        if (title != null ? !title.equals(article.title) : article.title != null) return false;
        if (content != null ? !content.equals(article.content) : article.content != null) return false;
        if (dateCreate != null ? !dateCreate.equals(article.dateCreate) : article.dateCreate != null) return false;
        if (dateClose != null ? !dateClose.equals(article.dateClose) : article.dateClose != null) return false;
        if (status != null ? !status.equals(article.status) : article.status != null) return false;
        if (type != null ? !type.equals(article.type) : article.type != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (dateCreate != null ? dateCreate.hashCode() : 0);
        result = 31 * result + (dateClose != null ? dateClose.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }
}
