package dataaccessors;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import models.Address;
import models.User;
import models.Venue;

public class VenuesDB {
	
	private static Database db = new Database();
	static Logger log = Logger.getLogger(VenuesDB.class);
    
	public static Venue getVenueById(int id) {
		db.connectMeIn();
		PropertyConfigurator.configure(VenuesDB.class.getProtectionDomain().getCodeSource().getLocation().getPath().replace("classes", "lib")+"log4j.properties");
		
		String SQL = "SELECT * from Venues";
	    
		Venue aVenue = new Venue();

		try {
			PreparedStatement stat = db.conn.prepareStatement(SQL);
			ResultSet rs = stat.executeQuery();
			
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
			log.error("SQLException: ",e);
			e.printStackTrace();
		}
		
		db.closeConnection();
		return aVenue;
	}
	
	public static List<Venue> getAllVenues(){
		db.connectMeIn();
		PropertyConfigurator.configure(VenuesDB.class.getProtectionDomain().getCodeSource().getLocation().getPath().replace("classes", "lib")+"log4j.properties");
		
		String SQL = "SELECT * from Venues";
	    
	    List<Venue> venues = new ArrayList<Venue>();
	    
		try {
			PreparedStatement stat = db.conn.prepareStatement(SQL);
			ResultSet rs = stat.executeQuery();
			
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
			log.error("SQLException: ",e);
			e.printStackTrace();
		}
		
		db.closeConnection();
		
		return venues;
	}

}
