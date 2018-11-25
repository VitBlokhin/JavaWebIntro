package org.communis.javawebintro.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "categories")
public class Category {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "DATE_CREATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreate;

    @Column(name = "DATE_CLOSE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateClose;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<Article> articles;

    public Set<Article> getArticles() {
        return getArticlesInternal();
    }

    public void setArticles(Set<Article> articles) {
        this.articles = articles;
    }

    private Set<Article> getArticlesInternal(){
        if(this.articles == null){
            this.articles = new HashSet<>();
        }
        return this.articles;
    }

    public void addArticle(Article article){
        getArticlesInternal().add(article);
        //article.setCategory(this);
    }

    public void removeArticle(Article article){
        getArticlesInternal().removeIf(a -> a.getId().equals(article.getId()));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Category that = (Category) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (dateCreate != null ? !dateCreate.equals(that.dateCreate) : that.dateCreate != null) return false;
        if (dateClose != null ? !dateClose.equals(that.dateClose) : that.dateClose != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (dateCreate != null ? dateCreate.hashCode() : 0);
        result = 31 * result + (dateClose != null ? dateClose.hashCode() : 0);
        return result;
    }
}
