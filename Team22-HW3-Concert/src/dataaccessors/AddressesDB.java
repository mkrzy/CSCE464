package dataaccessors;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import models.Address;

public class AddressesDB {
	private static Database db = new Database();
	static Logger log = Logger.getLogger(AddressesDB.class);
	
	//only the AddressesDB class should access this method
	private static void addAddress(String street, String city, String state, int zip) {
		db.connectMeIn();
		PropertyConfigurator.configure(AddressesDB.class.getProtectionDomain().getCodeSource().getLocation().getPath().replace("classes", "lib")+"log4j.properties");
		
		String SQL = "INSERT INTO Addresses(street, city, state, postalCode) VALUES (?,?,?,?)";
		
		try {
			PreparedStatement stat = db.conn.prepareStatement(SQL);
			stat.setString(1, street);
			stat.setString(2, city);
			stat.setString(3, state);
			stat.setInt(4, zip);
			
	  		stat.executeUpdate();
			
		} catch (SQLException e) {
			log.error("SQLException: ",e);
			e.printStackTrace();
		}
	}
	
	public static Address getAddress(Address anAddress) {
		db.connectMeIn();
		PropertyConfigurator.configure(AddressesDB.class.getProtectionDomain().getCodeSource().getLocation().getPath().replace("classes", "lib")+"log4j.properties");
		
		String street = anAddress.getStreet();
		String city = anAddress.getCity();
		String state = anAddress.getState();
		int zip = anAddress.getZip();
		
		String SQL = "SELECT * FROM Addresses WHERE street = ? AND city = ? AND state = ? AND postalCode = ?";
		
		try {
			PreparedStatement stat = db.conn.prepareStatement(SQL);
			stat.setString(1, street);
			stat.setString(2, city);
			stat.setString(3, state);
			stat.setInt(4, zip);

			ResultSet rs = stat.executeQuery();

			if(rs.next()) {
				int addressId = rs.getInt(1);
				anAddress = AddressesDB.getAddressById(addressId);
				stat.close();
				db.closeConnection();
				return anAddress;

			} else {
				AddressesDB.addAddress(street, city, state, zip);
				anAddress = AddressesDB.getAddress(anAddress);
				stat.close();
				db.closeConnection();
				return anAddress;
			}
			
		} catch (SQLException e) {
			log.error("SQLException: ",e);
			e.printStackTrace();
		}
		return anAddress;
	}
	
	public static Address getAddressById(int id) {
		db.connectMeIn();
		PropertyConfigurator.configure(AddressesDB.class.getProtectionDomain().getCodeSource().getLocation().getPath().replace("classes", "lib")+"log4j.properties");
		
		String SQL = "SELECT * FROM Addresses";
	    
		Address anAddress = new Address();

		try {
			PreparedStatement stat = db.conn.prepareStatement(SQL);
			ResultSet rs = stat.executeQuery();
			
			while (rs.next()){
				if(id == rs.getInt(1)) {
					String street = rs.getString(2);
					String city = rs.getString(3);
					String state = rs.getString(4);
					int postalCode = rs.getInt(5);
					
					anAddress = new Address(id, street, city, state, postalCode);

				}
		    }
		    stat.close();
		        
		} catch (SQLException e) {
			log.error("SQLException: ",e);
			e.printStackTrace();
		}
		
		db.closeConnection();
		return anAddress;
	}

}
