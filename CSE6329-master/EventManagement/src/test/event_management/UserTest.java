package test.event_management;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import data.EventDAO;
import data.PaymentDAO;
import data.UserDAO;
import functions.EventManagementFunctions;
import junitparams.FileParameters;
import junitparams.JUnitParamsRunner;
import model.EventModel;
import model.PaymentModel;

@RunWith(JUnitParamsRunner.class)
public class UserTest extends EventManagementFunctions {

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
		// populateDbWithValues("user");
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
	@FileParameters("src/excel/userTest.csv")
	public void test(String sno, String date, String time, String error) {
		driver.get(sAppURL);
		String testFileNo = "1";
		String methodName = new Throwable().getStackTrace()[0].getMethodName();
		int screenShoti = 1;
		String screenShotName = testFileNo + methodName + sno;
		populateDbWithValues("user");

		login(driver, prop.getProperty("user_username"), prop.getProperty("user_password"));
		assertEquals("Welcome", getText(driver, "header"));
		sleep();
		takeScreenshot(driver, screenShotName + (screenShoti++));

		if (error.equals("Date/time cannot be in past")) {
			// Go to Event summary page
			click(driver, "user_view_event_summary");
			assertEquals("Your Events", getText(driver, "header"));
			sleep();

			// set date and time
			setEventDateAndTime(driver, date, time);
			click(driver, "user_es_search");
			sleep();

			// checking if validation worked
			assertEquals(error, getAttributeValue(driver, "user_es_date_error"));
			takeScreenshot(driver, screenShotName + (screenShoti++));
			String login = logout(driver, "user_es");
			assertEquals(login, "Login Here");

		} else {
			// Go to user profile
			click(driver, "user_view_profile");
			assertEquals("Your Profile", getText(driver, "user_vp_header"));
			takeScreenshot(driver, screenShotName + (screenShoti++));
			sleep();

			// Go back
			click(driver, "user_vp_back");
			assertEquals("Welcome", getText(driver, "header"));
			sleep();
			// Go to user profile
			click(driver, "user_view_profile");
			assertEquals("Your Profile", getText(driver, "user_vp_header"));
			sleep();
			// Logout
			click(driver, "user_vp_logout");
			assertEquals("Login Here", getText(driver, "header"));
			sleep();
			// Login
			login(driver, prop.getProperty("user_username"), prop.getProperty("user_password"));
			assertEquals("Welcome", getText(driver, "header"));
			sleep();
			// Go to request event page
			click(driver, "user_request_event");
			assertEquals("Book your Event", getText(driver, "header"));
			sleep();
			// Go back
			click(driver, "user_re_back_btn");
			assertEquals("Welcome", getText(driver, "header"));
			sleep();
			// Go to request event page
			click(driver, "user_request_event");
			assertEquals("Book your Event", getText(driver, "header"));
			sleep();
			// Logout
			click(driver, "user_re_logout_btn");
			assertEquals("Login Here", getText(driver, "header"));
			sleep();
			// Login
			login(driver, prop.getProperty("user_username"), prop.getProperty("user_password"));
			assertEquals("Welcome", getText(driver, "header"));
			sleep();
			// Go to Event summary page
			click(driver, "user_view_event_summary");
			assertEquals("Your Events", getText(driver, "header"));
			sleep();
			// Go back
			click(driver, "user_es_back");
			assertEquals("Welcome", getText(driver, "header"));
			sleep();
			// Go to Event summary page
			click(driver, "user_view_event_summary");
			assertEquals("Your Events", getText(driver, "header"));
			sleep();
			// Logout
			click(driver, "user_es_logout");
			assertEquals("Login Here", getText(driver, "header"));
			sleep();
			// Login
			login(driver, prop.getProperty("user_username"), prop.getProperty("user_password"));
			assertEquals("Welcome", getText(driver, "header"));
			sleep();
			// Go to Event summary page
			click(driver, "user_view_event_summary");
			assertEquals("Your Events", getText(driver, "header"));
			sleep();
			// Enter the date and time and search
//		String date = "01/01/2021";
//		String time = "08:00";

			setEventDateAndTime(driver, date, time);
			click(driver, "user_es_search");
			sleep();
			takeScreenshot(driver, screenShotName + (screenShoti++));

			StringTokenizer st = new StringTokenizer(date, "/");
			String month = st.nextToken();
			String day = st.nextToken();
			String year = st.nextToken();
			String sqlDate = year + "-" + month + '-' + day;
			// Check the if the events listed are correct
			ArrayList<EventModel> listOfEvents = EventDAO.viewEvent("userT", sqlDate, time);
			List<WebElement> rows = driver.findElement(By.xpath(prop.getProperty("user_es_table")))
					.findElements(By.tagName("tr"));
			for (int i = 1; i < rows.size(); i++) {
				assertEquals(listOfEvents.get(i - 1).getName(),
						driver.findElement(By.xpath(prop.getProperty("user_es_table_contents_prefix") + (i + 1)
								+ prop.getProperty("user_es_table_contents_suffix"))).getText());
			}

			// View the first event
			click(driver, "user_es_first_event_view");
			assertEquals("Event Summary", getText(driver, "header"));
			sleep();
			// Go back
			click(driver, "user_ve_back");
			assertEquals("Your Events", getText(driver, "header"));
			sleep();
			// View the first event
			click(driver, "user_es_first_event_view");
			assertEquals("Event Summary", getText(driver, "header"));
			sleep();
			takeScreenshot(driver, screenShotName + (screenShoti++));

			// Logout
			click(driver, "user_ve_logout");
			assertEquals("Login Here", getText(driver, "header"));
			takeScreenshot(driver, screenShotName + (screenShoti++));
		}
	}

