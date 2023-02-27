package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;

import java.math.BigDecimal;
import java.util.List;

public interface TransferDao {
    List<Transfer> getSentTransfers (int accountId);
    List<Transfer> getReceivedTransfers (int accountId);
    Transfer getTransferDetails (int transferId);
    int sendTransfer (Transfer transfer);
//    Transfer updateBalance(int id, BigDecimal balance);
//
//    void updateAccount(int id, BigDecimal balance);
}
