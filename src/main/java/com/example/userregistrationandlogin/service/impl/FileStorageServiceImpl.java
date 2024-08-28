package com.example.userregistrationandlogin.service.impl;

import com.example.userregistrationandlogin.entity.FileStorageEntity;
import com.example.userregistrationandlogin.repository.FileStorageRepository;
import com.example.userregistrationandlogin.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class FileStorageServiceImpl implements FileStorageService {

    @Autowired
    FileStorageRepository fileStorageRepository;

    @Override
    public FileStorageEntity store(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        FileStorageEntity fileStorageEntity = new FileStorageEntity(fileName,file.getContentType(), file.getBytes());
        return fileStorageRepository.save(fileStorageEntity);
    }

    @Override
    public FileStorageEntity getFile(String id) {
        return fileStorageRepository.findById(id).get();
    }
}