	@Test
	@FileParameters("src/excel/requestEvent.csv")
	public void requestEventTest(String sNo, String date, String time, String duration, String hallName,
			String attendees, String eventName, String foodType, String meal, String isFormal, String drinkType,
			String entertainment, String dateError, String timeError, String hallError, String attendeesError,
			String eventNameError) {

		driver.get(sAppURL);
		String testFileNo = "1";
		String methodName = new Throwable().getStackTrace()[0].getMethodName();
		int screenShoti = 1;
		String screenShotName = testFileNo + methodName + sNo;
		populateDbWithValues("user");

		login(driver, prop.getProperty("user_username"), prop.getProperty("user_password"));
		assertEquals("Welcome", getText(driver, "header"));
		sleep();

		// Go to request event
		click(driver, "user_request_event");
		assertEquals("Book your Event", getText(driver, "user_re_header"));
		sleep();

		// Fill Form
		driver.findElement(By.xpath(prop.getProperty("user_re_date"))).sendKeys(date);
		driver.findElement(By.xpath(prop.getProperty("user_re_time"))).sendKeys(time);
		new Select(driver.findElement(By.xpath(prop.getProperty("user_re_duration")))).selectByVisibleText(duration);
		new Select(driver.findElement(By.xpath(prop.getProperty("user_re_hall")))).selectByVisibleText(hallName);
		driver.findElement(By.xpath(prop.getProperty("user_re_attendees"))).sendKeys(attendees);
		driver.findElement(By.xpath(prop.getProperty("user_re_eventname"))).sendKeys(eventName);
		new Select(driver.findElement(By.xpath(prop.getProperty("user_re_foodtype")))).selectByVisibleText(foodType);
		new Select(driver.findElement(By.xpath(prop.getProperty("user_re_mealtype")))).selectByVisibleText(meal);
		new Select(driver.findElement(By.xpath(prop.getProperty("user_re_mealformal")))).selectByVisibleText(isFormal);
		new Select(driver.findElement(By.xpath(prop.getProperty("user_re_drink_type")))).selectByVisibleText(drinkType);
		new Select(driver.findElement(By.xpath(prop.getProperty("user_re_entertain"))))
				.selectByVisibleText(entertainment);
		driver.findElement(By.xpath(prop.getProperty("user_re_request_btn"))).click();
		takeScreenshot(driver, screenShotName + (screenShoti++));
		sleep();

		String header1 = driver.findElement(By.xpath(prop.getProperty("header"))).getText();
		if (header1.equals("Pay For an Event")) {
			assertEquals("Pay For an Event", getText(driver, "user_pfe_header"));
			// Logout
			driver.findElement(By.xpath(prop.getProperty("user_pfe_logout_btn"))).click();
			assertEquals("Login Here", getText(driver, "header"));
		} else {
			assertEquals(dateError,
					driver.findElement(By.xpath(prop.getProperty("user_re_date_errors"))).getAttribute("value"));
			assertEquals(timeError,
					driver.findElement(By.xpath(prop.getProperty("user_re_duration_errors"))).getAttribute("value"));
			assertEquals(hallError,
					driver.findElement(By.xpath(prop.getProperty("user_re_hall_errors"))).getAttribute("value"));
			assertEquals(attendeesError,
					driver.findElement(By.xpath(prop.getProperty("user_re_attendees_errors"))).getAttribute("value"));
			assertEquals(eventNameError,
					driver.findElement(By.xpath(prop.getProperty("user_re_eventname_errors"))).getAttribute("value"));
		}

	}

