package org.communis.javawebintro.repository.specifications;

import org.communis.javawebintro.dto.filters.ObjectFilter;
import org.communis.javawebintro.entity.Category;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public abstract class CategorySpecification implements Specification<Category> {

    private CategorySpecification() {
    }

    public static CategorySpecification build(final ObjectFilter filter) {
        return new CategorySpecification() {
            @Override
            public Predicate toPredicate(Root<Category> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = null;

                if (filter != null) {
                    if (filter.getSearch() != null && !filter.getSearch().isEmpty()) {
                        predicate = criteriaBuilder.like(criteriaBuilder.upper(root.get("name")), '%' + filter.getSearch().toUpperCase() + '%');
                    }

                }
                return predicate;
            }
        };
    }
}
