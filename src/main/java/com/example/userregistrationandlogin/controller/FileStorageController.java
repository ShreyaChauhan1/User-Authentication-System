package com.example.userregistrationandlogin.controller;

import com.example.userregistrationandlogin.dto.ResponseData;
import com.example.userregistrationandlogin.entity.FileStorageEntity;
import com.example.userregistrationandlogin.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api")
public class FileStorageController {

    @Autowired
    private FileStorageService fileStorageService;

    @PostMapping("/upload")
    public ResponseEntity<ResponseData> uploadFile(@RequestParam("file") MultipartFile file){
            ResponseData res = new ResponseData();
            String message = "";
        try {
            fileStorageService.store(file);
            message = "File upload successfully : " + file.getOriginalFilename();
             res.setData(message);
        }catch (Exception e){
            message = "Could not upload the file : " + file.getOriginalFilename() + "!";
            res.setData(message);
        }
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> getFile(@PathVariable String id){
       FileStorageEntity fileStorageEntity =  fileStorageService.getFile(id);
        ResponseData res = new ResponseData();
        res.setData("");
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileStorageEntity.getName() + "\"")
                .body(fileStorageEntity.getData());
    }

//
//    @GetMapping("/files/{id}")
//    public ResponseEntity<byte[]> getFile(@PathVariable String id) {
//        FileDB fileDB = storageService.getFile(id);
//
//        return ResponseEntity.ok()
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileDB.getName() + "\"")
//                .body(fileDB.getData());
//    }
}
