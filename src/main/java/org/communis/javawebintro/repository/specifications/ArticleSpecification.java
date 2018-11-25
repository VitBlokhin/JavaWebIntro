package org.communis.javawebintro.repository.specifications;

import org.communis.javawebintro.dto.filters.ArticleFilterWrapper;
import org.communis.javawebintro.entity.Article;
import org.communis.javawebintro.entity.Category;
import org.communis.javawebintro.entity.User;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

public abstract class ArticleSpecification implements Specification<Article> {

    private ArticleSpecification() {
    }

    public static ArticleSpecification build(final ArticleFilterWrapper filter) {
        return new ArticleSpecification() {
            @Override
            public Predicate toPredicate(Root<Article> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                final List predicates = new ArrayList();

                if (filter != null) {
                    if (filter.getSearch() != null && !filter.getSearch().isEmpty()) {
                        predicates.add(criteriaBuilder.or(
                                criteriaBuilder.like(criteriaBuilder.upper(root.get("title")), '%' + filter.getSearch().toUpperCase() + '%'),
                                criteriaBuilder.like(criteriaBuilder.upper(root.get("content")), '%' + filter.getSearch().toUpperCase() + '%'))
                        );
                    }
                    if (filter.getCategoryId() != null) {
                        Join<Article, Category> category = root.join("category");

                        predicates.add(criteriaBuilder.equal(category.get("id"), filter.getCategoryId()));
                        //predicates.add(criteriaBuilder.equal(root.get("categoryId"), filter.getCategoryId()));
                    }
                    if (filter.getAuthorId() != null) {
                        Join<Article, User> author = root.join("author");

                        predicates.add(criteriaBuilder.equal(author.get("id"), filter.getAuthorId()));
                        //predicates.add(criteriaBuilder.equal(root.get("authorId"), filter.getAuthorId()));
                    }
                    if(filter.getStatus() != null){
                        predicates.add(criteriaBuilder.equal(root.get("status"), filter.getStatus()));
                    }
                    if(filter.getType() != null){
                        predicates.add(criteriaBuilder.equal(root.get("type"), filter.getType()));
                    }
                }
                return criteriaBuilder.and((Predicate[]) predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }
}
