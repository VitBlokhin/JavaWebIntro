package org.communis.javawebintro.repository;

import org.communis.javawebintro.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository extends JpaRepository<Article, Long>, JpaSpecificationExecutor<Article> {

    Optional<Article> findById(Long id);

    Optional<Article> findFirstByTitle(String title);

    Optional<Article> findFirstByContent(String content);

    Article findFirstByTitleAndContent(String title, String content);

    List<Article> findAllByAuthorId(Long id);
}
