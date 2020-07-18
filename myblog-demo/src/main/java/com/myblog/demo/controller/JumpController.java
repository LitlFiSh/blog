package com.myblog.demo.controller;

import com.myblog.demo.entity.Article;
import com.myblog.demo.entity.User;
import com.myblog.demo.repository.ArticleRepository;
import com.myblog.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
public class JumpController {

    @Autowired
    UserRepository userRepository;
    @Autowired
    ArticleRepository articleRepository;

    //首页跳转
    @GetMapping({"/","/index.html","/index"})
    public String getAllArticle(Model model){
        //List<Article> articles = articleRepository.findAll();
        //model.addAttribute("articles", articles);
        //System.out.println("执行了一次跳转");
        return "index";
    }

    //点击文章管理时的跳转
    @GetMapping("/artis")
    public String AuthorArticle(Model model, HttpServletRequest request){
        //获取当前登录的用户名
        Object user = request.getSession().getAttribute("loginUser");
        //根据用户名获取文章
        List<Article> articles = articleRepository.findAllByAuthor(user.toString());
        model.addAttribute("articles", articles);
        return "management/allArticle";
    }

    //点击添加文章的跳转
    @GetMapping("/arti")
    public String toAddArticle(){

        return "management/newArticle";
    }

    //登录检测和跳转
    @PostMapping("/user/login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        Map<String, Object> map, HttpSession session) {
        User user = userRepository.findByUsername(username);
        if (user.getPassword().equals(password)) {
            session.setAttribute("loginUser", username);
            return "redirect:/admin.html";
        } else {
            map.put("msg", "用户名或密码错误");
            return "login";
        }
    }

    //用户注销
    @RequestMapping("/user/logout")
    public String logout(HttpServletRequest request){
        request.getSession().removeAttribute("loginUser");
        return "redirect:/index.html";
    }
}
