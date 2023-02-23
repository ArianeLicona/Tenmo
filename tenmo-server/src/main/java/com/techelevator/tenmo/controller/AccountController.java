package com.techelevator.tenmo.controller;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@PreAuthorize("isAuthenticated()")
@RestController
@RequestMapping(path = "/account")
public class AccountController {




    @GetMapping(path = "/userAccounts")
    public List<Account> getAccounts(@Valid @PathVariable int id){
        return
    }


}