	@Test
	@FileParameters("src/excel/userUpdateProfileTest.csv")
	public void updateProfileTest(String sNo, String userName, String pass, String lastName, String firstName,
			String utaId, String phoneNumber, String email, String streetNumber, String streetName, String city,
			String state, String zip, String passError, String lastNameError, String firstNameError, String utaIdError,
			String phoneNumberError, String emailError, String streetNumberError, String streetNameError,
			String cityError, String stateError, String zipError) {
//		String sNo = "3", userName = "userT", pass = "Qwer$567", zip = "76013";

		createUser(userName, pass);

		driver.get(sAppURL);
		String testFileNo = "2";
		String methodName = new Throwable().getStackTrace()[0].getMethodName();
		int screenShoti = 1;
		String screenShotName = testFileNo + methodName + sNo;

		// Login
		login(driver, userName, pass);
		assertEquals("Welcome", getText(driver, "header"));
		sleep();

		// View Profile
		click(driver, "user_view_profile");
		assertEquals("Your Profile", getText(driver, "header"));
		sleep();

		// Change values
		clearAndSendKeys(driver, "user_vp_password", pass);
		clearAndSendKeys(driver, "user_vp_lastname", lastName);
		clearAndSendKeys(driver, "user_vp_firstname", firstName);
		clearAndSendKeys(driver, "user_vp_utaid", utaId);
		clearAndSendKeys(driver, "user_vp_phone_number", phoneNumber);
		clearAndSendKeys(driver, "user_vp_email", email);
		clearAndSendKeys(driver, "user_vp_street_number", streetNumber);
		clearAndSendKeys(driver, "user_vp_street_name", streetName);
		clearAndSendKeys(driver, "user_vp_city", city);
		clearAndSendKeys(driver, "user_vp_state", state);
		clearAndSendKeys(driver, "user_vp_zip", zip);

		sleep();
		takeScreenshot(driver, screenShotName + (screenShoti++));
		// Update
		click(driver, "user_vp_update");
		sleep();
		// takeScreenshot(driver, screenShotName + (screenShoti++));

		// Verify alert and dismiss
		Alert javascriptAlert = driver.switchTo().alert();
		assertEquals("Are you sure you want to update?", javascriptAlert.getText());
		javascriptAlert.dismiss();
		sleep();
		takeScreenshot(driver, screenShotName + (screenShoti++));

		// Verify alert and accept
		click(driver, "user_vp_update");
		javascriptAlert = driver.switchTo().alert();
		assertEquals("Are you sure you want to update?", javascriptAlert.getText());
		javascriptAlert.accept();
		sleep();
		takeScreenshot(driver, screenShotName + (screenShoti++));

		// Check if updation succeeded

		assertEquals(pass, getAttributeValue(driver, "user_vp_password"));
		assertEquals(pass, getAttributeValue(driver, "user_vp_password"));
		assertEquals(lastName, getAttributeValue(driver, "user_vp_lastname"));
		assertEquals(firstName, getAttributeValue(driver, "user_vp_firstname"));
		assertEquals(utaId, getAttributeValue(driver, "user_vp_utaid"));
		assertEquals(phoneNumber, getAttributeValue(driver, "user_vp_phone_number"));
		assertEquals(email, getAttributeValue(driver, "user_vp_email"));
		assertEquals(streetNumber, getAttributeValue(driver, "user_vp_street_number"));
		assertEquals(streetName, getAttributeValue(driver, "user_vp_street_name"));
		assertEquals(city, getAttributeValue(driver, "user_vp_city"));
		assertEquals(state, getAttributeValue(driver, "user_vp_state"));
		assertEquals(zip, getAttributeValue(driver, "user_vp_zip"));

		// logout
		click(driver, "user_vp_logout");
		assertEquals("Login Here", getText(driver, "header"));

	}

