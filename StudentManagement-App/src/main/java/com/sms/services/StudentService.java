package com.sms.services;

import com.sms.entities.StudentModel;
import com.sms.exceptions.StudentException;

import java.util.List;

public interface StudentService {

	public String registerStudentDetails(StudentModel studentModel) throws StudentException;

    public StudentModel updateStudentDetails(StudentModel studentModel, Integer studentId) throws StudentException;

    public String deleteStudentById(Integer studentId) throws StudentException;

    public List<StudentModel> getAllStudents() throws StudentException;

    public StudentModel getStudentById(Integer studentId) throws StudentException;

    public  List<StudentModel>  getStudentsById(String firstName) throws StudentException;

}
