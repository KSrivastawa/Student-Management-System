package com.sms.controller;

import com.sms.servicesImpls.StudentServiceImpl;
import com.sms.dtorequest.LoginUserRequest;
import com.sms.dtorequest.RegisterRequest;
import com.sms.dtoresponse.AuthenticationTokenResponse;
import com.sms.dtoresponse.UserRegisterResponse;
import com.sms.entities.StudentModel;
import com.sms.exceptions.StudentException;
import com.sms.exceptions.UserNotFoundException;
import com.sms.exceptions.UserRegistrationFailedException;
import com.sms.models.Role;
import com.sms.models.UserDto;
import com.sms.services.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import javax.management.relation.RoleNotFoundException;

@RestController
@RequestMapping("/api")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @Autowired
    private StudentServiceImpl studentService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;

    }

    @GetMapping
    public String getHello() {
        return "Welcome to Student Management System!";
    }

    @PostMapping("/addrole")
    public ResponseEntity<Role> addRole(@RequestBody Role role) throws RoleNotFoundException {
        Role role1 = authenticationService.addRole(role);
        return new ResponseEntity<Role>(role1, HttpStatus.CREATED);
    }

    @PostMapping("/register")
    public ResponseEntity<UserRegisterResponse> registerNewUserHandler(
            @Valid @RequestBody RegisterRequest registerRequest) throws UserRegistrationFailedException {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(authenticationService.registerNewUserService(registerRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationTokenResponse> loginUserHandler(@RequestBody LoginUserRequest loginUserRequest)
            throws UserRegistrationFailedException {

        return ResponseEntity.ok(authenticationService.loginUserService(loginUserRequest));
    }

    @GetMapping("/getuser/{email}")
    public ResponseEntity<UserDto> getUserByEmailHandler(@PathVariable String email) throws UserNotFoundException {

        return ResponseEntity.ok(authenticationService.getUserByEmail(email));
    }

    @PostMapping("/addstudent")
    public ResponseEntity<String> registerStudentHandler(@RequestBody StudentModel studentModel)
            throws StudentException {

        String model = studentService.registerStudentDetails(studentModel);

        return new ResponseEntity<String>(model, HttpStatus.CREATED);

    }

    @GetMapping("/getallstudents")
    public ResponseEntity<List<StudentModel>> getAllStudentsHandler() throws StudentException {

        List<StudentModel> studentModelList = studentService.getAllStudents();

        return new ResponseEntity<List<StudentModel>>(studentModelList, HttpStatus.OK);
    }

    @PutMapping("/updatestudent/{id}")
    public ResponseEntity<StudentModel> updateStudentHandler(@PathVariable("id") int id,
            @RequestBody StudentModel studentModel) throws StudentException {

        StudentModel model = studentService.updateStudentDetails(studentModel, id);

        return new ResponseEntity<StudentModel>(model, HttpStatus.OK);
    }

    @DeleteMapping("/deletestudent/{id}")
    public ResponseEntity<String> deleteStudentHandler(@PathVariable("id") int id) throws StudentException {

        String model = studentService.deleteStudentById(id);

        return new ResponseEntity<String>(model, HttpStatus.OK);
    }

    @GetMapping("/getstudent/{id}")
    public ResponseEntity<StudentModel> getStudentByIdHandler(@PathVariable("id") int id) throws StudentException {

        StudentModel studentModel = studentService.getStudentById(id);

        return new ResponseEntity<StudentModel>(studentModel, HttpStatus.OK);
    }

    @GetMapping("/getstudents/{firstName}")
    public ResponseEntity<List<StudentModel>> getStudentsByIdHandler(@PathVariable("firstName") String firstName)
            throws StudentException {

        List<StudentModel> studentModelList = studentService.getStudentsById(firstName);

        return new ResponseEntity<List<StudentModel>>(studentModelList, HttpStatus.OK);
    }

}
