package test.model;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import junitparams.FileParameters;
import junitparams.JUnitParamsRunner;
import model.PaymentErrorMsgs;
import model.PaymentModel;

@RunWith(JUnitParamsRunner.class)
public class PaymentModelTest {

	private PaymentModel paymentModel;
	private PaymentErrorMsgs paymentErrors;

	@Before
	public void setUp() throws Exception {
		paymentModel = new PaymentModel();
		paymentErrors = new PaymentErrorMsgs();
	}

	@Test
	@FileParameters("src/test/testFiles/Payment.csv")
	public void testValidatePayment(String sNo, String eventID, String price, String username, String cardNumber,
			String pin, String expDate, String cardErrorMsg, String pinErrorMsg, String expDateErrorMsg, String error) {
		paymentModel = new PaymentModel(eventID, username, cardNumber, expDate, pin, price);
		paymentModel.validateCreditCardNo(paymentErrors);
		paymentModel.validatePin(paymentErrors);
		paymentModel.validateExp(paymentErrors);
		assertEquals(eventID, paymentModel.getEventID());
		assertEquals(price, paymentModel.getPrice());
		assertEquals(username, paymentModel.getUsername());
		assertEquals(error, paymentErrors.getError());
		assertEquals(cardErrorMsg, paymentErrors.getCcNoError());
		assertEquals(pinErrorMsg, paymentErrors.getCcPinError());
		assertEquals(expDateErrorMsg, paymentErrors.getExpDateError());
	}
}
