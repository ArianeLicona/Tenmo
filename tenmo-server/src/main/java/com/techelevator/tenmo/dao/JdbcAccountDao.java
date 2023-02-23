package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcAccountDao implements AccountDao {

    private final JdbcTemplate jdbcTemplate;


    public JdbcAccountDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Account> getAccounts(int id) {
        List<Account> accounts = new ArrayList<>();
        String sql = "select tenmo_user.user_id, username, balance , account.account_id from tenmo_user join account ON account.user_id = tenmo_user.user_id where account.user_id = ?";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, id);
        while(result.next()){
            accounts.add(mapRowToAccount(result));
        }
        return accounts;
    }

    private Account mapRowToAccount(SqlRowSet rs) {
        Account account = new Account(rs.getInt("account_id"), rs.getInt("user_id"), rs.getBigDecimal("balance"));
        return account;
    }
}
