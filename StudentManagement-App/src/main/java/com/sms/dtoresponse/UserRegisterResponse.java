package com.sms.dtoresponse;

import lombok.*;

@Builder@AllArgsConstructor@NoArgsConstructor@Getter@Setter
public class UserRegisterResponse {

    private int userId;
    private String firstName;
    private String lastName;
    private String mobile;
    private String email;


}
