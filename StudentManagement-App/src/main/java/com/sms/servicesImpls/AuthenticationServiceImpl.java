package com.sms.servicesImpls;

import com.sms.config.AuthenticationUserDetailsService;
import com.sms.dtorequest.LoginUserRequest;
import com.sms.dtorequest.RegisterRequest;
import com.sms.dtoresponse.AuthenticationTokenResponse;
import com.sms.dtoresponse.UserRegisterResponse;
import com.sms.entities.UserModel;
import com.sms.exceptions.UserNotFoundException;
import com.sms.exceptions.UserRegistrationFailedException;
import com.sms.filters.JwtAuthenticationUtils;
import com.sms.models.Role;
import com.sms.models.RoleEnum;
import com.sms.models.UserDto;
import com.sms.repositories.RoleRepository;
import com.sms.repositories.UserModelRepository;
import com.sms.services.AuthenticationService;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

import javax.management.relation.RoleNotFoundException;

@Service 
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserModelRepository userModelRepository;

    private final AuthenticationManager authenticationManager;
    private final JwtAuthenticationUtils jwtAuthenticationUtils;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

   
    public AuthenticationServiceImpl(UserModelRepository userModelRepository,
                                     AuthenticationUserDetailsService authenticationUserDetailsService,
                                     AuthenticationManager authenticationManager, JwtAuthenticationUtils jwtAuthenticationUtils,
                                     PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userModelRepository = userModelRepository;
        this.authenticationManager = authenticationManager;
        this.jwtAuthenticationUtils = jwtAuthenticationUtils;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @Override
    public UserRegisterResponse registerNewUserService(RegisterRequest userRegisterRequest)
            throws UserRegistrationFailedException {

        if (userModelRepository.existsByEmail(userRegisterRequest.getEmail())) {
            throw new UserRegistrationFailedException("Be focussed Email already exists");
        }

        UserModel userModel = new UserModel();

        userModel.setEmail(userRegisterRequest.getEmail());
        userModel.setFirstName(userRegisterRequest.getFirstName());
        userModel.setLastName(userRegisterRequest.getLastName());
        userModel.setMobile(userRegisterRequest.getMobile());
        userModel.setPassword(passwordEncoder.encode(userRegisterRequest.getPassword()));
        
        Role role = roleRepository.findByName(RoleEnum.ROLE_ADMIN)
                .orElseThrow(() -> new RuntimeException("Error: What the hell man Role is not found."));

        userModel.setRoles(Collections.singletonList(role));

        UserModel savedUserModel = userModelRepository.save(userModel);

        return UserRegisterResponse.builder()
                .userId(savedUserModel.getUserId())
                .email(savedUserModel.getEmail())
                .firstName(savedUserModel.getFirstName())
                .lastName(savedUserModel.getLastName())
                .mobile(savedUserModel.getMobile())
                .build();
    }

    @Override
    public AuthenticationTokenResponse loginUserService(LoginUserRequest loginUserRequest)
            throws BadCredentialsException {

        userModelRepository.findByEmail(loginUserRequest.getEmail())
                .orElseThrow(() -> new BadCredentialsException(" Fuck Invalid username or password"));

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginUserRequest.getEmail(),
                        loginUserRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtAuthenticationUtils.generateToken(authentication);

        return new AuthenticationTokenResponse(token);
    }
    
    
    @Override
    public UserDto getUserByEmail(String email) throws UserNotFoundException {

        Optional<UserModel> userModelOptional = userModelRepository.findByEmail(email);
        
        if (userModelOptional.isPresent()) {
        	
        	UserModel model = userModelOptional.get();
        	
        	UserDto dto = new UserDto();
        	dto.setFirstName(model.getFirstName());
        	dto.setLastName(model.getLastName());
        	dto.setMobile(model.getMobile());
        	dto.setEmail(model.getEmail());
        	dto.setRole(model.getRoles().get(0).getName().toString());
        	
        	return dto;
        }

        throw new UserNotFoundException("User not found");

    }

    @Override
    public Role addRole(Role role) throws RoleNotFoundException {
        if (roleRepository.existsByName(role.getName())) {
            throw new RoleNotFoundException("Role already exists");
        }
        return roleRepository.save(role);
    }
    
    
    
}

