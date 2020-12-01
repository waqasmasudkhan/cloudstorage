package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Users;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AuthenticationService implements AuthenticationProvider {
    private UserMapper userMapper;
    private HashService hashService;

    public AuthenticationService(UserMapper userMapper,HashService hashService){
        this.userMapper=userMapper;
        this.hashService=hashService;
    }



    @Override
    public Authentication authenticate(Authentication auth) throws AuthenticationException {
        String userName = auth.getName();
        String password = auth.getCredentials().toString();
        Users users = userMapper.getUser(userName);
        if(users!=null){
            String encodedSalt = users.getSalt();
            String hashedPassword = hashService.getHashedValue(password,encodedSalt);
            if(userMapper.getPassword(userName).equals(hashedPassword)){
                return new UsernamePasswordAuthenticationToken(userName,password,new ArrayList<>());
            }
        }
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication){
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
