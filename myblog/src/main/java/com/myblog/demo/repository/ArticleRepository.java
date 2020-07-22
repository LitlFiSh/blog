package com.myblog.demo.repository;

import com.myblog.demo.entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Integer> {
    //通过作者找出所有文章
    Page<Article> findAllByAuthor(String author, Pageable pageable);
    //首页通过标题查找文章
    Page<Article> findAllByArticleTitleLike(String str, Pageable pageable);
    //管理页通过作者和标题查找文章
    Page<Article> findAllByAuthorAndArticleTitleLike(String author, String str, Pageable pageable);
}
