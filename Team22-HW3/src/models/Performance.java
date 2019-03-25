package models;

import java.sql.Date;
import java.sql.Time;
import java.text.NumberFormat;
import java.util.Locale;

public class Performance {
	private int id;
	private Concert concert;
	private Venue venue;
	private double price;
	private int numberPurchased;
	private Date date;
	private Time startTime;
	private Time endTime;

	public Performance() {
		super();
	}

	public Performance(int id, Concert concert, Venue venue, double price, int numberPurchased, Date date, Time startTime,
			Time endTime) {
		super();
		this.id = id;
		this.concert = concert;
		this.venue = venue;
		this.price = price;
		this.numberPurchased = numberPurchased;
		this.date = date;
		this.startTime = startTime;
		this.endTime = endTime;
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

	public Venue getVenue() {
		return venue;
	}

	public void setVenue(Venue venue) {
		this.venue = venue;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getNumberPurchased() {
		return numberPurchased;
	}

	public void setNumberPurchased(int numberPurchased) {
		this.numberPurchased = numberPurchased;
	}

	public int getAvailableSeats() {
		return venue.getAvailableSeats() - numberPurchased;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Time getStartTime() {
		return startTime;
	}

	public void setStartTime(Time startTime) {
		this.startTime = startTime;
	}

	public Time getEndTime() {
		return endTime;
	}

	public void setEndTime(Time endTime) {
		this.endTime = endTime;
	}
	
	public String getPrettyPrice() {
		return NumberFormat.getCurrencyInstance(new Locale("en", "US")).format(price);
	}

}
