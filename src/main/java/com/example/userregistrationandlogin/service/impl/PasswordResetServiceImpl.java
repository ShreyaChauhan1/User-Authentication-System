package com.example.userregistrationandlogin.service.impl;

import com.example.userregistrationandlogin.dto.EmailDetails;
import com.example.userregistrationandlogin.dto.UpdatePassword;
import com.example.userregistrationandlogin.entity.UserEntity;
import com.example.userregistrationandlogin.entity.UserTokenEntity;
import com.example.userregistrationandlogin.repository.UserRepository;
import com.example.userregistrationandlogin.repository.UserTokenRepository;
import com.example.userregistrationandlogin.service.EmailService;
import com.example.userregistrationandlogin.service.PasswordResetService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@Service
public class PasswordResetServiceImpl implements PasswordResetService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserTokenRepository userTokenRepository;

    @Autowired
    private  EmailService emailService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Value("${spring.mail.username}") private String sender;

    private final String UI_URL = "https://auth-app-front-end.vercel.app";

    @Override
    public String resetPassword(String token, String newPassword) throws  MessagingException{
        UserTokenEntity userToken = userTokenRepository.findByConfirmationToken(token);

        if (userToken == null || !userToken.isValid()) {
            return "Invalid or expired token.";
        }

        UserEntity user = userToken.getUser();
        if (user == null) {
            return "No user found for this token.";
        }

        // Encrypt the new password before saving
        String encodedPassword = passwordEncoder.encode(newPassword);

        user.setPassword(encodedPassword);
        userRepository.save(user);

        // Optionally remove the token if it should no longer be used
        userTokenRepository.delete(userToken);

        String body = "Your password has been reset successfully!";
        String subject = "Password reset successfully!";
        EmailDetails email = new EmailDetails(user.getEmail(), body, subject);
        emailService.sendEmail(email);
        return "Password has been successfully reset.";
    }

    @Override
    public String forgotPassword(String emailAddress) throws MessagingException {
        UserEntity user = userRepository.findByEmail(emailAddress);
        if (user == null) {
            return "User not found with the email address : " + emailAddress;
        }

        UserTokenEntity userTokenEntity = userTokenRepository.findByUser(user);
        String token = generateToken();

        // Set token expiration for 24 hours from now
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.HOUR, 24);

        if (userTokenEntity == null) {
            userTokenEntity = new UserTokenEntity();
            userTokenEntity.setUser(user);
        }

        userTokenEntity.setConfirmationToken(token);
        userTokenEntity.setExpirationDate(cal.getTime());
        userTokenRepository.save(userTokenEntity);

        String body = "<h1>Please click on the link below to reset your password : </h1>\n" +
                "<a href='" + UI_URL + "/resetPassword?token=" + token + "'>Reset password</a>";
        EmailDetails email = new EmailDetails(emailAddress, body, "Reset password");
        emailService.sendEmail(email);
        return "An email has been sent to " + user.getEmail() + " with instructions to reset your password";
    }

    @Override
    public String updatePassword(String emailAddress, UpdatePassword updatePassword) throws MessagingException {
       UserEntity user =  userRepository.findByEmail(emailAddress);
        if(user != null){
            String oldHashedPassword = passwordEncoder.encode(updatePassword.getOldPassword());
          if(passwordEncoder.matches(updatePassword.getOldPassword(), user.getPassword())){
              user.setPassword(passwordEncoder.encode((updatePassword.getNewPassword())));

              userRepository.save(user);
             return sendMail(emailAddress);

          }
          else {
              return "Old password is incorrect!";
          }
        }
        return "No user found with the email address : " + emailAddress;

    }

    public String generateToken(){
        return UUID.randomUUID().toString();
    }

    public String sendMail(String recipient) throws  MessagingException{
        String body = "Your password has been updated successfully!";
        String subject = "Password update";
        EmailDetails emailDetails = new EmailDetails(recipient, body , subject);
        return emailService.sendEmail(emailDetails);
    }
}
