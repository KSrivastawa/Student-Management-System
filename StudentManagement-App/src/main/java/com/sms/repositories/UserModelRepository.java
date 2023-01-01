package com.sms.repositories;

import com.sms.entities.UserModel;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserModelRepository extends JpaRepository<UserModel,Long> {

    Optional<UserModel> findByEmail(String email);

    boolean existsByEmail(String email);

    Optional<UserModel> findByFirstNameAndLastName(String firstName , String lastName);

}
