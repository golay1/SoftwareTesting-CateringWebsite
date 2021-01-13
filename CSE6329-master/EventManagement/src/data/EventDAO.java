package data;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import model.EventModel;
import util.SQLConnection;

public class EventDAO {
	static SQLConnection DBMgr = SQLConnection.getInstance();

	public static int AddEvent(String userName, String lastName, String firstName, String date, String name,
			String startTime, String duration, String hallName, String attendees, String foodType, String meal,
			String drinkType, String entertainmentItems, String isFormal) {
		Statement stmt = null;
		String status = "Pending";
		String staffFirstName = "none";
		String staffLastName = "none";
		int eventID = 0;
		Connection conn = SQLConnection.getDBConnection();
		try {
			stmt = conn.createStatement();
			String names = "Select fname,lname from user where username='" + userName + "'";
			ResultSet rsn = stmt.executeQuery(names);
			rsn.last();
			firstName = rsn.getString("fname");
			lastName = rsn.getString("lname");
			String count = "Select count(*) from events";
			ResultSet rs = stmt.executeQuery(count);
			rs.last();
			eventID = rs.getInt("count(*)") + 1;
			String insertEvent = "insert into events values(" + eventID + ",'" + userName + "','" + lastName + "','"
					+ firstName + "','" + date + "','" + name + "','" + startTime + "'," + duration + ",'" + hallName
					+ "'," + attendees + ",'" + foodType + "','" + meal + "','" + isFormal + "','" + drinkType + "','"
					+ entertainmentItems + "','" + status + "','" + staffFirstName + "','" + staffLastName + "')";
			stmt.executeUpdate(insertEvent);
			conn.commit();
		} catch (Exception e) {
		}
		return eventID;
	}

	public static ArrayList<EventModel> viewEvent(String userName, String date, String time) {
		Statement stmt = null;
		Connection conn = SQLConnection.getDBConnection();
		ArrayList<EventModel> events = new ArrayList<EventModel>();
		String sql = "";
		try {
			stmt = conn.createStatement();
			ResultSet roleSet = stmt.executeQuery("Select * from user where username='" + userName + "'");
			roleSet.last();
			String role = roleSet.getString("role");
			if (role.equalsIgnoreCase("user")) {
				sql = "Select * " + "from events where ((edate>='" + date + "' and starttime>='" + time
						+ "') or (edate>'" + date + "')) and username='" + userName
						+ "' and estatus='reserved' order by edate,starttime ";
			} else if (role.equalsIgnoreCase("manager")) {
				sql = "Select * from events where ((edate>='" + date + "' and starttime>='" + time + "') or (edate>'"
						+ date + "')" + ") and estatus='reserved' order by edate,starttime";
			} else /* if (role.equalsIgnoreCase("staff")) */ {
				sql = "Select * " + "from events where ((edate>='" + date + "' and starttime>='" + time
						+ "') or (edate>'" + date + "')" + ") and assignFname='" + roleSet.getString("fname") + "' and "
						+ "assignLname='" + roleSet.getString("lname") + "' "
						+ " and estatus='reserved' order by edate,starttime";
			}
			ResultSet resultSet = stmt.executeQuery(sql);
			while (resultSet.next()) {
				EventModel eventModel = new EventModel();
				double cost = PaymentDAO.sendCost(resultSet.getString("eventID"));
				eventModel.setEventID(resultSet.getString("eventID"));
				eventModel.setDate(resultSet.getString("edate"));
				eventModel.setStartTime(resultSet.getString("starttime"));
				eventModel.setName(resultSet.getString("ename"));
				eventModel.setDuration(resultSet.getString("duration"));
				eventModel.setLastName(resultSet.getString("lname"));
				eventModel.setFirstName(resultSet.getString("fname"));
				eventModel.setUserName(resultSet.getString("username"));
				eventModel.setHallName(resultSet.getString("hallname"));
				eventModel.setAttendees(resultSet.getString("estattendees"));
				eventModel.setFoodType(resultSet.getString("foodtype"));
				eventModel.setMeal(resultSet.getString("meal"));
				eventModel.setFormal(resultSet.getString("mealformal"));
				eventModel.setDrinkType(resultSet.getString("drinktype"));
				eventModel.setEntertainmentItems(resultSet.getString("entitems"));
				eventModel.setStatus(resultSet.getString("estatus"));
				eventModel.setStaffFirstName(resultSet.getString("assignFname"));
				eventModel.setStaffLastName(resultSet.getString("assignLname"));
				eventModel.setPrice(Double.toString(cost));
				events.add(eventModel);
			}
			conn.commit();
		} catch (Exception e) {
		}
		return events;
	}

