package dataaccessors;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
	    Statement stat;
	    
		Performance aPerformance = new Performance();

		try {
			stat = db.conn.createStatement();
			ResultSet rs = stat.executeQuery(SQL);
			
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
		String SQL = "SELECT * from Performances WHERE id = " + concertId;
	    Statement stat;
	    
	    List<Performance> performances = new ArrayList<Performance>();
	    
		try {
			stat = db.conn.createStatement();
			ResultSet rs = stat.executeQuery(SQL);
			
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
		String SQL = "SELECT * from Performances as p join Concerts as c on p.concertId = c.id join Venues as v on p.venueId = v.id where c.concertName like '%"+searchTerm+"%';";
		if(venueId > 0) {
			SQL = "SELECT * from Performances as p join Concerts as c on p.concertId = c.id join Venues as v on p.venueId = v.id where v.id = "+venueId+" and c.concertName like '%"+searchTerm+"%';";

		}
	    Statement stat;
	    
	    List<Performance> performances = new ArrayList<Performance>();
	    
		try {
			stat = db.conn.createStatement();
			ResultSet rs = stat.executeQuery(SQL);
			
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
			Statement stat = db.conn.createStatement();
			String SQL = "UPDATE Performances SET numberPurchased = " + updatedQuantity + " WHERE id = " + performanceId;

			stat.executeUpdate(SQL);
			stat.close();
			db.closeConnection();
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}

