package test.model;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import junitparams.FileParameters;
import junitparams.JUnitParamsRunner;
import model.UserErrorMsgs;
import model.UserModel;

@RunWith(JUnitParamsRunner.class)
public class UserModelTestFull {

	private UserModel userModel;
	private UserErrorMsgs errors;

	@Before
	public void setUp() throws Exception {
		userModel = new UserModel();
		errors = new UserErrorMsgs();
	}

	@Test
	@FileParameters("src/test/testFiles/UserModelTests.csv")
	public void test(String sno, String userName, String password, String lastName, String firstName, String utaId,
			String phoneNumber, String emailId, String streetNumber, String streetName, String city, String state,
			String zipCode, String role, String userNameError, String passwordError, String lastNameError,
			String firstNameError, String utaIdError, String phoneNumberError, String emailIdError,
			String streetNumberError, String streetNameError, String cityError, String stateError, String zipCodeError,
			String roleError, String error, String comment) {

		userModel = new UserModel(userName, password, lastName, firstName, utaId, phoneNumber, emailId, streetNumber,
				streetName, city, state, zipCode, role);
		@SuppressWarnings("unused")
		UserModel userModel1 = new UserModel(userName, password);

		userModel.validateUserName(errors);
		userModel.validatePassword(errors);
		userModel.validateLastName(errors);
		userModel.validateFirstName(errors);
		userModel.validateUtaId(errors);
		userModel.validatePhoneNumber(errors);
		userModel.validateEmail(errors);
		userModel.validateStreetNumber(errors);
		userModel.validateStreetName(errors);
		userModel.validateCity(errors);
		userModel.validateState(errors);
		userModel.validateZipCode(errors);
		userModel.validateRole(errors);

		assertEquals(userNameError, errors.getUserNameError());
		assertEquals(passwordError, errors.getPasswordError());
		assertEquals(lastNameError, errors.getLastNameError());
		assertEquals(firstNameError, errors.getFirstNameError());
		assertEquals(utaIdError, errors.getUtaIdError());
		assertEquals(phoneNumberError, errors.getPhoneError());
		assertEquals(emailIdError, errors.getEmailError());
		assertEquals(streetNumberError, errors.getStreetNumberError());
		assertEquals(streetNameError, errors.getStreetNameError());
		assertEquals(cityError, errors.getCityError());
		assertEquals(stateError, errors.getStateError());
		assertEquals(zipCodeError, errors.getZipCodeError());
		assertEquals(roleError, errors.getRoleError());
		assertEquals(error, errors.getErrorMsg());
	}

}
