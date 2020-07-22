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
        if(page == null)   //第一次执行时page没有初值，所以将初值设为0
            page = 0;
        if (page == -1)   //当page为第一页时点击上一页会传回page=-1，所以将其设回第一页
            page++;
        if (page == totalPage)   //同上，将page设置回最后一页
            page--;
        Sort sort = Sort.by(Sort.Direction.DESC, "id");   //通过id降序排序
        Pageable pageable = PageRequest.of(page, 10, sort);   //page：当前页、10：每一页显示的数量、sort：排序方法
        Page<Article> articleDatas = articleRepository.findAllByAuthor(
                request.getSession().getAttribute("loginUser").toString(), pageable);
        List<Article> articles = articleDatas.getContent();   //当前页面中应显示的文章
        model.addAttribute("articles", articles);
        model.addAttribute("TotalPages", articleDatas.getTotalPages());
        model.addAttribute("currentPage", page);

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
    public String searchArticle(@RequestParam("str") String str, Model model, Integer page,
                                Integer totalPage, HttpServletRequest request){
        String user = request.getSession().getAttribute("loginUser").toString();
        if(page == null)
            page = 0;
        if (page == -1)
            page++;
        if (page == totalPage)
            page--;
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(page, 10, sort);
        //传入的参数中 user表示作者、"%"+str+"%"表示模糊搜索含str内容的文章标题
        Page<Article> articleDatas = articleRepository.findAllByAuthorAndArticleContentIsLike(user, "%"+str+"%", pageable);
        List<Article> articles = articleDatas.getContent();
        model.addAttribute("articles", articles);
        model.addAttribute("TotalPages", articleDatas.getTotalPages());
        model.addAttribute("currentPage", page);
        model.addAttribute("searchBox", str);   //将搜索的内容传回到页面并显示在搜索框中
        return "ArticleManage/allArticle";
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
    //首页文章查找
    @GetMapping("/search")
    public String searchResult(Model model, @RequestParam("str") String str,
                               Integer page, Integer totalPage, HttpServletRequest request){
        if(page == null)
            page = 0;
        if (page == -1)
            page++;
        if (page == totalPage)
            page--;
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(page, 10, sort);
        Page<Article> articleDatas = articleRepository.findAllByArticleTitleLike("%"+str+"%", pageable);
        List<Article> articles = articleDatas.getContent();
        model.addAttribute("articles", articles);
        model.addAttribute("TotalPages", articleDatas.getTotalPages());
        model.addAttribute("currentPage", page);
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
