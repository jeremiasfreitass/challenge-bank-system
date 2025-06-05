package com.challange.bank.system.dto;

import com.challange.bank.system.model.enums.UserTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String document;
    private String password;
    private String email;
    private UserTypeEnum userType;
    private BigDecimal balance;

}

