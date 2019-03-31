package dataaccessors;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import models.Concert;

public class ConcertsDB {

	private static Database db = new Database();
	
	public static Concert getConcertById(int id) {
		db.connectMeIn();
		String SQL = "SELECT * FROM Concerts";
	    
		Concert aConcert = new Concert();

		try {
			PreparedStatement stat = db.conn.prepareStatement(SQL);
			ResultSet rs = stat.executeQuery();
			
			while (rs.next()){
				if(id == rs.getInt(1)) {
					String name = rs.getString(2);
					String description = rs.getString(3);
					String thumbnail = rs.getString(4);
					String rating = rs.getString(5);
					
					aConcert = new Concert(id, name, description, thumbnail, rating);
				}
		    }
		    stat.close();
		        
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		db.closeConnection();
		return aConcert;
	}
}
