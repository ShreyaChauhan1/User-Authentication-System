package com.example.userregistrationandlogin.service;

import com.example.userregistrationandlogin.entity.FileStorageEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileStorageService {

    FileStorageEntity store(MultipartFile file) throws IOException;
    FileStorageEntity getFile(String id);
}
