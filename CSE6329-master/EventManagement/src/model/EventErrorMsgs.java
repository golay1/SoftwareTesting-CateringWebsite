package model;

public class EventErrorMsgs {
	String errorMsg, eventNameError, durationError, hallError, attendeesError, staffFirstNameError, staffLastNameError,
			dateError;

	public EventErrorMsgs() {
		this.errorMsg = "";
		setEventNameError("");
		setDurationError("");
		setHallError("");
		setAttendeesError("");
		setStaffFirstNameError("");
		setStaffLastNameError("");
		setDateError("");
	}

	/**
	 * @return the dateError
	 */
	public String getDateError() {
		return dateError;
	}

	/**
	 * @param dateError the dateError to set
	 */
	public void setDateError(String dateError) {
		this.dateError = dateError;
	}

	/**
	 * @return the errorMsg
	 */
	public String getErrorMsg() {
		return errorMsg;
	}

	/**
	 * Set the error message
	 */
	public void setErrorMsg() {
		this.errorMsg = "Please correct the above errors";

	}

	/**
	 * @return the eventNameError
	 */
	public String getEventNameError() {
		return eventNameError;
	}

	/**
	 * @param eventNameError the eventNameError to set
	 */
	public void setEventNameError(String eventNameError) {
		this.eventNameError = eventNameError;
	}

	/**
	 * @return the durationError
	 */
	public String getDurationError() {
		return durationError;
	}

	/**
	 * @param durationError the durationError to set
	 */
	public void setDurationError(String durationError) {
		this.durationError = durationError;
	}

	/**
	 * @return the hallError
	 */
	public String getHallError() {
		return hallError;
	}

	/**
	 * @param hallError the hallError to set
	 */
	public void setHallError(String hallError) {
		this.hallError = hallError;
	}

	/**
	 * @return the attendeesError
	 */
	public String getAttendeesError() {
		return attendeesError;
	}

	/**
	 * @param attendeesError the attendeesError to set
	 */
	public void setAttendeesError(String attendeesError) {
		this.attendeesError = attendeesError;
	}

	/**
	 * @return the staffFirstNameError
	 */
	public String getStaffFirstNameError() {
		return staffFirstNameError;
	}

	/**
	 * @param staffFirstNameError the staffFirstNameError to set
	 */
	public void setStaffFirstNameError(String staffFirstNameError) {
		this.staffFirstNameError = staffFirstNameError;
	}

	/**
	 * @return the staffLastNameError
	 */
	public String getStaffLastNameError() {
		return staffLastNameError;
	}

	/**
	 * @param staffLastNameError the staffLastNameError to set
	 */
	public void setStaffLastNameError(String staffLastNameError) {
		this.staffLastNameError = staffLastNameError;
	}

}
