package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;

public interface TransferDao {

    List<Transfer> getTransferById(Account account);

    List<Transfer> getSentTransfers (int accountId);

    List<Transfer> getReceivedTransfers (int accountId);

    Transfer getTransferDetails (int transferId);

    void updateTransfer(Transfer transfer);

    int sendTransfer (Transfer transfer);


}
