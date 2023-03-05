package com.techelevator.tenmo;

import com.techelevator.tenmo.model.*;
import com.techelevator.tenmo.services.*;
import io.cucumber.core.gherkin.ScenarioOutline;

import java.util.ArrayList;
import java.util.List;

public class App {

    private static final String API_BASE_URL = "http://localhost:8080/";

    private final ConsoleService consoleService = new ConsoleService();
    private final AuthenticationService authenticationService = new AuthenticationService(API_BASE_URL);
    private AuthenticatedUser currentUser;

    public static void main(String[] args) {
        App app = new App();
        app.run();
    }

    private void run() {
        consoleService.printGreeting();
        loginMenu();
        if (currentUser != null) {
            mainMenu();
        }
    }

    private void loginMenu() {
        int menuSelection = -1;
        while (menuSelection != 0 && currentUser == null) {
            consoleService.printLoginMenu();
            menuSelection = consoleService.promptForMenuSelection("Please choose an option: ");
            if (menuSelection == 1) {
                handleRegister();
            } else if (menuSelection == 2) {
                handleLogin();
            } else if (menuSelection != 0) {
                System.out.println("Invalid Selection");
                consoleService.pause();
            }
        }
    }

    private void handleRegister() {
        System.out.println("Please register a new user account");
        UserCredentials credentials = consoleService.promptForCredentials();
        if (authenticationService.register(credentials)) {
            System.out.println("Registration successful. You can now login.");
        } else {
            consoleService.printErrorMessage();
        }
    }

    private void handleLogin() {
        UserCredentials credentials = consoleService.promptForCredentials();
        currentUser = authenticationService.login(credentials);
        if (currentUser == null) {
            consoleService.printErrorMessage();
        }
    }

    private void mainMenu() {
        int menuSelection = -1;
        while (menuSelection != 0) {
            consoleService.printMainMenu();
            menuSelection = consoleService.promptForMenuSelection("Please choose an option: ");
            if (menuSelection == 1) {
                viewCurrentBalance();
            } else if (menuSelection == 2) {
                viewTransferHistory();
            } else if (menuSelection == 3) {
                viewPendingRequests();
            } else if (menuSelection == 4) {
                sendBucks();
            } else if (menuSelection == 5) {
                requestBucks();
            } else if (menuSelection == 6) {
                loginMenu();
            } else if (menuSelection == 0) {
                continue;
            } else {
                System.out.println("Invalid Selection");
            }
            consoleService.pause();
        }
    }

    private void viewCurrentBalance() {
        AccountService accountService = new AccountService(currentUser);
        System.out.println("-------------------------------------");
        System.out.println("Your current balance is: $" + accountService.getAccountBalance(currentUser.getUser().getId()).getBalance());
        System.out.println("-------------------------------------");
    }

    //printing out transfer history
    private void viewTransferHistory() {
        TransferService transferService = new TransferService(currentUser);
        AccountService accountService = new AccountService(currentUser);
        Account[] accounts = accountService.getAllAccounts();
        Account currentAccount = null;
        for (Account account : accounts) {
            if (account.getUserId() == currentUser.getUser().getId()) {
                currentAccount = account;
            }
        }
        Transfer[] transfers = transferService.getTransfers(currentAccount);
        List<Transfer> approvedTransfers = new ArrayList<>();
        System.out.println("--------------------------------------------");
        System.out.println("            Completed Transfers            ");
        System.out.println("Transfer ID      From/To          Amount");
        System.out.println("--------------------------------------------");
        for (int i = 0; i < transfers.length; i++) {
            if (transfers[i].getTransferStatus().equals("Approved")) {
                approvedTransfers.add(transfers[i]);
            }
        }
        for (int i = 0; i < approvedTransfers.size(); i++) {
            printTransfersOrError(approvedTransfers.get(i));
        }

        consoleService.printViewTransferDetails(transferService.viewTransfer(consoleService.promptForInt("Please enter the transfer Id: ")));
    }

