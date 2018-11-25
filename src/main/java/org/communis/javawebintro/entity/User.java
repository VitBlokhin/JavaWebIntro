package org.communis.javawebintro.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.communis.javawebintro.enums.UserRole;
import org.communis.javawebintro.enums.UserStatus;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "LOGIN")
    private String login;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "DATE_CREATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreate;

    @Column(name = "DATE_BLOCK")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateBlock;

    @Column(name = "DATE_LAST_ONLINE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateLastOnline;

    @Column(name = "ROLE")
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Column(name = "STATUS")
    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<Article> articles;


    public Set<Article> getArticles() {
        return getArticlesInternal();
    }

    public void setArticles(Set<Article> articles) {
        this.articles = articles;
    }

    public void addArticle(Article article){
        getArticlesInternal().add(article);
        //article.setAuthor(this);
    }

    public void removeArticle(Article article){
        getArticlesInternal().removeIf(a -> a.getId().equals(article.getId()));
    }

    private Set<Article> getArticlesInternal() {
        if(this.articles == null){
            this.articles = new HashSet<>();
        }
        return this.articles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != null ? !id.equals(user.id) : user.id != null) return false;
        if (login != null ? !login.equals(user.login) : user.login != null) return false;
        if (password != null ? !password.equals(user.password) : user.password != null) return false;
        if (firstName != null ? !firstName.equals(user.firstName) : user.firstName != null) return false;
        if (lastName != null ? !lastName.equals(user.lastName) : user.lastName != null) return false;
        if (email != null ? !email.equals(user.email) : user.email != null) return false;
        if (dateCreate != null ? !dateCreate.equals(user.dateCreate) : user.dateCreate != null) return false;
        if (dateBlock != null ? !dateBlock.equals(user.dateBlock) : user.dateBlock != null) return false;
        if (dateLastOnline != null ? !dateLastOnline.equals(user.dateLastOnline) : user.dateLastOnline != null)
            return false;
        if (role != null ? !role.equals(user.role) : user.role != null) return false;
        if (status != null ? !status.equals(user.status) : user.status != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (login != null ? login.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (dateCreate != null ? dateCreate.hashCode() : 0);
        result = 31 * result + (dateBlock != null ? dateBlock.hashCode() : 0);
        result = 31 * result + (dateLastOnline != null ? dateLastOnline.hashCode() : 0);
        result = 31 * result + (role != null ? role.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }
}
