package test.event_management;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import data.UserDAO;
import functions.EventManagementFunctions;
import junitparams.FileParameters;
import junitparams.JUnitParamsRunner;

@RunWith(JUnitParamsRunner.class)
public class AdminTest extends EventManagementFunctions {

	public static WebDriver driver;

	String sAppURL, sSharedUIMapPath;

	@Before
	public void setUp() throws Exception {
		System.setProperty("webdriver.chrome.driver", new File("./chromedriver.exe").getCanonicalPath());
		driver = new ChromeDriver();
		prop = new Properties();

//	Load Configuration file
		prop.load(new FileInputStream("./Configuration/event_management_configuration.properties"));
		sAppURL = prop.getProperty("sAppURL");
		sSharedUIMapPath = prop.getProperty("SharedUIMap");

// Load login details
		prop.load(new FileInputStream("./Login/Login.properties"));

//	Load Shared UI Map
		prop.load(new FileInputStream(sSharedUIMapPath));

		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

		populateDbWithValues("admin");
	}

	@After
	public void tearDown() throws Exception {
		// Close chrome driver
		driver.quit();
		// Delete created user
		UserDAO.deleteUser("userT");
		UserDAO.deleteUser("staffT");
	}

	@Test
	public void adminLoginTest() {
		String user = "";
		driver.get(sAppURL);
		String testFileNo = "1";
		String methodName = new Throwable().getStackTrace()[0].getMethodName();
		int screenShoti = 1;
		String screenShotName = testFileNo + methodName;

		login(driver, prop.getProperty("admin_username"), prop.getProperty("admin_password"));
		assertEquals("Welcome", getText(driver, "header"));
		sleep();

		// Open view profile page
		driver.findElement(By.xpath(prop.getProperty("admin_view_profile"))).click();
		String eventSummary = driver.findElement(By.xpath(prop.getProperty("header"))).getText();
		assertEquals("Your Profile", eventSummary);
		sleep();

		// Testing back on viewProfile
		driver.findElement(By.xpath(prop.getProperty("admin_view_profile_back"))).click();
		sleep();
		// Testing if the back button worked, checking homepage button again
		String homepage2 = driver.findElement(By.xpath(prop.getProperty("header"))).getText();
		assertEquals("Welcome", homepage2);
		sleep();

		// Testing logout on viewProfile page
		driver.findElement(By.xpath(prop.getProperty("admin_view_profile"))).click();
		String loginHeader = logout(driver, "admin_view_profile");
		assertEquals("Login Here", loginHeader);
		sleep();

		login(driver, prop.getProperty("admin_username"), prop.getProperty("admin_password"));
		assertEquals("Welcome", getText(driver, "header"));
		sleep();

		// Go to search for User
		click(driver, "admin_search_user");
		assertEquals("Search User", getText(driver, "admin_su_header"));
		sleep();

		// Testing back button on Search User page
		click(driver, "admin_su_back_btn");
		assertEquals("Welcome", getText(driver, "header"));
		sleep();
		// Getting back to the Search User page
		click(driver, "admin_search_user");
		// Enter filters
		driver.findElement(By.xpath(prop.getProperty("admin_su_filters"))).sendKeys(user);
		click(driver, "admin_su_search_btn");
		// assertEquals(user,
		String lastname = driver.findElement(By.xpath(prop.getProperty("admin_su_lastname"))).getText();
		sleep();
		// View Selected User Profile
		click(driver, "admin_su_view_selected_user");
		assertEquals(lastname,
				driver.findElement(By.xpath(prop.getProperty("admin_vs_lastname"))).getAttribute("value"));
		sleep();

		// Testing back on view User Profile page
		click(driver, "admin_vs_back_btn");
		assertEquals("Search User", getText(driver, "admin_su_header"));
		sleep();

		// Again Entering filters on user Search
		driver.findElement(By.xpath(prop.getProperty("admin_su_filters"))).sendKeys(user);
		click(driver, "admin_su_search_btn");
		// View Selected User Profile
		click(driver, "admin_su_view_selected_user");
		assertEquals(lastname,
				driver.findElement(By.xpath(prop.getProperty("admin_vs_lastname"))).getAttribute("value"));
		sleep();
		// Logout
		click(driver, "admin_vs_logout_btn");
		assertEquals("Login Here", getText(driver, "header"));

		// Homepage logout check
		login(driver, prop.getProperty("admin_username"), prop.getProperty("admin_password"));
		loginHeader = logout(driver, "admin");
		assertEquals("Login Here", loginHeader);
	}

