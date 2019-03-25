package models;

import java.sql.Date;

public class Review {
	private int id;
	private Concert concert;
	private User user;
	private String review;
	private double rating;
	private Date reviewDate;
	
	public Review() {
		super();
	}

	public Review(int id, Concert concert, User user, String review, double rating, Date reviewDate) {
		super();
		this.id = id;
		this.concert = concert;
		this.user = user;
		this.review = review;
		this.rating = rating;
		this.reviewDate = reviewDate;
	}

	public Review(Concert concert, User user, String review, double rating, Date reviewDate) {
		super();
		this.concert = concert;
		this.user = user;
		this.review = review;
		this.rating = rating;
		this.reviewDate = reviewDate;
	}

	public int getId() {
		return id;
	}

	public Concert getConcert() {
		return concert;
	}

	public void setConcert(Concert concert) {
		this.concert = concert;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getReview() {
		return review;
	}

	public void setReview(String review) {
		this.review = review;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public Date getReviewDate() {
		return reviewDate;
	}

	public void setReviewDate(Date reviewDate) {
		this.reviewDate = reviewDate;
	}
	
}
