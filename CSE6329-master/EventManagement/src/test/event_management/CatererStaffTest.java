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
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import data.UserDAO;
import functions.EventManagementFunctions;
import junitparams.FileParameters;
import junitparams.JUnitParamsRunner;

@RunWith(JUnitParamsRunner.class)
public class CatererStaffTest extends EventManagementFunctions {

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

		populateDbWithValues("staff");

	}

	@Test
	@FileParameters("src/excel/csEvent.csv")
	public void staffTest(String sNo, String date, String time, String error) {
		String testFileNo = "1";
		String methodName = new Throwable().getStackTrace()[0].getMethodName();
		int i = 1;
		String screenShotName = testFileNo + methodName + sNo;
		driver.get(sAppURL);
		// Login and Testing Homepage header
		String actual = login(driver, prop.getProperty("staff_username"), prop.getProperty("staff_password"));
		sleep();
		String homepage = driver.findElement(By.xpath(prop.getProperty("header"))).getText();
		assertEquals("Welcome", homepage);
		takeScreenshot(driver, screenShotName + (i++));
		if (error.equals("Date/time cannot be in past")) {
			click(driver, "cs_view_event_summary");
			assertEquals("Events", getText(driver, "header"));
			sleep();

			// set date and time
			setEventDateAndTime(driver, date, time);
			click(driver, "cs_event_summary_search");
			sleep();

			// checking if validation worked
			assertEquals(error, getAttributeValue(driver, "cs_event_summary_date_error"));
			takeScreenshot(driver, screenShotName + (i++));
			actual = logout(driver, "cs_event_summary");
			assertEquals(actual, "Login Here");
		} else {
			driver.findElement(By.xpath(prop.getProperty("cs_logout"))).click();
			sleep();
			String login1 = driver.findElement(By.xpath(prop.getProperty("login"))).getText();
			assertEquals("Login Here", login1);

			actual = login(driver, prop.getProperty("staff_username"), prop.getProperty("staff_password"));
			sleep();

			// Testing view profile
			String viewProfile = viewProfile(driver);
			assertEquals("Your Profile", viewProfile);
			takeScreenshot(driver, screenShotName + (i++));
			sleep();

			// Testing back button on view profile
			driver.findElement(By.xpath(prop.getProperty("cs_view_profile_back"))).click();
			sleep();

			// Testing if the back button worked, checking homepage button again
			homepage = driver.findElement(By.xpath(prop.getProperty("header"))).getText();
			assertEquals("Welcome", homepage);

			// View profile again
			viewProfile = viewProfile(driver);
			sleep();

			// Testing logout button on view profile
			String login = logout(driver, "cs_view_profile");
			assertEquals("Login Here", login);
			sleep();

			String actual1 = login(driver, prop.getProperty("staff_username"), prop.getProperty("staff_password"));

			// Open view Event Summary page
			driver.findElement(By.xpath(prop.getProperty("cs_view_event_summary"))).click();
			String eventSummary = driver.findElement(By.xpath(prop.getProperty("header"))).getText();
			assertEquals("Events", eventSummary);
			sleep();

			// Testing back button on view event summary profile
			driver.findElement(By.xpath(prop.getProperty("cs_event_summary_back"))).click();

			// Testing if the back button worked, checking homepage button again
			String homepage2 = driver.findElement(By.xpath(prop.getProperty("header"))).getText();
			assertEquals("Welcome", homepage2);
			sleep();

			// Open view Event Summary page again for logout
			driver.findElement(By.xpath(prop.getProperty("cs_view_event_summary"))).click();
			eventSummary = driver.findElement(By.xpath(prop.getProperty("header"))).getText();
			assertEquals("Events", eventSummary);
			sleep();

			// Testing logout button on view event summary
			login = logout(driver, "cs_event_summary");
			assertEquals("Login Here", login);
			sleep();

			actual = login(driver, prop.getProperty("staff_username"), prop.getProperty("staff_password"));

			// Open view Event Summary page
			driver.findElement(By.xpath(prop.getProperty("cs_view_event_summary"))).click();
			sleep();

			// Set Date and time to search
			setEventDateAndTime(driver, date, time);
			takeScreenshot(driver, screenShotName + (i++));

			// Click search button
			driver.findElement(By.xpath(prop.getProperty("cs_event_summary_search"))).click();
			takeScreenshot(driver, screenShotName + (i++));
			sleep();

			// View the first event
			driver.findElement(By.xpath(prop.getProperty("cs_event_summary_list_first_view"))).click();
			String selectedEvent = driver.findElement(By.xpath(prop.getProperty("header"))).getText();
			assertEquals("Event Summary", selectedEvent);
			sleep();

			// Testing back on view selected event
			driver.findElement(By.xpath(prop.getProperty("cs_selected_event_back"))).click();
			eventSummary = driver.findElement(By.xpath(prop.getProperty("header"))).getText();
			assertEquals("Events", eventSummary);
			sleep();

			// Set Date and time to search
			setEventDateAndTime(driver, date, time);

			// Click search button
			driver.findElement(By.xpath(prop.getProperty("cs_event_summary_search"))).click();
			String eventName = driver.findElement(By.xpath(prop.getProperty("cs_event_summary_event_name"))).getText();
			sleep();

			// View the first event
			driver.findElement(By.xpath(prop.getProperty("cs_event_summary_list_first_view"))).click();
			String selectedEvent1 = driver.findElement(By.xpath(prop.getProperty("header"))).getText();
			assertEquals("Event Summary", selectedEvent1);
			sleep();

			assertEquals(eventName, driver.findElement(By.xpath(prop.getProperty("cs_selected_event_event_name")))
					.getAttribute("value"));
			takeScreenshot(driver, screenShotName + (i++));

			// Testing logout on viewSelectedEvent
			login = logout(driver, "cs_selected_event");
			assertEquals("Login Here", login);

			sleep();
		}
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
