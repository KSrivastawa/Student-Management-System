package com.sms.dtoresponse;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class AuthenticationTokenResponse {

    private final String token;
    private final String type = "Bearer ";
    private final String instruction = "Handle error!";


    public AuthenticationTokenResponse(String token) {
        this.token = token;
    }
}
