package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class JdbcAccountDao implements AccountDao {

    private final JdbcTemplate jdbcTemplate;


    public JdbcAccountDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Account getAccount(int id) {
        Account account = null;
        String sql = "Select account_id, balance, account.user_id from account join tenmo_user ON tenmo_user.user_id = account.user_id where tenmo_user.user_id = ?";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql,id);
        while(result.next()){
            account = mapRowToAccount(result);
        }
        return account;
    }

    @Override //method to add money to balance
    public BigDecimal addMoneyToBalance(int id, BigDecimal balance) {
        Account account = getAccount(id);
        BigDecimal updatedBalance = account.getBalance().add(addMoneyToBalance(id, balance));
        String sql = "UPDATE account SET balance = balance + ? WHERE user_id = ?;";
        jdbcTemplate.update(sql, balance, id);
        return updatedBalance;
    }

    @Override //method to subtract money from balance
    public BigDecimal subtractMoneyFromBalance(int id, BigDecimal balance) {
        Account account = getAccount(id);
        BigDecimal updatedBalance = account.getBalance().subtract(subtractMoneyFromBalance(id, balance));
        String sql = "UPDATE account SET balance = balance - ? WHERE user_id = ?;";
        jdbcTemplate.update(sql, balance, id);
        return updatedBalance;
    }

//    @Override
//    public void updateAccount(int id, BigDecimal balance) {
//        String sql = "UPDATE account SET balance WHERE account_id = ? AND transfer_status = ;";
//        jdbcTemplate.update(sql, balance, id);
//    }
// I feel like this one above is needed for updating the toAccount and fromAccount when a transfer
// is successful? If so it will need an if statement to ensure the transfer_status is complete/approved
    private Account mapRowToAccount(SqlRowSet rs) {return new Account(rs.getInt("account_id"), rs.getInt("user_id"), rs.getBigDecimal("balance"));}
}
