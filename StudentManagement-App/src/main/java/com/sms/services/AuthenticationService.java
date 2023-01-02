package com.sms.services;

import com.sms.dtorequest.LoginUserRequest;
import com.sms.dtorequest.RegisterRequest;
import com.sms.dtoresponse.AuthenticationTokenResponse;
import com.sms.dtoresponse.UserRegisterResponse;
import com.sms.exceptions.UserNotFoundException;
import com.sms.exceptions.UserRegistrationFailedException;
import com.sms.models.Role;
import com.sms.models.UserDto;

import javax.management.relation.RoleNotFoundException;

import org.springframework.security.authentication.BadCredentialsException;

public interface AuthenticationService {

    UserRegisterResponse registerNewUserService(RegisterRequest userRegisterRequest)throws UserRegistrationFailedException;
    
    AuthenticationTokenResponse loginUserService(LoginUserRequest loginUserRequest)throws BadCredentialsException;
    
    UserDto getUserByEmail(String email) throws UserNotFoundException;
    
    Role addRole(Role role) throws RoleNotFoundException;
}
