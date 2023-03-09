package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.exceptions.EmptyRowSetException;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class JdbcTransferDao implements TransferDao {
    private final JdbcTemplate jdbcTemplate;

    private final String SELECT = "SELECT transfer.transfer_id, transfer_type.transfer_type_desc, transfer_status.transfer_status_desc, transfer.account_from, transfer.account_to, transfer.amount, tenmo_user.username FROM transfer ";
    private final String JOIN = "JOIN account ON account.account_id = transfer.account_from JOIN tenmo_user ON tenmo_user.user_id = account.user_id JOIN transfer_status ON transfer_status.transfer_status_id = transfer.transfer_status_id JOIN transfer_type ON transfer_type.transfer_type_id = transfer.transfer_type_id ";
    private final String INSERT_TRANSFER = "INSERT INTO transfer (transfer_type_id, transfer_status_id, account_from, account_to, amount) VALUES ((select transfer_type_id from transfer_type where transfer_type_desc = ?), (select transfer_status_id from transfer_status where transfer_status_desc = ?), ?, ?, ?)";


    public JdbcTransferDao(JdbcTemplate jdbcTemplate) {this.jdbcTemplate = jdbcTemplate;}


    @Override
    public List<Transfer> getTransferById(Account account) {
        List<Transfer> transfers = new ArrayList<>();
        if (account.getAccountId() <= 1999) {
            throw new IllegalArgumentException();
        }
        String sql = SELECT + JOIN;
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql);
        if (result != null) {
            while (result.next()) {
                transfers.add(mapRowToTransfer(result));
            }
        } else {
            throw new EmptyRowSetException();
        }
        return transfers;
    }


    //method to get sent transfers
    @Override
    public List<Transfer> getSentTransfers (int transferId){
        List<Transfer> transfers = new ArrayList<>();
//        if (transferId <= 2999){
//            throw new IllegalArgumentException();
//        }
        String sql = SELECT + JOIN + "WHERE transfer.account_from = ?";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, transferId);
//        if (result != null){
        while (result.next()) {
            transfers.add(mapRowToTransfer(result));
            }
//        } else {
//            throw new EmptyRowSetException();
//        }
        return transfers;
    }



    //method to get received transfers
    @Override
    public List<Transfer> getReceivedTransfers (int accountId){
        List<Transfer> transfers = new ArrayList<>();
        String sql = SELECT + JOIN + "WHERE account_to = ?";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, accountId);
        while (result.next()){
            transfers.add(mapRowToTransfer(result));
        }
        return transfers;
    }


    //method to get transfer details
    @Override
    public Transfer getTransferDetails (int transferId) {
//        if (transferId <= 2999) {
//            throw new IllegalArgumentException();
//        }
        Transfer transfer = null;
        String sql = SELECT + JOIN + "WHERE transfer_id = ?";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, transferId);
//        if (result != null) {
            while (result.next()) {
                transfer = mapRowToTransfer(result);
            }
//        } else {
//            throw new EmptyRowSetException();
//        }
            return transfer;
        }


    //method to update transfer status
    @Override
    public void updateTransfer(Transfer transfer) {
        JdbcAccountDao accountDao = new JdbcAccountDao(jdbcTemplate);

        if (transfer.getTransferStatus() == null ) {
            throw new IllegalArgumentException();
        }
        String sql = "UPDATE transfer SET transfer_status_id = ? WHERE transfer_id = ?";
        jdbcTemplate.update(sql, transfer.getTransferId());
        accountDao.addBalance(transfer.getAccountTo(), transfer.getAmount());
        accountDao.subtractBalance(transfer.getAccountFrom(), transfer.getAmount());
//        try {
            jdbcTemplate.update(sql, transfer);
//        } catch (EmptyResultDataAccessException e){
//            e.getMessage();
//        }
    }


    //method to send a transfer
    @Override
    public int sendTransfer (Transfer transfer) {
        JdbcAccountDao accountDao = new JdbcAccountDao(jdbcTemplate);
        int createTransfer = 0;

//        try {
            if (transfer.getTransferStatus().equals("Approved")) {

                accountDao.addBalance(transfer.getAccountTo(), transfer.getAmount());
                accountDao.subtractBalance(transfer.getAccountFrom(), transfer.getAmount());
            }
            createTransfer = jdbcTemplate.update(INSERT_TRANSFER,
                    transfer.getTransferType(),
                    transfer.getTransferStatus(),
                    transfer.getAccountFrom(),
                    transfer.getAccountTo(),
                    transfer.getAmount());
//        } catch (NullPointerException e) {
//            System.out.println("Must not be null");
//        }
        return createTransfer;
    }



    //method to map elements to construct transfer object
    public Transfer mapRowToTransfer (SqlRowSet result){
        Transfer transfer = new Transfer(
                result.getInt("transfer_id"),
                result.getString("transfer_type_desc"),
                result.getString("transfer_status_desc"),
                result.getInt("account_from"),
                result.getInt("account_to"),
                result.getBigDecimal("amount"),
                result.getString("username"));
        return transfer;
    }
}
