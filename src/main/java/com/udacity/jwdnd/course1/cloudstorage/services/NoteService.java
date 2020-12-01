package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {
    NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper){
        this.noteMapper=noteMapper;
    }

    public Integer getNoteId(String userName){
        return noteMapper.getNoteId(userName);
    }

    public Integer addNote(Note note){
        return noteMapper.addNote(note);
    }

    public Integer updateNote(Note note){
        return noteMapper.updateNote(note);
    }

    public Integer deleteNote(Integer noteId){
        return noteMapper.deleteNote(noteId);
    }

    public List<Note> getAllNotes(String userName){
        return noteMapper.getAllNotes(userName);
    }

}
