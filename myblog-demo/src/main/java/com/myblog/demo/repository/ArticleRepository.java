package com.myblog.demo.repository;

import com.myblog.demo.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Integer> {
    List<Article> findAllByAuthor(String author);
}
