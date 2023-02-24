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
    public Account getAccount(int id) {
        Account account = null;
        String sql = "Select account_id, balance, account.user_id from account join tenmo_user ON tenmo_user.user_id = account.user_id where tenmo_user.user_id = ?";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql,id);
        while(result.next()){
            account = mapRowToAccount(result);
        }
        return account;
    }

    private Account mapRowToAccount(SqlRowSet rs) {return new Account(rs.getInt("account_id"), rs.getInt("user_id"), rs.getBigDecimal("balance"));}
}
