package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface NoteMapper {

    @Insert("INSERT INTO NOTES (noteTitle, noteDescription, userId) VALUES (#{noteTitle},#{noteDescription},#{userId});")
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    int addNote(Note note);

    @Update("UPDATE NOTES SET noteTitle=#{noteTitle},noteDescription=#{noteDescription}, userId=#{userId} WHERE noteId=#{noteId};")
    int updateNote(Note note);

    @Delete("DELETE FROM NOTES WHERE noteId=#{noteId};")
    Integer deleteNote(Integer noteId);

    @Select("SELECT COUNT(noteId) FROM NOTES INNER JOIN USERS on notes.userId=USERS.userId WHERE USERS.userName=#{userName};")
    Integer getNoteId(String userName);

    @Select("Select * from NOTES INNER JOIN USERS on notes.userId=USERS.userId WHERE USERS.userName=#{userName};")
    List<Note> getAllNotes(String userName);

}