	public static EventModel viewSelectedEvent(String eventID) {
		Statement stmt = null;
		EventModel eventModel = new EventModel();
		Connection conn = SQLConnection.getDBConnection();
		String sql = "";
		try {
			stmt = conn.createStatement();
			sql = "Select * from events where eventID=" + eventID;
			ResultSet resultSet = stmt.executeQuery(sql);
			resultSet.last();
			eventModel.setEventID(resultSet.getString("eventID"));
			eventModel.setDate(resultSet.getString("edate"));
			eventModel.setStartTime(resultSet.getString("starttime"));
			eventModel.setName(resultSet.getString("ename"));
			eventModel.setDuration(resultSet.getString("duration"));
			eventModel.setLastName(resultSet.getString("lname"));
			eventModel.setFirstName(resultSet.getString("fname"));
			eventModel.setUserName(resultSet.getString("username"));
			eventModel.setHallName(resultSet.getString("hallname"));
			eventModel.setAttendees(resultSet.getString("estattendees"));
			eventModel.setFoodType(resultSet.getString("foodtype"));
			eventModel.setMeal(resultSet.getString("meal"));
			eventModel.setFormal(resultSet.getString("mealformal"));
			eventModel.setDrinkType(resultSet.getString("drinktype"));
			eventModel.setEntertainmentItems(resultSet.getString("entitems"));
			eventModel.setStatus(resultSet.getString("estatus"));
			eventModel.setStaffFirstName(resultSet.getString("assignFname"));
			eventModel.setStaffLastName(resultSet.getString("assignLname"));
			conn.commit();
		} catch (Exception e) {
		}
		return eventModel;
	}

	public static void cancelEvent(String eventID) {
		Statement stmt = null;
		Connection conn = SQLConnection.getDBConnection();
		try {
			stmt = conn.createStatement();
			String sql = "Update events set estatus='cancelled'  where eventID=" + eventID;
			stmt.executeUpdate(sql);
			conn.commit();
		} catch (Exception e) {
			}
	}

	public static void modifyEvent(EventModel event, String eventID) {
		Statement stmt = null;

		Connection conn = SQLConnection.getDBConnection();
		try {
			stmt = conn.createStatement();
			String sql = "Update events set edate='" + event.getDate() + "',ename='" + event.getName() + "',"
					+ "starttime='" + event.getStartTime() + "',duration=" + event.getDuration() + "" + ",hallname='"
					+ event.getHallName() + "',estattendees=" + event.getAttendees() + ",foodtype='"
					+ event.getFoodType() + "',meal='" + event.getMeal() + "',mealformal='" + event.getFormal()
					+ "',drinktype='" + event.getDrinkType() + "'" + ",entitems='" + event.getEntertainmentItems() + "'"
					+ "where eventID=" + eventID;
			stmt.executeUpdate(sql);
			conn.commit();
		} catch (Exception e) {
			}
	}

	public static void assign(String eventID, String staffFirstName, String staffLastName) {
		Statement stmt = null;
		Connection conn = SQLConnection.getDBConnection();
		try {
			stmt = conn.createStatement();
			String sql = "update events set assignFname='" + staffFirstName + "' , " + "assignLname='" + staffLastName
					+ "' where eventID=" + eventID;
			stmt.executeUpdate(sql);
			conn.commit();
		} catch (Exception e) {
			}

	}

	public static boolean checkEventName(String event) {
		boolean decision = false;
		Statement stmt = null;
		Connection conn = SQLConnection.getDBConnection();
		try {
			stmt = conn.createStatement();
			String sql = "Select * from events where ename='" + event + "'";
			ResultSet rs = stmt.executeQuery(sql);
			rs.last();

			if (rs.getRow() == 1) {
				decision = true;
			}

			conn.commit();
		} catch (Exception e) {
			}
		return decision;
	}

	public static boolean canUserReserveEventOnDay(String userName, String date) {
		boolean result = false;
		Statement stmt = null;
		Connection conn = SQLConnection.getDBConnection();
		try {
			stmt = conn.createStatement();
			String sql = "Select * from events where username = '" + userName + "'and edate ='" + date + "'";
			ResultSet rs = stmt.executeQuery(sql);
			rs.last();
			int a = rs.getRow();
			if (rs.getRow() >= 2) {
				result = false;
			} else {
				result = true;
			}
			conn.commit();
		} catch (Exception e) {
		}
		return result;

	}

	public static boolean canUserReserveEventOnWeek(String userName, String date) {
		boolean result = false;
		Statement stmt = null;
		Connection conn = SQLConnection.getDBConnection();
		try {
			stmt = conn.createStatement();
			String sql = "Select * from events where username = '" + userName + "'and edate between '" + date
					+ "'and date_add('" + date + "', INTERVAL 7 DAY)";
			ResultSet rs = stmt.executeQuery(sql);
			rs.last();
			if (rs.getRow() >= 5) {
				result = false;
			} else {
				result = true;
			}
			conn.commit();
		} catch (Exception e) {
		}
		return result;
	}
}
