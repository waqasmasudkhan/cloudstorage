package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private static WebDriver driver;
	private Login login= new Login(driver);
	private SignUp signup= new SignUp(driver);
	private Home home = new Home(driver);
	private Result result = new Result(driver);



	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
	}

	@BeforeEach
	public void beforeEach() throws InterruptedException {
		//this.driver = new ChromeDriver();
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
		//	driver.quit();
		}
	}

	/*@AfterAll
	public static void afterAll(){
		if(this.driver != null){
			driver.quit();
		}
	}*/

//A test that verifies that an unauthorized user can only access the login and signup pages.

	@Test
	public void getLoginPage() {
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	public void getSignUpPage() {
		driver.get("http://localhost:" + this.port + "/signup");
		Assertions.assertEquals("Sign Up", driver.getTitle());
	}


	@Test
	public void loginSignUpNotesTest() throws InterruptedException {
		//A test that signs up a new user, logs in, verifies that the home page is accessible, logs out, and verifies that the home page is no longer accessible.
		driver.get("http://localhost:" + this.port + "/signup");
		Thread.sleep(3000);
		signup.setFirstName("testFirstName");
		signup.setLastName("testLastName");
		signup.setUserName("testUserName");
		signup.setPassword("test");
		signup.clickSignUpButton();
		Thread.sleep(2000);
		login.setUserName("testUserName");
		login.setPassword("test");
		login.clickSubmit();
		Thread.sleep(5000);
		Assertions.assertEquals("Home", driver.getTitle());
		home.clickLogoutButton();
		driver.get("http://localhost:" + this.port + "/home");
		Thread.sleep(5000);
		Assertions.assertNotEquals("Home", driver.getTitle());

	//A test that creates a note, and verifies it is displayed.

		driver.get("http://localhost:" + this.port + "/login");
		Thread.sleep(3000);
		login.setUserName("testUserName");
		login.setPassword("test");
		login.clickSubmit();
		Thread.sleep(5000);
		home.clickOnNotesTab();
		Thread.sleep(2000);
		home.AddNotesButton();
		Thread.sleep(2000);
		home.setNotesTitle("This is a note");
		home.setNotesDescription("Lots of notes !!!!");
		home.clickSaveNoteChangesButton();
		Thread.sleep(2000);
		result.clickNotesSavedSuccess();
		Thread.sleep(2000);
		home.clickOnNotesTab();
		Thread.sleep(3000);
		Assertions.assertEquals(true, home.noteExists("This is a note","Lots of notes !!!!"));

		//A test that edits an existing note and verifies that the changes are displayed.

		driver.get("http://localhost:" + this.port + "/login");
		Thread.sleep(3000);
		login.setUserName("testUserName");
		login.setPassword("test");
		login.clickSubmit();
		Thread.sleep(5000);
		home.clickOnNotesTab();
		Thread.sleep(2000);
		home.clickEditNotesButton();
		Thread.sleep(2000);
		home.notesEdit("This is a title","This is a description");
		home.clickSaveNoteChangesButton();
		Thread.sleep(2000);
		result.clickNotesSavedSuccess();
		Thread.sleep(2000);
		home.clickOnNotesTab();
		Thread.sleep(3000);
		Assertions.assertEquals(true, home.noteExists("This is a title","This is a description"));

	    //A test that deletes a note and verifies that the note is no longer displayed.

		driver.get("http://localhost:" + this.port + "/login");
		Thread.sleep(3000);
		login.setUserName("testUserName");
		login.setPassword("test");
		login.clickSubmit();
		Thread.sleep(5000);
		home.clickOnNotesTab();
		Thread.sleep(2000);
		home.clickDeleteNotesButton();
		Thread.sleep(3000);
		result.clickNotesDeleteSuccess();
		Thread.sleep(3000);
		home.clickOnNotesTab();
		Thread.sleep(2000);
		Assertions.assertEquals(false,home.noteExists("This is a title","This is a description"));
	}


	@Test
	public void credentialsTest() throws InterruptedException {
		//A test that creates a set of credentials, verifies that they are displayed, and verifies that the displayed password is encrypted.
		driver.get("http://localhost:" + this.port + "/signup");
		Thread.sleep(3000);
		signup.setFirstName("Waqas");
		signup.setLastName("Khan");
		signup.setUserName("waqas");
		signup.setPassword("waqas");
		signup.clickSignUpButton();
		Thread.sleep(2000);
		login.setUserName("waqas");
		login.setPassword("waqas");
		login.clickSubmit();
		Thread.sleep(5000);
		home.setClickCredentialTab();
		Thread.sleep(3000);
		home.clickAddCredentialsButton();
		Thread.sleep(2000);
		home.setCredentialUrl("www.facebook.com");
		home.setCredentialUserName("testUser");
		home.setCredentialPassword("testPassword");
		Thread.sleep(2000);
		home.clickSaveCredentialsButton();
		Thread.sleep(3000);
		result.clickCredentialsSavedSuccessLink();;
		Thread.sleep(3000);
		home.setClickCredentialTab();
		Thread.sleep(3000);
		Assertions.assertEquals(true,home.credentialExists("www.facebook.com","testUser"));
		Assertions.assertEquals(true,home.credentialEncrypted("testPassword"));

	//A test that views an existing set of credentials, verifies that the viewable password is unencrypted, edits the credentials, and verifies that the changes are displayed.

		driver.get("http://localhost:" + this.port + "/login");
		Thread.sleep(2000);
		login.setUserName("waqas");
		login.setPassword("waqas");
		login.clickSubmit();
		Thread.sleep(5000);
		home.setClickCredentialTab();
		Thread.sleep(3000);
		home.clickEditCredentialButton();
		Thread.sleep(2000);
		Assertions.assertEquals(true,home.credentialEditUnencrypted("testPassword"));
		home.clearEditCredential("www.facebook.com", "user","test");
		Thread.sleep(1000);
		home.clickSaveCredentialsButton();
		Thread.sleep(3000);
		result.clickCredentialsEditSuccessfulLink();
		Thread.sleep(2000);
		home.setClickCredentialTab();
		Thread.sleep(2000);
		Assertions.assertEquals(true,home.credentialExists("www.facebook.com", "user"));

	//A test that deletes an existing set of credentials and verifies that the credentials are no longer displayed.

		driver.get("http://localhost:" + this.port + "/login");
		Thread.sleep(2000);
		login.setUserName("waqas");
		login.setPassword("waqas");
		login.clickSubmit();
		Thread.sleep(5000);
		home.setClickCredentialTab();
		Thread.sleep(3000);
		home.clickDeleteCredentialButton();
		Thread.sleep(2000);
		result.clickCredentialsDeleteSuccessfulLink();
		Thread.sleep(2000);
		home.setClickCredentialTab();
		Thread.sleep(2000);
		Assertions.assertEquals(false,home.credentialExists("www.facebook.com", "user"));
	}



}