	@Test
	@FileParameters("src/excel/userUpdateProfileErrorTest.csv")
	public void updateProfileErrorTest(String sNo, String userName, String pass, String lastName, String firstName,
			String utaId, String phoneNumber, String email, String streetNumber, String streetName, String city,
			String state, String zip, String passError, String lastNameError, String firstNameError, String utaIdError,
			String phoneNumberError, String emailError, String streetNumberError, String streetNameError,
			String cityError, String stateError, String zipError) {
//		String sNo = "3", userName = "userT", pass = "Qwer$567", zip = "76013";

		String passLogin = "Qwer$567";
		createUser(userName, passLogin);

		driver.get(sAppURL);
		String testFileNo = "4";
		String methodName = new Throwable().getStackTrace()[0].getMethodName();
		int screenShoti = 1;
		String screenShotName = testFileNo + methodName + sNo;

		// Login
		login(driver, userName, passLogin);
		assertEquals("Welcome", getText(driver, "header"));
		sleep();

		// View Profile
		click(driver, "user_view_profile");
		assertEquals("Your Profile", getText(driver, "header"));
		sleep();

		// Change values
		clearAndSendKeys(driver, "user_vp_password", pass);
		clearAndSendKeys(driver, "user_vp_lastname", lastName);
		clearAndSendKeys(driver, "user_vp_firstname", firstName);
		clearAndSendKeys(driver, "user_vp_utaid", utaId);
		clearAndSendKeys(driver, "user_vp_phone_number", phoneNumber);
		clearAndSendKeys(driver, "user_vp_email", email);
		clearAndSendKeys(driver, "user_vp_street_number", streetNumber);
		clearAndSendKeys(driver, "user_vp_street_name", streetName);
		clearAndSendKeys(driver, "user_vp_city", city);
		clearAndSendKeys(driver, "user_vp_state", state);
		clearAndSendKeys(driver, "user_vp_zip", zip);

		sleep();
		takeScreenshot(driver, screenShotName + (screenShoti++));
		// Update
		click(driver, "user_vp_update");
		sleep();
		// takeScreenshot(driver, screenShotName + (screenShoti++));

		// Verify alert and accept
		Alert javascriptAlert = driver.switchTo().alert();
		assertEquals("Are you sure you want to update?", javascriptAlert.getText());
		javascriptAlert.accept();
		sleep();
		takeScreenshot(driver, screenShotName + (screenShoti++));

		// Check if updation succeeded
		assertEquals(passError, getAttributeValue(driver, "user_vp_pass_error"));
		assertEquals(lastNameError, getAttributeValue(driver, "user_vp_last_name_error"));
		assertEquals(firstNameError, getAttributeValue(driver, "user_vp_first_name_error"));
		assertEquals(utaIdError, getAttributeValue(driver, "user_vp_utaid_error"));
		assertEquals(phoneNumberError, getAttributeValue(driver, "user_vp_phone_error"));
		assertEquals(emailError, getAttributeValue(driver, "user_vp_email_error"));
		assertEquals(streetNumberError, getAttributeValue(driver, "user_vp_stno_error"));
		assertEquals(streetNameError, getAttributeValue(driver, "user_vp_stname_error"));
		assertEquals(cityError, getAttributeValue(driver, "user_vp_city_error"));
		assertEquals(stateError, getAttributeValue(driver, "user_vp_state_error"));
		assertEquals(zipError, getAttributeValue(driver, "user_vp_zip_error"));

		// logout
		click(driver, "user_vp_logout");
		assertEquals("Login Here", getText(driver, "header"));

	}

