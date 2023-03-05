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

    @GetMapping(path = "/sent/{id}") //get all sent transfers by accountId
    List<Transfer> getSentTransfers(@PathVariable int id) {
        return dao.getSentTransfers(id);
    }

    @GetMapping(path = "/received/{id}") //get all received transfers by accountId
    List<Transfer> getReceivedTransfers(@PathVariable int id) {
        return dao.getReceivedTransfers(id);
    }

    @GetMapping(path = "/details/{id}") //get all details of transfer by transferId
    Transfer getTransferDetails (@PathVariable int id) {
        return dao.getTransferDetails(id);
    }

    @ResponseStatus(HttpStatus.CREATED) // send a transfer
    @PostMapping(path = "/send")
    void sendTransfer(@RequestBody Transfer transfer) throws AccountNotFoundException {
        dao.sendTransfer(transfer);
        /*need to implement a way to update accountTo and accountFrom which will need the following things
        to do so: addToBalance in Account, subtractFromBalance in Account, updateAccount?
         */
    }
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PutMapping(path = "/update")
    void updateTransfer(@RequestBody Transfer transfer){
        dao.sendTransfer(transfer);
    }

//    @GetMapping(path = "/transfer_status/filter")
//    Transfer getTransferStatusDesc(@PathVariable String tranferStatusDesc) {
//        return dao.getTransferStatusDesc(tranferStatusDesc);
//    }


}
