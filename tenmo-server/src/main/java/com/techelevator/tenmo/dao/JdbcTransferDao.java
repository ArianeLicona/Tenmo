package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class JdbcTransferDao implements TransferDao {
    private final JdbcTemplate jdbcTemplate;

    private final String SELECT_DETAIL = "SELECT transfer_id, transfer_type_desc, transfer_status_desc, account_from, account_to, amount ";
    private final String JOIN_DETAIL = "JOIN transfer_type ON transfer_type.transfer_type_id = transfer.transfer_type_id, JOIN transfer_status ON transfer_status.transfer_status_id = transfer.transfer_status_id ";
    private final String SELECT_USER = "SELECT transfer_id, username, amount ";
    private final String JOIN_USER = "JOIN account ON account_id.account = account_id.transfer JOIN tenmo_user ON tenmo_user.user_id = account.user_id ";
//    private final String INSERT_TRANSFER = "INSERT INTO transfer (transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, amount) VALUES (?, ?, ?, ?, ?, ?)";
    // Commented this one out because the one below does the trick, I believe.
    private final String INSERT_TRANSFER = "INSERT INTO transfer (transfer_type_id, transfer_status_id, account_from, account_to, amount) VALUES ((select transfer_type_id from transfer_type where transfer_type_desc = ?), (select transfer_status_id from transfer_status where transfer_status_desc = ?), ?, ?, ?)";
    // subquery for inserting values into multiple tables.

    public static final int PENDING_STATUS_ID = 1;
    public static final int APPROVED_STATUS_ID = 2;
    public static final int REJECTED_STATUS_ID = 3;
    public static final int REQUEST_ID = 1;
    public static final int SEND_ID = 2;

    public JdbcTransferDao(JdbcTemplate jdbcTemplate) {this.jdbcTemplate = jdbcTemplate;}

    @Override
    public List<Transfer> getSentTransfers (int accountId){
        List<Transfer> transfers = new ArrayList<>();
        String sql = SELECT_USER + JOIN_USER + "WHERE account_from = ?";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, accountId);
        while (result.next()){
            transfers.add(mapRowToTransfer(result));
        }
        return transfers;
    }

    @Override
    public List<Transfer> getReceivedTransfers (int accountId){
        List<Transfer> transfers = new ArrayList<>();
        String sql = SELECT_USER + JOIN_USER + "WHERE account_to = ?";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, accountId);
        while (result.next()){
            transfers.add(mapRowToTransfer(result));
        }
        return transfers;
    }

    @Override
    public Transfer getTransferDetails (int transferId){
        String sql = SELECT_DETAIL + JOIN_DETAIL + "WHERE transfer_id = ?";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, transferId);
        if (result.next()){
            return mapRowToTransferDetail(result);

        }
        return null;
    }

    @Override //method to update transfer status
    public void updateTransfer(Transfer transfer) {
        String sql = "UPDATE transfer SET transfer_status_id = ? WHERE transfer_id = ?";
        jdbcTemplate.update(sql, transfer.getTransferId(), transfer.getTransferStatus());
    }


    @Override
    public int sendTransfer (Transfer transfer){
        return jdbcTemplate.update(INSERT_TRANSFER,
                transfer.getTransferType(),
                transfer.getTransferStatus(),
                transfer.getAccountFrom(),
                transfer.getAccountTo(),
                transfer.getAmount());
    }

    public Transfer mapRowToTransfer (SqlRowSet result){
        return new Transfer(
                result.getInt("transfer_id"),
                result.getInt("account_from"),
                result.getInt("account_to"),
                result.getBigDecimal("amount"),
                result.getString("username"));
    }

    public Transfer mapRowToTransferDetail (SqlRowSet result){
        return new Transfer(
                result.getInt("transfer_id"),
                result.getString("transfer_type_desc"),
                result.getString("transfer_status_desc"),
                result.getInt("account_from"),
                result.getInt("account_to"),
                result.getBigDecimal("amount"));
    }
}
