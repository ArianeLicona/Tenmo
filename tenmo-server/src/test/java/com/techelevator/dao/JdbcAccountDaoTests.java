package com.techelevator.dao;

import com.techelevator.tenmo.dao.JdbcAccountDao;
import com.techelevator.tenmo.exceptions.EmptyRowSetException;
import com.techelevator.tenmo.model.Account;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class JdbcAccountDaoTests extends BaseDaoTests{
    protected static final Account ACCOUNT_1 = new Account(2001, 1001, new BigDecimal(100.00));

    private JdbcAccountDao testDao;

    @Before
    public void setup(){
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        testDao = new JdbcAccountDao(jdbcTemplate);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getAccount_with_invalid_user_id_throws_exception(){
        testDao.getAccount(999);
    }

    @Test
    public void getAccount_returns_proper_user(){
        Account test = testDao.getAccount(1001);
        Assert.assertEquals(test.getAccountId(), ACCOUNT_1.getAccountId());
    }

    @Test(expected=IllegalArgumentException.class)
    public void updateBalance_throws_exception_for_null_input(){
        testDao.updateBalance(null, null);
    }

    @Test()
    public void subtractBalance_subtracts_appropriate_amount(){
        testDao.subtractBalance(2001, new BigDecimal(50));
        Account test = testDao.getAccount(1001);
        Assert.assertEquals(test.getBalance(), new BigDecimal(50));
    }
    }
