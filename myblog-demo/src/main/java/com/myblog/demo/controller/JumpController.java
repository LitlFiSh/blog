package com.myblog.demo.controller;

import com.myblog.demo.entity.User;
import com.myblog.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
public class JumpController {

    @Autowired
    UserRepository userRepository;

    @PostMapping(value = "/user/login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        Map<String, Object> map, HttpSession session){
        if(username == null || password == null){
            map.put("msg","用户名或密码不能为空");
            return "login";
        }
        else{
            User user = userRepository.findByUsername(username);
            if(user.getPassword().equals(password)) {
                session.setAttribute("loginUser",username);
                return "redirect:/admin.html";
            }
            else{
                map.put("msg", "用户名或密码错误");
                return "login";
            }
        }
    }

}