	@Test
	@FileParameters("src/excel/adminTest.csv")
	public void searchUserTest(String sno, String user) {
		// populateDbWithValues("admin");
		driver.get(sAppURL);
		String testFileNo = "1";
		String methodName = new Throwable().getStackTrace()[0].getMethodName();
		int screenShoti = 1;
		String screenShotName = testFileNo + methodName + sno;

		login(driver, prop.getProperty("admin_username"), prop.getProperty("admin_password"));
		assertEquals("Welcome", getText(driver, "header"));
		sleep();
		takeScreenshot(driver, screenShotName + (screenShoti++));

		// Go to search for User
		click(driver, "admin_search_user");
		assertEquals("Search User", getText(driver, "admin_su_header"));
		takeScreenshot(driver, screenShotName + (screenShoti++));
		sleep();

		// Enter filters
		driver.findElement(By.xpath(prop.getProperty("admin_su_filters"))).sendKeys(user);
		click(driver, "admin_su_search_btn");
		assertEquals(user, driver.findElement(By.xpath(prop.getProperty("admin_su_lastname"))).getText());
		takeScreenshot(driver, screenShotName + (screenShoti++));

		// View Selected User Profile
		click(driver, "admin_su_view_selected_user");
		assertEquals(user, driver.findElement(By.xpath(prop.getProperty("admin_vs_lastname"))).getAttribute("value"));
		takeScreenshot(driver, screenShotName + (screenShoti++));

		// Logout
		click(driver, "admin_vs_logout_btn");
		assertEquals("Login Here", getText(driver, "header"));
		takeScreenshot(driver, screenShotName + (screenShoti++));
	}

	@Test
	@FileParameters("src/excel/adminModifyProfileTest.csv")
	public void modifyProfileTest(String sNo, String userName, String pass, String lastName, String firstName,
			String utaId, String phoneNumber, String email, String streetNumber, String streetName, String city,
			String state, String zip, String role) {

		driver.get(sAppURL);
		String testFileNo = "2";
		String methodName = new Throwable().getStackTrace()[0].getMethodName();
		int screenShoti = 1;
		String screenShotName = testFileNo + methodName + sNo;

		// Admin Login
		login(driver, prop.getProperty("admin_username"), prop.getProperty("admin_password"));
		assertEquals("Welcome", getText(driver, "header"));
		sleep();

		// Go to search for User
		click(driver, "admin_search_user");
		assertEquals("Search User", getText(driver, "admin_su_header"));
//		takeScreenshot(driver, screenShotName + (screenShoti++));
		sleep();

		// Enter filters
		driver.findElement(By.xpath(prop.getProperty("admin_su_filters"))).sendKeys(lastName);
		click(driver, "admin_su_search_btn");
		assertEquals(lastName, driver.findElement(By.xpath(prop.getProperty("admin_su_lastname"))).getText());
		sleep();

		// View Selected User Profile
		click(driver, "admin_su_view_selected_user");
		assertEquals(lastName,
				driver.findElement(By.xpath(prop.getProperty("admin_vs_lastname"))).getAttribute("value"));
		sleep();

		// Change values
		clearAndSendKeys(driver, "admin_vs_lastname", lastName);
		clearAndSendKeys(driver, "admin_vs_firstname", firstName);
		clearAndSendKeys(driver, "admin_vs_utaid", utaId);
		clearAndSendKeys(driver, "admin_vs_phone", phoneNumber);
		clearAndSendKeys(driver, "admin_vs_email", email);
		clearAndSendKeys(driver, "admin_vs_street_num", streetNumber);
		clearAndSendKeys(driver, "admin_vs_street_name", streetName);
		clearAndSendKeys(driver, "admin_vs_city", city);
		clearAndSendKeys(driver, "admin_vs_state", state);
		clearAndSendKeys(driver, "admin_vs_zipcode", zip);
//		clearAndSendKeys(driver, "admin_vs_role","User");
		new Select(driver.findElement(By.xpath(prop.getProperty("admin_vs_role")))).selectByVisibleText(role);
		sleep();

		takeScreenshot(driver, screenShotName + (screenShoti++));
		// Update
		click(driver, "admin_vs_update");
		sleep();
		// takeScreenshot(driver, screenShotName + (screenShoti++));

		// Verify alert and dismiss
		Alert javascriptAlert = driver.switchTo().alert();
		assertEquals("Are you sure you want to modify?", javascriptAlert.getText());
		javascriptAlert.dismiss();
		sleep();

		click(driver, "admin_vs_update");
		// Verify alert and accept
		javascriptAlert = driver.switchTo().alert();
		assertEquals("Are you sure you want to modify?", javascriptAlert.getText());
		javascriptAlert.accept();
		// sleep();
		takeScreenshot(driver, screenShotName + (screenShoti++));

		// Check if modify succeeded

		assertEquals(lastName, getAttributeValue(driver, "admin_vs_lastname"));
		assertEquals(firstName, getAttributeValue(driver, "admin_vs_firstname"));
		assertEquals(utaId, getAttributeValue(driver, "admin_vs_utaid"));
		assertEquals(phoneNumber, getAttributeValue(driver, "admin_vs_phone"));
		assertEquals(email, getAttributeValue(driver, "admin_vs_email"));
		assertEquals(streetNumber, getAttributeValue(driver, "admin_vs_street_num"));
		assertEquals(streetName, getAttributeValue(driver, "admin_vs_street_name"));
		assertEquals(city, getAttributeValue(driver, "admin_vs_city"));
		assertEquals(state, getAttributeValue(driver, "admin_vs_state"));
		assertEquals(zip, getAttributeValue(driver, "admin_vs_zipcode"));
		assertEquals(role, getAttributeValueFromSelect(driver, "admin_vs_role"));

		// logout
		click(driver, "admin_vs_logout_btn");
		assertEquals("Login Here", getText(driver, "header"));

	}

