package com.challange.picpay.challange_picpay.model;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity(name="user")
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;

    @Column(unique = true)
    private String CPF;

    private String password;

    @Column(unique = true)
    private String email;

    private BigDecimal balance;
    private UserType userType;
}
