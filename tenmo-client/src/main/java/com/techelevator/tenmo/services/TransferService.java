package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.util.BasicLogger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

public class TransferService {
    private static final String API_BASE_URL = "http://localhost:8080/transfer/";
    private static final RestTemplate restTemplate = new RestTemplate();

    private AuthenticatedUser authenticatedUser;

    public TransferService(){
        this.authenticatedUser = authenticatedUser;
    }

    //method to get all past transfers. not sure if i did this right, i might add a test just to make sure.
    public static Transfer getPastTransfers(){
       Transfer transfers = null;
        try {
            transfers = restTemplate.getForObject(API_BASE_URL, Transfer.class);
        } catch (RestClientResponseException e) {
            BasicLogger.log(e.getRawStatusCode() + " : " + e.getStatusText());
        } catch (ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return transfers;
    }

    //method to get pending transfers
    public static Transfer getPendingTransfers(){
        Transfer transfers = null;
        try {
            transfers = restTemplate.getForObject(API_BASE_URL, Transfer.class);
        } catch (RestClientResponseException e) {
            BasicLogger.log(e.getRawStatusCode() + " : " + e.getStatusText());
        } catch (ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return transfers;
    }

    //not 100% sure this method has everything it needs. would appreciate feedback!
    private HttpEntity<Transfer> makeEntity(Transfer transfer){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authenticatedUser.getToken());
        return new HttpEntity<>(transfer, headers);
    }


}