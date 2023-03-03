package com.techelevator.tenmo.controller;


import com.techelevator.tenmo.dao.JdbcUserDao;
import com.techelevator.tenmo.model.User;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@PreAuthorize("isAuthenticated()")
@RequestMapping(path = "/users")
public class UserController {
    JdbcUserDao dao;

    public UserController (JdbcUserDao dao) {this.dao = dao;}

    @GetMapping
    List<User> getAllUsers () {
        return dao.findAll();
    }
}
