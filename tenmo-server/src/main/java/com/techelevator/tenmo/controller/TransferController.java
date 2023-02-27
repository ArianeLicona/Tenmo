package com.techelevator.tenmo.controller;


import com.techelevator.tenmo.dao.JdbcTransferDao;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.AccountNotFoundException;
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
    List<Transfer> getSentTransfers(int accountId) {
        return dao.getSentTransfers(accountId);
    }
    @GetMapping(path = "/received/{id}") //get all received transfers by accountId
    List<Transfer> getReceivedTransfers(int accountId) {
        return dao.getReceivedTransfers(accountId);
    }
    @GetMapping(path = "/details/{id}") //get all details of transfer by transferId
    Transfer getTransferDetails (int transferId) {
        return dao.getTransferDetails(transferId);
    }
    @ResponseStatus(HttpStatus.CREATED) // send a transfer
    @PostMapping(path = "/send")
    int sendTransfer(@RequestBody Transfer transfer) throws AccountNotFoundException {
        return dao.sendTransfer(transfer);
        /*need to implement a way to update accountTo and accountFrom which will need the following things
        to do so: addToBalance in Account, subtractFromBalance in Account, updateAccount?
         */
    }
}
/*
    List<Transfer> getSentTransfers (int accountId);
    List<Transfer> getReceivedTransfers (int accountId);
    Transfer getTransferDetails (int transferId);
    int sendTransfer (Transfer transfer);

Transfer Controller
Responds to GET Listing past transfers. (complete)
Responds to GET Listing pending transfers. (complete)
POST for a new transfer request (complete)
Allows POST/PUT requests to update a current transfer to change state of PENDING to COMPLETED in
the transfers table. (I'm not sure to how navigate this just yet, we may need more classes in the Dao?
*/
