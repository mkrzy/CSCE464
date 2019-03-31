package dataaccessors;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import models.BankAccount;

public class BankDB {
	private static Database db = new Database();

	public static BankAccount getBankAccountById(int bankAccountId) {
		db.connectMeIn();
	    
		BankAccount aBankAccount = new BankAccount();
	    String SQL = "SELECT * FROM CreditCards WHERE id = ?";

		try {
			PreparedStatement stat = db.conn.prepareStatement(SQL);
			stat.setInt(1, bankAccountId);

			ResultSet rs = stat.executeQuery();
			
			while (rs.next()){
				int userId = rs.getInt(2);
				String name = rs.getString(3);
				String ccNumber = rs.getString(4);
				String type = rs.getString(5);
				int cvv = rs.getInt(6);
				Date expDate = rs.getDate(7);
				double balance = rs.getDouble(8);
				
				aBankAccount = new BankAccount(bankAccountId, userId, name, type, ccNumber, cvv, expDate, balance);
		    }
		        
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		db.closeConnection();
		return aBankAccount;
	}
	
	public static String adjustBankAccountBalance(BankAccount aCard, double adjustment) {
		db.connectMeIn();
		String bankAccountNumber = aCard.getBankAccountNumber();
		String cardHolderName = aCard.getCardHolderName();
		String cardType = aCard.getCardType();
		int cvv = aCard.getCvv();
		Date expirationDate = aCard.getExpirationDate();
		String SQL1 = "SELECT * FROM CreditCards WHERE creditCardNumber = ?";
		
		try {
			PreparedStatement stat = db.conn.prepareStatement(SQL1);
			stat.setString(1, bankAccountNumber);
			
			ResultSet rs = stat.executeQuery();
			
			if(rs.next()) {
				int bankAccountId = rs.getInt(1);
				String rsCardHolderName = rs.getString(3);
				String rsCardType = rs.getString(5);
				int rsCvv = rs.getInt(6);
				Date rsExpirationDate = rs.getDate(7);
				
				double oldBalance = rs.getDouble(8);
				double newBalance = oldBalance + adjustment;
				
				if((!cardHolderName.equals(rsCardHolderName)) || (!cardType.equals(rsCardType)) || (cvv != rsCvv) || (!expirationDate.equals(rsExpirationDate))) {
					stat.close();
					db.closeConnection();
					return "Transaction was not successful: incorrect details";
				} else if(newBalance < 0) {
					stat.close();
					db.closeConnection();
					return "Transaction was not successful: insufficient funds";
				} else {
					String SQL2 = "UPDATE CreditCards SET balance = ? WHERE id = ?";
					PreparedStatement stat2 = db.conn.prepareStatement(SQL2);
					stat2.setDouble(1, newBalance);
					stat2.setInt(2, bankAccountId);
					stat2.executeUpdate();
					stat2.close();
					db.closeConnection();
					return "Transaction was a success!";
				}
				
			} else {
				BankDB.addBankAccount(aCard);
				BankDB.adjustBankAccountBalance(aCard, adjustment);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return "Something went wrong";
	}

	public static BankAccount addBankAccount(BankAccount aCard) {
		db.connectMeIn();
		int userId = aCard.getUserId();
		String cardHolderName = aCard.getCardHolderName();
		String bankAccountNumber = aCard.getBankAccountNumber();
		String cardType = aCard.getCardType();
		int cvv = aCard.getCvv();
		Date date = aCard.getExpirationDate();
		double balance = 100.0; //Balance not specified: double balance = aCard.getBalance();
		String SQL1 = "SELECT * FROM CreditCards WHERE creditCardNumber = ? AND cvv = ?";
		
		try {
			PreparedStatement stat = db.conn.prepareStatement(SQL1);
			stat.setString(1, bankAccountNumber);
			stat.setInt(2, cvv);
			ResultSet rs = stat.executeQuery();
			
			if(rs.first()) {
				int bankAccountId = rs.getInt(1);
				aCard = BankDB.getBankAccountById(bankAccountId);
				stat.close();
				db.closeConnection();
				return aCard;
			} else {
				String SQL2 = "INSERT INTO CreditCards(userId, cardHolderName, creditCardNumber, cardType, cvv, expirationDate, balance) VALUES (?, ?, ?, ?, ?, DATE ?, ?)";
				
				PreparedStatement stat2 = db.conn.prepareStatement(SQL2);
				stat2.setInt(1, userId);
				stat2.setString(2, cardHolderName);
				stat2.setString(3, bankAccountNumber);
				stat2.setString(4, cardType);
				stat2.setInt(5, cvv);
				stat2.setDate(6, date);
				stat2.setDouble(7, balance);
				stat2.executeUpdate();
				stat2.close();
				db.closeConnection();
				
				return BankDB.addBankAccount(aCard);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return aCard;		
	}
}
