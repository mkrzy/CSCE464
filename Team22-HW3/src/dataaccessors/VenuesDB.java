package dataaccessors;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import models.Address;
import models.User;
import models.Venue;

public class VenuesDB {
	
	private static Database db = new Database();
    
	public static Venue getVenueById(int id) {
		db.connectMeIn();
		String SQL = "SELECT * from Venues";
	    Statement stat;
	    
		Venue aVenue = new Venue();

		try {
			stat = db.conn.createStatement();
			ResultSet rs = stat.executeQuery(SQL);
			
			while (rs.next()){
				if(id == rs.getInt(1)) {
					String name = rs.getString(2);
					int seatsAvailable = rs.getInt(3);
					int addressId = rs.getInt(4);
					int ownerId = rs.getInt(5);
					
					Address address = AddressesDB.getAddressById(addressId);
					User owner = UsersDB.getUserById(ownerId);
					aVenue = new Venue(id, name, seatsAvailable, address, owner);
				}
		    }
		    stat.close();
		        
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		db.closeConnection();
		return aVenue;
	}
	
	public static List<Venue> getAllVenues(){
		db.connectMeIn();
		String SQL = "SELECT * from Venues";
	    Statement stat;
	    
	    List<Venue> venues = new ArrayList<Venue>();
	    
		try {
			stat = db.conn.createStatement();
			ResultSet rs = stat.executeQuery(SQL);
			
			while (rs.next()){
				int venueId = rs.getInt(1);
				String name = rs.getString(2);
				int seatsAvailable = rs.getInt(3);
				int addressId = rs.getInt(4);
				int ownerId = rs.getInt(5);
				
				Address address = AddressesDB.getAddressById(addressId);
				User owner = UsersDB.getUserById(ownerId);
				Venue aVenue = new Venue(venueId, name, seatsAvailable, address, owner);
		        venues.add(aVenue);
		    }
			
		    stat.close();
		        
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		db.closeConnection();
		
		return venues;
	}

}
