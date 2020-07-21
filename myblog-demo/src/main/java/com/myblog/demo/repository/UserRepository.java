package com.myblog.demo.repository;

import com.myblog.demo.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    //登录时用于检测用户是否存在
    User findByUsername(String username);
    //用户管理页面显示所有用户，当前登录用户除外
    Page<User> findAllByUsernameNot(String username, Pageable pageable);
}
