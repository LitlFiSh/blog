package com.myblog.demo.controller;

import com.myblog.demo.entity.User;
import com.myblog.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class UserController {
    @Autowired
    UserRepository userRepository;

    //实现用户的增删查改
}
