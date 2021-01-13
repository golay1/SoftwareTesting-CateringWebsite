package model;

import java.io.Serializable;
import java.util.HashSet;

import data.UserDAO;

// TODO
public class UserModel implements Serializable {
	private static final long serialVersionUID = 1L;

	String userName, password, lastName, firstName, utaId, phoneNumber, emailId, streetNumber, streetName, city, state,
			zipCode, role;

	public UserModel(String userName, String password, String lastName, String firstName, String utaId,
			String phoneNumber, String emailId, String streetNumber, String streetName, String city, String state,
			String zipCode, String role) {

		this.setUserName(userName);
		this.setPassword(password);
		this.setLastName(lastName);
		this.setFirstName(firstName);
		this.setUtaId(utaId);
		this.setPhoneNumber(phoneNumber);
		this.setEmailId(emailId);
		this.setStreetNumber(streetNumber);
		this.setStreetName(streetName);
		this.setCity(city);
		this.setState(state);
		this.setZipCode(zipCode);
		this.setRole(role);

	}

	public UserModel(String userName, String password) {
		this.setUserName(userName);
		this.setPassword(password);
	}

	public UserModel() {
		// TODO Auto-generated constructor stub
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getUtaId() {
		return utaId;
	}

	public void setUtaId(String utaId) {
		this.utaId = utaId;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getStreetNumber() {
		return streetNumber;
	}

	public void setStreetNumber(String streetNumber) {
		this.streetNumber = streetNumber;
	}

	public String getStreetName() {
		return streetName;
	}

	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public void validatePassword(UserErrorMsgs errors) {
		String password = getPassword();
		String error = "";
		if (!password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).{7,30}$")) {
			errors.setPasswordError("Password must contain an upper case letter"
					+ "Password must contain a lower case letter" + "Password length must be >7 and <30"
					+ "Password must contain a number" + "Password must contain a special character");
			errors.setErrorMsg();
		}
//		return error;
	}

	public void validatePhoneNumber(UserErrorMsgs errors) {
		String error = "";
		String phoneNumber = this.getPhoneNumber();
		if (phoneNumber.length() != 10) {
			error = "Phone number must have 10 digits";
			errors.setPhoneError(error);
			errors.setErrorMsg();
		} else {
			try {
				Long.parseLong(phoneNumber);
			} catch (Exception e) {
				error = "Phone number must be numeric";
				errors.setPhoneError(error);
				errors.setErrorMsg();
			}
		}
//		return error;
	}

	public void validateCity(UserErrorMsgs errors) {
		String error = "";
		String city = this.getCity();
		if (city.equalsIgnoreCase("")) {
			error = "city length must be >2 and <30";
			errors.setCityError(error);
			errors.setErrorMsg();
		} else {
			String specialChars = " ~`!@#$%^&*()-_=+\\\\|[{]};:'\\\",<.>/?";
			char firstCharacter = city.charAt(0);

			if (city.length() <= 2 || city.length() >= 30) {
				error = "city length must be >2 and <30";
				errors.setCityError(error);
				errors.setErrorMsg();
			} else if (!(Character.isUpperCase(firstCharacter))) {
				error = "city must start with a capital letter";
				errors.setCityError(error);
				errors.setErrorMsg();
			}

			for (int i = 0; i < city.length(); i++) {
				char currentCharacter = city.charAt(i);

				if (specialChars.contains(String.valueOf(currentCharacter))) {
					error = "city cannot contain special characters";
					errors.setCityError(error);
					errors.setErrorMsg();
				} else if (Character.isDigit(city.charAt(i))) {
					error = "city cannot be a number";
					errors.setCityError(error);
					errors.setErrorMsg();
				}
			}
		}
	}

	public void validateEmail(UserErrorMsgs errors) {
		String error = "";
		if (getEmailId().length() < 7 || getEmailId().length() > 45) {
			error = "Email address must be between 7 and 45 characters long";
			errors.setEmailError(error);
			errors.setErrorMsg();
		} else if (!getEmailId().contains("@")) {
			error = "Email address needs to contain @";
			errors.setEmailError(error);
			errors.setErrorMsg();
		} else {
			int index = getEmailId().lastIndexOf(".");
			if (index == -1) {
				error = "Invalid domain name";
				errors.setEmailError(error);
				errors.setErrorMsg();
			} else {
				String domain = getEmailId().substring(index).toLowerCase();
				HashSet<String> domains = new HashSet<String>();
				domains.add(".org");
				domains.add(".edu");
				domains.add(".com");
				domains.add(".net");
				domains.add(".gov");
				domains.add(".mil");
				if (!domains.contains(domain)) {
					error = "Invalid domain name";
					errors.setEmailError(error);
					errors.setErrorMsg();
				}
			}
		}
	}

	public void validateFirstName(UserErrorMsgs errors) {
		String error = "";
		String firstName = this.getFirstName();
		if (firstName.equals("")) {
			error = "First name length must be >2 and <30";
			errors.setFirstNameError(error);
			errors.setErrorMsg();
		} else {
			String specialChars = " ~`!@#$%^&*()-_=+\\\\|[{]};:'\\\",<.>/?";
			char firstCharacter = firstName.charAt(0);

			if (firstName.length() <= 2 || firstName.length() >= 30) {
				error = "First name length must be >2 and <30";
				errors.setFirstNameError(error);
				errors.setErrorMsg();
			} else if (!(Character.isUpperCase(firstCharacter))) {
				error = "First name must start with a capital letter";
				errors.setFirstNameError(error);
				errors.setErrorMsg();
			}

			for (int i = 0; i < firstName.length(); i++) {
				char currentCharacter = firstName.charAt(i);
				if (specialChars.contains(String.valueOf(currentCharacter))) {
					error = "First name cannot contain special characters";
					errors.setFirstNameError(error);
					errors.setErrorMsg();
				} else if (Character.isDigit(firstName.charAt(i))) {
					error = "First name cannot be a number";

					errors.setFirstNameError(error);
					errors.setErrorMsg();
				}
			}
		}

	}

	public void validateLastName(UserErrorMsgs errors) {
		String error = "";
		String lastName = this.getLastName();
		if (lastName.equals("")) {
			error = "Last name length must be >2 and <30";
			errors.setLastNameError(error);
			errors.setErrorMsg();
		} else {
			String specialChars = " ~`!@#$%^&*()-_=+\\\\|[{]};:'\\\",<.>/?";
			char firstCharacter = lastName.charAt(0);

			if (lastName.length() <= 2 || lastName.length() >= 30) {
				error = "Last name length must be >2 and <30";
				errors.setLastNameError(error);
				errors.setErrorMsg();
			} else if (!(Character.isUpperCase(firstCharacter))) {
				error = "Last name must start with a capital letter";
				errors.setLastNameError(error);
				errors.setErrorMsg();
			}

			for (int i = 0; i < lastName.length(); i++) {
				char currentCharacter = lastName.charAt(i);

				if (specialChars.contains(String.valueOf(currentCharacter))) {
					error = "Last name cannot contain special characters";
					errors.setLastNameError(error);
					errors.setErrorMsg();
				} else if (Character.isDigit(lastName.charAt(i))) {
					error = "Last name cannot be a number";
					errors.setLastNameError(error);
					errors.setErrorMsg();
				}
			}
		}
	}

	public void validateRole(UserErrorMsgs errors) {
		String error = "";
		if (UserDAO.doesUserTypeExist(getRole())) {
			if (getRole().equalsIgnoreCase("admin")) {
				error = "System can only have one Admin";
				errors.setRoleError(error);
				errors.setErrorMsg();
			} else if (getRole().equalsIgnoreCase("manager")) {
				error = "System can only have one Caterer Mgr";
				errors.setRoleError(error);
				errors.setErrorMsg();
			}
		} else {
			error = "Invalid role";
			errors.setRoleError(error);
			errors.setErrorMsg();
		}
	}

	public void validateState(UserErrorMsgs errors) {

		String error = "";
		String state = this.getState();
		if (state.length() != 2) {
			error = "State must be a 2 letter abbreviation";
			errors.setStateError(error);
			errors.setErrorMsg();
		} else {
			for (int i = 0; i < state.length(); i++) {
				if (Character.isDigit(state.charAt(i))) {
					error = "State must be non-numeric";
					errors.setStateError(error);
					errors.setErrorMsg();
					return;
				}
			}
			HashSet<String> states = new HashSet<String>();
			populateStates(states);
			state = state.toUpperCase();
			if (!states.contains(state)) {
				error = "State abbreviation not found";
				errors.setStateError(error);
				errors.setErrorMsg();
			}
		}
	}

	public void populateStates(HashSet<String> states) {
		states.add("AL");
		states.add("AK");
		states.add("AZ");
		states.add("AR");
		states.add("CA");
		states.add("CO");
		states.add("CT");
		states.add("DE");
		states.add("FL");
		states.add("GA");
		states.add("HI");
		states.add("ID");
		states.add("IL");
		states.add("IN");
		states.add("IA");
		states.add("KS");
		states.add("KY");
		states.add("LA");
		states.add("ME");
		states.add("MD");
		states.add("MA");
		states.add("MI");
		states.add("MN");
		states.add("MS");
		states.add("MO");
		states.add("MT");
		states.add("NE");
		states.add("NV");
		states.add("NH");
		states.add("NJ");
		states.add("NM");
		states.add("NY");
		states.add("NC");
		states.add("ND");
		states.add("OH");
		states.add("OK");
		states.add("OR");
		states.add("PA");
		states.add("RI");
		states.add("SC");
		states.add("SD");
		states.add("TN");
		states.add("TX");
		states.add("UT");
		states.add("VT");
		states.add("VA");
		states.add("WA");
		states.add("WV");
		states.add("WI");
		states.add("WY");
	}

	public void validateStreetName(UserErrorMsgs errors) {
		String error = "";
		String streetName = this.getStreetName();

		if (streetName.length() == 0) {
			error = "Street name length must be greater than zero";
			errors.setStreetNameError(error);
			errors.setErrorMsg();
		} else if (streetName.length() >= 40) {
			error = "Street name length must be less than 40";
			errors.setStreetNameError(error);
			errors.setErrorMsg();
		} else {
			for (int i = 0; i < streetName.length(); i++) {
				if (Character.isDigit(streetName.charAt(i))) {
					error = "Street name be non-numeric";
					errors.setStreetNameError(error);
					errors.setErrorMsg();
				}
			}
		}

	}

	public void validateStreetNumber(UserErrorMsgs errors) {
		String error = "";
		String streetNumber = this.getStreetNumber();
		if (streetNumber.equals("")) {
			error = "Street number length must be >0 and <7";
			errors.setStreetNumberError(error);
			errors.setErrorMsg();
		} else if (streetNumber.length() >= 7) {
			error = "Street number length must be >0 and <7";
			errors.setStreetNumberError(error);
			errors.setErrorMsg();
		} else {
			try {
				Long.parseLong(streetNumber);
			} catch (Exception e) {
				error = "Street number must be numeric";
				errors.setStreetNumberError(error);
				errors.setErrorMsg();
			}
		}
	}

	public void validateUserName(UserErrorMsgs errors) {
		String error = "";
		String userName = this.getUserName();
		if (userName.length() <= 4 || userName.length() > 20) {
			error = "Username length must be >4 and <=20";
			errors.setUserNameError(error);
			errors.setErrorMsg();
		} else /*
				 * if (!Character.isLetter(userName.charAt(0))) { error =
				 * "Username must start with a letter"; } else
				 */ {
			String specialChars = " ~`!@#$%^&*()-_=+\\\\|[{]};:'\\\",<.>/?";
			for (int i = 0; i < userName.length(); i++) {
				char currentCharacter = userName.charAt(i);
				if (specialChars.contains(String.valueOf(currentCharacter))) {
					error = "Username cannot contain special characters";
					errors.setUserNameError(error);
					errors.setErrorMsg();
					return;
				}
			}
			if (!Character.isLetter(userName.charAt(0))) {
				error = "Username must start with a letter";
				errors.setUserNameError(error);
				errors.setErrorMsg();
			} else if (UserDAO.isUserNameTaken(userName)) {
				error = "Username already in database";
				errors.setUserNameError(error);
				errors.setErrorMsg();
			}
		}
	}

	public void validateUtaId(UserErrorMsgs errors) {
		String error = "";
		String utaId = this.getUtaId();
		if (utaId.length() != 10) {
			error = "UTA Id must have a length of 10";
			errors.setUtaIdError(error);
			errors.setErrorMsg();
		} else {
			try {
				Long.parseLong(utaId);
			} catch (Exception e) {
				error = "UTA Id must be numeric";
				errors.setUtaIdError(error);
				errors.setErrorMsg();
			}
		}
		if (UserDAO.isUTAIDtaken(utaId)) {
			error = "UTA ID already in database";
			errors.setUtaIdError(error);
			errors.setErrorMsg();
		}
	}

	public void validateZipCode(UserErrorMsgs errors) {
		String error = "";
		String zipcode = this.getZipCode();
		if (zipcode.length() != 5) {
			error = "Zipcode must have a length of 5";
			errors.setZipCodeError(error);
			errors.setErrorMsg();
		} else {
			try {
				Long.parseLong(zipcode);
			} catch (Exception e) {
				error = "zipcode must be numeric";
				errors.setZipCodeError(error);
				errors.setErrorMsg();
			}
		}
	}
}
