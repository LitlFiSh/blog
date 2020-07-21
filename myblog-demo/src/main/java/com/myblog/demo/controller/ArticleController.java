package com.myblog.demo.controller;

import com.myblog.demo.entity.Article;
import com.myblog.demo.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class ArticleController {
    @Autowired
    ArticleRepository articleRepository;

    //登录后的跳转，跳转至显示用户的所有文章
    @GetMapping("/allArti")
    public String allArticle(Model model, Integer page, Integer totalPage, HttpServletRequest request){
        if(page == null)
            page = 0;
        if (page == -1)
            page++;
        if (page == totalPage)
            page--;
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(page, 10, sort);
        Page<Article> articleDatas = articleRepository.findAllByAuthor(
                request.getSession().getAttribute("loginUser").toString(), pageable);
        List<Article> articles = articleDatas.getContent();
        model.addAttribute("articles", articles);
        model.addAttribute("TotalPages", articleDatas.getTotalPages());
        model.addAttribute("currentPage", page);

        return "management/allArticle";
    }

    //实现文章的增删查改

    //首页点击文章标题显示文章详细内容
    @GetMapping("/arti")
    public String viewArticle(@RequestParam("id") Integer id, Model model){
        Article article = articleRepository.getOne(id);
        model.addAttribute("article", article);
        return "management/viewArticle";
    }

    //添加文章
    @PostMapping("/article")
    public String addArticle(Article article, HttpServletRequest request) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        // new Date()为获取当前系统时间
        article.setPublishDate(df.format(new Date()));
        article.setAuthor(request.getSession().getAttribute("loginUser").toString());
        articleRepository.save(article);
        return "redirect:/allArti";
    }

    //修改文章
    @PutMapping("/article")
    public String editArticle(Article article, HttpServletRequest request){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        // new Date()为获取当前系统时间
        article.setPublishDate(df.format(new Date()));
        article.setAuthor(request.getSession().getAttribute("loginUser").toString());
        articleRepository.save(article);
        return "redirect:/allArti";
    }

    //删除文章
    @DeleteMapping("/article/{id}")
    public String deleteArticle(@PathVariable("id") Integer id){
        articleRepository.deleteById(id);
        return "redirect:/allArti";
    }

    //首页显示所有文章
    @GetMapping({"/","index","index.html"})
    public String showAllArti(Model model, Integer page, Integer totalPage){
        if(page == null)
            page = 0;
        if (page == -1)
            page++;
        if (page == totalPage)
            page--;
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(page, 10, sort);
        Page<Article> articleDatas = articleRepository.findAll(pageable);
        List<Article> articles = articleDatas.getContent();
        model.addAttribute("articles", articles);
        model.addAttribute("TotalPages", articleDatas.getTotalPages());
        model.addAttribute("currentPage", page);

        return "index";
    }

    //公共侧边栏中添加文章的跳转
    @GetMapping("/newArti")
    public String toNewArti(){
        return "management/newArticle";
    }

    //修改文章跳转
    @GetMapping("/article/{id}")
    public String toEditArti(@PathVariable("id") Integer id, Model model){
        Article article = articleRepository.getOne(id);
        model.addAttribute("article", article);
        return "management/editArticle";
    }
}
