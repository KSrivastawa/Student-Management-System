package com.sms.servicesImpls;

import com.sms.entities.StudentModel;
import com.sms.exceptions.StudentException;
import com.sms.repositories.StudentRepository;
import com.sms.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public String registerStudentDetails(StudentModel studentModel) throws StudentException {

        Optional<StudentModel> studentOptional = studentRepository.findByEmail(studentModel.getEmail());

            if (studentOptional.isPresent()) {
                throw new StudentException("Student already exists with same email: " + studentModel.getEmail());
            }

            StudentModel student = new StudentModel();

            student.setFirstName(studentModel.getFirstName());
            student.setLastName(studentModel.getLastName());
            student.setEmail(studentModel.getEmail());
            student.setMobile(studentModel.getMobile());
            student.setUrl(studentModel.getUrl());

            studentRepository.save(student);

            return "Student registered successfully with email: " + studentModel.getEmail();


    }

    @Override
    public StudentModel updateStudentDetails(StudentModel studentModel, Integer studentId)
            throws StudentException {

        Optional<StudentModel> studentModelOptional = studentRepository.findById(studentId);

            if (!studentModelOptional.isPresent()) {
                throw new StudentException("Student not found with id: " + studentId);
            }

            studentModelOptional.get().setFirstName(studentModel.getFirstName());
            studentModelOptional.get().setLastName(studentModel.getLastName());
            studentModelOptional.get().setEmail(studentModel.getEmail());
            studentModelOptional.get().setMobile(studentModel.getMobile());
            studentModelOptional.get().setUrl(studentModel.getUrl());

            StudentModel student = studentModelOptional.get();

            return studentRepository.save(student);


    }

    @Override
    public String deleteStudentById(Integer studentId) throws StudentException {

        Optional<StudentModel> studentModelOptional = studentRepository.findById(studentId);
            
            if (!studentModelOptional.isPresent()) {
                throw new StudentException("Student not found with id: " + studentId);
            }

            studentRepository.deleteById(studentId);

            return "Student deleted successfully with id: " + studentId;

    }

    @Override
    public List<StudentModel> getAllStudents() throws StudentException {

        List<StudentModel> studentModelList = studentRepository.findAll();

        if (studentModelList.isEmpty()) {
            throw new StudentException("Student list is Empty!");
        }
        return studentModelList;

    }

    @Override
    public StudentModel getStudentById(Integer studentId) throws StudentException {

        Optional<StudentModel> studentModelOptional = studentRepository.findById(studentId);

        if (!studentModelOptional.isPresent()) {
            throw new StudentException("Student not found with id: " + studentId);
        }

        return studentModelOptional.get();

    }

    @Override
    public List<StudentModel> getStudentsById(String firstName) throws StudentException {

        List<StudentModel> studentModelList = studentRepository.findByFirstName(firstName);

        if (studentModelList.isEmpty()) {
            throw new StudentException("Student not found with first name: " + firstName);
        }

        return studentModelList;

    }
}

