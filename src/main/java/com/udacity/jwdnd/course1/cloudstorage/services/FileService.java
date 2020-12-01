package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Files;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileService {
    FileMapper fileMapper;

    public FileService(FileMapper fileMapper){
        this.fileMapper=fileMapper;
    }

    public boolean isFileNameAvailable(String fileName){

        if(fileMapper.fileNameAvailable(fileName)!=null){
            return true;
        }
        else {
            return false;
        }
    }

    public void uploadFile(MultipartFile multipartFile, Integer userId) throws IOException {
        try {
            Files newFile = new Files(null,multipartFile.getOriginalFilename(), multipartFile.getContentType(), multipartFile.getSize(), userId, multipartFile.getBytes());
            System.out.println("Name of the file: "+newFile.getFileName()+" and file contents : "+newFile.getContentType()+" and file size "+newFile.getFileSize()+" and userId "+newFile.getUserId());
            int result =fileMapper.uploadFiles(newFile);
            System.out.println("The file was successfully uploaded! result is:"+result);
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    public Integer deleteFile(Integer fileId){
        return fileMapper.deleteFileById(fileId);
    }

    public Files downloadFile(Integer fileId){
        return fileMapper.downloadFileById(fileId);
    }


    public List<Files> getAllFiles(String userName){
        List<Files> totalFiles = new ArrayList<Files>();
        totalFiles = fileMapper.getAllFiles(userName);
        return totalFiles;
    }



}
