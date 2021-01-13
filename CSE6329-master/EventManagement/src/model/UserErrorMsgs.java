package model;

public class UserErrorMsgs {

	private String errorMsg, passwordError, phoneError, cityError, firstNameError, lastNameError, roleError, stateError,
			streetNameError, streetNumberError, userNameError, utaIdError, zipCodeError, emailError;

	public UserErrorMsgs() {
		this.setCityError("");
		this.setEmailError("");
		this.setErrorMsg("");
		this.setFirstNameError("");
		this.setLastNameError("");
		this.setPasswordError("");
		this.setPhoneError("");
		this.setRoleError("");
		this.setStateError("");
		this.setStreetNameError("");
		this.setStreetNumberError("");
		this.setUserNameError("");
		this.setUtaIdError("");
		this.setZipCodeError("");
	}

	/**
	 * @return the errorMsg
	 */
	public String getErrorMsg() {
		return errorMsg;
	}

	/**
	 * @return the emailError
	 */
	public String getEmailError() {
		return emailError;
	}

	/**
	 * @param emailError the emailError to set
	 */
	public void setEmailError(String emailError) {
		this.emailError = emailError;
	}

	/**
	 * @param creates error message
	 */
	public void setErrorMsg() {

		setErrorMsg("Please correct the above errors");
	}

	public void setErrorMsg(String s) {
		this.errorMsg = s;
	}

	/**
	 * @return the passwordError
	 */
	public String getPasswordError() {
		return passwordError;
	}

	/**
	 * @param passwordError the passwordError to set
	 */
	public void setPasswordError(String passwordError) {
		this.passwordError = passwordError;
	}

	/**
	 * @return the phoneError
	 */
	public String getPhoneError() {
		return phoneError;
	}

	/**
	 * @param phoneError the phoneError to set
	 */
	public void setPhoneError(String phoneError) {
		this.phoneError = phoneError;
	}

	/**
	 * @return the cityError
	 */
	public String getCityError() {
		return cityError;
	}

	/**
	 * @param cityError the cityError to set
	 */
	public void setCityError(String cityError) {
		this.cityError = cityError;
	}

	/**
	 * @return the firstNameError
	 */
	public String getFirstNameError() {
		return firstNameError;
	}

	/**
	 * @param firstNameError the firstNameError to set
	 */
	public void setFirstNameError(String firstNameError) {
		this.firstNameError = firstNameError;
	}

	/**
	 * @return the roleError
	 */
	public String getRoleError() {
		return roleError;
	}

	/**
	 * @param roleError the roleError to set
	 */
	public void setRoleError(String roleError) {
		this.roleError = roleError;
	}

	/**
	 * @return the stateError
	 */
	public String getStateError() {
		return stateError;
	}

	/**
	 * @param stateError the stateError to set
	 */
	public void setStateError(String stateError) {
		this.stateError = stateError;
	}

	/**
	 * @return the streetNameError
	 */
	public String getStreetNameError() {
		return streetNameError;
	}

	/**
	 * @param streetNameError the streetNameError to set
	 */
	public void setStreetNameError(String streetNameError) {
		this.streetNameError = streetNameError;
	}

	/**
	 * @return the streetNumberError
	 */
	public String getStreetNumberError() {
		return streetNumberError;
	}

	/**
	 * @param streetNumberError the streetNumberError to set
	 */
	public void setStreetNumberError(String streetNumberError) {
		this.streetNumberError = streetNumberError;
	}

	/**
	 * @return the userNameError
	 */
	public String getUserNameError() {
		return userNameError;
	}

	/**
	 * @param userNameError the userNameError to set
	 */
	public void setUserNameError(String userNameError) {
		this.userNameError = userNameError;
	}

	/**
	 * @return the utaIdError
	 */
	public String getUtaIdError() {
		return utaIdError;
	}

	/**
	 * @param utaIdError the utaIdError to set
	 */
	public void setUtaIdError(String utaIdError) {
		this.utaIdError = utaIdError;
	}

	/**
	 * @return the zipCodeError
	 */
	public String getZipCodeError() {
		return zipCodeError;
	}

	/**
	 * @param zipCodeError the zipCodeError to set
	 */
	public void setZipCodeError(String zipCodeError) {
		this.zipCodeError = zipCodeError;
	}

	/**
	 * @return the lastNameError
	 */
	public String getLastNameError() {
		return lastNameError;
	}

	/**
	 * @param lastNameError the lastNameError to set
	 */
	public void setLastNameError(String lastNameError) {
		this.lastNameError = lastNameError;
	}

}
