package com.example.userregistrationandlogin.controller;

import com.example.userregistrationandlogin.config.VerificationCodeGenerator;
import com.example.userregistrationandlogin.dto.*;
import com.example.userregistrationandlogin.service.PasswordResetService;
import com.example.userregistrationandlogin.service.impl.EmailServiceImpl;
import com.example.userregistrationandlogin.service.impl.PasswordResetServiceImpl;
import com.example.userregistrationandlogin.service.impl.UserActivationServiceImpl;
import com.example.userregistrationandlogin.service.impl.UserAuthServiceImpl;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/user")
public class UserAuthController {

    @Autowired private UserAuthServiceImpl userAuthService;

    @Autowired
    JavaMailSender javaMailSender;

    @Autowired private EmailServiceImpl emailService;

    @Autowired private UserActivationServiceImpl userActivationService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserAuthServiceImpl userAuthServiceImpl;

    @Autowired
    private PasswordResetServiceImpl passwordResetServiceImpl;

    @Autowired
    private PasswordResetService passwordResetService;


    @GetMapping("/")
    public ResponseEntity<ResponseData> health(){
        ResponseData res = new ResponseData();
        res.setData("Auth api is up");
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PostMapping("register")
    public ResponseEntity<ResponseData> registerUser(@RequestBody UserRegistration user) throws MessagingException {

        String hashedPassword = passwordEncoder.encode(user.getPassword());
        int verificationCode = VerificationCodeGenerator.generateVerificationCode();
        UserRegistration addUser = new UserRegistration(
                null,
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                hashedPassword,
                false,
                verificationCode
        );
          String message = userAuthService.registerUser(addUser);
          try {

          if(message.equals("User Registered successfully!")){
              String messageBody = "<h1>Your activation code is : " + verificationCode;
              String subject = "Account verification code ";
            emailService.sendEmail(new EmailDetails(user.getEmail(),messageBody,subject));
          }
          }
          catch (Exception e){
              System.out.println("exception is : " + e);
          }
        ResponseData res = new ResponseData();
          res.setData(message);
          return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @PostMapping("loginUser")
    public ResponseEntity<ResponseData> loginUser(@RequestBody UserLogin user, HttpServletRequest request) throws MessagingException{ // Add HttpServletRequest parameter
        String message = userAuthService.loginUser(user, request); // Pass the request to the service method
        ResponseData res = new ResponseData();
        res.setData(message);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("activate")
    public ResponseEntity<ResponseData> activateAccount(@RequestParam("email") String email, @RequestParam("activationCode") Integer activationCode){
        String message = userActivationService.verifyUser(email, activationCode);
        ResponseData res = new ResponseData();
        res.setData(message);
        return  new ResponseEntity<>(res,HttpStatus.OK);
    }

    @PostMapping("updatePassword")
    public ResponseEntity<ResponseData> updatePassword(@RequestParam("email") String email,@RequestBody UpdatePassword updatePassword) throws MessagingException{
      String message =   passwordResetService.updatePassword(email,updatePassword);
      ResponseData res = new ResponseData();
      res.setData(message);
      return  new ResponseEntity<>(res,HttpStatus.OK);
    }

}
