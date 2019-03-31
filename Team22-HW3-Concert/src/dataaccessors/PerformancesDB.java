package dataaccessors;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import models.Concert;
import models.Performance;
import models.Venue;

public class PerformancesDB {
	private static Database db = new Database();

	public static Performance getPerformanceById(int id) {
		db.connectMeIn();
		String SQL = "SELECT * from Performances";
	    
		Performance aPerformance = new Performance();

		try {
			PreparedStatement stat = db.conn.prepareStatement(SQL);
			ResultSet rs = stat.executeQuery();
			
			while (rs.next()){
				if(id == rs.getInt(1)) {
					int performanceId = rs.getInt(1);
					int concertId = rs.getInt(2);
					int venueId = rs.getInt(3);
					double price = rs.getDouble(4);
					int seatsPurchased = rs.getInt(5);
					Date date = rs.getDate(6);
					Time startTime = rs.getTime(7);
					Time endTime = rs.getTime(8);
					
					Concert concert = ConcertsDB.getConcertById(concertId);
					Venue venue = VenuesDB.getVenueById(venueId);
					
					aPerformance = new Performance(performanceId, concert, venue, price, seatsPurchased, date, startTime, endTime);
				}
		    }
		    stat.close();
		        
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		db.closeConnection();
		return aPerformance;
	}
	
	public static List<Performance> getPerformancesByConcertId(int concertId){
		db.connectMeIn();
		String SQL = "SELECT * from Performances WHERE id = ?";
	    
	    List<Performance> performances = new ArrayList<Performance>();
	    
		try {
			PreparedStatement stat = db.conn.prepareStatement(SQL);
			stat.setInt(1, concertId);
			ResultSet rs = stat.executeQuery();
			
			while (rs.next()){
				int performanceId = rs.getInt(1);
				int venueId = rs.getInt(3);
				double price = rs.getDouble(4);
				int seatsPurchased = rs.getInt(5);
				Date date = rs.getDate(6);
				Time startTime = rs.getTime(7);
				Time endTime = rs.getTime(8);
				
				Concert concert = ConcertsDB.getConcertById(concertId);
				Venue venue = VenuesDB.getVenueById(venueId);
				
				Performance aPerformance = new Performance(performanceId, concert, venue, price, seatsPurchased, date, startTime, endTime);
				performances.add(aPerformance);
		    }
			
		    stat.close();
		        
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		db.closeConnection();
		
		return performances;
	}
	
	public static List<Performance> getPerformancesFromSearch(int venueId, String searchTerm){
		db.connectMeIn();
		searchTerm = "%"+searchTerm+"%";
		String SQL = "SELECT * from Performances as p join Concerts as c on p.concertId = c.id join Venues as v on p.venueId = v.id where c.concertName like ?";
		String SQL2 = "SELECT * from Performances as p join Concerts as c on p.concertId = c.id join Venues as v on p.venueId = v.id where v.id = ? and c.concertName like ?";

	    List<Performance> performances = new ArrayList<Performance>();
	    
		try {
			PreparedStatement stat = db.conn.prepareStatement(SQL);
			PreparedStatement stat2 = db.conn.prepareStatement(SQL2);
			ResultSet rs;
			
			if(venueId > 0) {
				stat2.setInt(1, venueId);
				stat2.setString(2, searchTerm);
				rs = stat2.executeQuery();
			} else {
				stat.setString(1, searchTerm);
				rs = stat.executeQuery();
			}
			
			while (rs.next()){
				int performanceId = rs.getInt(1);
				int concertId = rs.getInt(2);
				venueId = rs.getInt(3);
				double price = rs.getDouble(4);
				int seatsPurchased = rs.getInt(5);
				Date date = rs.getDate(6);
				Time startTime = rs.getTime(7);
				Time endTime = rs.getTime(8);
				
				Concert concert = ConcertsDB.getConcertById(concertId);
				Venue venue = VenuesDB.getVenueById(venueId);
				
				Performance aPerformance = new Performance(performanceId, concert, venue, price, seatsPurchased, date, startTime, endTime);
				performances.add(aPerformance);
		    }
			
			stat.close();
			stat2.close();
		        
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		db.closeConnection();
		
		return performances;
	}

	public static void updateSeatsPurchased(Performance performance, int adjustment) {
		db.connectMeIn();
		
		int performanceId = performance.getId();
		int originalQuantity = performance.getNumberPurchased();
		int updatedQuantity = originalQuantity + adjustment;
		
		try {
			String SQL = "UPDATE Performances SET numberPurchased = ? WHERE id = ?";
			
			PreparedStatement stat = db.conn.prepareStatement(SQL);
			stat.setInt(1, updatedQuantity);
			stat.setInt(2, performanceId);

			stat.executeUpdate();
			stat.close();
			db.closeConnection();
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}

