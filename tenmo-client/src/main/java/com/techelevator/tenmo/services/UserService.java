package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.User;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

public class UserService {
    public final String API_URL = "http://localhost:8080/users";

    public final RestTemplate RESTTEMPLATE = new RestTemplate();

    private AuthenticatedUser authenticatedUser;

    public UserService (AuthenticatedUser authenticatedUser) {this.authenticatedUser = authenticatedUser;}

//    public List<User> getAllUsers(){
//        HttpHeaders headers = getHeaders();
//        HttpEntity<Void> entity = new HttpEntity<Void>(headers);
//        ResponseEntity<User> response = RESTTEMPLATE.getForEntity(API_URL, entity, User.class);
//        return ;
//    }

    public HttpHeaders getHeaders(){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authenticatedUser.getToken());
        return headers;
    }
}
