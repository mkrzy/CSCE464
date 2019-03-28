package dataaccessors;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import models.Concert;
import models.Review;
import models.User;

public class ReviewsDB {
	private static Database db = new Database();

	
	public static List<Review> getReviewsByConcertId(int concertId){
		db.connectMeIn();
		String SQL = "SELECT * from CustomerReviews";
	    Statement stat;
	    
	    List<Review> reviews = new ArrayList<Review>();

		try {
			stat = db.conn.createStatement();
			ResultSet rs = stat.executeQuery(SQL);
			
			while (rs.next()){
				if(concertId == rs.getInt(2)) {
					int id = rs.getInt(1);
					int userId = rs.getInt(3);
					String review = rs.getString(4);
					double rating = rs.getDouble(5);
					Date date = rs.getDate(6);
					
					Concert concert = ConcertsDB.getConcertById(concertId);
					User user = UsersDB.getUserById(userId);
					Review aReview = new Review(id, concert, user, review, rating, date);
					reviews.add(aReview);
				}
		    }
		    stat.close();
		        
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		db.closeConnection();
		return reviews;
	}
	
	public static String addReview(Review aReview) {
		db.connectMeIn();
		String returnMessage = "";
		
		try {
			Statement stat = db.conn.createStatement();
			int concertId = aReview.getConcert().getId();
			int userId = aReview.getUser().getId();
			String review = aReview.getReview();
			double rating = aReview.getRating();
			Date date = aReview.getReviewDate();
			String dateString = "DATE '" + date.toString()+"'";
			
			String SQL1 = "SELECT * FROM CustomerReviews WHERE concertId = "+concertId;
			String SQL2 = "INSERT INTO CustomerReviews(concertId, userId, review, rating, reviewDate) VALUES (" + concertId + ", " + userId + ", '" + review + "', " + rating + ", " + dateString + ");";
			
			ResultSet rs = stat.executeQuery(SQL1);
			while (rs.next()){
				if(userId == rs.getInt(3)) {
					returnMessage = "You have already submitted a review for this concert.";
					stat.close();
					return returnMessage;
				}
			}
			
			stat.executeUpdate(SQL2);
			returnMessage = "Your review has been added!";
			stat.close();
	
		} catch (SQLException e) {
			e.printStackTrace();
			returnMessage = "Error - Something went wrong.";
		}
		db.closeConnection();
		return returnMessage;
	}
}
