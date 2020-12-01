package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
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
public class NoteController {

    CredentialService credentialService;
    EncryptionService encryptionService;
    NoteService noteService;
    UserService userService;
    FileService fileService;



    public NoteController(CredentialService credentialService, EncryptionService encryptionService, NoteService noteService, UserService userService, FileService fileService){
        this.credentialService=credentialService;
        this.encryptionService=encryptionService;
        this.noteService=noteService;
        this.userService=userService;
        this.fileService=fileService;
    }

    @PostMapping("/addupdatenotes")
    public String addUpdateNote(@ModelAttribute("currentCredentials") Credentials credentials, @ModelAttribute("currentNote") Note note, Model model, Authentication authentication){
        model.addAttribute("encryptionService", encryptionService);
        authentication= SecurityContextHolder.getContext().getAuthentication();
        String userName= (String)authentication.getPrincipal();
        note.setUserId(userService.getUserId((String)authentication.getPrincipal()));

        if(!noteService.getAllNotes(userName).isEmpty()){
            model.addAttribute("notesList",noteService.getAllNotes(userName));
        }

        if(note.getNoteId()==null) {
            if (noteService.addNote(note) > 0) {
                model.addAttribute("notesSavedSuccess",true);
            } else {
                model.addAttribute("notesSavedError",true);
            }
        }
        else{
            if (noteService.updateNote(note)>0){
                model.addAttribute("notesSavedSuccess",true);
            }
            else{
                System.out.println("The Note has not been updated successfully");
            }
        }

        List<Credentials> allCredentials = new ArrayList<Credentials>();
        allCredentials = credentialService.getAllCredentials((String)authentication.getPrincipal());
        if(!allCredentials.isEmpty()) {
            model.addAttribute("credentialsList", allCredentials);
        }
        return "result.html";
    }


    @GetMapping("/notes/delete")
    public String deleteNote(@ModelAttribute("currentCredentials")Credentials credentials, @ModelAttribute("currentNote") Note note, @RequestParam(value="noteId", required=true) Integer noteId, Model model, Authentication authentication){
        authentication= SecurityContextHolder.getContext().getAuthentication();
        String userName= (String)authentication.getPrincipal();
        if(!noteService.getAllNotes(userName).isEmpty()){
            model.addAttribute("notesList",noteService.getAllNotes(userName));
        }
        List<Credentials> allCredentials = new ArrayList<Credentials>();
        allCredentials = credentialService.getAllCredentials((String)authentication.getPrincipal());
        if(!allCredentials.isEmpty()) {
            model.addAttribute("credentialsList", allCredentials);
        }
        model.addAttribute("displayUrl",allCredentials);
        model.addAttribute("encryptionService", encryptionService);
        if(noteService.deleteNote(note.getNoteId())!=0){
            model.addAttribute("notesDeleteSuccess",true);
        }else{
            model.addAttribute("notesDeleteError",true);
        }
        return "result.html";
    }


}
