package models;

import java.sql.Date;

public class CreditCard {
	private int id;
	private User user;
	private String cardHolderName;
	private String creditCardNumber;
	private String cardType;
	private int cvv;
	private Date expirationDate;
	private double balance;

	public CreditCard() {
		super();
	}
	
	public CreditCard(User user, String name, String cardType, String cardNumber, int securityCode,
			Date expirationDate) {
		super();
		this.user = user;
		this.cardHolderName = name;
		this.cardType = cardType;
		this.creditCardNumber = cardNumber;
		this.cvv = securityCode;
		this.expirationDate = expirationDate;
	}

	public CreditCard(int id, User user, String name, String cardType, String cardNumber, int securityCode,
			Date expirationDate, double balance) {
		super();
		this.id = id;
		this.user = user;
		this.cardHolderName = name;
		this.cardType = cardType;
		this.creditCardNumber = cardNumber;
		this.cvv = securityCode;
		this.expirationDate = expirationDate;
		this.balance = balance;
	}
	
	public int getId() {
		return id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getCardHolderName() {
		return cardHolderName;
	}

	public void setCardHolderName(String cardHolderName) {
		this.cardHolderName = cardHolderName;
	}

	public String getCreditCardNumber() {
		return creditCardNumber;
	}

	public void setCreditCardNumber(String creditCardNumber) {
		this.creditCardNumber = creditCardNumber;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public int getCvv() {
		return cvv;
	}

	public void setCvv(int cvv) {
		this.cvv = cvv;
	}

	public Date getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

}
