package com.techelevator.dao;
import com.techelevator.tenmo.dao.JdbcTransferDao;
import com.techelevator.tenmo.dao.JdbcUserDao;
import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.Transfer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
public class JdbcTransferDaoTests extends BaseDaoTests {

    protected static final Transfer TRANSFER_1 = new Transfer(3001, "Send", "Approved", 2002, 2001, new BigDecimal("15.00"));
    protected static final Transfer TRANSFER_2 = new Transfer(3002, "Send", "Approved", 2001, 2002, new BigDecimal("12.00"));
    private static final Transfer TRANSFER_3 = new Transfer(3003, "Send", "Approved", 2002, 2001, new BigDecimal("5.00"));
    private JdbcTransferDao sut;

    @Before
    public void setup() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        sut = new JdbcTransferDao(jdbcTemplate);
    }

    @Test
    public void updateTransfer_updates_correctly() {
        );
    }
}

//    @Test(expected = IllegalArgumentException.class)
//    public void getSentTransfers_with_invalid_transfer_id_throws_exception() {
//        sut.getSentTransfers(2999);
//    }
//
//
//    @Test(expected = IllegalArgumentException.class)
//    public void getTransferDetails_returns_details_matching_id() {
//        sut.getTransferDetails(2999);
//    }
//
//    @Test(expected=IllegalArgumentException.class)
//    public void updateTransfer_cannot_be_null(){
//        sut.updateTransfer(TRANSFER_1);
//    }
//
//}

