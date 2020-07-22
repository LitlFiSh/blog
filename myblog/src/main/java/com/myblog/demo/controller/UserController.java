package com.myblog.demo.controller;

import com.myblog.demo.entity.User;
import com.myblog.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
public class UserController {
    @Autowired
    UserRepository userRepository;
    //实现用户的增删查改

    //用户修改密码
    @PutMapping("/userPwd")
    public String userPassword(@RequestParam("oldPwd") String oldPwd,
                               @RequestParam("newPwd") String newPwd,
                               HttpServletRequest request, Map<String, Object> map){
        String username = request.getSession().getAttribute("loginUser").toString();
        User user = userRepository.findByUsername(username);
        if(oldPwd == user.getPassword()) {
            user.setPassword(newPwd);
            userRepository.save(user);
            return "redirect:/user/logout";   //修改密码成功，注销用户
        }
        else {
            map.put("errorPwd","密码错误");
            return "UserManage/personal_info";   //回到修改密码页面并显示错误
        }
    }

    //管理员添加用户
    @PostMapping("/user")
    public String addUser(User user){
        userRepository.save(user);
        return "redirect:/UserManage/allUser";   //重定向到管理用户页面
    }

    //管理员删除用户
    @DeleteMapping("/user/{id}")
    public String deleteUser(@PathVariable("id") Integer id){
        userRepository.deleteById(id);
        return "redirect:/UserManage/allUser";   //重定向到管理用户界面
    }

    //管理员修改用户
    @PutMapping("/user")
    public String updateUser(User user){
        userRepository.save(user);
        return "redirect:/UserManage/allUser";   //重定向到管理用户界面
    }

    //修改密码页面跳转
    @GetMapping("/editPwd")
    public String editPwd(){
        return "UserManage/personal_info";   //跳转到修改密码页面
    }

    //添加用户的跳转
    @GetMapping("/newUser")
    public String newUser(){
        return "newUser";   //跳转到添加用户页面
    }

    //修改用户的跳转
    @GetMapping("/editUser/{id}")
    public String editUser(@PathVariable("id") Integer id, Model model){
        User user = userRepository.getOne(id);
        model.addAttribute("user", user);
        return "UserManage/editUser";   //跳转到修改页面
    }

    //跳转到管理用户页面
    @GetMapping("/userManage")
    public String allUser(Model model, Integer page, Integer totalPage, HttpServletRequest request){
        if(page == null)
            page = 0;
        if (page == -1)
            page++;
        if (page == totalPage)
            page--;
        String username = request.getSession().getAttribute("loginUser").toString();
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(page, 10, sort);
        Page<User> userDatas = userRepository.findAllByUsernameNot(username, pageable);
        List<User> users = userDatas.getContent();
        model.addAttribute("users", users);
        model.addAttribute("TotalPages", userDatas.getTotalPages());
        model.addAttribute("currentPage", page);

        return "UserMAnage/allUser";   //跳转到管理用户页面
    }
}
