package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Login {

    @FindBy(id="inputUsername")
    private WebElement userName;

    @FindBy(id="inputPassword")
    private WebElement password;

    @FindBy(id="submitButtonLogin")
    private WebElement loginButton;

    @FindBy(id="signUpLink")
    private WebElement signUpLink;

    public Login(WebDriver driver){
        PageFactory.initElements(driver,this);
    }

    public void setUserName(String uName){
        userName.sendKeys(uName);
    }

    public void setPassword(String passwd){
        password.sendKeys(passwd);
    }

    public void clickSubmit(){
        loginButton.click();
    }

    public void clickSignUpLink(){
        signUpLink.click();
    }


}
