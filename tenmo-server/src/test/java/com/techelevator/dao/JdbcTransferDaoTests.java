package com.techelevator.dao;
import com.techelevator.tenmo.dao.JdbcTransferDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TestingDatabaseConfig.class)
public class JdbcTransferDaoTests extends BaseDaoTests {

    protected static final Transfer TRANSFER_1 = new Transfer(3001, "Send", "Approved", 2002, 2001, new BigDecimal("15.00"));
    protected static final Transfer TRANSFER_2 = new Transfer(3002, "Send", "Approved", 2001, 2002, new BigDecimal("12.00"));
    private static final Transfer TRANSFER_3 = new Transfer(3003, "Send", null, 2002, 2001, new BigDecimal("5.00"));

    private JdbcTransferDao sut;

    @Before
    public void setup() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        sut = new JdbcTransferDao(jdbcTemplate);
    }

    @Test
    public void getSentTransfers_returns_sent_transfers (){
        List<Transfer> transfers = sut.getSentTransfers(TRANSFER_1.getTransferId());
        Assert.assertNotNull(transfers);
        Assert.assertEquals(transfers, transfers);
    }
    @Test
    public void getSentTransfers_has_valid_transfer_id() {
        List<Transfer> transfers = sut.getSentTransfers(TRANSFER_1.getTransferId());
        Assert.assertEquals(transfers, transfers);
        }


    @Test(expected = IllegalArgumentException.class)
    public void getTransferById_with_invalid_account_id_throws_exception() {
        sut.getTransferById(new Account(1999, 1001, new BigDecimal("15.00")));
    }

    @Test(expected=IllegalArgumentException.class)
    public void updateTransfer_transfer_status_cannot_be_null(){
        sut.updateTransfer(TRANSFER_3);
    }

    @Test(expected = NullPointerException.class)
    public void sendTransfer_transfer_status_cannot_be_null() {
        sut.sendTransfer(TRANSFER_3);
    }

}

