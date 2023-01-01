package com.sms.repositories;

import com.sms.entities.StudentModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<StudentModel, Integer> {

    Optional<StudentModel> findByEmail(String email);

    List<StudentModel> findByFirstName(String firstName);

}
