package functions;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

import data.EventDAO;
import data.PaymentDAO;
import data.UserDAO;
import model.PaymentModel;

public class EventManagementFunctions {

	public static Properties prop;

	public String login(WebDriver driver, String sUserName, String sPassword) {

		// prop.list(System.out);

		String usernamePath = prop.getProperty("username");
		// Provide user name.
		driver.findElement(By.xpath(usernamePath)).clear();
		driver.findElement(By.xpath(prop.getProperty("username"))).sendKeys(sUserName);

		// Provide Password.
		driver.findElement(By.xpath(prop.getProperty("password"))).clear();
		driver.findElement(By.xpath(prop.getProperty("password"))).sendKeys(sPassword);

		// Click on Login button.
		driver.findElement(By.xpath(prop.getProperty("login_submit"))).click();

		if (driver.findElement(By.xpath("html/body/center/h3")).getText().equals("Welcome")) {
			return "";
		}

		return driver.findElement(By.xpath(prop.getProperty("login_errors"))).getAttribute("value");
	}

	public String viewProfile(WebDriver driver) {
		String viewProfile = "";
		driver.findElement(By.xpath(prop.getProperty("view_profile"))).click();
		viewProfile = driver.findElement(By.xpath(prop.getProperty("header"))).getText();
		return viewProfile;
	}

	public String logout(WebDriver driver, String logoutPage) {
		driver.findElement(By.xpath(prop.getProperty(logoutPage + "_logout"))).click();
		String login1 = driver.findElement(By.xpath(prop.getProperty("login"))).getText();
		// assertEquals("Login Here", login1);
		return login1;
	}

	public void setEventDateAndTime(WebDriver driver, String date, String time) {
		driver.findElement(By.xpath(prop.getProperty("event_summary_date"))).clear();
		driver.findElement(By.xpath(prop.getProperty("event_summary_date"))).sendKeys(date);
		driver.findElement(By.xpath(prop.getProperty("event_summary_time"))).clear();
		driver.findElement(By.xpath(prop.getProperty("event_summary_time"))).sendKeys(time);
	}

	public void takeScreenshot(WebDriver driver, String screenshotname) {
		try {
			File source = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(source, new File("./ScreenShots/" + screenshotname + ".png"));
		} catch (IOException e) {
		}
		try {
//				  Change the delay value to 1_000 to insert a 1 second delay after 
//				  each screenshot
			Thread.sleep(1_000);
		} catch (InterruptedException e) {
		}
	}

	public void populateDbWithValues(String userType) {

		String uName = "userT", pwd = "Qwer$567", lname = "User", fname = "User", role = "User", utaid = "9999999999",
				phno = "9999999999", email = "useruser@user.com", stno = "1", stname = "user st", city = "User",
				zip = "12345", state = "TX";

		// Add needed users
		UserDAO.register(uName, pwd, lname, fname, role, utaid, phno, email, stno, stname, city, state, zip);
		String uNameS = "staffT", utaidS = "9999999998", roleS = "staff";
		UserDAO.register(uNameS, pwd, lname, fname, roleS, utaidS, phno, email, stno, stname, city, state, zip);

		if (!userType.equalsIgnoreCase("admin")) {
			// Add needed events and pay/reserve them
			String userName = "userT", lastName = "user", firstName = "user", date = "2021-04-07",
					name = "EventForTest", startTime = "02:00", duration = "2", hallName = "KC", attendees = "5",
					foodType = "American", meal = "breakfast", drinkType = "standard", entertainmentItems = "music",
					isFormal = "formal";

			for (int i = 0; i < 5; i++) {
				int eId = EventDAO.AddEvent(userName, lastName, firstName, date, (name + i), startTime, duration,
						hallName, attendees, foodType, meal, drinkType, entertainmentItems, isFormal);
				PaymentDAO.AddPayment(new PaymentModel("" + eId, userName, "1111111111111111", "0130", "1111", "200"));
				if (userType.equals("staff")) {
					EventDAO.assign(eId + "", fname, lname);
				}
			}
		}
	}

	public void createUser(String userName, String pass) {
		String lname = "User", fname = "User", role = "User", utaid = "9999999999", phno = "9999999999",
				email = "useruser@user.com", stno = "1", stname = "user st", city = "User", zip = "12345", state = "TX";

		// Add needed users
		UserDAO.register(userName, pass, lname, fname, role, utaid, phno, email, stno, stname, city, state, zip);

	}

	public void createUser1(String userName, String pass) {
		String lname = "Li", fname = "Li", role = "User", utaid = "9499999999", phno = "9999999999",
				email = "useruser@user.com", stno = "1", stname = "user st", city = "User", zip = "12345", state = "TX";

		// Add needed users
		UserDAO.register(userName, pass, lname, fname, role, utaid, phno, email, stno, stname, city, state, zip);

	}

	public void click(WebDriver driver, String path) {
		driver.findElement(By.xpath(prop.getProperty(path))).click();
	}

	public String getText(WebDriver driver, String path) {
		return driver.findElement(By.xpath(prop.getProperty(path))).getText();
	}

	public void sleep() {
		try {
			Thread.sleep(1_000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}
	}

	public void clearAndSendKeys(WebDriver driver, String xpath, String keys) {
		driver.findElement(By.xpath(prop.getProperty(xpath))).clear();
		driver.findElement(By.xpath(prop.getProperty(xpath))).sendKeys(keys);
	}

	public String getAttributeValue(WebDriver driver, String xpath) {
		return driver.findElement(By.xpath(prop.getProperty(xpath))).getAttribute("value");
	}

	public String getAttributeValueFromSelect(WebDriver driver, String xpath) {
		return new Select(driver.findElement(By.xpath(prop.getProperty(xpath)))).getFirstSelectedOption().getText();
	}
}