    //printing out pending requests
    private void viewPendingRequests() {
        TransferService transferService = new TransferService(currentUser);
        AccountService accountService = new AccountService(currentUser);
        Account[] accounts = accountService.getAllAccounts();
        Account currentAccount = null;
        System.out.println("--------------------------------------------");
        System.out.println("            Pending Transfers              ");
        System.out.println("Transfer ID      From/To          Amount");
        System.out.println("--------------------------------------------");
        for (Account account : accounts) {
            if (account.getUserId() == currentUser.getUser().getId()) {
                currentAccount = account;
            }
        }
        Transfer[] transfers = transferService.getTransfers(currentAccount);
        List<Transfer> pendingTransfers = new ArrayList<>();
        for (int i = 0; i < transfers.length; i++) {
            if (transfers[i].getTransferStatus().equals("Pending")) {
                pendingTransfers.add(transfers[i]);
            }
        }
        for (int i = 0; i < pendingTransfers.size(); i++) {
            printTransfersOrError(pendingTransfers.get(i));

        }
        System.out.println("Please Approve or Reject Transfer");
        Transfer transfer = transferService.viewTransfer(consoleService.promptForInt("Please enter the transfer Id: "));
        int choice = consoleService.promptForProccedOrMainMenu();
        if(choice == 1) {
            approveOrRejectTransferRequest(transfer);
        }else{
            mainMenu();
        }
    }


    private void sendBucks() { //this currently specifies which user name you are logged in to when it prints out all usernames and ids
        // TODO Auto-generated method stub
        AccountService accountService = new AccountService(currentUser);
        TransferService transferService = new TransferService(currentUser);
        UserService userService = new UserService(currentUser);
        Account[] accounts = accountService.getAllAccounts();
        Account currentAccount = null;
        for (int i = 0; i < accounts.length; i++) {
            if (currentUser.getUser().getId() == (accounts[i].getUserId())) {
                currentAccount = accounts[i];
                System.out.println("-------------------------");
                System.out.println("Your Username: " + currentUser.getUser().getUsername());
                System.out.println("Your User ID: " + currentUser.getUser().getId());
                System.out.println("Your Account ID: " + currentAccount.getAccountId());
                System.out.println("-------------------------");
            }
        }
        for (int j = 0; j < accounts.length; j++) {
            if (accounts[j].equals(currentAccount)) {
                continue;
            }
            System.out.println("-------------------------");
            System.out.println("User Id: " + accounts[j].getUserId());
            System.out.println("Account Id: " + accounts[j].getAccountId());
            System.out.println("Username: " + userService.getUser(accounts[j].getUserId()).getUsername());
            System.out.println("-------------------------");
        }
        transferService.createSendTransfer(consoleService.promptForTransfer(currentAccount, "Send", "Approved"));
    }

    private void requestBucks() {
        UserService userService = new UserService(currentUser);
        AccountService accountService = new AccountService(currentUser);
        TransferService transferService = new TransferService(currentUser);
        User[] users = userService.getAllUsers();
        for (User user : users) {
            consoleService.printUser(user);
        }
        int userId = consoleService.promptForInt("Please enter the user ID of the user you'd like to request from: ");
        Transfer transfer = consoleService.promptForTransfer(accountService.getAccount(userId), "Request", "Pending");
        transferService.createSendTransfer(transfer);
    }

    private void printTransfersOrError(Transfer transfer) {
        if (transfer != null) {
            consoleService.printTransfers(transfer);
        } else {
            consoleService.printErrorMessage();
        }
    }

    private void printPendingTransfersOrError(Transfer transfer) {
        if (transfer != null) {
            consoleService.printPendingTransfers(transfer);
        } else {
            consoleService.printErrorMessage();
        }
    }

    //
    public void approveOrRejectTransferRequest(Transfer transfer) {
        TransferService transferService = new TransferService(currentUser);
        consoleService.printPendingTransfers(transfer);
        int menuSelection = consoleService.promptForMenuSelection("Press 1.) to approve     Press 2.) to reject ");
        if (menuSelection == 1) {
            transfer.setTransferType("Approved");
            transferService.updateRequest(transfer);
            System.out.println("Transaction Approved!");
        } else if (menuSelection == 2) {
            transfer.setTransferStatus("Transaction Rejected!");
            transferService.updateRequest(transfer);
        }
    }
}
