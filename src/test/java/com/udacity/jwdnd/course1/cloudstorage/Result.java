package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Result {
    @FindBy(id="notesSavedSuccess")
    private WebElement notesSavedSuccess;

    @FindBy(id="notesUpdateSuccess")
    private WebElement notesUpdateSuccess;

    @FindBy(id="notes-delete-success")
    private WebElement notesDeletesSuccess;

    @FindBy(id="credentials-saved-success")
    private WebElement credentialsSavedSuccess;

    @FindBy(id="credentials-update-success")
    private WebElement credentialsUpdateSuccess;

    @FindBy(id="credentials-delete-success")
    private WebElement credentialsDeleteSuccess;

    public Result(WebDriver driver){
        PageFactory.initElements(driver,this);
    }

    public void clickNotesSavedSuccess(){
        notesSavedSuccess.click();
    }

    public void clickNotesUpdateSuccess(){
        notesUpdateSuccess.click();
    }

    public void clickNotesDeleteSuccess(){
        notesDeletesSuccess.click();
    }

    public void clickCredentialsSavedSuccessLink(){
        credentialsSavedSuccess.click();
    }

    public void clickCredentialsEditSuccessfulLink(){
        credentialsUpdateSuccess.click();
    }

    public void clickCredentialsDeleteSuccessfulLink(){
        credentialsDeleteSuccess.click();
    }


}
