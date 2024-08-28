package com.example.userregistrationandlogin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class UserRegistration {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Boolean isActivated;
    private int activationCode;

}
