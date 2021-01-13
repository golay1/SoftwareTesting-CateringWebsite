package data;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import model.PaymentModel;
import util.SQLConnection;

public class PaymentDAO {
	static SQLConnection DBMgr = SQLConnection.getInstance();

	public static PaymentModel generatePayment(int eventID, String userName) {
		PaymentModel payment = new PaymentModel();
		Statement stmt = null;
		Connection conn = SQLConnection.getDBConnection();
		try {
			stmt = conn.createStatement();
			String eventDetails = "Select * from events where eventID=" + eventID;
			ResultSet event = stmt.executeQuery(eventDetails);
			event.last();
			double price = 0.0;
			// All the prices below are per person
			String meal = event.getString("meal");// 3 types breakfast ($8) , lunch ($12), supper($18)
			String mealFormal = event.getString("mealformal");// informal (No extra cost),formal (1.5 * meal)
			String drinkType = event.getString("drinktype");// std (No extra cost), alcoholic ($15)
			String entitems = event.getString("entitems");// music $50
			int att = event.getInt("estattendees");
			String hall = event.getString("hallname");
			int duration = event.getInt("duration");

			int capacity = 0;// Capacity of Hall

			double costPerPerson = 0.0;

			if (hall.equalsIgnoreCase("Maverick")) {
				capacity = 100;
			} else if (hall.equalsIgnoreCase("KC")) {
				capacity = 25;
			} else if (hall.equalsIgnoreCase("Arlington")) {
				capacity = 50;
			} else if (hall.equalsIgnoreCase("Shard")) {
				capacity = 25;
			} else {
				capacity = 75;
			}

			price = (double) 2 * duration * capacity;

			if (meal.equalsIgnoreCase("Breakfast")) {
				costPerPerson = 8.0;
			} else if (meal.equalsIgnoreCase("Lunch")) {
				costPerPerson = 12.0;
			} else {
				costPerPerson = 18.0;
			}

			if (mealFormal.equalsIgnoreCase("formal")) {
				costPerPerson *= 1.5;
			}

			if (drinkType.equalsIgnoreCase("alcohol")) {
				costPerPerson += 15;
			}

			costPerPerson *= att;

			price += costPerPerson;

			if (entitems.equalsIgnoreCase("music")) {
				price += 50;
			}

			payment.setEventID(Integer.toString(eventID));
			payment.setUsername(userName);
			payment.setPrice(Double.toString(price));
			conn.commit();
		} catch (SQLException e) {
		}
		return payment;
	}

	public static void AddPayment(PaymentModel payment) {
		Statement stmt = null;
		Connection conn = SQLConnection.getDBConnection();
		try {
			stmt = conn.createStatement();
			String sql = "Insert into payments values(" + payment.getEventID() + ",'" + payment.getUsername() + "','"
					+ payment.getCardNumber() + "','" + payment.getExpDate() + "'," + payment.getPin() + ","
					+ payment.getPrice() + ")";
			stmt.executeUpdate(sql);
			String sql1 = "Update events set estatus='reserved' where eventID=" + payment.getEventID();
			stmt.executeUpdate(sql1);
			conn.commit();
		} catch (SQLException e) {
		}
	}

	public static double sendCost(String eventID) {
		double price = 0.0;
		Statement stmt = null;
		Connection conn = SQLConnection.getDBConnection();
		try {
			stmt = conn.createStatement();
			String sql = "select price from payments where eventID=" + eventID;
			ResultSet priceSet = stmt.executeQuery(sql);
			priceSet.last();
			price = (double) priceSet.getInt("price");
			conn.commit();
		} catch (SQLException e) {
		}
		return price;
	}
}
