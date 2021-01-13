package model;

public class PaymentErrorMsgs {

	private String expDateError, ccNoError, ccPinError, error;

	public PaymentErrorMsgs() {
		this.error = "";
		this.setCcNoError("");
		this.setCcPinError("");
		this.setExpDateError("");
	}

	/**
	 * @return the expDateError
	 */
	public String getExpDateError() {
		return expDateError;
	}

	/**
	 * @param expDateError the expDateError to set
	 */
	public void setExpDateError(String expDateError) {
		this.expDateError = expDateError;
	}

	/**
	 * @return the ccNoError
	 */
	public String getCcNoError() {
		return ccNoError;
	}

	/**
	 * @param ccNoError the ccNoError to set
	 */
	public void setCcNoError(String ccNoError) {
		this.ccNoError = ccNoError;
	}

	/**
	 * @return the ccPinError
	 */
	public String getCcPinError() {
		return ccPinError;
	}

	/**
	 * @param ccPinError the ccPinError to set
	 */
	public void setCcPinError(String ccPinError) {
		this.ccPinError = ccPinError;
	}

	/**
	 * @return the error
	 */
	public String getError() {
		return error;
	}

	/**
	 * @param error the error to set
	 */
	public void setError() {
		this.error = "Please correct above errors";
	}

}
