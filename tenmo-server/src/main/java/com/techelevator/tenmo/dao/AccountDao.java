package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;

import java.math.BigDecimal;

public interface AccountDao {

    Account getAccount(int id);


     public BigDecimal addMoneyToBalance(int id, BigDecimal balance); //abstract method for adding

    public BigDecimal subtractMoneyFromBalance(int id, BigDecimal balance); //abstract method for subtracting

//    void updateAccount(int id, BigDecimal balance); /* abstract method that may be needed for updating the
//    accounts? */
}
