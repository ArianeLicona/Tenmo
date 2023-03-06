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
    protected static final Account ACCOUNT_2 = new Account(2002, 1002, new BigDecimal(100.00));
    protected static final Account ACCOUNT_3 = new Account(2003, 1003, new BigDecimal(0));

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

    @Test
    public void allAccounts_returns_all_accounts(){
        List<Account> accounts = new ArrayList<>();
        accounts = testDao.allAccounts();
        Assert.assertEquals(accounts.get(1).getAccountId(), ACCOUNT_1.getAccountId());
        Assert.assertEquals(accounts.get(2).getAccountId(), ACCOUNT_2.getAccountId());
        Assert.assertEquals(accounts.get(3).getAccountId(), ACCOUNT_3.getAccountId());
    }

    @Test(expected=IllegalArgumentException.class)
    public void updateBalance_throws_exception_for_null_input(){
        testDao.updateBalance(null, null);
    }

    @Test()
    public void subtractBalance_subtracts_appropriate_amount(){
        testDao.subtractBalance(ACCOUNT_1.getAccountId(), new BigDecimal(50.00));
        Assert.assertEquals(ACCOUNT_1.getBalance(), new BigDecimal(50.00));
    }
    }