	@Test
	@FileParameters("src/excel/ModifyEventTest.csv")
	public void userModifyEventTest(String sno, String date1, String time, String date2, String stTime1,
			String duration1, String hall1, String attendees1, /* String eName, */String foodType1, String mealType1,
			String mealformal1, String drinkType1, String entItems1) {
		driver.get(sAppURL);

		// populateDbWithValues("user");
		createUser("userT", "Qwer$567");
		String userName = "userT", lastName = "user", firstName = "user", date = "2021-04-07", name = "EventForTest",
				startTime = "13:00", duration = "3", hallName = "KC", attendees = "5", foodType = "Indian",
				meal = "lunch", drinkType = "alcohol", entertainmentItems = "non-music", isFormal = "formal";

		int eId = EventDAO.AddEvent(userName, lastName, firstName, date, name, startTime, duration, hallName, attendees,
				foodType, meal, drinkType, entertainmentItems, isFormal);
		PaymentDAO.AddPayment(new PaymentModel("" + eId, userName, "1111111111111111", "0130", "1111", "200"));

		String testFileNo = "5";
		String methodName = new Throwable().getStackTrace()[0].getMethodName();
		int j = 1;
		String screenShotName = testFileNo + methodName + sno;

		sleep();
		// login(driver, "userT", "Qwer$567");
		login(driver, prop.getProperty("user_username"), prop.getProperty("user_password"));
		sleep();

		// Check if it is home page
		String value = driver.findElement(By.xpath(prop.getProperty("header"))).getText();
		assertEquals("Welcome", value);
		sleep();

		// Go to view event summary
		click(driver, "user_view_event_summary");
		assertEquals("Your Events", getText(driver, "header"));
		sleep();

		setEventDateAndTime(driver, date1, time);
		click(driver, "user_es_search");
		sleep();

		takeScreenshot(driver, screenShotName + (j++));

		// View the first event
		click(driver, "user_es_first_event_view");
		assertEquals("Event Summary", getText(driver, "header"));
		sleep();

		takeScreenshot(driver, screenShotName + (j++));

		// change the values for all the editable fields
		clearAndSendKeys(driver, "user_ve_date", date2);
		clearAndSendKeys(driver, "user_ve_starttime", stTime1);
		new Select(driver.findElement(By.xpath(prop.getProperty("user_ve_duration")))).selectByVisibleText(duration1);
		new Select(driver.findElement(By.xpath(prop.getProperty("user_ve_hall_name")))).selectByVisibleText(hall1);
		clearAndSendKeys(driver, "user_ve_est_attendees", attendees1);
		// clearAndSendKeys(driver, "cm_selected_event_event_name", eName);
		new Select(driver.findElement(By.xpath(prop.getProperty("user_ve_food_type")))).selectByVisibleText(foodType1);
		new Select(driver.findElement(By.xpath(prop.getProperty("user_ve_meal_type")))).selectByVisibleText(mealType1);
		new Select(driver.findElement(By.xpath(prop.getProperty("user_ve_meal_formal"))))
				.selectByVisibleText(mealformal1);
		new Select(driver.findElement(By.xpath(prop.getProperty("user_ve_drink_type"))))
				.selectByVisibleText(drinkType1);
		new Select(driver.findElement(By.xpath(prop.getProperty("user_ve_ent_items")))).selectByVisibleText(entItems1);

		sleep();
		takeScreenshot(driver, screenShotName + (j++));
		click(driver, "user_ve_modify");
		sleep();

		// Verify alert and cancel
		Alert javascriptAlert = driver.switchTo().alert();
		assertEquals("Are you sure you want to modify?", javascriptAlert.getText());
		javascriptAlert.dismiss();
		sleep();
		takeScreenshot(driver, screenShotName + (j++));

		// Verify alert and accept
		click(driver, "user_ve_modify");
		javascriptAlert = driver.switchTo().alert();
		assertEquals("Are you sure you want to modify?", javascriptAlert.getText());
		javascriptAlert.accept();
		sleep();
		takeScreenshot(driver, screenShotName + (j++));
		// checking modify was successful
		assertEquals("Your Events", getText(driver, "header"));
		sleep();

		setEventDateAndTime(driver, date1, time);
		click(driver, "user_es_search");
		sleep();

		takeScreenshot(driver, screenShotName + (j++));

		click(driver, "user_es_first_event_view");
		assertEquals("Event Summary", getText(driver, "header"));
		sleep();

		// Check if modification has succeeded
		// assertEquals(date2, getAttributeValue(driver, "cm_selected_event_date"));
		assertEquals(stTime1, getAttributeValue(driver, "user_ve_starttime"));
		assertEquals(duration1, getAttributeValueFromSelect(driver, "user_ve_duration"));
		assertEquals(hall1, getAttributeValueFromSelect(driver, "user_ve_hall_name"));
		assertEquals(attendees1, getAttributeValue(driver, "user_ve_est_attendees"));

		// assertEquals(eName, getAttributeValue(driver,
		// "cm_selected_event_event_name"));

		assertEquals(foodType1, getAttributeValueFromSelect(driver, "user_ve_food_type"));
		assertEquals(mealType1, getAttributeValueFromSelect(driver, "user_ve_meal_type"));
		assertEquals(mealformal1, getAttributeValueFromSelect(driver, "user_ve_meal_formal"));
		assertEquals(drinkType1, getAttributeValueFromSelect(driver, "user_ve_drink_type"));
		assertEquals(entItems1, getAttributeValueFromSelect(driver, "user_ve_ent_items"));

		// logout
		click(driver, "user_ve_logout");
		assertEquals("Login Here", getText(driver, "header"));

	}

