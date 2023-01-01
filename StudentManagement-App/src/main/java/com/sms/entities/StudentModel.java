package com.sms.entities;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Setter
@Getter
public class StudentModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int studentId;

    private String firstName;
    private String lastName;
    private String email;
    private String mobile;
    private String url;

}
