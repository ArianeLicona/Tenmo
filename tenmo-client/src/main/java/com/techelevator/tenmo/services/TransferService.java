package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.util.BasicLogger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

public class TransferService {
    private static final String API_BASE_URL = "http://localhost:8080/transfer/";
    private final RestTemplate restTemplate = new RestTemplate();

    private AuthenticatedUser authenticatedUser;

    public TransferService(AuthenticatedUser authenticatedUser){
        this.authenticatedUser = authenticatedUser;
    }

    //method to get all past transfers. not sure if i did this right, i might add a test just to make sure.
    public Transfer[] getPastTransfers(int id){
        Transfer[] transfers = null;
        try {
            transfers = restTemplate.getForObject(API_BASE_URL + id, Transfer[].class);
        } catch (RestClientResponseException e) {
            BasicLogger.log(e.getRawStatusCode() + " : " + e.getStatusText());
        } catch (ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return transfers;
    }

    //method to get pending transfers
    public Transfer[] getPendingTransfers(int id){
        Transfer[] transfers = null;
        try {
            transfers = restTemplate.getForObject(API_BASE_URL + id, Transfer[].class);
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
