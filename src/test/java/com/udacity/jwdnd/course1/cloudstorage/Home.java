package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;

public class Home {

    @FindBy(id="credential-url")
    private WebElement credentialUrl;

    @FindBy(id="credential-username")
    private WebElement credentialUserName;

    @FindBy(id="nav-credentials-tab")
    private WebElement clickCredentialTab;

    @FindBy(id="credential-password")
    private WebElement credentialPassword;

    @FindBy(id="addCredentialsButton")
    private WebElement addCredentialsButton;

    @FindBy(id="saveCredentials")
    private WebElement saveCredentialsButton;

    @FindBy(id="closeCredentialButton")
    private WebElement closeCredentialButton;

    @FindBy(id="editCredential")
    private WebElement editCredentialButton;

    @FindBy(id="deleteCredential")
    private WebElement deleteCredentialButton;

    @FindBy(id="nav-notes-tab")
    private WebElement clickNotesTab;

    @FindBy(id="createNotesButton")
    private WebElement createNotesButton;

    @FindBy(id="note-title")
    private WebElement setNoteTitle;

    @FindBy(id="note-description")
    private WebElement setNoteDescription;

    @FindBy(id="save-note-changes")
    private WebElement saveNoteChanges;

    @FindBy(id="close-note-changes")
    private WebElement closeNoteChanges;

    @FindBy(id="notes-edit-button")
    private WebElement editNotesButton;

    @FindBy(id="notes-delete-button")
    private WebElement deleteNotesButton;

    @FindBy(xpath = "//*[@id=\"userTable\"]/tbody/tr/th")
    private List<WebElement> notesTitle= new ArrayList<WebElement>();

    @FindBy(xpath = "//*[@id=\"userTable\"]/tbody/tr/td[2]")
    private List<WebElement> notesDescription= new ArrayList<WebElement>();

    @FindBy(xpath="//*[@id=\"credentialTable\"]/tbody/tr/th")
    private List<WebElement> credentialUrlArray = new ArrayList<WebElement>();

    @FindBy(xpath="//*[@id=\"credentialTable\"]/tbody/tr/td[2]")
    private List<WebElement> credentialUsernameArray = new ArrayList<WebElement>();

    @FindBy(xpath = "//*[@id=\"credentialTable\"]/tbody/tr/td[3]")
    private List<WebElement> credentialPasswordArray = new ArrayList<WebElement>();

    @FindBy(xpath = "//*[@id=\"credential-password\"]")
    private WebElement credentialPassword12;

    @FindBy(id="logoutButton")
    private WebElement logoutButton;

    public Home(WebDriver driver){
        PageFactory.initElements(driver,this);
    }

    //Credentials methods
    public void setCredentialUrl(String url) {
        credentialUrl.sendKeys(url);
    }

    public void setCredentialUserName(String userName) {
        credentialUserName.sendKeys(userName);
    }

    public void setCredentialPassword(String password) {
        credentialPassword.sendKeys(password);
    }

    public void setClickCredentialTab(){
        clickCredentialTab.click();
    }

    public void clickAddCredentialsButton(){
        addCredentialsButton.click();
    }

    public void clickSaveCredentialsButton(){
        saveCredentialsButton.click();
    }

    public void clickCloseCredentialButton(){
        closeCredentialButton.click();
    }

    public void clickEditCredentialButton(){
        editCredentialButton.click();
    }

    public void clickDeleteCredentialButton(){
        deleteCredentialButton.click();
    }

    //Notes methods

    public void clickOnNotesTab(){
        clickNotesTab.click();
    }

    public void AddNotesButton(){
        createNotesButton.click();
    }

    public void setNotesTitle(String title){
        setNoteTitle.sendKeys(title);
    }

    public void setNotesDescription(String description){
        setNoteDescription.sendKeys(description);
    }

    public void clickSaveNoteChangesButton(){
        saveNoteChanges.click();
    }

    public void clickCloseNotesTab(){
        closeNoteChanges.click();
    }

    public void clickEditNotesButton(){
        editNotesButton.click();
    }

    public void clickDeleteNotesButton(){
        deleteNotesButton.click();
    }

    public boolean noteExists(String title, String description){
        int titleCount = notesTitle.size();
        int descriptionCount = notesDescription.size();
        for(int j=0;j<titleCount;j++){
            System.out.println(notesTitle.get(j).getText()+" "+notesDescription.get(j).getText());

            if(notesTitle.get(j).getText().equalsIgnoreCase(title) && notesDescription.get(j).getText().equals(description)){
                return true;
            }
        }
        return false;
    }

    public void notesEdit(String title, String description){
        setNoteTitle.clear();
        setNoteDescription.clear();
        setNoteTitle.sendKeys(title);
        setNoteDescription.sendKeys(description);
    }

    public boolean credentialExists(String url, String userName){
        int credentialTableSize = credentialUrlArray.size();
        for(int i=0; i<credentialTableSize;i++){
            System.out.println("URL is "+credentialUrlArray.get(i).getText()+" Username is "+credentialUsernameArray.get(i).getText());
            if(credentialUrlArray.get(i).getText().equalsIgnoreCase(url)&&credentialUsernameArray.get(i).getText().equalsIgnoreCase(userName)){
                return true;
            }
        }
        return false;
    }

    public boolean credentialEncrypted(String password){
        int credentialTableSize = credentialUrlArray.size();
        for(int i=0; i<credentialTableSize;i++){
            if(credentialPasswordArray.get(i).getText().equalsIgnoreCase(password)){
                return false;
            }
        }
        return true;
    }

    public boolean credentialEditUnencrypted(String password){
        if(credentialPassword.getAttribute("value").equalsIgnoreCase(password)) {
            return true;
        }
        return false;
    }

    public void clearEditCredential(String url, String userName, String password){
        credentialUrl.clear();
        credentialUserName.clear();
        credentialPassword.clear();
        credentialUrl.sendKeys(url);
        credentialUserName.sendKeys(userName);
        credentialPassword.sendKeys(password);
    }

    public void clickLogoutButton(){
        logoutButton.click();
    }

}
