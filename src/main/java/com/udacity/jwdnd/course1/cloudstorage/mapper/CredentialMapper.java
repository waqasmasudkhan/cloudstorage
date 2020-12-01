package com.udacity.jwdnd.course1.cloudstorage.mapper;


import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CredentialMapper {
    @Insert("INSERT INTO CREDENTIALS (url,userName,key,password,userId) VALUES(#{url},#{userName},#{key},#{password},#{userId});")
    int addCredentials(Credentials credentials);
    @Options(useGeneratedKeys=true, keyProperty = "credentialId")

    @Update("UPDATE CREDENTIALS SET url=#{url},userName=#{userName}, key=#{key}, password=#{password} where credentialId=#{credentialId};")
    int updateCredentials(Credentials credentials);

    @Delete("Delete from CREDENTIALS where credentialId=#{credentialId};")
    Integer deleteCredentials(Integer credentialId);

    @Select("SELECT * FROM CREDENTIALS WHERE userName=#{userName};")
    Credentials getCredentials();

    @Select("SELECT userId FROM USERS WHERE userName=#{userName};")
    int getUserId(String userName);

    @Select("Select credentialId from credentials inner join USERS on credentials.userId=users.userId where users.username=#{userName};")
    int getCredentialId(String userId);

    @Select("Select * from credentials where credentialId=#{credentialId};")
    Credentials getUserByCredentialId(int credentialId);

    @Select("SELECT key_val from credentials where credentialId=#{credentialId};")
    String getCredentialKeyVal(int credentialId);

    @Select("SELECT * FROM CREDENTIALS inner join USERS on credentials.userId=users.userId where users.username=#{userName};")
    List<Credentials> getAllCredentials(String userName);

    @Select("SELECT url FROM CREDENTIALS;")
    List<String> getAllUrlCredentials();

    @Select("SELECT username FROM CREDENTIALS;")
    List<String> getAllUsernameCredentials();

    @Select("SELECT password FROM CREDENTIALS;")
    List<String> getAllPasswordCredentials();

}
