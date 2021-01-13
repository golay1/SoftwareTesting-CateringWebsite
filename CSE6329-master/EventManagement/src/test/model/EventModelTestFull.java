package test.model;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import junitparams.FileParameters;
import junitparams.JUnitParamsRunner;
import model.EventErrorMsgs;
import model.EventModel;

@RunWith(JUnitParamsRunner.class)
public class EventModelTestFull {

	private EventModel eventModel;
	private EventErrorMsgs errors;

	@Before
	public void setUp() throws Exception {
		eventModel = new EventModel();
		errors = new EventErrorMsgs();
	}

	@Test
	@FileParameters("src/test/testFiles/EventModelTests1.csv")
	public void test(String sno, String eventID, String userName, String lastName, String firstName, String date,
			String name, String startTime, String duration, String hallName, String attendees, String foodType,
			String meal, String drinkType, String entertainmentItems, String status, String staffLastName,
			String staffFirstName, String formal, String price, String userNameError, String lastNameError,
			String firstNameError, String eventNameError, String durationError, String hallError, String attendeesError,
			String staffFirstNameError, String staffLastNameError, String dateError, String errorMsg, String comment)
			throws Exception {
		eventModel = new EventModel(eventID, date, name, startTime, duration, hallName, attendees, foodType, meal,
				drinkType, entertainmentItems, formal, staffFirstName, staffLastName, status, price);

		eventModel.validateDate(userName, errors);
		eventModel.validateAttendees(errors);
		eventModel.validateDuration(errors);
		eventModel.validateEvenName(errors);
		eventModel.validateHall(errors);
		eventModel.validateStaffExists(errors);
		eventModel.setUserName(userName);
		eventModel.setLastName(lastName);
		eventModel.setFirstName(firstName);

		assertEquals(eventNameError, errors.getEventNameError());
		assertEquals(durationError, errors.getDurationError());
		assertEquals(hallError, errors.getHallError());
		assertEquals(attendeesError, errors.getAttendeesError());
		assertEquals(staffFirstNameError, errors.getStaffFirstNameError());
		assertEquals(staffLastNameError, errors.getStaffLastNameError());
		assertEquals(dateError, errors.getDateError());
		assertEquals(errorMsg, errors.getErrorMsg());
		assertEquals(eventID, eventModel.getEventID());
		assertEquals(userName, eventModel.getUserName());
		assertEquals(firstName, eventModel.getFirstName());
		assertEquals(lastName, eventModel.getLastName());
		assertEquals(foodType, eventModel.getFoodType());
		assertEquals(meal, eventModel.getMeal());
		assertEquals(foodType, eventModel.getFoodType());
		assertEquals(drinkType, eventModel.getDrinkType());
		assertEquals(entertainmentItems, eventModel.getEntertainmentItems());
		assertEquals(status, eventModel.getStatus());
		assertEquals(formal, eventModel.getFormal());
		assertEquals(price, eventModel.getPrice());
	}

}
