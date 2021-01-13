package model;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class PaymentModel implements Serializable {

	private static final long serialVersionUID = 1L;
	String eventID, username, cardNumber, expDate, pin, price;

	public PaymentModel(String eventID, String username, String cardNumber, String expDate, String pin, String price) {
		this.setEventID(eventID);
		this.setUsername(username);
		this.setCardNumber(cardNumber);
		this.setExpDate(expDate);
		this.setPin(pin);
		this.setPrice(price);
	}

	public PaymentModel() {

	}

	public String getEventID() {
		return eventID;
	}

	public void setEventID(String eventID) {
		this.eventID = eventID;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getExpDate() {
		return expDate;
	}

	public void setExpDate(String expDate) {
		this.expDate = expDate;
	}

	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public void validateCreditCardNo(PaymentErrorMsgs errors) {
		String error = "";
		if (getCardNumber().equals("")) {
			error = "Credit card cannot be empty";
			errors.setCcNoError(error);
			errors.setError();
		} else if (!checkCreditCardNo(getCardNumber())) {
			error = "Credit card number must be a number";
			errors.setCcNoError(error);
			errors.setError();
		} else if (getCardNumber().length() != 16) {
			error += "Credit card number must be 16 digits";
			errors.setCcNoError(error);
			errors.setError();
		}
//		return error;
	}

	public boolean checkCreditCardNo(String cardNo) {
		boolean result = true;
		try {
			Long.parseLong(cardNo);
		} catch (Exception e) {
			result = false;
		}
		return result;
	}

	public void validatePin(PaymentErrorMsgs errors) {
		String error = "";
		if (getPin().equals("")) {
			error = "Credit card pin cannot be empty";
			errors.setCcPinError(error);
			errors.setError();
		} else if (!(checkInt(getPin()))) {
			error = "Credit card pin must be a number";
			errors.setCcPinError(error);
			errors.setError();
		} else if (getPin().length() != 4) {
			error = "Credit card pin must be 4 digits";
			errors.setCcPinError(error);
			errors.setError();
		}
//		return error;
	}

	public void validateExp(PaymentErrorMsgs errors) {
		String error = "";
		if (getExpDate().equals("")) {
			error = "Exp date cannot be empty";
			errors.setExpDateError(error);
			errors.setError();
		} else if (!checkInt(getExpDate())) {
			error = "Credit Card exp date must be a number";
			errors.setExpDateError(error);
			errors.setError();
		} else if (getExpDate().length() != 4) {
			error = "Credit card exp date must be 4 digits";
			errors.setExpDateError(error);
			errors.setError();
		} else {
			SimpleDateFormat sdf = new SimpleDateFormat("MMyy", Locale.ENGLISH);
			String currentTime = new SimpleDateFormat("MMyy").format(Calendar.getInstance().getTime());
			Date date1 = null, date2 = null;
			sdf.setLenient(false);
			try {
				date1 = sdf.parse(getExpDate());
				date2 = sdf.parse(currentTime);
				if (date1.before(date2)) {
					error = ("Credit card exp date must not be in past");
					errors.setExpDateError(error);
					errors.setError();
				}
			} catch (ParseException e) {
				errors.setExpDateError("Wrong Exp date format");
				errors.setError();
				// return "Wrong Exp date format";
			}

		}

//		return error;
	}

	public boolean checkInt(String val) {
		boolean result = true;
		try {
			Integer.parseInt(val);
		} catch (Exception e) {
			result = false;
		}
		return result;
	}
}
