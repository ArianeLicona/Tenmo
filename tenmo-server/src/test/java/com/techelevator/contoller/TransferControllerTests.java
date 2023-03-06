package com.techelevator.contoller;


import com.techelevator.tenmo.controller.TransferController;
import com.techelevator.tenmo.dao.JdbcAccountDao;
import com.techelevator.tenmo.dao.JdbcTransferDao;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class TransferControllerTests {

    @InjectMocks
    private TransferController transferController;

    @Mock
    private JdbcTransferDao transferDao;

    List<Transfer> LISTFROMACCOUNT10 = new ArrayList<>();

    Transfer AppTRANSFER = new Transfer(1,"Send","Approved",10,11, BigDecimal.valueOf(10.00),"test");


    private MockMvc mockMvc;

    @Before
    public void setup() {

        LISTFROMACCOUNT10.add(AppTRANSFER);
        LISTFROMACCOUNT10.add(new Transfer(2,"Send","Approved",10,11, BigDecimal.valueOf(150.00),"test"));


        mockMvc = MockMvcBuilders.standaloneSetup(transferController)
                .build();
    }

    @Test
    public void getTransferByIdTest() throws Exception {

        when(transferDao.getTransferDetails(1)).thenReturn(AppTRANSFER);

        mockMvc.perform(get("/transfer/details/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.transferId", is(1)))
                .andExpect(jsonPath("$.transferType", is("Send")))
                .andExpect(jsonPath("$.accountFrom", is(10)))
                .andExpect(jsonPath("$.accountTo", is(11)))
                .andExpect(jsonPath("$.amount", is(10.00)))
                .andExpect(jsonPath("$.username", is("test")));


    }

    @Test
    public void getListOfTransfer() throws Exception {

        when(transferDao.getReceivedTransfers(10)).thenReturn(LISTFROMACCOUNT10);

        mockMvc.perform(get("/transfer/received/10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].transferId", is(1)))
                .andExpect(jsonPath("$[0].transferType", is("Send")))
                .andExpect(jsonPath("$[0].accountFrom", is(10)))
                .andExpect(jsonPath("$[0].accountTo", is(11)))
                .andExpect(jsonPath("$[0].amount", is(10.00)))
                .andExpect(jsonPath("$[0].username", is("test")))
                .andExpect(jsonPath("$[1].transferId", is(2)))
                .andExpect(jsonPath("$[1].transferType", is("Send")))
                .andExpect(jsonPath("$[1].accountFrom", is(10)))
                .andExpect(jsonPath("$[1].accountTo", is(11)))
                .andExpect(jsonPath("$[1].amount", is(150.00)))
                .andExpect(jsonPath("$[1].username", is("test")));
    }


}
