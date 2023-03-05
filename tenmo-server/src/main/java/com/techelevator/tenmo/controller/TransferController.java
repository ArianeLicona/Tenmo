package com.techelevator.tenmo.controller;


import com.techelevator.tenmo.dao.JdbcTransferDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.AccountNotFoundException;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@PreAuthorize("isAuthenticated()")
@RequestMapping(path = "/transfer")
public class TransferController {
    JdbcTransferDao dao;

    public TransferController(JdbcTransferDao dao) {
        this.dao = dao;
    }


    //get all sent transfers by accountId
    @GetMapping(path = "/sent/{id}")
    List<Transfer> getSentTransfers(@PathVariable int id) {
        return dao.getSentTransfers(id);
    }


    //get all received transfers by accountId
    @GetMapping(path = "/received/{id}")
    List<Transfer> getReceivedTransfers(@PathVariable int id) {
        return dao.getReceivedTransfers(id);
    }


    //get all details of transfer by transferId
    @GetMapping(path = "/details/{id}")
    Transfer getTransferDetails (@PathVariable int id) {
        return dao.getTransferDetails(id);
    }


    // send a transfer
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "/send")
    void sendTransfer(@RequestBody Transfer transfer) throws AccountNotFoundException {
        dao.sendTransfer(transfer);
    }

    //updates the transfer
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PutMapping(path = "/update")
    void updateTransfer(@RequestBody Transfer transfer){
        dao.updateTransfer(transfer);
    }




}
