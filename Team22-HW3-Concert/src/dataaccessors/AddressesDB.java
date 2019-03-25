package dataaccessors;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import models.Address;

public class AddressesDB {
	private static Database db = new Database();
	
	//only the AddressesDB class should access this method
	private static void addAddress(String street, String city, String state, int zip) {
		db.connectMeIn();
		String SQL = "INSERT INTO Addresses(street, city, state, postalCode) VALUES ('" + street + "', '" + city + "', '" + state + "', " + zip + ");";
		
		try {
			Statement stat = db.conn.createStatement();
	  		stat.executeUpdate(SQL);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static Address getAddress(Address anAddress) {
		db.connectMeIn();
		String street = anAddress.getStreet();
		String city = anAddress.getCity();
		String state = anAddress.getState();
		int zip = anAddress.getZip();
		
		try {
			Statement stat = db.conn.createStatement();

			String SQL = "SELECT * FROM Addresses WHERE street='" + street + "' AND city='" + city + "' AND state='" + state + "' AND postalCode=" + zip + ";";

			ResultSet rs = stat.executeQuery(SQL);

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
			e.printStackTrace();
		}
		return anAddress;
	}
	
	public static Address getAddressById(int id) {
		db.connectMeIn();
		String SQL = "SELECT * FROM Addresses";
	    Statement stat;
	    
		Address anAddress = new Address();

		try {
			stat = db.conn.createStatement();
			ResultSet rs = stat.executeQuery(SQL);
			
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
			e.printStackTrace();
		}
		
		db.closeConnection();
		return anAddress;
	}

}
