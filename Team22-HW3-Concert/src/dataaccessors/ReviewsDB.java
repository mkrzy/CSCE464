package dataaccessors;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
	    
	    List<Review> reviews = new ArrayList<Review>();

		try {
			PreparedStatement stat = db.conn.prepareStatement(SQL);
			ResultSet rs = stat.executeQuery();
			
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
		
		String SQL1 = "SELECT * FROM CustomerReviews WHERE concertId = ?";
		String SQL2 = "INSERT INTO CustomerReviews(concertId, userId, review, rating, reviewDate) VALUES (?, ?, ?, ?, DATE ?)";
		
		
		try {
			int concertId = aReview.getConcert().getId();
			int userId = aReview.getUser().getId();
			String review = aReview.getReview();
			double rating = aReview.getRating();
			Date date = aReview.getReviewDate();

			PreparedStatement stat = db.conn.prepareStatement(SQL1);
			stat.setInt(1, concertId);
			ResultSet rs = stat.executeQuery();
			
			while (rs.next()){
				if(userId == rs.getInt(3)) {
					returnMessage = "Error - You have already submitted a review for this concert.";
					stat.close();
					return returnMessage;
				}
			}
			
			PreparedStatement stat2 = db.conn.prepareStatement(SQL2);
			stat2.setInt(1, concertId);
			stat2.setInt(2, userId);
			stat2.setString(3, review);
			stat2.setDouble(4, rating);
			stat2.setDate(5, date);
			stat2.executeUpdate();
			
			returnMessage = "Your review has been added!";
			stat2.close();
	
		} catch (SQLException e) {
			e.printStackTrace();
			returnMessage = "Error - Something went wrong.";
		}
		db.closeConnection();
		return returnMessage;
	}
}
