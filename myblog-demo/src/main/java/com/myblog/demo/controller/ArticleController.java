package com.myblog.demo.controller;

import com.myblog.demo.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ArticleController {
    @Autowired
    ArticleRepository articleRepository;
    
    //实现文章的增删查改
}
