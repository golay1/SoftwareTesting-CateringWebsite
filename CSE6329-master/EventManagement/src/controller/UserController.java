package controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import data.UserDAO;
import model.UserErrorMsgs;
import model.UserModel;

/**
 * Servlet implementation class UserController
 */
@WebServlet("/UserController")
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public UserController() {
		// TODO Auto-generated constructor stub
	}

	private UserModel getRegistrationParam(HttpServletRequest request) {
		return new UserModel(request.getParameter("username"), request.getParameter("user_password"),
				request.getParameter("last_name"), request.getParameter("first_name"), request.getParameter("uta_id"),
				request.getParameter("phone_number"), request.getParameter("email_id"),
				request.getParameter("street_number"), request.getParameter("street_name"),
				request.getParameter("city"), request.getParameter("state"), request.getParameter("zip_code"),
				request.getParameter("role"));
	}

	private UserModel getUserParam(HttpServletRequest request) {
		return new UserModel(request.getParameter("username"), request.getParameter("user_password"),
				request.getParameter("last_name"), request.getParameter("first_name"), request.getParameter("uta_id"),
				request.getParameter("phone_number"), request.getParameter("email_id"),
				request.getParameter("street_number"), request.getParameter("street_name"),
				request.getParameter("city"), request.getParameter("state"), request.getParameter("zip_code"),
				request.getParameter("role"));
	}

	private UserModel getLoginParam(HttpServletRequest request) {
		return new UserModel(request.getParameter("username"), request.getParameter("Upassword"));
	}

	private void Login(UserModel userModel, HttpServletRequest request, HttpServletResponse response,
			HttpSession session) throws ServletException, IOException {
		boolean check = UserDAO.checkUser(userModel.getUserName(), userModel.getPassword());
		if (check) {
			session.removeAttribute("UserErrorMsgs");
			String role = UserDAO.role(userModel.getUserName());
			if (role.equalsIgnoreCase("user")) {
				session.setAttribute("userName", userModel.getUserName());
				getServletContext().getRequestDispatcher("/UserHomePage.jsp").forward(request, response);
			} else if (role.equalsIgnoreCase("admin")) {
				session.setAttribute("userName", userModel.getUserName());
				getServletContext().getRequestDispatcher("/AdminHomePage.jsp").forward(request, response);
			} else if (role.equalsIgnoreCase("manager")) {
				session.setAttribute("userName", userModel.getUserName());
				getServletContext().getRequestDispatcher("/ManagerHomePage.jsp").forward(request, response);
			} else /* if (role.equalsIgnoreCase("staff")) */ {
				session.setAttribute("userName", userModel.getUserName());
				getServletContext().getRequestDispatcher("/StaffHomePage.jsp").forward(request, response);
			}
		} else {
			UserErrorMsgs userError = new UserErrorMsgs();
			userError.setErrorMsg("Login failed. UserName or password incorrect");
			session.setAttribute("UserErrorMsgs", userError);
			getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
		}
	}

	public void register(UserModel userModel, HttpServletRequest request, HttpServletResponse response,
			HttpSession session) throws ServletException, IOException {
		// Do all validations
		UserErrorMsgs registrationErrors = new UserErrorMsgs();

		userModel.validateUserName(registrationErrors);
		userModel.validatePassword(registrationErrors);
		userModel.validateFirstName(registrationErrors);
		userModel.validateLastName(registrationErrors);
		userModel.validateRole(registrationErrors);
		userModel.validateEmail(registrationErrors);
		userModel.validateUtaId(registrationErrors);
		userModel.validatePhoneNumber(registrationErrors);
		userModel.validateStreetNumber(registrationErrors);
		userModel.validateStreetName(registrationErrors);
		userModel.validateCity(registrationErrors);
		userModel.validateState(registrationErrors);
		userModel.validateZipCode(registrationErrors);

		if (registrationErrors.getErrorMsg() == "") {
			session.removeAttribute("UserErrorMsgs");
			// no errors
			UserDAO.register(userModel.getUserName(), userModel.getPassword(), userModel.getLastName(),
					userModel.getFirstName(), userModel.getRole(), userModel.getUtaId(), userModel.getPhoneNumber(),
					userModel.getEmailId(), userModel.getStreetNumber(), userModel.getStreetName(), userModel.getCity(),
					userModel.getState(), userModel.getZipCode());
			session.removeAttribute("RegistrationModel");
			getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
		} else {
			session.setAttribute("RegistrationModel", userModel);
			session.setAttribute("UserErrorMsgs", registrationErrors);
			getServletContext().getRequestDispatcher("/registration.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		String action = request.getParameter("action");
		if (action.equalsIgnoreCase("login")) {
			UserModel userModel = getLoginParam(request);
			Login(userModel, request, response, session);
		}

		else if (action.equalsIgnoreCase("register")) {
			UserModel userModel = getRegistrationParam(request);
			register(userModel, request, response, session);
		}

		else if (action.equalsIgnoreCase("view")) {
			String userName = request.getParameter("userName");
			UserModel usermodel = UserDAO.viewprofile(userName);
			session.setAttribute("UserModel", usermodel);
			getServletContext().getRequestDispatcher("/ViewProfile.jsp").forward(request, response);
		}

		else if (action.equalsIgnoreCase("Back")) {
			String userName = request.getParameter("username");
			String role = UserDAO.role(userName);
			if (role.equalsIgnoreCase("user")) {
				session.setAttribute("userName", userName);
				getServletContext().getRequestDispatcher("/UserHomePage.jsp").forward(request, response);
			} else if (role.equalsIgnoreCase("admin")) {
				session.setAttribute("userName", userName);
				getServletContext().getRequestDispatcher("/AdminHomePage.jsp").forward(request, response);
			} else if (role.equalsIgnoreCase("manager")) {
				session.setAttribute("userName", userName);
				getServletContext().getRequestDispatcher("/ManagerHomePage.jsp").forward(request, response);
			} else /* if (role.equalsIgnoreCase("staff")) */ {
				session.setAttribute("userName", userName);
				getServletContext().getRequestDispatcher("/StaffHomePage.jsp").forward(request, response);
			}
		}

		else if (action.equalsIgnoreCase("add")) {
			String userName = request.getParameter("userName");
			DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
			DateFormat tf = new SimpleDateFormat("hh:mm aa");
			Date dateobj = new Date();
			session.setAttribute("currdate", df.format(dateobj));
			session.setAttribute("currtime", tf.format(dateobj));
			session.setAttribute("userName", userName);
			getServletContext().getRequestDispatcher("/RequestEvent.jsp").forward(request, response);
		}

		else if (action.equalsIgnoreCase("update")) {
			String userName = request.getParameter("username");
			UserModel usermodelUpdated = getUserParam(request);
			UserModel actualUserModel = UserDAO.viewprofile(userName);
			UserErrorMsgs updationErrors = new UserErrorMsgs();
			compareAndAddErrors(actualUserModel, usermodelUpdated, updationErrors);
			if (updationErrors.getErrorMsg() == "") {
				session.removeAttribute("UserErrorMsgs");
				UserDAO.updateprofile(usermodelUpdated, userName);
				session.setAttribute("UserModel", usermodelUpdated);
			} else {
				session.setAttribute("UserModel", actualUserModel);
				session.setAttribute("UserErrorMsgs", updationErrors);
			}
			session.setAttribute("userName", userName);
			getServletContext().getRequestDispatcher("/ViewProfile.jsp").forward(request, response);
		}

		else if (action.equalsIgnoreCase("search")) {
			String lastName = request.getParameter("lastName");
			ArrayList<UserModel> usersList = UserDAO.search(lastName);
			session.setAttribute("UserModelList", usersList);
			getServletContext().getRequestDispatcher("/UserSearch.jsp").forward(request, response);
		}

		else if (action.equalsIgnoreCase("viewUserProfile")) {
			String userName = request.getParameter("id");
			UserModel usermodel = UserDAO.viewprofile(userName);
			ArrayList<String> listRoles = new ArrayList<String>();
			if (usermodel.getRole().equalsIgnoreCase("user")) {
				listRoles.add("User");
				listRoles.add("Manager");
				listRoles.add("Staff");
				listRoles.add("Admin");
			} else if (usermodel.getRole().equalsIgnoreCase("manager")) {
				listRoles.add("Manager");
				listRoles.add("User");
				listRoles.add("Staff");
				listRoles.add("Admin");
			} else if (usermodel.getRole().equalsIgnoreCase("staff")) {
				listRoles.add("Staff");
				listRoles.add("Manager");
				listRoles.add("User");
				listRoles.add("Admin");
			} else /* if (usermodel.getRole().equalsIgnoreCase("admin")) */ {
				listRoles.add("Admin");
				listRoles.add("Manager");
				listRoles.add("Staff");
				listRoles.add("User");
			}
			session.setAttribute("UserModel", usermodel);
			session.setAttribute("listRoles", listRoles);
			getServletContext().getRequestDispatcher("/ViewUserProfile.jsp").forward(request, response);
		}

		else if (action.equalsIgnoreCase("modify")) {
			String userName = request.getParameter("username");
			UserModel usermodel = getUserParam(request);
			UserErrorMsgs userErrors = new UserErrorMsgs();
			usermodel.validateRole(userErrors);
			usermodel.validateCity(userErrors);
			usermodel.validateEmail(userErrors);
			usermodel.validateFirstName(userErrors);
			usermodel.validateLastName(userErrors);
			usermodel.validatePassword(userErrors);
			usermodel.validatePhoneNumber(userErrors);
			usermodel.validateState(userErrors);
			usermodel.validateStreetName(userErrors);
			usermodel.validateStreetNumber(userErrors);
			userErrors.setUserNameError("");
			userErrors.setUtaIdError("");
			usermodel.validateZipCode(userErrors);
			// userErrors.setErrorMsg();
			if (userErrors.getErrorMsg().equals("")) {
				session.removeAttribute("UserErrorMsgs");
				UserDAO.updateprofile(usermodel, userName);
				session.setAttribute("UserModel", usermodel);
				ArrayList<String> listRoles = new ArrayList<String>();
				if (usermodel.getRole().equalsIgnoreCase("user")) {
					listRoles.add("User");
					listRoles.add("Manager");
					listRoles.add("Staff");
					listRoles.add("Admin");
				} else /* if(usermodel.getRole().equalsIgnoreCase("staff")) */ {
					listRoles.add("Staff");
					listRoles.add("Manager");
					listRoles.add("User");
					listRoles.add("Admin");
				}
				session.setAttribute("listRoles", listRoles);
				session.removeAttribute("action");
				getServletContext().getRequestDispatcher("/ViewUserProfile.jsp").forward(request, response);
			} else {
				session.setAttribute("UserErrorMsgs", userErrors);
				getServletContext().getRequestDispatcher("/ViewUserProfile.jsp").forward(request, response);
			}
		} else if (action.equalsIgnoreCase("delete")) {
			String userName = request.getParameter("username");
			UserDAO.deleteUser(userName);
			session.removeAttribute("UserModel");
			session.removeAttribute("UserModelList");
			session.removeAttribute("action");
			getServletContext().getRequestDispatcher("/UserSearch.jsp").forward(request, response);
		}

		else if (action.equalsIgnoreCase("eventSummary")) {
			String userName = request.getParameter("userName");
			DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
			DateFormat tf = new SimpleDateFormat("hh:mm aa");
			Date dateobj = new Date();
			session.setAttribute("currdate", df.format(dateobj));
			session.setAttribute("currtime", tf.format(dateobj));
			session.setAttribute("userName", userName);
			getServletContext().getRequestDispatcher("/EventSummary.jsp").forward(request, response);
		}

		else if (action.equalsIgnoreCase("eventSummaryM")) {
			String userName = request.getParameter("userName");
			DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
			DateFormat tf = new SimpleDateFormat("hh:mm aa");
			Date dateobj = new Date();
			session.setAttribute("currdate", df.format(dateobj));
			session.setAttribute("currtime", tf.format(dateobj));
			session.setAttribute("userName", userName);
			getServletContext().getRequestDispatcher("/ManagerEventSummary.jsp").forward(request, response);
		}

		else if (action.equalsIgnoreCase("eventSummaryS")) {
			String userName = request.getParameter("userName");
			DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
			DateFormat tf = new SimpleDateFormat("hh:mm aa");
			Date dateobj = new Date();
			session.setAttribute("currdate", df.format(dateobj));
			session.setAttribute("currtime", tf.format(dateobj));
			session.setAttribute("userName", userName);
			getServletContext().getRequestDispatcher("/StaffEventSummary.jsp").forward(request, response);
		}

		else {
			session.invalidate();
			getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
		}
	}

	public void compareAndAddErrors(UserModel actualUser, UserModel updatedUser, UserErrorMsgs errors) {
		if (!actualUser.getCity().equals(updatedUser.getCity()))
			updatedUser.validateCity(errors);

		if (!actualUser.getEmailId().equals(updatedUser.getEmailId()))
			updatedUser.validateEmail(errors);
		if (!actualUser.getFirstName().equals(updatedUser.getFirstName()))
			updatedUser.validateFirstName(errors);
		if (!actualUser.getLastName().equals(updatedUser.getLastName()))
			updatedUser.validateLastName(errors);
		if (!actualUser.getPassword().equals(updatedUser.getPassword()))
			updatedUser.validatePassword(errors);
		if (!actualUser.getPhoneNumber().equals(updatedUser.getPhoneNumber()))
			updatedUser.validatePhoneNumber(errors);
		if (!actualUser.getState().equals(updatedUser.getState()))
			updatedUser.validateState(errors);
		if (!actualUser.getStreetName().equals(updatedUser.getStreetName()))
			updatedUser.validateStreetName(errors);
		if (!actualUser.getStreetNumber().equals(updatedUser.getStreetNumber()))
			updatedUser.validateStreetNumber(errors);
		if (!actualUser.getUtaId().equals(updatedUser.getUtaId()))
			updatedUser.validateUtaId(errors);
		if (!actualUser.getZipCode().equals(updatedUser.getZipCode()))
			updatedUser.validateZipCode(errors);
	}

}
