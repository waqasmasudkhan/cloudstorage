package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Users;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserMapper {

    @Insert("INSERT INTO USERS (username,salt,password,firstname,lastname) VALUES (#{userName},#{salt},#{password},#{firstName},#{lastName});")
    @Options(useGeneratedKeys = true, keyProperty = "userId")
    int addUser(Users user);

    @Select("SELECT * FROM USERS WHERE username=#{userName};")
    Users getUser(String userName);

    @Select("SELECT password FROM USERS WHERE username=#{userName};")
    String getPassword(String userName);

    @Select("SELECT username FROM USERS WHERE username=#{userName};")
    String isUsernameAvailable(Users user);

    @Select("Select userId FROM USERS WHERE username=#{userName};")
    int getUserId(String userName);

}