	@Test
	@FileParameters("src/excel/adminModifyProfileErrorTest.csv")
	public void modifyProfileErrorTest(String sNo, String searchUser, String lastName, String firstName,
			String phoneNumber, String email, String streetNumber, String streetName, String city, String state,
			String zip, String role, String lastNameError, String firstNameError, String phoneNumberError,
			String emailError, String streetNumberError, String streetNameError, String cityError, String stateError,
			String zipError, String roleError) {

		driver.get(sAppURL);
		String testFileNo = "3";
		String methodName = new Throwable().getStackTrace()[0].getMethodName();
		int screenShoti = 1;
		String screenShotName = testFileNo + methodName + sNo;
		// String searchUser = "User";

		// Admin Login
		login(driver, prop.getProperty("admin_username"), prop.getProperty("admin_password"));
		assertEquals("Welcome", getText(driver, "header"));
		sleep();

		// Go to search for User
		click(driver, "admin_search_user");
		assertEquals("Search User", getText(driver, "admin_su_header"));
		sleep();

		// Enter filters
		driver.findElement(By.xpath(prop.getProperty("admin_su_filters"))).sendKeys(searchUser);
		click(driver, "admin_su_search_btn");
		assertEquals(searchUser, driver.findElement(By.xpath(prop.getProperty("admin_su_lastname"))).getText());
		sleep();

		// View Selected User Profile
		click(driver, "admin_su_view_selected_user");
		assertEquals(searchUser,
				driver.findElement(By.xpath(prop.getProperty("admin_vs_lastname"))).getAttribute("value"));
		sleep();

		// Change values
		clearAndSendKeys(driver, "admin_vs_lastname", lastName);
		clearAndSendKeys(driver, "admin_vs_firstname", firstName);
		clearAndSendKeys(driver, "admin_vs_phone", phoneNumber);
		clearAndSendKeys(driver, "admin_vs_email", email);
		clearAndSendKeys(driver, "admin_vs_street_num", streetNumber);
		clearAndSendKeys(driver, "admin_vs_street_name", streetName);
		clearAndSendKeys(driver, "admin_vs_city", city);
		clearAndSendKeys(driver, "admin_vs_state", state);
		clearAndSendKeys(driver, "admin_vs_zipcode", zip);
		new Select(driver.findElement(By.xpath(prop.getProperty("admin_vs_role")))).selectByVisibleText(role);
		sleep();

		takeScreenshot(driver, screenShotName + (screenShoti++));
		// Update
		click(driver, "admin_vs_update");
		sleep();

		// Verify alert and accept
		Alert javascriptAlert = driver.switchTo().alert();
		assertEquals("Are you sure you want to modify?", javascriptAlert.getText());
		javascriptAlert.accept();
		takeScreenshot(driver, screenShotName + (screenShoti++));

		// Check if modify succeeded
		assertEquals(lastNameError, getAttributeValue(driver, "admin_vs_lastname_error"));
		assertEquals(firstNameError, getAttributeValue(driver, "admin_vs_firstname_error"));
		assertEquals(phoneNumberError, getAttributeValue(driver, "admin_vs_phone_error"));
		assertEquals(emailError, getAttributeValue(driver, "admin_vs_email_error"));
		assertEquals(streetNumberError, getAttributeValue(driver, "admin_vs_street_num_error"));
		assertEquals(streetNameError, getAttributeValue(driver, "admin_vs_street_name_error"));
		assertEquals(cityError, getAttributeValue(driver, "admin_vs_city_error"));
		assertEquals(stateError, getAttributeValue(driver, "admin_vs_state_error"));
		assertEquals(zipError, getAttributeValue(driver, "admin_vs_zipcode_error"));
		assertEquals(roleError, getAttributeValue(driver, "admin_vs_role_error"));

		// logout
		click(driver, "admin_vs_logout_btn");
		assertEquals("Login Here", getText(driver, "header"));
	}

