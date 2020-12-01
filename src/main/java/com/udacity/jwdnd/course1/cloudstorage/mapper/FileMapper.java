package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Files;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface FileMapper {
    @Insert("INSERT INTO FILES (fileId,fileName,contentType,fileSize,userId,fileData) VALUES (#{fileId},#{fileName},#{contentType},#{fileSize},#{userId},#{fileData});")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int uploadFiles(Files file);

    @Delete("Delete FROM FILES WHERE fileId=#{fileId};")
    Integer deleteFileById(Integer fileId);

    @Select("Select * FROM FILES WHERE fileId=#{fileId};")
    Files downloadFileById(Integer fileId);

    @Select("Select 1 from FILES WHERE filename=#{fileName};")
    Integer fileNameAvailable(String fileName);

    @Select("Select * from FILES INNER JOIN USERS on files.userId=USERS.userId WHERE USERS.userName=#{userName};")
    List<Files> getAllFiles(String userName);

}
