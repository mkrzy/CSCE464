package dataaccessors;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import models.User;

public class UsersDB {
	
	private static Database db = new Database();
	static Logger log = Logger.getLogger(UsersDB.class);

    public static void registerUser(User aUser) {
       	db.connectMeIn();
		PropertyConfigurator.configure(UsersDB.class.getProtectionDomain().getCodeSource().getLocation().getPath().replace("classes", "lib")+"log4j.properties");
		
       	try {
       		String SQL = "INSERT INTO Users (FirstName, LastName, Username, Password) VALUES (?,?,?,?)";
  		  
       		String firstName = aUser.getFirstName();
       		String lastName = aUser.getLastName();
       		String username = aUser.getUsername();
       		String password = aUser.getPassword();

       		PreparedStatement stat = db.conn.prepareStatement(SQL);
       		stat.setString(1, firstName);
       		stat.setString(2, lastName);
       		stat.setString(3, username);
       		stat.setString(4, password);
       		
       		stat.executeUpdate();
       		
       	} catch (SQLException e) {
			log.error("SQLException: ",e);
       		e.printStackTrace();
  		}
       	db.closeConnection();
    }
    
    public static User getUserByUsername(String aUsername) {   
	   	db.connectMeIn();
		PropertyConfigurator.configure(UsersDB.class.getProtectionDomain().getCodeSource().getLocation().getPath().replace("classes", "lib")+"log4j.properties");
		
		String SQL = "SELECT * from Users";
	   
	    User aUser = new User();
		try {
			PreparedStatement stat = db.conn.prepareStatement(SQL);
			ResultSet rs = stat.executeQuery();
			
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
			log.error("SQLException: ",e);
			e.printStackTrace();
		}
		
		db.closeConnection();
	   	return aUser;
    }
	
	public static User getUserById(int userId) {
		db.connectMeIn();
		PropertyConfigurator.configure(UsersDB.class.getProtectionDomain().getCodeSource().getLocation().getPath().replace("classes", "lib")+"log4j.properties");
		
		String SQL = "SELECT * from Users";
	   
	    User aUser = new User();
		try {
			PreparedStatement stat = db.conn.prepareStatement(SQL);
			ResultSet rs = stat.executeQuery();
			
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
			log.error("SQLException: ",e);
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