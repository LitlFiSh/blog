package com.myblog.demo.repository;

import com.myblog.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    //登录时用于检测用户是否存在
    User findByUsername(String username);
}
