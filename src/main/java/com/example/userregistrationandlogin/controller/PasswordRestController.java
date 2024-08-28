package com.example.userregistrationandlogin.controller;

import com.example.userregistrationandlogin.dto.ResponseData;
import com.example.userregistrationandlogin.service.impl.PasswordResetServiceImpl;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/user")
public class PasswordRestController {

    @Autowired
    private PasswordResetServiceImpl passwordResetService;

    @GetMapping("forgotPassword")
    public ResponseEntity<ResponseData> forgotPassword(@RequestParam("email") String email) throws MessagingException {
        String message = passwordResetService.forgotPassword(email);
        ResponseData res = new ResponseData();
        res.setData(message);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("resetPassword")
    public ResponseEntity<ResponseData> resetPassword(@RequestParam("token") String token, @RequestParam("password") String password) throws  MessagingException{
        String message = passwordResetService.resetPassword(token, password);
        ResponseData res = new ResponseData();
        res.setData(message);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