	@Test
	@FileParameters("src/excel/userModifyErrorTest.csv")
	public void modifyEventErrorTest(String sNo, String dateSearch, String timeSearch, String date2, String stTime2,
			String duration2, String hall2, String attendees2, String dateError, String durationError,
			String hallNameError, String attendeesError) {
		driver.get(sAppURL);
		String testFileNo = "6";
		String methodName = new Throwable().getStackTrace()[0].getMethodName();
		int j = 1;
		String screenShotName = testFileNo + methodName + sNo;

		populateDbWithValues("user");

		String userName = "userT", lastName = "user", firstName = "user", date = "2022-04-07", name = "EventForTest",
				startTime = "14:00", duration = "2", hallName = "KC", attendees = "5", foodType = "American",
				meal = "breakfast", drinkType = "standard", entertainmentItems = "music", isFormal = "formal";

		for (int i = 0; i < 5; i++) {
			int eId = EventDAO.AddEvent(userName, lastName, firstName, date, (name + i), startTime, duration, hallName,
					attendees, foodType, meal, drinkType, entertainmentItems, isFormal);
			PaymentDAO.AddPayment(new PaymentModel("" + eId, userName, "1111111111111111", "0130", "1111", "200"));
		}

		sleep();
		login(driver, prop.getProperty("user_username"), prop.getProperty("user_password"));
		sleep();

		// Check if it is home page
		String value = driver.findElement(By.xpath(prop.getProperty("header"))).getText();
		assertEquals("Welcome", value);
		sleep();

		// Go to view event summary
		click(driver, "user_view_event_summary");
		assertEquals("Your Events", getText(driver, "header"));
		sleep();

		setEventDateAndTime(driver, dateSearch, timeSearch);
		click(driver, "user_es_search");
		sleep();

		takeScreenshot(driver, screenShotName + (j++));

		// View the first event
		click(driver, "user_es_first_event_view");
		assertEquals("Event Summary", getText(driver, "header"));
		sleep();

		takeScreenshot(driver, screenShotName + (j++));

		// change the values for all the editable fields
		clearAndSendKeys(driver, "user_ve_date", date2);
		clearAndSendKeys(driver, "user_ve_starttime", stTime2);
		new Select(driver.findElement(By.xpath(prop.getProperty("user_ve_duration")))).selectByVisibleText(duration2);
		new Select(driver.findElement(By.xpath(prop.getProperty("user_ve_hall_name")))).selectByVisibleText(hall2);
		clearAndSendKeys(driver, "user_ve_est_attendees", attendees2);
		sleep();

		takeScreenshot(driver, screenShotName + (j++));
		click(driver, "user_ve_modify");
		Alert javascriptAlert = driver.switchTo().alert();
		assertEquals("Are you sure you want to modify?", javascriptAlert.getText());
		javascriptAlert.accept();
		sleep();
		takeScreenshot(driver, screenShotName + (j++));

		String dateError1 = getAttributeValue(driver, "user_ve_date_error");

		assertEquals(dateError, getAttributeValue(driver, "user_ve_date_error"));
		assertEquals(durationError, getAttributeValue(driver, "user_ve_duration_error"));
		assertEquals(hallNameError, getAttributeValue(driver, "user_ve_hall_name_error"));
		assertEquals(attendeesError, getAttributeValue(driver, "user_ve_est_attendees_error"));

		// logout
		click(driver, "user_ve_logout");
		assertEquals("Login Here", getText(driver, "header"));
	}

