package model;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.StringTokenizer;

import data.EventDAO;
import data.UserDAO;

public class EventModel implements Serializable {

	private static final long serialVersionUID = 1L;
	String eventID, userName, lastName, firstName, date, name, startTime, duration, hallName, attendees, foodType, meal,
			drinkType, entertainmentItems, status, staffFirstName, staffLastName, formal;
	String price;

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public EventModel(String eventID, String date, String name, String startTime, String duration, String hallName,
			String attendees, String foodType, String meal, String drinkType, String entertainmentItems, String formal,
			String staffFirstName, String staffLastName, String status, String price) {
		this.setEventID(eventID);
		this.setName(name);
		this.setStartTime(startTime);
		this.setDuration(duration);
		this.setHallName(hallName);
		this.setAttendees(attendees);
		this.setDate(date);
		this.setDrinkType(drinkType);
		this.setEntertainmentItems(entertainmentItems);
		this.setFoodType(foodType);
		this.setMeal(meal);
		this.setFormal(formal);
		this.setStaffFirstName(staffFirstName);
		this.setStaffLastName(staffLastName);
		this.setStatus(status);
		this.setPrice(price);
	}

	public EventModel() {

	}

	/**
	 * @return the eventID
	 */
	public String getEventID() {
		return eventID;
	}

