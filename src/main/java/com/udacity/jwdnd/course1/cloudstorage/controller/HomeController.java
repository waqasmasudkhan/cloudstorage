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
public class HomeController {
    CredentialService credentialService;
    EncryptionService encryptionService;
    NoteService noteService;
    UserService userService;
    FileService fileService;



    public HomeController(CredentialService credentialService, EncryptionService encryptionService, NoteService noteService, UserService userService, FileService fileService){
        this.credentialService=credentialService;
        this.encryptionService=encryptionService;
        this.noteService=noteService;
        this.userService=userService;
        this.fileService=fileService;
    }

    @GetMapping("/home")
    public String getHome(@ModelAttribute("currentCredentials") Credentials credentials,@ModelAttribute("currentNote") Note note, Model model, Authentication authentication){
        model.addAttribute("encryptionService", encryptionService);
        authentication = SecurityContextHolder.getContext().getAuthentication();
        List<Credentials> allCredentials = new ArrayList<Credentials>();
        allCredentials = credentialService.getAllCredentials((String)authentication.getPrincipal());
        String userName= (String)authentication.getPrincipal();
        List<Files> allFiles = new ArrayList<Files>();
        allFiles = fileService.getAllFiles(userName);
        if(!allCredentials.isEmpty()) {
            model.addAttribute("credentialsList", allCredentials);
        }

        if(!noteService.getAllNotes(userName).isEmpty()){
            model.addAttribute("notesList",noteService.getAllNotes(userName));
        }

        if(!allFiles.isEmpty()) {
            model.addAttribute("filesList", allFiles);
        }
        return "home";
    }
}
