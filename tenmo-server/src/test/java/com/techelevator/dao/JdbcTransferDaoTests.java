package com.techelevator.dao;
import com.techelevator.tenmo.dao.JdbcTransferDao;
import com.techelevator.tenmo.dao.JdbcUserDao;
import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
    private static final Transfer TRANSFER_3 = new Transfer(3003, "Send", "Approved", 2002, 2001, new BigDecimal("5.00"));
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
//
////    @Test
////    public void updateTransfer_updates_correctly() {
//
////    }
//
//    @Test
//    public void getTransferDetails_returns_transfer() {
//        Transfer actualTransfer = sut.getTransferDetails(TRANSFER_1.getTransferId());
//
//        Assert.assertEquals(TRANSFER_1, actualTransfer);
//    }
//
////    @Test
////    public void sendTransfer_creates_a_transfer() {
////        Transfer newTransfer = new Transfer(-1,"Send", "Approved", 2003, 2001, new BigDecimal("15.00"));
////
////        boolean transferWasCreated = sut.sendTransfer();
////
////        Assert.assertTrue(transferWasCreated);
////
////        Transfer actualTransfer = sut.getTransferDetails();
////        newTransfer.setId(actualTransfer.getTransferId());
////
////
////        Assert.assertEquals(newUser, actualUser);
////    }
//
//
//
//    @Test
//    public void getAllTransfers_returns_all_users() {
//        List<Transfer> transfers = sut.getAllTransfers();
//
//        Assert.assertNotNull(transfers);
//        Assert.assertEquals(3, transfers.size());
//        Assert.assertEquals(TRANSFER_1, transfers.get(0));
//        Assert.assertEquals(TRANSFER_2, transfers.get(1));
//        Assert.assertEquals(TRANSFER_3, transfers.get(2));
//    }
//
//    @Test(expected = IllegalArgumentException.class)
//    public void getSentTransfers_with_invalid_transfer_id_throws_exception() {
//        sut.getSentTransfers(2999);
//    }
////
////
////    @Test(expected = IllegalArgumentException.class)
////    public void getTransferDetails_returns_details_matching_id() {
////        sut.getTransferDetails(2999);
////    }
////
////    @Test(expected=IllegalArgumentException.class)
////    public void updateTransfer_cannot_be_null(){
////        sut.updateTransfer(TRANSFER_1);
////    }
////
}
//