	/**
	 * @param eventID the eventID to set
	 */
	public void setEventID(String eventID) {
		this.eventID = eventID;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the date
	 */
	public String getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(String date) {
		this.date = date;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the startTime
	 */
	public String getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	/**
	 * @return the duration
	 */
	public String getDuration() {
		return duration;
	}

	/**
	 * @param duration the duration to set
	 */
	public void setDuration(String duration) {
		this.duration = duration;
	}

	/**
	 * @return the hallName
	 */
	public String getHallName() {
		return hallName;
	}

	/**
	 * @param hallName the hallName to set
	 */
	public void setHallName(String hallName) {
		this.hallName = hallName;
	}

	/**
	 * @return the attendees
	 */
	public String getAttendees() {
		return attendees;
	}

	/**
	 * @param attendees the attendees to set
	 */
	public void setAttendees(String attendees) {
		this.attendees = attendees;
	}

	/**
	 * @return the foodType
	 */
	public String getFoodType() {
		return foodType;
	}

	/**
	 * @param foodType the foodType to set
	 */
	public void setFoodType(String foodType) {
		this.foodType = foodType;
	}

	/**
	 * @return the meal
	 */
	public String getMeal() {
		return meal;
	}

	/**
	 * @param meal the meal to set
	 */
	public void setMeal(String meal) {
		this.meal = meal;
	}

	/**
	 * @return the drinkType
	 */
	public String getDrinkType() {
		return drinkType;
	}

	/**
	 * @param drinkType the drinkType to set
	 */
	public void setDrinkType(String drinkType) {
		this.drinkType = drinkType;
	}

	/**
	 * @return the entertainmentItems
	 */
	public String getEntertainmentItems() {
		return entertainmentItems;
	}

	/**
	 * @param entertainmentItems the entertainmentItems to set
	 */
	public void setEntertainmentItems(String entertainmentItems) {
		this.entertainmentItems = entertainmentItems;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the staffFirstName
	 */
	public String getStaffFirstName() {
		return staffFirstName;
	}

	/**
	 * @param staffFirstName the staffFirstName to set
	 */
	public void setStaffFirstName(String staffFirstName) {
		this.staffFirstName = staffFirstName;
	}

	/**
	 * @return the staffLastName
	 */
	public String getStaffLastName() {
		return staffLastName;
	}

	/**
	 * @param staffLastName the staffLastName to set
	 */
	public void setStaffLastName(String staffLastName) {
		this.staffLastName = staffLastName;
	}

	/**
	 * @return the isFormal
	 */
	public String getFormal() {
		return formal;
	}

	/**
	 * @param isFormal the isFormal to set
	 */
	public void setFormal(String mealFormal) {
		this.formal = mealFormal;
	}

	public void validateStaffExists(EventErrorMsgs errors) {
		String error = "";
		if (!UserDAO.doesUserExist(getStaffFirstName(), getStaffLastName(), "Staff")) {
			error = "No name found for expected role";
			errors.setStaffFirstNameError(error);
			errors.setStaffLastNameError(error);
			errors.setErrorMsg();
		}
	}

	public void validateEvenName(EventErrorMsgs errors) {
		// TODO validation
		String event = getName();
		String error = "";
		if (event.length() <= 2 || event.length() >= 30) {
			error = "Event name length must be >2 and <30";
			errors.setEventNameError(error);
			errors.setErrorMsg();
		} else if (!(Character.isUpperCase(event.charAt(0)))) {
			error = "Event name must start with a capital letter";
			errors.setEventNameError(error);
			errors.setErrorMsg();
		} else {
			boolean check = EventDAO.checkEventName(event);
			if (check) {
				error = "Event names must be unique";
				errors.setEventNameError(error);
				errors.setErrorMsg();
			}
		}
		}

	public void validateDate(String userName, EventErrorMsgs errors) throws ParseException {
		String error = "";
		String todayTime = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
		String date = getDate();
		if (todayTime.compareTo(date) > 0) {
			error = "Date cannot be in past";
			errors.setDateError(error);
			errors.setErrorMsg();
		} else if (!EventDAO.canUserReserveEventOnDay(userName, date)) {
			error = "More than 2 events per day not allowed";
			errors.setDateError(error);
			errors.setErrorMsg();
		} else if (!EventDAO.canUserReserveEventOnWeek(userName, date)) {
			error = "More than 5 events per 7 day period not allowed";
			errors.setDateError(error);
			errors.setErrorMsg();
		}
	}

	public void validateDuration(EventErrorMsgs errors) {
		// TODO validation
		String error = "";
		String startTime = getStartTime();
		StringTokenizer sto = new StringTokenizer(startTime, ":");
		int eventStartTime = Integer.parseInt(sto.nextToken() + sto.nextToken());

		int eventduration = Integer.parseInt(getDuration());
		eventduration *= 100;
		String date = getDate();
		StringTokenizer st = new StringTokenizer(date, "-");
		int year = Integer.parseInt(st.nextToken());
		int month = Integer.parseInt(st.nextToken());
		int dayOfRes = Integer.parseInt(st.nextToken());
		LocalDate localDate = LocalDate.of(year, month, dayOfRes);
		DayOfWeek day = localDate.getDayOfWeek();
		int openTime, closeTime = 2300;
		if (day.equals(DayOfWeek.SUNDAY)) {
			openTime = 1200;
		} else {
			openTime = 700;
		}
		int dayU = day.getValue();
		if (dayU > 4) {
			closeTime = 2600;
		}

		if (eventStartTime < openTime) {
			error = "Start time cannot preceed open time";
			errors.setDurationError(error);
			errors.setErrorMsg();
		}
		if (eventStartTime + eventduration > closeTime) {
			error = "Duration cannot exceed close time";
			errors.setDurationError(error);
			errors.setErrorMsg();
		}
	}

	public void validateHall(EventErrorMsgs errors) {
		// TODO validation
		String error = "";
		String hallName = this.getHallName();
		int cap;

		try {
			int a = Integer.parseInt(attendees);

			if (hallName.equals("maverick")) {
				cap = 100;
			} else if (hallName.equals("kc")) {
				cap = 25;
			} else if (hallName.equals("arlington")) {
				cap = 50;
			} else if (hallName.equals("shard")) {
				cap = 25;
			} else if (hallName.equals("liberty")) {
				cap = 75;
			} else {
				errors.setHallError("Hall not found");
				errors.setErrorMsg();
				return;
			}

			if (cap < a) {
				error = "Chosen hall too small";
				errors.setHallError(error);
				errors.setErrorMsg();
			}
		} catch (Exception e) {
		}

	}

	public void validateAttendees(EventErrorMsgs errors) {
		// TODO validation
		String error = "";

		try {
			int att = Integer.parseInt(getAttendees());
			if (att <= 0) {
				error = "Estimated attendees must be greater than 0";
				errors.setAttendeesError(error);
				errors.setErrorMsg();
			} else if (att > 100) {
				error = "Estimated attendees must be <=100";
				errors.setAttendeesError(error);
				errors.setErrorMsg();
			}
		} catch (Exception e) {
			error = "Estimated attendees must be a number";
			errors.setAttendeesError(error);
			errors.setErrorMsg();
		}

	}

}
