package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.util.BasicLogger;
import org.springframework.http.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

public class TransferService {
    private static final String API_BASE_URL = "http://localhost:8080/transfer/";
    private static final RestTemplate restTemplate = new RestTemplate();

    private AuthenticatedUser authenticatedUser;

    public TransferService(AuthenticatedUser authenticatedUser){
        this.authenticatedUser = authenticatedUser;
    }

    //method to get all past transfers.
    public Transfer[] getTransfers(Account account){
        Transfer[] transfers = null;
        try {
           ResponseEntity<Transfer[]> response = restTemplate.exchange(API_BASE_URL+"/sent/"+account.getAccountId(), HttpMethod.GET, makeAuthEntity(), Transfer[].class);
           transfers = response.getBody();
        } catch (RestClientResponseException e) {
            BasicLogger.log(e.getRawStatusCode() + " : " + e.getStatusText());
        } catch (ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return transfers;
    }

    // method to post transfer object the endpoint for sending transfers... it's not working properly
    public void createSendTransfer(Transfer transfer) {
        HttpEntity<Transfer> entity = makeTransferEntity(transfer);
        Transfer sendingTransfer = null;
        try {
            sendingTransfer = restTemplate.postForObject(API_BASE_URL + "/send", entity, Transfer.class);
        } catch (RestClientResponseException e) {
            BasicLogger.log(e.getRawStatusCode() + " : " + e.getStatusText());
        } catch (ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
    }


    public Transfer viewTransfer(int id){
        Transfer transfer = null;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authenticatedUser.getToken());
        HttpEntity<Void> entity = new HttpEntity<Void>(headers);
        try {
            ResponseEntity<Transfer> response = restTemplate.exchange(API_BASE_URL+"/details/"+id, HttpMethod.GET, makeAuthEntity(), Transfer.class);
            transfer = response.getBody();
        } catch (RestClientResponseException e) {
            BasicLogger.log(e.getRawStatusCode() + " : " + e.getStatusText());
        } catch (ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }

        return transfer;

    }

    // lists pending transfer by userId
//    public Transfer[] getPendingTransfers(AuthenticatedUser authenticatedUser) {
//        Transfer[] pendingTransfers = null;
//        try {
//            pendingTransfers = restTemplate.exchange(API_BASE_URL + "/tenmo_user" +
//                    authenticatedUser.getUser().getId() + "/pending", HttpMethod.GET,
//                    makeAuthEntity(), Transfer[].class).getBody();
//        } catch (RestClientResponseException e) {
//            BasicLogger.log(e.getRawStatusCode() + " : " + e.getStatusText());
//        } catch (ResourceAccessException e) {
//            BasicLogger.log(e.getMessage());
//        }
//
//        return pendingTransfers;
//    }
//
//    public Transfer getTransferStatusByDesc(AuthenticatedUser authenticatedUser, String transferStatusDesc) {
//        Transfer transfer = null;
//        try {
//            ResponseEntity<Transfer> response = restTemplate.exchange(API_BASE_URL + "/transfer_status/filter?desc=" +
//                    transferStatusDesc, HttpMethod.GET, makeTransfeStatusEntity(transfer), Transfer.class);
//            transfer = response.getBody();
//        } catch (RestClientResponseException e) {
//            BasicLogger.log(e.getRawStatusCode() + " : " + e.getStatusText());
//        } catch (ResourceAccessException e) {
//            BasicLogger.log(e.getMessage());
//        }
//
//        return transfer;
//    }
//
    public void updateTransferStatus (AuthenticatedUser authenticatedUser, Transfer transfer) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authenticatedUser.getToken());
        HttpEntity<Transfer> entity = new HttpEntity(transfer, headers);
        try {
            restTemplate.put(API_BASE_URL, HttpMethod.PUT, entity, Transfer.class);
        } catch (RestClientResponseException e) {
            BasicLogger.log(e.getRawStatusCode() + " : " + e.getStatusText());
        } catch (ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
    }
    public void approveRequest(int transferId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authenticatedUser.getToken());
        HttpEntity<Transfer> entity = new HttpEntity(headers);
        Transfer approved = null;
        try {
            ResponseEntity<Transfer> response = restTemplate.exchange(API_BASE_URL + transferId + "/approved", HttpMethod.PUT, entity, Transfer.class);
            approved = response.getBody();
        } catch (RestClientResponseException e) {
            BasicLogger.log(e.getRawStatusCode() + " : " + e.getStatusText());
        } catch (ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
    }

    public void rejectRequest(int transferId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authenticatedUser.getToken());
        HttpEntity<Transfer> entity = new HttpEntity(headers);
        Transfer rejected = null;
        try {
            ResponseEntity<Transfer> response = restTemplate.exchange(API_BASE_URL + transferId + "/rejected", HttpMethod.PUT, entity, Transfer.class);
            rejected = response.getBody();
        } catch (RestClientResponseException e) {
            BasicLogger.log(e.getRawStatusCode() + " : " + e.getStatusText());
        } catch (ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
    }

    private HttpEntity<Void> makeAuthEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authenticatedUser.getToken());
        return new HttpEntity<>(headers);
    }

    private HttpEntity<Transfer> makeTransferEntity(Transfer transfer) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authenticatedUser.getToken());
        return new HttpEntity<>(transfer,headers);
    }

    private HttpEntity<Transfer> makeTransfeStatusEntity(Transfer transfer) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authenticatedUser.getToken());
        return new HttpEntity<>(transfer,headers);
    }




}
