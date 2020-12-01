package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.model.Files;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping
public class CredentialController {

    CredentialService credentialService;
    EncryptionService encryptionService;
    NoteService noteService;
    UserService userService;
    FileService fileService;



    public CredentialController(CredentialService credentialService, EncryptionService encryptionService, NoteService noteService, UserService userService, FileService fileService){
        this.credentialService=credentialService;
        this.encryptionService=encryptionService;
        this.noteService=noteService;
        this.userService=userService;
        this.fileService=fileService;
    }

    @PostMapping("/credentials")
    public String postHome(@ModelAttribute("currentCredentials") Credentials credentials, @ModelAttribute("currentNote") Note note, Model model, Authentication authentication){
        model.addAttribute("encryptionService", encryptionService);
        authentication = SecurityContextHolder.getContext().getAuthentication();
        int userId = credentialService.getUserId((String)authentication.getPrincipal());
        String userName= (String)authentication.getPrincipal();
        credentials.setUserId(userId);
        if(credentials.getCredentialId()!=0){
            if(credentialService.updateCredential(credentials)>0){
                model.addAttribute("credentialsUpdateSuccess", true);
            }
            else{
                model.addAttribute("credentialsUpdateError", true);
            }
        }else{
            if(credentialService.newCredential(credentials)>0){
                model.addAttribute("credentialsSavedSuccess", true);
            }
            else{
                model.addAttribute("credentialsSavedError", true);
            }
        }
        List<Credentials> allCredentials = new ArrayList<Credentials>();
        allCredentials = credentialService.getAllCredentials((String)authentication.getPrincipal());
        if(!allCredentials.isEmpty()) {
            model.addAttribute("credentialsList", allCredentials);
        }
        model.addAttribute("displayUrl",allCredentials);
        if(!noteService.getAllNotes(userName).isEmpty()){
            model.addAttribute("notesList",noteService.getAllNotes(userName));
        }

        List<Files> allFiles = new ArrayList<Files>();
        allFiles = fileService.getAllFiles(userName);

        if(allFiles.isEmpty()) {
            model.addAttribute("filesList", allFiles);
        }

        return "result.html";
    }


    @GetMapping("/credential/delete")
    public String deleteCredential(@ModelAttribute("currentCredentials")Credentials credentials, @ModelAttribute("currentNote") Note note, @RequestParam(value="credentialId", required=true) Integer credentialId, Model model, Authentication authentication){
        authentication= SecurityContextHolder.getContext().getAuthentication();
        String userName= (String)authentication.getPrincipal();
        System.out.println(userName);
        if(!noteService.getAllNotes(userName).isEmpty()){
            model.addAttribute("notesList",noteService.getAllNotes(userName));
        }

        System.out.println("The credential ID is"+credentials.getCredentialId());
        model.addAttribute("encryptionService", encryptionService);
        List<Credentials> allCredentials = new ArrayList<Credentials>();
        allCredentials = credentialService.getAllCredentials((String)authentication.getPrincipal());
        System.out.println(allCredentials.isEmpty());
        if(!allCredentials.isEmpty()) {
            model.addAttribute("credentialsList", allCredentials);
        }
        model.addAttribute("displayUrl",allCredentials);
        if(credentialService.deleteCredential(credentials.getCredentialId())!=0){
            model.addAttribute("credentialsDeleteSuccess",true);
        }else{
            model.addAttribute("credentialsDeleteError",true);
        }
        return "result.html";
    }

}
