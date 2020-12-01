package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialService {
    private EncryptionService encryptionService;
    private CredentialMapper credentialMapper;


    public CredentialService(EncryptionService encryptionService, CredentialMapper credentialMapper){
        this.encryptionService=encryptionService;
        this.credentialMapper=credentialMapper;
    }

    public int newCredential(Credentials credentials){
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        String encryptedPassword = encryptionService.encryptValue(credentials.getPassword(), encodedKey);
        credentials.setKey_val(encodedKey);
        credentials.setPassword(encryptedPassword);
        return credentialMapper.addCredentials(credentials);
    }

    public int updateCredential(Credentials credentials){
        System.out.println("CredentialId: "+credentials.getCredentialId()+" url "+credentials.getUrl()+" "+credentials.getUserName()+" "+credentials.getKey_val()+" "+credentials.getPassword()+" "+credentials.getUserId());
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        String encryptedPassword = encryptionService.encryptValue(credentials.getPassword(), encodedKey);
        credentials.setKey_val(encodedKey);
        credentials.setPassword(encryptedPassword);
        return credentialMapper.updateCredentials(credentials);
    }

    public Credentials getCredentials(Credentials credentials){
        credentials =credentialMapper.getCredentials();
        String decryptedPassword = encryptionService.decryptValue(credentials.getPassword(), credentials.getKey_val());
        credentials.setPassword(decryptedPassword);
        return credentials;
    }

    public int getUserId(String userName){
        return credentialMapper.getUserId(userName);
    }

    public int getCredentialsId(String userId){
        return credentialMapper.getCredentialId(userId);
    }

    public Integer deleteCredential(Integer credentialId){

        return credentialMapper.deleteCredentials(credentialId);
    }

    public Credentials getUserByCredentialId(int credentialId){
        return credentialMapper.getUserByCredentialId(credentialId);
    }

    public List<Credentials> getAllCredentials(String userName){
        List<Credentials> totalCredentials = new ArrayList<Credentials>();
        totalCredentials = credentialMapper.getAllCredentials(userName);
        return totalCredentials;
    }

    public List<String> getAllUrlCredentials(){
        List<String> urlAllCredentials = new ArrayList<String>();
        urlAllCredentials = credentialMapper.getAllUrlCredentials();
        return urlAllCredentials;
    }

    public List<String> getAllUsernameCredentials(){
        List<String> getAllUsernameCredentials = new ArrayList<String>();
        getAllUsernameCredentials = credentialMapper.getAllUsernameCredentials();
        return getAllUsernameCredentials;
    }

    public List<String> getAllPasswordCredentials(){
        List<String> getAllPasswordCredentials = new ArrayList<String>();
        getAllPasswordCredentials = credentialMapper.getAllPasswordCredentials();
        return getAllPasswordCredentials;
    }

}
