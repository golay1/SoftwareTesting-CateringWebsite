package controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import data.EventDAO;
import data.PaymentDAO;
import model.EventErrorMsgs;
import model.EventModel;
import model.PaymentModel;

/**
 * Servlet implementation class EventController
 */
@WebServlet("/EventController")
public class EventController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public EventController() {
		super();
		// TODO Auto-generated constructor stub
	}

	private EventModel getEventParam(HttpServletRequest request) {
		return new EventModel(request.getParameter("eventId"), request.getParameter("date"),
				request.getParameter("event_name"), request.getParameter("start_time"),
				request.getParameter("duration"), request.getParameter("Hall_Name"),
				request.getParameter("est_attendees"), request.getParameter("Food_Type"),
				request.getParameter("Meal_Type"), request.getParameter("Drink_Type"),
				request.getParameter("entertainment"), request.getParameter("Meal_Formality"),
				request.getParameter("staffFirstName"), request.getParameter("staffLastName"),
				request.getParameter("event_status"), request.getParameter("price"));
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		String action = request.getParameter("action");
		String userName = request.getParameter("userName");
		if (action.equalsIgnoreCase("add")) {
			EventModel eventmodel = getEventParam(request);
			EventErrorMsgs eventErrors = new EventErrorMsgs();
			eventmodel.validateAttendees(eventErrors);
			eventmodel.validateDuration(eventErrors);
			eventmodel.validateEvenName(eventErrors);
			eventmodel.validateHall(eventErrors);
			eventErrors.setStaffFirstNameError("");
			eventErrors.setStaffLastNameError("");
			try {
				eventmodel.validateDate(userName, eventErrors);
			} catch (ParseException e) {
			}
			if (eventErrors.getErrorMsg().equals("")) {
				session.removeAttribute("EventErrorMsgs");
				int eventID = EventDAO.AddEvent(userName, eventmodel.getLastName(), eventmodel.getFirstName(),
						eventmodel.getDate(), eventmodel.getName(), eventmodel.getStartTime(), eventmodel.getDuration(),
						eventmodel.getHallName(), eventmodel.getAttendees(), eventmodel.getFoodType(),
						eventmodel.getMeal(), eventmodel.getDrinkType(), eventmodel.getEntertainmentItems(),
						eventmodel.getFormal());
				PaymentModel paymentModel = PaymentDAO.generatePayment(eventID, userName);
				session.setAttribute("PaymentModel", paymentModel);
				getServletContext().getRequestDispatcher("/Payment.jsp").forward(request, response);
			} else {
				session.setAttribute("EventModel", eventmodel);
				session.setAttribute("EventErrorMsgs", eventErrors);
				getServletContext().getRequestDispatcher("/RequestEvent.jsp").forward(request, response);
			}
		}

		else if (action.equalsIgnoreCase("viewEvent")) {
			String date = request.getParameter("date");
			String time = request.getParameter("start_time");
			EventErrorMsgs errors = new EventErrorMsgs();
			isDateValid(date, time, errors);
			if (errors.getErrorMsg().equals("")) {
				ArrayList<EventModel> eventSummary = EventDAO.viewEvent(userName, date, time);
				session.setAttribute("EventModelList", eventSummary);
				getServletContext().getRequestDispatcher("/EventSummary.jsp").forward(request, response);
			} else {
				session.setAttribute("EventErrorMsgs", errors);
				getServletContext().getRequestDispatcher("/EventSummary.jsp").forward(request, response);
			}
		}

		else if (action.equalsIgnoreCase("viewEventM")) {
			String date = request.getParameter("date");
			String time = request.getParameter("start_time");
			EventErrorMsgs errors = new EventErrorMsgs();
			isDateValid(date, time, errors);
			if (errors.getErrorMsg().equals("")) {
				ArrayList<EventModel> eventSummary = EventDAO.viewEvent(userName, date, time);
				session.setAttribute("EventModelList", eventSummary);
				getServletContext().getRequestDispatcher("/ManagerEventSummary.jsp").forward(request, response);
			} else {
				session.setAttribute("EventErrorMsgs", errors);
				getServletContext().getRequestDispatcher("/EventSummary.jsp").forward(request, response);
			}
		}

		else if (action.equalsIgnoreCase("viewEventS")) {
			String date = request.getParameter("date");
			String time = request.getParameter("start_time");
			EventErrorMsgs errors = new EventErrorMsgs();
			isDateValid(date, time, errors);
			if (errors.getErrorMsg().equals("")) {
				ArrayList<EventModel> eventSummary = EventDAO.viewEvent(userName, date, time);
				session.setAttribute("EventModelList", eventSummary);
				getServletContext().getRequestDispatcher("/StaffEventSummary.jsp").forward(request, response);
			} else {
				session.setAttribute("EventErrorMsgs", errors);
				getServletContext().getRequestDispatcher("/EventSummary.jsp").forward(request, response);
			}
		}

		else if (action.equalsIgnoreCase("viewSelectedEvent")) {
			String id = request.getParameter("id");
			EventModel eventmodel = EventDAO.viewSelectedEvent(id);
			session.setAttribute("EventModel", eventmodel);
			getServletContext().getRequestDispatcher("/ViewEventSummary.jsp").forward(request, response);
		}

		else if (action.equalsIgnoreCase("viewSelectedEventM")) {
			String id = request.getParameter("id");
			EventModel eventmodel = EventDAO.viewSelectedEvent(id);
			session.setAttribute("EventModel", eventmodel);
			getServletContext().getRequestDispatcher("/MgrViewEventSummary.jsp").forward(request, response);
		}

		else if (action.equalsIgnoreCase("viewSelectedEventS")) {
			String id = request.getParameter("id");
			EventModel eventmodel = EventDAO.viewSelectedEvent(id);
			session.setAttribute("EventModel", eventmodel);
			getServletContext().getRequestDispatcher("/StaffViewEventSummary.jsp").forward(request, response);
		}

		else if (action.equalsIgnoreCase("cancel")) {
			String eventID = request.getParameter("eventId");
			EventDAO.cancelEvent(eventID);
			EventModel eventmodel = EventDAO.viewSelectedEvent(eventID);
			session.setAttribute("userName", userName);
			session.setAttribute("EventModel", eventmodel);
			getServletContext().getRequestDispatcher("/ViewEventSummary.jsp").forward(request, response);
		}

		else if (action.equalsIgnoreCase("modify")) {
			String eventID = request.getParameter("eventId");
			EventModel eventModel = getEventParam(request);
			EventErrorMsgs eventErrors = new EventErrorMsgs();
			eventModel.validateAttendees(eventErrors);
			eventModel.validateDuration(eventErrors);
			eventModel.validateHall(eventErrors);
			try {
				eventModel.validateDate(request.getParameter("userName"), eventErrors);
			} catch (ParseException e) {
			}
			if (eventErrors.getErrorMsg().equals("")) {
				EventDAO.modifyEvent(eventModel, eventID);
				getServletContext().getRequestDispatcher("/EventSummary.jsp").forward(request, response);
			} else {
				session.setAttribute("EventErrorMsgs", eventErrors);
				getServletContext().getRequestDispatcher("/ViewEventSummary.jsp").forward(request, response);

			}
		} else if (action.equalsIgnoreCase("change"))

		{
			String eventID = request.getParameter("eventId");
			EventModel eventModel = getEventParam(request);
			EventErrorMsgs eventErrors = new EventErrorMsgs();
			eventModel.validateAttendees(eventErrors);
			eventModel.validateDuration(eventErrors);
			eventModel.validateHall(eventErrors);
			eventErrors.setEventNameError("");
			eventErrors.setStaffFirstNameError("");
			eventErrors.setStaffLastNameError("");
			try {
				eventModel.validateDate(request.getParameter("userName"), eventErrors);
			} catch (ParseException e) {
			}

			if (eventErrors.getErrorMsg().equals("")) {
				EventDAO.modifyEvent(eventModel, eventID);
				EventModel newEventModel = EventDAO.viewSelectedEvent(eventID);
				session.setAttribute("EventModel", newEventModel);
				session.removeAttribute("EventErrorMsgs");
				getServletContext().getRequestDispatcher("/MgrViewEventSummary.jsp").forward(request, response);
			} else {
				session.setAttribute("EventErrorMsgs", eventErrors);
				getServletContext().getRequestDispatcher("/MgrViewEventSummary.jsp").forward(request, response);
			}
		}

		else {
			String eventID = request.getParameter("eventId");
			EventModel eventModel = getEventParam(request);
			EventErrorMsgs eventErrors = new EventErrorMsgs();

			eventModel.validateStaffExists(eventErrors);
			eventModel.validateStaffExists(eventErrors);
			if (!eventErrors.getErrorMsg().equals("")) {
				session.setAttribute("EventErrorMsgs", eventErrors);
				getServletContext().getRequestDispatcher("/MgrViewEventSummary.jsp").forward(request, response);
			} else {
				EventDAO.assign(eventID, eventModel.getStaffFirstName(), eventModel.getStaffLastName());
				EventModel newEventModel = EventDAO.viewSelectedEvent(eventID);
				session.setAttribute("EventModel", newEventModel);
				session.removeAttribute("EventErrorMsgs");
				getServletContext().getRequestDispatcher("/MgrViewEventSummary.jsp").forward(request, response);
			}
		}

	}

	public void isDateValid(String date, String time, EventErrorMsgs errors) {
		String error = "";
		String todayTime = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
		if (todayTime.compareTo(date) > 0) {
			error = "Date/time cannot be in past";
			errors.setDateError(error);
			errors.setErrorMsg();
		}
	}

}
