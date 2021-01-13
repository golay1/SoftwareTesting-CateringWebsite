package data;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.UserModel;
import util.SQLConnection;

//TODO
public class UserDAO {
	static SQLConnection DBMgr = SQLConnection.getInstance();

	public static void register(String username, String pwd, String lname, String fname, String role, String utaid,
			String phno, String email, String stno, String stname, String city, String state, String zip) {
		Statement stmt = null;
		Connection conn = SQLConnection.getDBConnection();
		try {
			stmt = conn.createStatement();
			String insertUser = "insert into user values('" + username + "','" + utaid + "','" + pwd + "','" + fname
					+ "','" + lname + "','" + role + "','" + phno + "','" + email + "','" + stno + "','" + stname
					+ "','" + city + "','" + state + "'," + Integer.parseInt(zip) + ")";
			stmt.executeUpdate(insertUser);
			conn.commit();
			conn.close();
		} catch (SQLException e) {
		}
	}

	public static boolean checkUser(String username, String password) {
		boolean decision = false;
		Statement stmt = null;
		Connection conn = SQLConnection.getDBConnection();
		try {
			stmt = conn.createStatement();
			String sql = "Select * from user where username='" + username + "' and password='" + password + "'";
			ResultSet rs = stmt.executeQuery(sql);
			rs.last();

			if (rs.getRow() == 1) {
				decision = true;
			}
			conn.commit();

		} catch (SQLException e) {
		}
		return decision;
	}

	public static String role(String username) {
		String role = "";
		Statement stmt = null;
		Connection conn = SQLConnection.getDBConnection();
		try {
			stmt = conn.createStatement();
			String sql = "Select * from user where username='" + username + "'";
			ResultSet rs = stmt.executeQuery(sql);
			rs.last();
			role = rs.getString("role");
			conn.commit();

		} catch (SQLException e) {
		}
		return role;
	}

	public static UserModel viewprofile(String userName) {
		Statement stmt = null;
		Connection conn = SQLConnection.getDBConnection();
		UserModel usermodel = null;
		try {
			stmt = conn.createStatement();
			String sql = "Select * from user where username='" + userName + "'";
			ResultSet rs = stmt.executeQuery(sql);
			rs.last();
			usermodel = new UserModel(rs.getString("username"), rs.getString("password"), rs.getString("lname"),
					rs.getString("fname"), rs.getString("utaID"), rs.getString("phone"), rs.getString("email"),
					rs.getString("streetno"), rs.getString("streetname"), rs.getString("city"), rs.getString("state"),
					Integer.toString(rs.getInt("zipcode")), rs.getString("role"));
			conn.commit();
		} catch (SQLException e) {
		}
		return usermodel;
	}

	public static void updateprofile(UserModel usermodel, String username) {
		Statement stmt = null;
		Connection conn = SQLConnection.getDBConnection();
		try {
			stmt = conn.createStatement();
			String sql = "Update user set utaID='" + usermodel.getUtaId() + "' , fname='" + usermodel.getFirstName()
					+ "' , lname='" + usermodel.getLastName() + "' , role='" + usermodel.getRole() + "' , phone='"
					+ usermodel.getPhoneNumber() + "' , email='" + usermodel.getEmailId() + "' , streetno="
					+ usermodel.getStreetNumber() + " , city='" + usermodel.getCity() + "' , state='"
					+ usermodel.getState() + "' , zipcode=" + usermodel.getZipCode() + ", streetname='"
					+ usermodel.getStreetName() + "' where username='" + username + "'";
			stmt.executeUpdate(sql);
			conn.commit();
		} catch (SQLException e) {
		}
	}

	public static ArrayList<UserModel> search(String userLastName) {
		Statement stmt = null;
		Connection conn = SQLConnection.getDBConnection();
		ArrayList<UserModel> users = new ArrayList<UserModel>();
		try {
			stmt = conn.createStatement();
			String sql = "Select * from user where lname = '" + userLastName + "' order by lname, fname, role";
			if (userLastName.equals("")) {
				sql = "Select * from user order by lname, fname, role";
			}
			ResultSet resultSet = stmt.executeQuery(sql);
			while (resultSet.next()) {
				UserModel userModel = new UserModel();
				userModel.setLastName(resultSet.getString("lname"));
				userModel.setFirstName(resultSet.getString("fname"));
				userModel.setUserName(resultSet.getString("username"));
				userModel.setRole(resultSet.getString("role"));
				users.add(userModel);
			}
			conn.commit();
		} catch (Exception e) {
		}
		return users;
	}

	public static void deleteUser(String userName) {
		Statement stmt = null;
		Connection conn = SQLConnection.getDBConnection();
		try {
			stmt = conn.createStatement();
			String sql2 = "Delete from payments where username='" + userName + "'";
			String sql1 = "Delete from events where username='" + userName + "'";
			String sql = "Delete from user where username='" + userName + "'";
			stmt.executeUpdate(sql2);
			stmt.executeUpdate(sql1);
			stmt.executeUpdate(sql);
			conn.commit();
			conn.close();
		} catch (Exception e) {
		}
	}

	public static boolean isUserNameTaken(String userName) {
		boolean decision = false;
		Statement stmt = null;
		Connection conn = SQLConnection.getDBConnection();
		try {
			stmt = conn.createStatement();
			String sql = "Select * from user where username='" + userName + "'";
			ResultSet rs = stmt.executeQuery(sql);
			rs.last();
			if (rs.getRow() == 1) {
				decision = true;
			}
			conn.commit();

		} catch (SQLException e) {
		}
		return decision;
	}

	public static boolean isUTAIDtaken(String utaID) {
		boolean decision = false;
		Statement stmt = null;
		Connection conn = SQLConnection.getDBConnection();
		try {
			stmt = conn.createStatement();
			String sql = "Select * from user where utaID='" + utaID + "'";
			ResultSet rs = stmt.executeQuery(sql);
			rs.last();
			if (rs.getRow() == 1) {
				decision = true;
			}
			conn.commit();

		} catch (SQLException e) {
		}
		return decision;
	}

	public static boolean doesUserTypeExist(String role) {
		boolean decision = false;
		Statement stmt = null;
		Connection conn = SQLConnection.getDBConnection();
		try {
			stmt = conn.createStatement();
			String sql = "Select * from user where role='" + role + "'";
			ResultSet rs = stmt.executeQuery(sql);
			rs.last();
			if (rs.getRow() >= 1) {
				decision = true;
			}
			conn.commit();

		} catch (SQLException e) {
		}
		return decision;
	}

	public static boolean doesUserExist(String firstName, String lastName, String role) {
		boolean result = false;
		Statement stmt = null;
		Connection conn = SQLConnection.getDBConnection();

		try {
			stmt = conn.createStatement();
			String sql = "Select * from user where fname = '" + firstName + "' and lname='" + lastName + "' and role='"
					+ role + "'";
			ResultSet rs = stmt.executeQuery(sql);
			rs.last();
			if (rs.getRow() == 1) {
				result = true;
			}
			conn.commit();
		} catch (SQLException ex) {
		}

		return result;
	}
}
