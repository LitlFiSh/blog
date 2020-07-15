package com.myblog.demo.controller;

import com.myblog.demo.entity.Article;
import com.myblog.demo.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ArticleController {
    @Autowired
    ArticleRepository articleRepository;

    @GetMapping("/arti/{id}")
    public Article getArticle(@PathVariable("id") Integer id){
        Article a = articleRepository.getOne(id);
        return a;
    }

    //@PostMapping
    public Article insertArticle(Article article){
        Article a = articleRepository.save(article);
        return a;
    }
}
