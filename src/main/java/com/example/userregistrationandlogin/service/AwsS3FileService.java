package com.example.userregistrationandlogin.service;

import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface AwsS3FileService {
    String uploadFile(MultipartFile multipartFile) throws FileUploadException, IOException;

    Object downloadFile(String fileName) throws IOException;

    boolean delete(String fileName);
}
