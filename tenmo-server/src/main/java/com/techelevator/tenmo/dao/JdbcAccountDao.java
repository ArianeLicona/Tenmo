package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.security.auth.login.AccountNotFoundException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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
// method to list all accounts.. It needs the filter, but that's done on the client side... right?
    @Override
    public List<Account> allAccounts() {
        List<Account> accounts = new ArrayList<>();
        String sql = "SELECT account_id, tenmo_user.user_id, tenmo_user.username, balance " +
                "FROM tenmo_user JOIN account ON tenmo_user.user_id = account.user_id;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while (results.next()) {
            accounts.add(mapRowToAccount(results));
        }
        return accounts;
    }
//I'm just not really sure these two methods are necessary, but I am also not sure
//    @Override //method to add money to balance
//    public BigDecimal addMoneyToBalance(int id, BigDecimal balance) {
//        Account account = getAccount(id);
//        BigDecimal updatedBalance = account.getBalance().add(addMoneyToBalance(id, balance));
//        String sql = "UPDATE account SET balance = balance + ? WHERE user_id = ?;";
//        jdbcTemplate.update(sql, balance, id);
//        return updatedBalance;
//    }
//
//    @Override //method to subtract money from balance
//    public BigDecimal subtractMoneyFromBalance(int id, BigDecimal balance) {
//        Account account = getAccount(id);
//        BigDecimal updatedBalance = account.getBalance().subtract(subtractMoneyFromBalance(id, balance));
//        String sql = "UPDATE account SET balance = balance - ? WHERE user_id = ?;";
//        jdbcTemplate.update(sql, balance, id);
//        return updatedBalance;
//    }

    @Override
    public int updateBalance(Account account) {
        String sql = "UPDATE account SET balance WHERE account_id = ?;";
        return jdbcTemplate.update(sql, account.getBalance(), account.getAccountId());
        }

    private Account mapRowToAccount(SqlRowSet rs) {return new Account(rs.getInt("account_id"), rs.getInt("user_id"), rs.getBigDecimal("balance"));}
}
