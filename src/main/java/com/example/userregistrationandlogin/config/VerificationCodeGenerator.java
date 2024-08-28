package com.example.userregistrationandlogin.config;

import java.util.Random;

public class VerificationCodeGenerator {

    private static final Random RANDOM = new Random();

    public static int generateVerificationCode() {
        int number = RANDOM.nextInt(900000) + 100000; // This will generate a number between 100000 and 999999
        return number;
    }
}
