package com.sms.repositories;

import com.sms.models.Role;
import com.sms.models.RoleEnum;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    Optional<Role> findByName(RoleEnum roleAdmin);
    
    boolean existsByName(RoleEnum roleEnum);

}