	@Test
	@FileParameters("src/excel/adminDeleteUserTest.csv")
	public void deleteUserTest(String sNo, String lastName) {

		createUser1("userD", "password");
		driver.get(sAppURL);
		String testFileNo = "4";
		String methodName = new Throwable().getStackTrace()[0].getMethodName();
		int screenShoti = 1;
		String screenShotName = testFileNo + methodName + sNo;
		String searchUser = "User";

		// Admin Login
		login(driver, prop.getProperty("admin_username"), prop.getProperty("admin_password"));
		assertEquals("Welcome", getText(driver, "header"));
		sleep();

		// Go to search for User
		click(driver, "admin_search_user");
		assertEquals("Search User", getText(driver, "admin_su_header"));
		sleep();

		// Enter filters
		driver.findElement(By.xpath(prop.getProperty("admin_su_filters"))).sendKeys(lastName);
		click(driver, "admin_su_search_btn");
		assertEquals(lastName, driver.findElement(By.xpath(prop.getProperty("admin_su_lastname"))).getText());
		sleep();

		// View Selected User Profile
		click(driver, "admin_su_view_selected_user");
		assertEquals(lastName,
				driver.findElement(By.xpath(prop.getProperty("admin_vs_lastname"))).getAttribute("value"));
		takeScreenshot(driver, screenShotName + (screenShoti++));
		// Update
		click(driver, "admin_vs_delete_btn");
		sleep();

		// Verify alert and dismiss
		Alert javascriptAlert = driver.switchTo().alert();
		assertEquals("Are you sure you want to delete?", javascriptAlert.getText());
		javascriptAlert.dismiss();
		sleep();

		click(driver, "admin_vs_delete_btn");

		// Verify alert and accept

		javascriptAlert = driver.switchTo().alert();
		assertEquals("Are you sure you want to delete?", javascriptAlert.getText());
		javascriptAlert.accept();
		sleep();
		takeScreenshot(driver, screenShotName + (screenShoti++));

		// Check if delete succeeded

		driver.findElement(By.xpath(prop.getProperty("admin_su_filters"))).sendKeys(lastName);
		click(driver, "admin_su_search_btn");
		takeScreenshot(driver, screenShotName + (screenShoti++));
		sleep();

		// logout
		click(driver, "admin_su_logout_btn");
		assertEquals("Login Here", getText(driver, "header"));
	}

}
