package com.example.userregistrationandlogin.controller;

import com.example.userregistrationandlogin.dto.ResponseData;
import com.example.userregistrationandlogin.service.AwsS3FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class AwsFileController {

    @Autowired
    private AwsS3FileService awsS3FileService;

    @PostMapping("/aws/upload")
    public ResponseEntity<ResponseData> upload(@RequestParam("file")MultipartFile file) throws IOException {
        awsS3FileService.uploadFile(file);
        String message = "File uploaded successfully!";
        ResponseData res = new ResponseData();
        res.setData(message);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

}
