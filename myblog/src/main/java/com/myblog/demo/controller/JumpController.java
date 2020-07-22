package com.myblog.demo.controller;

import com.myblog.demo.entity.User;
import com.myblog.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
public class JumpController {

    @Autowired
    UserRepository userRepository;

    //登录检测和跳转
    @PostMapping("/user/login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        Map<String, Object> map, HttpSession session) {
        User user = userRepository.findByUsername(username);
        if (user.getPassword().equals(password)) {
            session.setAttribute("loginUser", username);
            if(user.getUsername().equals("superAdmin"))
                session.setAttribute("admin", "true");  //判断是否为超级管理员
            return "redirect:/allArti";   //登录后交由ArticleController处理请求
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
