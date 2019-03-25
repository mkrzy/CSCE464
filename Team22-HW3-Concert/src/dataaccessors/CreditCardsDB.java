package dataaccessors;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import models.CreditCard;
import models.User;

public class CreditCardsDB {
	private static Database db = new Database();

	public static CreditCard getCreditCardById(int creditCardId) {
		db.connectMeIn();
	    
	    CreditCard aCreditCard = new CreditCard();

		try {
		    Statement stat = db.conn.createStatement();
		    String SQL = "SELECT * FROM CreditCards WHERE id = " + creditCardId;

			ResultSet rs = stat.executeQuery(SQL);
			
			while (rs.next()){
				int userId = rs.getInt(2);
				String name = rs.getString(3);
				String ccNumber = rs.getString(4);
				String type = rs.getString(5);
				int cvv = rs.getInt(6);
				Date expDate = rs.getDate(7);
				double balance = rs.getDouble(8);
				
				User user = UsersDB.getUserById(userId);

				aCreditCard = new CreditCard(creditCardId, user, name, type, ccNumber, cvv, expDate, balance);
		    }
		        
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		db.closeConnection();
		return aCreditCard;
	}
	
	public static String adjustCreditCardBalance(CreditCard aCard, double adjustment) {
		db.connectMeIn();
		String creditCardNumber = aCard.getCreditCardNumber();
		String cardHolderName = aCard.getCardHolderName();
		String cardType = aCard.getCardType();
		int cvv = aCard.getCvv();
		Date expirationDate = aCard.getExpirationDate();
		
		try {
			Statement stat = db.conn.createStatement();
			String SQL1 = "SELECT * FROM CreditCards WHERE creditCardNumber = "+creditCardNumber;
			
			ResultSet rs = stat.executeQuery(SQL1);
			
			if(rs.next()) {
				int creditCardId = rs.getInt(1);
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
					String SQL2 = "UPDATE CreditCards SET balance = " + newBalance + " WHERE id = " + creditCardId;
					stat.executeUpdate(SQL2);
					stat.close();
					db.closeConnection();
					return "Transaction was a success!";
				}
				
			} else {
				CreditCardsDB.addCreditCard(aCard);
				CreditCardsDB.adjustCreditCardBalance(aCard, adjustment);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return "Something went wrong";
	}

	public static CreditCard addCreditCard(CreditCard aCard) {
		db.connectMeIn();
		int userId = aCard.getUser().getId();
		String cardHolderName = aCard.getCardHolderName();
		String creditCardNumber = aCard.getCreditCardNumber();
		String cardType = aCard.getCardType();
		int cvv = aCard.getCvv();
		Date date = aCard.getExpirationDate();
		double balance = 100.0; //Balance not specified: double balance = aCard.getBalance();
		
		try {
			Statement stat = db.conn.createStatement();
			String SQL1 = "SELECT * FROM CreditCards WHERE creditCardNumber = '" + creditCardNumber + "' AND cvv = "+cvv;
			
			ResultSet rs = stat.executeQuery(SQL1);
			if(rs.first()) {
				int creditCardId = rs.getInt(1);
				aCard = CreditCardsDB.getCreditCardById(creditCardId);
				stat.close();
				db.closeConnection();
				return aCard;
			} else {
				String SQL2 = "INSERT INTO CreditCards(userId, cardHolderName, creditCardNumber, cardType, cvv, expirationDate, balance) VALUES (" + userId + ", '" + cardHolderName + "', '" + creditCardNumber + "', '" + cardType + "', " + cvv + ", '" + date + "', " + balance + ")";
				
				stat.executeUpdate(SQL2);
				stat.close();
				db.closeConnection();
				return CreditCardsDB.addCreditCard(aCard);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return aCard;		
	}
}
