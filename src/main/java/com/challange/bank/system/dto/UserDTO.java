package com.challange.bank.system.dto;

import com.challange.bank.system.model.enums.UserTypeEnum;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class UserDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private UserTypeEnum userType;
    private BigDecimal balance;
    public UserDTO() {}
}

