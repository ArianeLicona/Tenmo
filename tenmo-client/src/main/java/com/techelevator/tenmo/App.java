package com.techelevator.tenmo;

import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import com.techelevator.tenmo.model.UserCredentials;
import com.techelevator.tenmo.services.*;

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
        System.out.println("Your current balance is: $"+accountService.getAccountBalance(currentUser.getUser().getId()).getBalance());
        System.out.println("-------------------------------------");
	}

    //printing out transfer history
    private void viewTransferHistory() {
        TransferService transferService = new TransferService(currentUser);
        Transfer[] transfers = transferService.getTransfers();
        List<Transfer> pendingTransfers = new ArrayList<>();
        for(int i =0; i < transfers.length; i++){
            if(transfers[i].getTransferStatus().equals("Completed")){
                pendingTransfers.add(transfers[i]);
            }
        }
        for(int i =0; i< pendingTransfers.size(); i++) {
            printTransfersOrError(pendingTransfers.get(i));
        }
    }

    //printing out pending requests
    private void viewPendingRequests() {
        TransferService transferService = new TransferService(currentUser);
        Transfer[] transfers = transferService.getTransfers();
        List<Transfer> pendingTransfers = new ArrayList<>();
        for(int i =0; i < transfers.length; i++){
            if(transfers[i].getTransferStatus().equals("Pending")){
                pendingTransfers.add(transfers[i]);
            }
        }
        for(int i =0; i< pendingTransfers.size(); i++) {
            printTransfersOrError(pendingTransfers.get(i));
        }
    }

	private void sendBucks() {
		// TODO Auto-generated method stub
            UserService userService = new UserService(currentUser);
            User[] users = userService.getAllUsers();
            for(int i = 0; i < users.length;i++){
                System.out.println(users[i].getUsername());
                System.out.println(users[i].getId());

            }
        }

	private void requestBucks() {
		// TODO Auto-generated method stub
		
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


}
