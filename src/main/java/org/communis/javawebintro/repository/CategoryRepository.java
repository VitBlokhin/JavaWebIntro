package org.communis.javawebintro.repository;

import org.communis.javawebintro.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long>, JpaSpecificationExecutor<Category> {

    Optional<Category> findById(Long id);
    Optional<Category> findFirstByName(String name);
}
