package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import data.PaymentDAO;
import model.PaymentErrorMsgs;
import model.PaymentModel;

/**
 * Servlet implementation class PaymentController
 */
@WebServlet("/PaymentController")
public class PaymentController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public PaymentController() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PaymentModel getPaymentParam(HttpServletRequest request) {
		return new PaymentModel(request.getParameter("eventID"), request.getParameter("userName"),
				request.getParameter("cardNo"), request.getParameter("expiryDate"), request.getParameter("cvv"),
				request.getParameter("price"));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		PaymentModel paymentModel = getPaymentParam(request);
		PaymentErrorMsgs paymentErrors = new PaymentErrorMsgs();
		paymentModel.validateCreditCardNo(paymentErrors);
		paymentModel.validatePin(paymentErrors);
		paymentModel.validateExp(paymentErrors);
		if (paymentErrors.getError() != "") {
			session.setAttribute("PaymentErrorMsgs", paymentErrors);
			getServletContext().getRequestDispatcher("/Payment.jsp").forward(request, response);
		} else {
			session.removeAttribute("PaymentErrorMsgs");
			PaymentDAO.AddPayment(paymentModel);
			String userName = request.getParameter("userName");
			session.setAttribute("userName", userName);
			getServletContext().getRequestDispatcher("/EventSummary.jsp").forward(request, response);
		}

	}

}
