package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;

public interface TransferDao {
    List<Transfer> getSentTransfers (int accountId);
    List<Transfer> getReceivedTransfers (int accountId);
    Transfer getTransferDetails (int transferId);
    int sendTransfer (Transfer transfer);

    //what Darsea is currently working on.. I am moving this to account
//    int updateBalance(Transfer transfer) throws AccountNotFoundException;

}
