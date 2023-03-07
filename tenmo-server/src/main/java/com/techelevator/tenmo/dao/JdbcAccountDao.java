package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.exceptions.EmptyRowSetException;
import com.techelevator.tenmo.model.Account;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcAccountDao implements AccountDao {

    private final JdbcTemplate jdbcTemplate;
    public JdbcAccountDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    //method to get an account
    @Override
    public Account getAccount(int id) {
        if (id <= 999){
            throw new IllegalArgumentException();
        }
        Account account = null;
        String sql = "Select account_id, balance, account.user_id from account join tenmo_user ON tenmo_user.user_id = account.user_id where tenmo_user.user_id = ?";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql,id);
        if (result != null){
            while(result.next()){
                account = mapRowToAccount(result);
            }
        } else {

        }
        return account;
    }


    //method to get a list of all accounts
    @Override
    public List<Account> allAccounts() {
        List<Account> accounts = new ArrayList<>();
        String sql = "SELECT account_id, tenmo_user.user_id, tenmo_user.username, balance " +
                "FROM tenmo_user JOIN account ON tenmo_user.user_id = account.user_id;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        if (results != null){
            while (results.next()) {
                accounts.add(mapRowToAccount(results));
            }
        } else {
            throw new EmptyRowSetException();
        }
        return accounts;
    }


    //method to update a transfer
    @Override
    public void updateBalance(String username, BigDecimal amount) {
        if (username == null || amount == null){
            throw new IllegalArgumentException();
        }
        String sql = "UPDATE account SET balance WHERE account_id = ?;";
        try {
            jdbcTemplate.update(sql, username, amount);
        } catch (EmptyResultDataAccessException e){
            e.getMessage();
        }
    }


    //method to map result to construct account object
    private Account mapRowToAccount(SqlRowSet rs) {
        return new Account(
                    rs.getInt("account_id"),
                    rs.getInt("user_id"),
                    rs.getBigDecimal("balance"));
    }



    //method to subtract money from an account
    public void subtractBalance (int accountId, BigDecimal amount){
        if (accountId <= 1999 || amount == null){
            throw new IllegalArgumentException();
        }
        String sql = "UPDATE account SET balance = (SELECT balance FROM account WHERE account_id = ?) - ? WHERE account_id = ?;";
        try {
            jdbcTemplate.update(sql, accountId, amount, accountId);
        } catch (EmptyResultDataAccessException e){
            e.getMessage();
        }
    }


    //method to add money to an account
    public void addBalance (int accountId, BigDecimal amount){
        if (accountId <= 1999 || amount == null){
            throw new IllegalArgumentException();
        }
        String sql = "UPDATE account SET balance = (SELECT balance FROM account WHERE account_id = ?) + ? WHERE account_id = ?;";
        try {
            jdbcTemplate.update(sql, accountId, amount, accountId);
        } catch (EmptyResultDataAccessException e){
            e.getMessage();
        }
    }
}
