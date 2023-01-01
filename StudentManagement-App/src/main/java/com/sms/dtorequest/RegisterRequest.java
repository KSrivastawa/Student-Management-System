package com.sms.dtorequest;

import lombok.*;

@Getter@Setter@AllArgsConstructor@NoArgsConstructor@Builder
public class RegisterRequest {

    private String firstName;
    private String lastName;
    private String mobile;
    private String email;
    private String password;


}
