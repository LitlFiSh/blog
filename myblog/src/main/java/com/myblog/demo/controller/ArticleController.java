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
    public String allArticle(Model model, Integer page, HttpServletRequest request){
        if(page == null)
            page = 1;
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(page-1, 10, sort);
        Page<Article> articleDatas = articleRepository.findAllByAuthor(
                request.getSession().getAttribute("loginUser").toString(), pageable);
        model.addAttribute("articles", articleDatas);
        return "ArticleManage/allArticle";
    }

    //首页点击文章标题显示文章详细内容
    @GetMapping("/arti")
    public String viewArticle(@RequestParam("id") Integer id, Model model){
        Article article = articleRepository.getOne(id);
        model.addAttribute("article", article);   //传递文章详细内容
        return "ArticleManage/viewArticle";
    }

    //添加文章
    @PostMapping("/article")
    public String addArticle(Article article, HttpServletRequest request) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        article.setPublishDate(df.format(new Date()));   // new Date()为获取当前系统时间
        article.setUpdateDate(df.format(new Date()));
        article.setAuthor(request.getSession().getAttribute("loginUser").toString());   //当前登录的用户即为文章作者
        articleRepository.save(article);   //保存到数据库中、其中article.id为空，即文章在数据库中不存在，进行添加操作
        return "redirect:/allArti";
    }

    //修改文章
    @PutMapping("/article")
    public String editArticle(Article article, HttpServletRequest request){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        // new Date()为获取当前系统时间
        article.setUpdateDate(df.format(new Date()));
        articleRepository.save(article);   //更新数据库内容，id和author已经从页面传回，进行更新操作
        return "redirect:/allArti";
    }

    //删除文章
    @DeleteMapping("/article/{id}")
    public String deleteArticle(@PathVariable("id") Integer id){
        articleRepository.deleteById(id);   //通过文章id进行删除操作
        return "redirect:/allArti";
    }

    //管理页面查找文章
    @GetMapping("/article/search")
    public String searchArticle(@RequestParam("str") String str, Model model,
                                Integer page, HttpServletRequest request){
        String user = request.getSession().getAttribute("loginUser").toString();
        if(page == null)
            page = 1;
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(page-1, 10, sort);
        //传入的参数中 user表示作者、"%"+str+"%"表示模糊搜索含str内容的文章标题
        Page<Article> articleDatas = articleRepository.findAllByAuthorAndArticleTitleLike(user, "%"+str+"%", pageable);
        model.addAttribute("articles", articleDatas);
        model.addAttribute("searchBox", str);
        return "ArticleManage/allArticle";
    }

    //首页显示所有文章
    @GetMapping({"/","/index","/index.html"})
    public String showAllArti(Model model, Integer page){
        if(page == null)
            page = 1;
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(page-1, 10, sort);
        Page<Article> articleDatas = articleRepository.findAll(pageable);
        model.addAttribute("articles", articleDatas);
        return "index";
    }

    //首页文章查找
    @GetMapping("/search")
    public String searchResult(Model model, @RequestParam("str") String str,
                               Integer page){
        if(page == null)
            page = 1;
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(page-1, 10, sort);
        Page<Article> articleDatas = articleRepository.findAllByArticleTitleLike("%"+str+"%", pageable);
        model.addAttribute("articles", articleDatas);
        model.addAttribute("searchBox", str);
        return "index";
    }

    //公共侧边栏中添加文章的跳转
    @GetMapping("/newArti")
    public String toNewArti(){
        return "ArticleManage/newArticle";
    }

    //修改文章跳转
    @GetMapping("/article/{id}")
    public String toEditArti(@PathVariable("id") Integer id, Model model){
        Article article = articleRepository.getOne(id);   //通过文章id获取文章详细内容
        model.addAttribute("article", article);   //将文章内容传给编辑文章页面进行回显
        return "ArticleManage/editArticle";
    }
}
