package dataaccessors;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import models.Concert;

public class ConcertsDB {

	private static Database db = new Database();
	static Logger log = Logger.getLogger(ConcertsDB.class);
	
	public static Concert getConcertById(int id) {
		db.connectMeIn();
		PropertyConfigurator.configure(ConcertsDB.class.getProtectionDomain().getCodeSource().getLocation().getPath().replace("classes", "lib")+"log4j.properties");
		
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
			log.error("SQLException: ",e);
			e.printStackTrace();
		}
		
		db.closeConnection();
		return aConcert;
	}
}
