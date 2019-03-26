package models;

import java.sql.Date;

public class BankAccount {
	private int id;
	private int userId;
	private String cardHolderName;
	private String bankAccountNumber;
	private String cardType;
	private int cvv;
	private Date expirationDate;
	private double balance;

	public BankAccount() {
		super();
	}
	
	public BankAccount(int userId, String name, String cardType, String cardNumber, int securityCode,
			Date expirationDate) {
		super();
		this.userId = userId;
		this.cardHolderName = name;
		this.cardType = cardType;
		this.bankAccountNumber = cardNumber;
		this.cvv = securityCode;
		this.expirationDate = expirationDate;
	}

	public BankAccount(int id, int userId, String name, String cardType, String cardNumber, int securityCode,
			Date expirationDate, double balance) {
		super();
		this.id = id;
		this.userId = userId;
		this.cardHolderName = name;
		this.cardType = cardType;
		this.bankAccountNumber = cardNumber;
		this.cvv = securityCode;
		this.expirationDate = expirationDate;
		this.balance = balance;
	}
	
	public int getId() {
		return id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getCardHolderName() {
		return cardHolderName;
	}

	public void setCardHolderName(String cardHolderName) {
		this.cardHolderName = cardHolderName;
	}

	public String getBankAccountNumber() {
		return bankAccountNumber;
	}

	public void setBankAccountNumber(String bankAccountNumber) {
		this.bankAccountNumber = bankAccountNumber;
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
