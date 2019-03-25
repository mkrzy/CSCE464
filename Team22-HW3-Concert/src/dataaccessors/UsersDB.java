package dataaccessors;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import models.User;

public class UsersDB {
	
	private static Database db = new Database();

    public static void registerUser(User aUser) {
       	db.connectMeIn();
       	try {
  		  db.stmt = db.conn.createStatement();
  		  String sql;
  		  
  		  String firstName = aUser.getFirstName();
  		  String lastName = aUser.getLastName();
  		  String username = aUser.getUsername();
  		  String password = aUser.getPassword();

  		  sql = "INSERT INTO Users (FirstName, LastName, Username, Password)" +
  		          "VALUES ('" + firstName +
  				  "', '" + lastName + 
  				  "', '" + username + 
  				  "', '" + password + "')";
  		  db.stmt.executeUpdate(sql);
  		    		  
  		  } catch (SQLException e) {
  				e.printStackTrace();
  		}
       	db.closeConnection();
    }
    
    public static User getUserByUsername(String aUsername) {   
	   	db.connectMeIn();
		String SQL = "SELECT * from Users";
	    Statement stat;
	   
	    User aUser = new User();
		try {
			stat = db.conn.createStatement();
			ResultSet rs = stat.executeQuery(SQL);
			
			while (rs.next()){
				if(aUsername.equals( rs.getString(2) )) {
					int id = rs.getInt(1);
					String firstName = rs.getString(2);
					String lastName = rs.getString(3);
					String username = rs.getString(4);
					String password = rs.getString(5);
					aUser = new User(id, firstName, lastName, username, password);
				} 
		    }
			
		    stat.close();
		        
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		db.closeConnection();
	   	return aUser;
    }
	
	public static User getUserById(int userId) {
		db.connectMeIn();
		String SQL = "SELECT * from Users";
	    Statement stat;
	   
	    User aUser = new User();
		try {
			stat = db.conn.createStatement();
			ResultSet rs = stat.executeQuery(SQL);
			
			while (rs.next()){
				if(userId == rs.getInt(1)) {
					aUser.setFirstName(rs.getString(4));
					aUser.setLastName(rs.getString(5));
					aUser.setUsername(rs.getString(2));
					aUser.setPassword(rs.getString(3));
				} 
		    }
			
		    stat.close();
		        
		} catch (SQLException e) {
			e.printStackTrace();
		}
		db.closeConnection();
		return aUser;
	}
	
	public static boolean userExists(String username) {
		User user = getUserByUsername(username);
		if(username.equals(user.getUsername())) {
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean verifyUser(String username, String password) {
		User user = getUserByUsername(username);
		if(username.equals(user.getUsername()) && password.equals(user.getPassword())) {
			return true;
		} else {
			return false;
		}
	}
	
}