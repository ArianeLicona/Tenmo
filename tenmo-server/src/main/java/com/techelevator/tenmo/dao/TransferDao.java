package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;

import java.util.List;

public interface TransferDao {
    List<Transfer> getSentTransfers (int accountId);
    List<Transfer> getReceivedTransfers (int accountId);
    Transfer getTransferDetails (int transferId);
    int sendTransfer (Transfer transfer);
}