	@Test
	@FileParameters("src/excel/cancelEventTest.csv")
	public void cancelEventErrorTest(String sNo, String dateSearch, String timeSearch, String estatus) {
		driver.get(sAppURL);
		String testFileNo = "7";
		String methodName = new Throwable().getStackTrace()[0].getMethodName();
		int j = 1;
		String screenShotName = testFileNo + methodName + sNo;

		populateDbWithValues("user");

		String userName = "userT", lastName = "user", firstName = "user", date = "2021-04-07", name = "EventForTest",
				startTime = "13:00", duration = "2", hallName = "Maverick", attendees = "5", foodType = "American",
				meal = "Breakfast", drinkType = "Standard", entertainmentItems = "Music", isFormal = "Informal";

		int eId = EventDAO.AddEvent(userName, lastName, firstName, date, name, startTime, duration, hallName, attendees,
				foodType, meal, drinkType, entertainmentItems, isFormal);
		PaymentDAO.AddPayment(new PaymentModel("" + eId, userName, "1111111111111111", "0130", "1111", "200"));

		sleep();
		login(driver, prop.getProperty("user_username"), prop.getProperty("user_password"));
		sleep();

		// Check if it is home page
		String value = driver.findElement(By.xpath(prop.getProperty("header"))).getText();
		assertEquals("Welcome", value);
		sleep();

		// Go to view event summary
		click(driver, "user_view_event_summary");
		assertEquals("Your Events", getText(driver, "header"));
		sleep();

		setEventDateAndTime(driver, dateSearch, timeSearch);
		click(driver, "user_es_search");
		sleep();

		takeScreenshot(driver, screenShotName + (j++));

		// View the first event
		click(driver, "user_es_first_event_view");
		assertEquals("Event Summary", getText(driver, "header"));
		sleep();

		takeScreenshot(driver, screenShotName + (j++));

		// Verify alert and cancel
		click(driver, "user_ve_cancel");
		Alert javascriptAlert = driver.switchTo().alert();
		assertEquals("Are you sure you want to cancel?", javascriptAlert.getText());
		javascriptAlert.dismiss();
		sleep();
		takeScreenshot(driver, screenShotName + (j++));

		click(driver, "user_ve_cancel");
		javascriptAlert = driver.switchTo().alert();
		assertEquals("Are you sure you want to cancel?", javascriptAlert.getText());
		javascriptAlert.accept();
		sleep();
		takeScreenshot(driver, screenShotName + (j++));

		assertEquals(estatus, getAttributeValue(driver, "user_ve_estatus"));
		// logout
		click(driver, "user_ve_logout");
		assertEquals("Login Here", getText(driver, "header"));
	}

