package com.techelevator.tenmo.services;


import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import com.techelevator.tenmo.model.UserCredentials;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

public class ConsoleService {

    private final Scanner scanner = new Scanner(System.in);

    public int promptForMenuSelection(String prompt) {
        int menuSelection;
        System.out.print(prompt);
        try {
            menuSelection = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            menuSelection = -1;
        }
        return menuSelection;
    }

    public void printGreeting() {
        System.out.println("*********************");
        System.out.println("* Welcome to TEnmo! *");
        System.out.println("*********************");
    }

    public void printLoginMenu() {
        System.out.println();
        System.out.println("1: Register");
        System.out.println("2: Login");
        System.out.println("0: Exit");
        System.out.println();
    }

    public void printMainMenu() {
        System.out.println();
        System.out.println("1: View your current balance");
        System.out.println("2: View your past transfers");
        System.out.println("3: View your pending requests");
        System.out.println("4: Send TE bucks");
        System.out.println("5: Request TE bucks");
        System.out.println("0: Exit");
        System.out.println();
    }

    public UserCredentials promptForCredentials() {
        String username = promptForString("Username: ");
        String password = promptForString("Password: ");
        return new UserCredentials(username, password);
    }

    public String promptForString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    public int promptForInt(String prompt) {
        System.out.print(prompt);
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number.");
            }
        }
    }

    public BigDecimal promptForBigDecimal(String prompt) {
        System.out.print(prompt);
        while (true) {
            try {
                return new BigDecimal(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a decimal number.");
            }
        }
    }

    public Transfer promptForTransfer(Account account, String type, String status) {
        Transfer transfer = new Transfer();
        transfer.setTransferType(type);
        transfer.setTransferStatus(status);
        transfer.setAccountFrom(account.getAccountId());
        transfer.setAccountTo(promptForInt("Please enter the user id of the receiver."));
        transfer.setAmount(promptForBigDecimal("Please enter the amount (XX.XX) of TEBucks to send."));
        return transfer;
    }

    public void pause() {
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }
    public void printTransfers(Transfer transfer) {
        System.out.println("--------------------------------------------");
        System.out.println("Transfers");
        System.out.println("ID            From/To          Amount");
        System.out.println("--------------------------------------------");
        if (transfer == null) {
            System.out.println("No past transfers to print");
        } else {
            System.out.println(transfer.getTransferId() + "     From:  " + transfer.getAccountFrom() + "     $ " + transfer.getAmount());
            System.out.println(transfer.getTransferId() + "     To :  " + transfer.getAccountTo() + "     $ " + transfer.getAmount());
        }
        System.out.println("Please enter transfer ID to view details (0 to cancel): ");
    }
    public void printPendingTransfers(Transfer transfer) {
        System.out.println("--------------------------------------------");
        System.out.println("Pending Transfers");
        System.out.println("ID            To          Amount");
        System.out.println("--------------------------------------------");
        if (transfer == null) {
            System.out.println("No past transfers to print");
        } else {
            System.out.println(transfer.getTransferId() + "     " + transfer.getAccountTo() + "     $ " + transfer.getAmount());
        }
        System.out.println("Please enter transfer ID to view details (0 to cancel): ");
    }

    public void printUser (User user){
        System.out.println("Username: " + user.getUsername());
        System.out.println("User ID: " + user.getId());
        System.out.println("-------------------------");
    }


    public void printErrorMessage() {
        System.out.println("An error occurred. Check the log for details.");
    }

//    public void requestBucks() {
//        System.out.println("--------------------------------------------");
//        System.out.println("List of registered TEnmo users and their ID.");
//        System.out.println("--------------------------------------------");
////        if (users == null){
////            System.out.println("No users to print.");
////        } else {
////            for (User user : users) {
////                System.out.println(user.getUsername());
////            }
////        promptForInt("Please input the user ID of the person you'd like to request from.");
////        Transfer transfer = promptForTransfer(Account account);
////        transfer.setTypeId(1);
////        transfer.setTransferType("Request");
////        transfer.setStatusId(1);
////        transfer.setTransferStatus("Pending");
////        }
  }
