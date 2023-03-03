package com.techelevator.tenmo.controller;


import com.techelevator.tenmo.dao.JdbcAccountDao;
import com.techelevator.tenmo.model.Account;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import javax.validation.Valid;
import java.util.List;

@PreAuthorize("isAuthenticated()")
@RestController
@RequestMapping(path = "/account")
public class AccountController {

    JdbcAccountDao dao;

    public AccountController(JdbcAccountDao dao) {
        this.dao = dao;
    }

    @GetMapping(path = "/user/{id}")
    public Account getAccount(@Valid @PathVariable int id){
        return dao.getAccount(id);
    }

    @GetMapping(path = "/users")
    public List<Account> getAccounts(){return dao.allAccounts();}



}