	@Test
	@FileParameters("src/excel/userPaymentTest.csv")
	public void paymentErrorTest(String sNo, String cardNum, String pin, String expiryDate, String cardNumError,
			String pinError, String expiryDateError, String errorMsg) {

		driver.get(sAppURL);
		String testFileNo = "8";
		String methodName = new Throwable().getStackTrace()[0].getMethodName();
		int screenShoti = 1;
		String screenShotName = testFileNo + methodName + sNo;
		populateDbWithValues("user");

		// Login
		login(driver, prop.getProperty("user_username"), prop.getProperty("user_password"));
		assertEquals("Welcome", getText(driver, "header"));
		sleep();

		// Go to request event
		click(driver, "user_request_event");
		assertEquals("Book your Event", getText(driver, "user_re_header"));
		sleep();

		// Fill request event form
		clearAndSendKeys(driver, "user_re_date", "08-06-2020");
		clearAndSendKeys(driver, "user_re_time", "11:00AM");
		clearAndSendKeys(driver, "user_re_attendees", "15");
		clearAndSendKeys(driver, "user_re_eventname", "Eventpaytest1");
		driver.findElement(By.xpath(prop.getProperty("user_re_request_btn"))).click();
		sleep();

		// Fill payment form
		driver.findElement(By.xpath(prop.getProperty("user_pfe_cardnum"))).sendKeys(cardNum);
		driver.findElement(By.xpath(prop.getProperty("user_pfe_pin"))).sendKeys(pin);
		driver.findElement(By.xpath(prop.getProperty("user_pfe_expirydate"))).sendKeys(expiryDate);
		driver.findElement(By.xpath(prop.getProperty("user_pfe_pay_btn"))).click();
		sleep();

		// Verify alert and dismiss
		Alert javascriptAlert = driver.switchTo().alert();
		assertEquals("Are you sure you want to pay?", javascriptAlert.getText());
		javascriptAlert.dismiss();
		sleep();

		// Verify alert and accept
		driver.findElement(By.xpath(prop.getProperty("user_pfe_pay_btn"))).click();
		javascriptAlert = driver.switchTo().alert();
		assertEquals("Are you sure you want to pay?", javascriptAlert.getText());
		javascriptAlert.accept();
		takeScreenshot(driver, screenShotName + (screenShoti++));
		sleep();

		// Verify error messages
		String header2 = driver.findElement(By.xpath(prop.getProperty("header"))).getText();
		if (header2.equals("Your Events")) {
			assertEquals("Your Events", getText(driver, "user_es_header"));
			// Logout if successful payment redirects to view event summary
			driver.findElement(By.xpath(prop.getProperty("user_es_logout"))).click();
			assertEquals("Login Here", getText(driver, "header"));
		} else {
			assertEquals(cardNumError,
					driver.findElement(By.xpath(prop.getProperty("user_pfe_cardnum_error"))).getAttribute("value"));
			assertEquals(pinError,
					driver.findElement(By.xpath(prop.getProperty("user_pfe_pin_error"))).getAttribute("value"));
			assertEquals(expiryDateError,
					driver.findElement(By.xpath(prop.getProperty("user_pfe_expirydate_error"))).getAttribute("value"));
		}
	}

}
