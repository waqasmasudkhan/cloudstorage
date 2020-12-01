package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Users;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class UserService {

    private UserMapper userMapper;

    public UserService(UserMapper userMapper){
        this.userMapper=userMapper;
    }

    public int addUser(Users user){
        byte[] salt = new byte[16];
        Random randomNo = new Random();
        randomNo.nextBytes(salt);
        String encodedSalt = Base64.encodeBase64String(salt);
        HashService hashService = new HashService();
        String hashedPassword = hashService.getHashedValue(user.getPassword(),encodedSalt);
        user.setPassword(hashedPassword);
        user.setSalt(encodedSalt);
        System.out.println(user.getUserName()+user.getSalt()+user.getPassword()+user.getFirstName()+user.getLastName());
        return userMapper.addUser(user);
    }

    public int getUserId(String userName){
        return userMapper.getUserId(userName);
    }

    public boolean isUsernameAvailable(Users user){
        return userMapper.isUsernameAvailable(user)==null?true:false;
    }

}
