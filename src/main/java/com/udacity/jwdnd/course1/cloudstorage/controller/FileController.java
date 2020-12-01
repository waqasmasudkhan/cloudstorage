package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.model.Files;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class FileController {
    private FileService fileService;
    private UserService userService;

    public FileController(FileService fileService, UserService userService) throws IOException{
        this.fileService=fileService;
        this.userService=userService;
    }

    @PostMapping("file-upload")
    public String uploadFile(@RequestParam("fileUpload")MultipartFile multipartFile, @ModelAttribute("currentCredentials")Credentials credentials, @ModelAttribute("currentNote") Note note, Model model, Authentication authentication){
        authentication= SecurityContextHolder.getContext().getAuthentication();
        String userName = (String)authentication.getPrincipal();
        Integer userId = userService.getUserId(userName);
        if(multipartFile.getSize()!=0) {
            try {
                if (!fileService.isFileNameAvailable(multipartFile.getOriginalFilename())) {
                    fileService.uploadFile(multipartFile, userId);
                    model.addAttribute("fileUploadSuccess", true);
                } else {
                    model.addAttribute("fileNameError", true);
                }
            } catch (IOException e) {
                e.printStackTrace();
                model.addAttribute("error", e);
            }
        }else{
            model.addAttribute("fileEmptyUploadError",true);
        }

        List<Files> allFiles = new ArrayList<Files>();
        allFiles = fileService.getAllFiles(userName);

        if(allFiles.isEmpty()) {
            model.addAttribute("filesList", allFiles);
        }
        return "result.html";
    }

    @GetMapping("/files/delete")
    public String deleteFile(@ModelAttribute("currentCredentials") Credentials credentials, @ModelAttribute("currentNote") Note note,@ModelAttribute("file") Files file,@RequestParam(value="fileId", required=true) Integer fileId, Model model, Authentication authentication){
        Integer modifiedRows = fileService.deleteFile(fileId);
        if(modifiedRows!=0){
            model.addAttribute("deleteFileSuccess",true);
        }else{
            model.addAttribute("deleteFileError",false);
        }
        return "result.html";
    }

    @GetMapping("/files/download")
    public ResponseEntity downloadFile(@RequestParam(value="fileId", required=true) Integer fileId, Model model, Authentication authentication){
        Files newFile = fileService.downloadFile(fileId);
        System.out.println(newFile.getFileId()+" "+newFile.getFileName()+" "+newFile.getFileSize()+" "+newFile.getContentType()+" "+newFile.getUserId());
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(newFile.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+newFile.getFileName()+"\"")
                .body(newFile.getFileData());
    }


}
