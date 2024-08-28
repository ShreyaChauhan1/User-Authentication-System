package com.example.userregistrationandlogin.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Random;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class JwtUtils {
    private String jwt;

    public String generateToken(){
        return UUID.randomUUID().toString();
    }

    public boolean validateToken(){
    return false;
    }
}
