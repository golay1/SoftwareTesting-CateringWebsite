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

import data.EventDAO;
import data.PaymentDAO;
import data.UserDAO;
import functions.EventManagementFunctions;
import junitparams.FileParameters;
import junitparams.JUnitParamsRunner;
import model.PaymentModel;

@RunWith(JUnitParamsRunner.class)
public class CatererManagerTest extends EventManagementFunctions {

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

	}

	@Test
	@FileParameters("src/excel/cmAssign.csv")
	public void cmAssignTest(String sNo, String date, String time, String firstName, String lastName, String error) {
		driver.get(sAppURL);
		populateDbWithValues("manager");
		String testFileNo = "1";
		String methodName = new Throwable().getStackTrace()[0].getMethodName();
		int i = 1;
		String screenShotName = testFileNo + methodName + sNo;

		sleep();
		login(driver, prop.getProperty("manager_username"), prop.getProperty("manager_password"));
		sleep();

		// Check if it is home page
		String value = driver.findElement(By.xpath(prop.getProperty("header"))).getText();
		assertEquals("Welcome", value);

		// Check view Profile button
		click(driver, "cm_view_profile");
		assertEquals("Your Profile", getText(driver, "header"));
		sleep();

		// Check back button on view profile
		click(driver, "cm_view_profile_back");
		assertEquals("Welcome", getText(driver, "header"));
		sleep();

		// Check view Profile button
		click(driver, "cm_view_profile");
		assertEquals("Your Profile", getText(driver, "header"));
		sleep();

		// Check logout button
		click(driver, "cm_view_profile_logout");
		assertEquals("Login Here", getText(driver, "header"));
		sleep();

		// Login and go to homepage
		login(driver, prop.getProperty("manager_username"), prop.getProperty("manager_password"));
		assertEquals("Welcome", getText(driver, "header"));
		sleep();

		// Go to view event summary
		click(driver, "cm_view_event_summary");
		assertEquals("Events", getText(driver, "header"));
		sleep();

		// Check back button
		click(driver, "cm_event_summary_back");
		assertEquals("Welcome", getText(driver, "header"));
		sleep();

		// Check logout
		click(driver, "cm_logout");
		assertEquals("Login Here", getText(driver, "header"));
		sleep();

		// Login, go to event summary
		login(driver, prop.getProperty("manager_username"), prop.getProperty("manager_password"));
		assertEquals("Welcome", getText(driver, "header"));
		sleep();
		click(driver, "cm_view_event_summary");
		assertEquals("Events", getText(driver, "header"));
		sleep();

		setEventDateAndTime(driver, date, time);
		// Click search button
		driver.findElement(By.xpath(prop.getProperty("cm_event_summary_search"))).click();
		sleep();

		if (!error.equals("Date/time cannot be in past")) {
			// View the first event
			driver.findElement(By.xpath(prop.getProperty("cm_event_summary_list_first_view"))).click();
			sleep();
			// assign it to staffT
			driver.findElement(By.xpath(prop.getProperty("cm_selected_event_assign_first_name"))).clear();
			driver.findElement(By.xpath(prop.getProperty("cm_selected_event_assign_first_name"))).sendKeys(firstName);
			driver.findElement(By.xpath(prop.getProperty("cm_selected_event_assign_last_name"))).clear();
			driver.findElement(By.xpath(prop.getProperty("cm_selected_event_assign_last_name"))).sendKeys(lastName);

			driver.findElement(By.xpath(prop.getProperty("cm_selected_event_assign_button"))).click();

			String firstNameError = driver
					.findElement(By.xpath(prop.getProperty("cm_selected_event_assign_first_name_error")))
					.getAttribute("value");
			takeScreenshot(driver, screenShotName + (i++));
			assertEquals(error, firstNameError);
			sleep();

			String lastNameError = driver
					.findElement(By.xpath(prop.getProperty("cm_selected_event_assign_last_name_error")))
					.getAttribute("value");

			assertEquals(error, lastNameError);

			driver.findElement(By.xpath(prop.getProperty("cm_selected_event_logout_button"))).click();
		} else {
			assertEquals(error, getAttributeValue(driver, "cm_event_search_date_time_error"));
			String login = logout(driver, "cm_event_summary");
			assertEquals(login, "Login Here");
		}
	}

	@Test
	@FileParameters("src/excel/loginErrors.csv")
	public void loginTest(String sNo, String userName, String pass, String error) {
		String testFileNo = "2";
		String methodName = new Throwable().getStackTrace()[0].getMethodName();
		int i = 1;
		String screenShotName = testFileNo + methodName + sNo;
		driver.get(sAppURL);
		String actual = login(driver, userName, pass);
		sleep();
		takeScreenshot(driver, screenShotName + (i++));
		assertEquals(error, actual);
	}

	@Test
	@FileParameters("src/excel/registrationErrors.csv")
	public void registrationTest(String sNo, String username, String password, String lastName, String firstName,
			String role, String utaId, String phone, String email, String streetNo, String streetName, String city,
			String state, String zip, String usernameError, String passwordError, String lastNameError,
			String firstNameError, String roleError, String utaIdError, String phoneError, String emailError,
			String streetNoError, String streetNameError, String cityError, String stateError, String zipError) {
		driver.get(sAppURL);
		String testFileNo = "3";
		String methodName = new Throwable().getStackTrace()[0].getMethodName();
		int i = 1;
		String screenShotName = testFileNo + methodName + sNo;

		driver.findElement(By.xpath(prop.getProperty("login_register"))).click();
		sleep();

		String header = driver.findElement(By.xpath(prop.getProperty("registration_heading"))).getText();
		assertEquals("Fill in your details", header);

		driver.findElement(By.xpath(prop.getProperty("registration_username"))).sendKeys(username);
		driver.findElement(By.xpath(prop.getProperty("registration_password"))).sendKeys(password);
		driver.findElement(By.xpath(prop.getProperty("registration_lastname"))).sendKeys(lastName);
		driver.findElement(By.xpath(prop.getProperty("registration_firstname"))).sendKeys(firstName);
//		if (role.length() != 0)
		new Select(driver.findElement(By.xpath(prop.getProperty("registration_role")))).selectByVisibleText(role);
		driver.findElement(By.xpath(prop.getProperty("registration_utaid"))).sendKeys(utaId);
		driver.findElement(By.xpath(prop.getProperty("registration_phno"))).sendKeys(phone);
		driver.findElement(By.xpath(prop.getProperty("registration_email"))).sendKeys(email);
		driver.findElement(By.xpath(prop.getProperty("registration_stno"))).sendKeys(streetNo);
		driver.findElement(By.xpath(prop.getProperty("registration_stname"))).sendKeys(streetName);
		driver.findElement(By.xpath(prop.getProperty("registration_city"))).sendKeys(city);
		driver.findElement(By.xpath(prop.getProperty("registration_state"))).sendKeys(state);
		driver.findElement(By.xpath(prop.getProperty("registration_zipcode"))).sendKeys(zip);

		driver.findElement(By.xpath(prop.getProperty("registration_register_button"))).click();

		takeScreenshot(driver, screenShotName + (i++));
		sleep();

		header = driver.findElement(By.xpath(prop.getProperty("registration_heading"))).getText();
		if (header.equals("Fill in your details")) {
			assertEquals("Fill in your details", header);

			assertEquals(usernameError, driver.findElement(By.xpath(prop.getProperty("registration_username_errors")))
					.getAttribute("value"));
			assertEquals(passwordError, driver.findElement(By.xpath(prop.getProperty("registration_password_errors")))
					.getAttribute("value"));
			assertEquals(lastNameError, driver.findElement(By.xpath(prop.getProperty("registration_lastname_errors")))
					.getAttribute("value"));
			assertEquals(firstNameError, driver.findElement(By.xpath(prop.getProperty("registration_firstname_errors")))
					.getAttribute("value"));
			assertEquals(roleError,
					driver.findElement(By.xpath(prop.getProperty("registration_role_errors"))).getAttribute("value"));
			assertEquals(utaIdError,
					driver.findElement(By.xpath(prop.getProperty("registration_utaid_errors"))).getAttribute("value"));
			assertEquals(phoneError,
					driver.findElement(By.xpath(prop.getProperty("registration_phno_errors"))).getAttribute("value"));
			assertEquals(emailError,
					driver.findElement(By.xpath(prop.getProperty("registration_email_errors"))).getAttribute("value"));
			assertEquals(streetNoError,
					driver.findElement(By.xpath(prop.getProperty("registration_stno_errors"))).getAttribute("value"));
			assertEquals(streetNameError,
					driver.findElement(By.xpath(prop.getProperty("registration_stname_errors"))).getAttribute("value"));
			assertEquals(cityError,
					driver.findElement(By.xpath(prop.getProperty("registration_city_errors"))).getAttribute("value"));
			assertEquals(stateError,
					driver.findElement(By.xpath(prop.getProperty("registration_state_errors"))).getAttribute("value"));
			assertEquals(zipError, driver.findElement(By.xpath(prop.getProperty("registration_zipcode_errors")))
					.getAttribute("value"));
		} else {
			assertEquals("Login Here", header);
			sleep();
			UserDAO.deleteUser(username);
		}
	}

	@Test
	@FileParameters("src/excel/ModifyEventTest.csv")
	public void cmModifyEventTest(String sno, String date1, String time, String date2, String stTime1, String duration1,
			String hall1, String attendees1, /* String eName, */String foodType1, String mealType1, String mealformal1,
			String drinkType1, String entItems1) {
		driver.get(sAppURL);

		String uName = "userT", pwd = "Qwer$567", lname = "User", fname = "User", role = "User", utaid = "9999999999",
				phno = "9999999999", email = "useruser@user.com", stno = "1", stname = "user st", city = "User",
				zip = "12345", state = "TX";

		// Add needed users
		UserDAO.register(uName, pwd, lname, fname, role, utaid, phno, email, stno, stname, city, state, zip);

		String userName = "userT", lastName = "user", firstName = "user", date = "2021-04-07", name = "EventForTest",
				startTime = "13:00", duration = "3", hallName = "KC", attendees = "5", foodType = "Indian",
				meal = "lunch", drinkType = "alcohol", entertainmentItems = "non-music", isFormal = "formal";

		int eId = EventDAO.AddEvent(userName, lastName, firstName, date, name, startTime, duration, hallName, attendees,
				foodType, meal, drinkType, entertainmentItems, isFormal);
		PaymentDAO.AddPayment(new PaymentModel("" + eId, userName, "1111111111111111", "0130", "1111", "200"));

		String testFileNo = "4";
		String methodName = new Throwable().getStackTrace()[0].getMethodName();
		int j = 1;
		String screenShotName = testFileNo + methodName + sno;

		sleep();
		login(driver, prop.getProperty("manager_username"), prop.getProperty("manager_password"));
		sleep();

		// Check if it is home page
		String value = driver.findElement(By.xpath(prop.getProperty("header"))).getText();
		assertEquals("Welcome", value);
		sleep();

		// Go to view event summary
		click(driver, "cm_view_event_summary");
		assertEquals("Events", getText(driver, "header"));
		sleep();

		setEventDateAndTime(driver, date1, time);
		click(driver, "cm_event_summary_search");
		sleep();

		takeScreenshot(driver, screenShotName + (j++));

		// View the first event
		click(driver, "cm_event_summary_list_first_view");
		assertEquals("Event Summary", getText(driver, "header"));
		sleep();

		takeScreenshot(driver, screenShotName + (j++));

		// change the values for all the editable fields
		clearAndSendKeys(driver, "cm_selected_event_date", date2);
		clearAndSendKeys(driver, "cm_selected_event_start_time", stTime1);
		new Select(driver.findElement(By.xpath(prop.getProperty("cm_selected_event_duration"))))
				.selectByVisibleText(duration1);
		new Select(driver.findElement(By.xpath(prop.getProperty("cm_selected_event_hall_name"))))
				.selectByVisibleText(hall1);
		clearAndSendKeys(driver, "cm_selected_event_est_attendees", attendees1);
		new Select(driver.findElement(By.xpath(prop.getProperty("cm_selected_event_food_type"))))
				.selectByVisibleText(foodType1);
		new Select(driver.findElement(By.xpath(prop.getProperty("cm_selected_event_meal_type"))))
				.selectByVisibleText(mealType1);
		new Select(driver.findElement(By.xpath(prop.getProperty("cm_selected_event_meal_formal"))))
				.selectByVisibleText(mealformal1);
		new Select(driver.findElement(By.xpath(prop.getProperty("cm_selected_event_drink_type"))))
				.selectByVisibleText(drinkType1);
		new Select(driver.findElement(By.xpath(prop.getProperty("cm_selected_event_ent_items"))))
				.selectByVisibleText(entItems1);

		sleep();
		takeScreenshot(driver, screenShotName + (j++));
		click(driver, "cm_selected_event_change_button");
		sleep();

		// Verify alert and cancel
		Alert javascriptAlert = driver.switchTo().alert();
		assertEquals("Are you sure you want to change?", javascriptAlert.getText());
		javascriptAlert.dismiss();
		sleep();
		takeScreenshot(driver, screenShotName + (j++));

		// Verify alert and accept
		click(driver, "cm_selected_event_change_button");
		javascriptAlert = driver.switchTo().alert();
		assertEquals("Are you sure you want to change?", javascriptAlert.getText());
		javascriptAlert.accept();
		sleep();
		takeScreenshot(driver, screenShotName + (j++));

		// Check if modification has succeeded
		assertEquals(stTime1, getAttributeValue(driver, "cm_selected_event_start_time"));
		assertEquals(duration1, getAttributeValueFromSelect(driver, "cm_selected_event_duration"));
		assertEquals(hall1, getAttributeValueFromSelect(driver, "cm_selected_event_hall_name"));
		assertEquals(attendees1, getAttributeValue(driver, "cm_selected_event_est_attendees"));

		assertEquals(foodType1, getAttributeValueFromSelect(driver, "cm_selected_event_food_type"));
		assertEquals(mealType1, getAttributeValueFromSelect(driver, "cm_selected_event_meal_type"));
		assertEquals(mealformal1, getAttributeValueFromSelect(driver, "cm_selected_event_meal_formal"));
		assertEquals(drinkType1, getAttributeValueFromSelect(driver, "cm_selected_event_drink_type"));
		assertEquals(entItems1, getAttributeValueFromSelect(driver, "cm_selected_event_ent_items"));

		// logout
		click(driver, "cm_selected_event_logout_button");
		assertEquals("Login Here", getText(driver, "header"));

	}

	@Test
	@FileParameters("src/excel/managerModifyErrorTest.csv")
	public void modifyEventErrorTest(String sNo, String dateTest, String timeTest, String date3, String startTime3,
			String duration3, String hall3, String attendees3, String dateError, String durationError,
			String hallNameError, String attendeesError) {
		driver.get(sAppURL);
		String testFileNo = "5";
		String methodName = new Throwable().getStackTrace()[0].getMethodName();
		int j = 1;
		String screenShotName = testFileNo + methodName + sNo;

		populateDbWithValues("manager");

		String userName = "userT", lastName = "user", firstName = "user", date = "2022-04-07", name = "EventForTest",
				startTime = "02:00", duration = "2", hallName = "KC", attendees = "5", foodType = "American",
				meal = "breakfast", drinkType = "standard", entertainmentItems = "music", isFormal = "formal";

		for (int i = 0; i < 5; i++) {
			int eId = EventDAO.AddEvent(userName, lastName, firstName, date, (name + i), startTime, duration, hallName,
					attendees, foodType, meal, drinkType, entertainmentItems, isFormal);
			PaymentDAO.AddPayment(new PaymentModel("" + eId, userName, "1111111111111111", "0130", "1111", "200"));
		}

		sleep();
		login(driver, prop.getProperty("manager_username"), prop.getProperty("manager_password"));
		sleep();

		// Check if it is home page
		String value = driver.findElement(By.xpath(prop.getProperty("header"))).getText();
		assertEquals("Welcome", value);
		sleep();

		// Go to view event summary
		click(driver, "cm_view_event_summary");
		assertEquals("Events", getText(driver, "header"));
		sleep();

		setEventDateAndTime(driver, dateTest, timeTest);
		click(driver, "cm_event_summary_search");
		sleep();

		takeScreenshot(driver, screenShotName + (j++));

		// View the first event
		click(driver, "cm_event_summary_list_first_view");
		assertEquals("Event Summary", getText(driver, "header"));
		sleep();

		takeScreenshot(driver, screenShotName + (j++));

		clearAndSendKeys(driver, "cm_selected_event_date", date3);
		clearAndSendKeys(driver, "cm_selected_event_start_time", startTime3);
		new Select(driver.findElement(By.xpath(prop.getProperty("cm_selected_event_duration"))))
				.selectByVisibleText(duration3);
		new Select(driver.findElement(By.xpath(prop.getProperty("cm_selected_event_hall_name"))))
				.selectByVisibleText(hall3);
		clearAndSendKeys(driver, "cm_selected_event_est_attendees", attendees3);

		// Verify alert and accept
		click(driver, "cm_selected_event_change_button");
		Alert javascriptAlert = driver.switchTo().alert();
		assertEquals("Are you sure you want to change?", javascriptAlert.getText());
		javascriptAlert.accept();
		sleep();
		takeScreenshot(driver, screenShotName + (j++));

		assertEquals(dateError, getAttributeValue(driver, "cm_selected_event_date_error"));
		assertEquals(durationError, getAttributeValue(driver, "cm_selected_event_duration_error"));
		assertEquals(hallNameError, getAttributeValue(driver, "cm_selected_event_hall_name_error"));
		assertEquals(attendeesError, getAttributeValue(driver, "cm_selected_event_est_attendees_error"));

		// logout
		click(driver, "cm_selected_event_logout_button");
		assertEquals("Login Here", getText(driver, "header"));
	}

	@After
	public void tearDown() {
		// Close chrome driver
		driver.quit();
		// Delete all created users and events
		UserDAO.deleteUser("userT");
		UserDAO.deleteUser("staffT");
	}
}
