package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;

import javax.security.auth.login.AccountNotFoundException;
import java.math.BigDecimal;
import java.util.List;

public interface AccountDao {

    Account getAccount(int id);

    // method to list all accounts
    List<Account> allAccounts();

// I'm not sure is the add-subtract methods are completely necessary, there might be a better way to do this.
//     public BigDecimal addMoneyToBalance(int id, BigDecimal balance); //abstract method for adding
//
//    public BigDecimal subtractMoneyFromBalance(int id, BigDecimal balance); //abstract method for subtracting

    public int updateBalance(Account account);



//    void updateAccount(int id, BigDecimal balance); /* abstract method that may be needed for updating the
//    accounts? */
}
