package dataaccessors;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import models.CreditCard;
import models.User;

public class CreditCardsDB {
	private static Database db = new Database();
	static Logger log = Logger.getLogger(CreditCardsDB.class);

	public static CreditCard getCreditCardById(int creditCardId) {
		db.connectMeIn();
		PropertyConfigurator.configure(CreditCardsDB.class.getProtectionDomain().getCodeSource().getLocation().getPath().replace("classes", "lib")+"log4j.properties");
	    
	    CreditCard aCreditCard = new CreditCard();
	    String SQL = "SELECT * FROM CreditCards WHERE id = ?";

		try {
		    PreparedStatement stat = db.conn.prepareStatement(SQL);
		    stat.setInt(1, creditCardId);

			ResultSet rs = stat.executeQuery();
			
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
			log.error("SQLException: ",e);
			e.printStackTrace();
		}
		
		db.closeConnection();
		return aCreditCard;
	}
	
	public static CreditCard getCreditCardByNumber(String creditCardNumber) {
		db.connectMeIn();
		PropertyConfigurator.configure(CreditCardsDB.class.getProtectionDomain().getCodeSource().getLocation().getPath().replace("classes", "lib")+"log4j.properties");
	    
	    CreditCard aCreditCard = new CreditCard();
	    String SQL = "SELECT * FROM CreditCards WHERE creditCardNumber = ?";

		try {
		    PreparedStatement stat = db.conn.prepareStatement(SQL);
		    stat.setString(1, creditCardNumber);
		    
			ResultSet rs = stat.executeQuery();
			
			while (rs.next()){
				int creditCardId = rs.getInt(1);
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
			log.error("SQLException: ",e);
			e.printStackTrace();
		}
		
		db.closeConnection();
		return aCreditCard;
	}
	
	public static String adjustCreditCardBalance(CreditCard aCard, double adjustment) {
		db.connectMeIn();
		PropertyConfigurator.configure(CreditCardsDB.class.getProtectionDomain().getCodeSource().getLocation().getPath().replace("classes", "lib")+"log4j.properties");
		
		String creditCardNumber = aCard.getCreditCardNumber();
		String cardHolderName = aCard.getCardHolderName();
		String cardType = aCard.getCardType();
		int cvv = aCard.getCvv();
		Date expirationDate = aCard.getExpirationDate();
		String SQL = "SELECT * FROM CreditCards WHERE creditCardNumber = ?";
		
		try {
			PreparedStatement stat = db.conn.prepareStatement(SQL);
			stat.setString(1, creditCardNumber);
			
			ResultSet rs = stat.executeQuery();
			
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
					String SQL2 = "UPDATE CreditCards SET balance = ? WHERE id = ?";
					PreparedStatement stat2 = db.conn.prepareStatement(SQL2);
					stat2.setDouble(1, newBalance);
					stat2.setInt(2, creditCardId);
					stat2.executeUpdate();
					stat2.close();
					db.closeConnection();
					return "Transaction was a success!";
				}
				
			} else {
				CreditCardsDB.addCreditCard(aCard);
				CreditCardsDB.adjustCreditCardBalance(aCard, adjustment);
			}
			
		} catch (SQLException e) {
			log.error("SQLException: ",e);
			e.printStackTrace();
		}
		
		return "Something went wrong";
	}

	public static CreditCard addCreditCard(CreditCard aCard) {
		db.connectMeIn();
		PropertyConfigurator.configure(CreditCardsDB.class.getProtectionDomain().getCodeSource().getLocation().getPath().replace("classes", "lib")+"log4j.properties");
		
		int userId = aCard.getUser().getId();
		String cardHolderName = aCard.getCardHolderName();
		String creditCardNumber = aCard.getCreditCardNumber();
		String cardType = aCard.getCardType();
		int cvv = aCard.getCvv();
		Date date = aCard.getExpirationDate();
		double balance = 100.0; //Balance not specified: double balance = aCard.getBalance();
		String SQL = "SELECT * FROM CreditCards WHERE creditCardNumber = ? AND cvv = ?";
		String SQL2 = "INSERT INTO CreditCards(userId, cardHolderName, creditCardNumber, cardType, cvv, expirationDate, balance) VALUES (?, ?, ?, ?, ?, DATE '?', ?)";
		
		try {
			PreparedStatement stat = db.conn.prepareStatement(SQL);
			stat.setString(1, creditCardNumber);
			stat.setInt(2, cvv);
			
			ResultSet rs = stat.executeQuery();
			if(rs.first()) {
				int creditCardId = rs.getInt(1);
				aCard = CreditCardsDB.getCreditCardById(creditCardId);
				
				stat.close();
				db.closeConnection();
				return aCard;
			} else {
				PreparedStatement stat2 = db.conn.prepareStatement(SQL2);
				stat2.setInt(1, userId);
				stat2.setString(2, cardHolderName);
				stat2.setString(3, creditCardNumber);
				stat2.setString(4, cardType);
				stat2.setInt(5, cvv);
				stat2.setDate(6, date);
				stat2.setDouble(7, balance);
				
				stat2.executeUpdate();
				stat2.close();
				db.closeConnection();
				return CreditCardsDB.addCreditCard(aCard);
			}
		} catch (SQLException e) {
			log.error("SQLException: ",e);
			e.printStackTrace();
		}
		return aCard;
	}
}
