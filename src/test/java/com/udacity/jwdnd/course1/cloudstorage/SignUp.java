package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignUp {

    @FindBy(id="inputFirstName")
    private WebElement firstName;

    @FindBy(id="inputLastName")
    private WebElement lastName;

    @FindBy(id="inputUserName")
    private WebElement userName;

    @FindBy(id="inputPassword")
    private WebElement password;

    @FindBy(id="signUpButton")
    private WebElement clickSignUpButton;

    @FindBy(id="backToLoginLink")
    private WebElement backToLoginLink;

    public SignUp(WebDriver driver){
        PageFactory.initElements(driver,this);
    }


    public void setFirstName(String fName){
        firstName.sendKeys(fName);
    }

    public void setLastName(String lName){
        lastName.sendKeys(lName);
    }

    public void setUserName(String uName){
        userName.sendKeys(uName);
    }

    public void setPassword(String passwd){
        password.sendKeys(passwd);
    }

    public void clickSignUpButton(){
        clickSignUpButton.click();
    }

    public void clickBackToLoginLink(){
        backToLoginLink.click();
    }

}
