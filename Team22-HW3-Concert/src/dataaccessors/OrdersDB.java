package dataaccessors;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import models.Address;
import models.CreditCard;
import models.Performance;
import models.Order;
import models.OrderItem;
import models.User;

public class OrdersDB {
	private static Database db = new Database();

	public static String addOrder(Order anOrder) {
		db.connectMeIn();
		CreditCard creditCard = anOrder.getCreditCard();
		double adjustment = anOrder.getTotalCost();
		String message = "";

	
		try {
			int customerId = anOrder.getCustomer().getId();
			double totalCost = anOrder.getTotalCost();
			int creditCardId = anOrder.getCreditCard().getId();
			int billingAddressId = anOrder.getBillingAddress().getId();
			Date orderDate = anOrder.getOrderDate();

			String SQL = "INSERT INTO Orders(customerId, totalCost, creditCardId, billingAddressId, orderDate) VALUES (?, ?, ?, ?, DATE ?)";

			PreparedStatement stat = db.conn.prepareStatement(SQL);
			stat.setInt(1, customerId);
			stat.setDouble(2, totalCost);
			stat.setInt(3, creditCardId);
			stat.setInt(4, billingAddressId);
			stat.setDate(5, orderDate);
			
			stat.executeUpdate();
			
			Order orderWithId = OrdersDB.getOrder(anOrder);
			OrdersDB.addOrderItems(orderWithId);
			message="Order was completed successfully.";
	
		} catch (SQLException e) {
			message = CreditCardsDB.adjustCreditCardBalance(creditCard, adjustment);
			message = "There was a problem placing your order - you have been refunded for the charge";
		}
		db.closeConnection();
		return message;
	}
	
	private static Order getOrder(Order anOrder) {
		db.connectMeIn();
		int customerId = anOrder.getCustomer().getId();
		double totalCost = anOrder.getTotalCost();
		int creditCardId = anOrder.getCreditCard().getId();
		int billingAddressId = anOrder.getBillingAddress().getId();
		Date orderDate = anOrder.getOrderDate();
		List<OrderItem> orderItems = anOrder.getOrderItems();
		
		try {
			String SQL = "SELECT * FROM Orders WHERE customerId=? AND creditCardId=? AND billingAddressId=? AND orderDate=?";

			PreparedStatement stat = db.conn.prepareStatement(SQL);
			stat.setInt(1, customerId);
			stat.setInt(2, creditCardId);
			stat.setInt(3, billingAddressId);
			stat.setDate(4, orderDate);
			ResultSet rs = stat.executeQuery();

			if(rs.next()) {
				int orderId = rs.getInt(1);
				User customer = UsersDB.getUserById(customerId);
				CreditCard creditCard = CreditCardsDB.getCreditCardById(creditCardId);
				Address address = AddressesDB.getAddressById(billingAddressId);
				anOrder = new Order(orderId, customer, totalCost, creditCard, address, orderDate, orderItems);
				stat.close();
				db.closeConnection();
				return anOrder;

			} 
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return anOrder;
	}

	public static void addOrderItems(Order anOrder) {
		db.connectMeIn();
		List<OrderItem> orderItems = anOrder.getOrderItems();
		for (OrderItem orderItem : orderItems) {
			Performance performance = orderItem.getPerformance();
			int orderId = anOrder.getId();
			int performanceId = performance.getId();
			int quantity = orderItem.getQuantity();
			
			try {
				PerformancesDB.updateSeatsPurchased(performance, quantity);

				String SQL = "INSERT INTO OrderItems(orderId, performanceId, quantity) VALUES (?, ?, ?)";
				
				PreparedStatement stat = db.conn.prepareStatement(SQL);
				stat.setInt(1, orderId);
				stat.setInt(2, performanceId);
				stat.setInt(3, quantity);
			
				stat.executeUpdate();
				stat.close();
		
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		db.closeConnection();
	}

	public static List<Order> getOrdersByCustomerId(int id) {
		db.connectMeIn();
		String SQL = "SELECT * FROM Orders WHERE customerId = ?";
		
	    List<Order> orders = new ArrayList<Order>();
	    
		try {
			PreparedStatement stat = db.conn.prepareStatement(SQL);
			stat.setInt(1, id);
			ResultSet rs = stat.executeQuery();
			
			while (rs.next()){
				int orderId = rs.getInt(1);
				int customerId = rs.getInt(2);
				double totalCost = rs.getDouble(3);
				int creditCardId = rs.getInt(4);
				int billingAddressId = rs.getInt(5);
				Date orderDate = rs.getDate(6);
				
				User customer = UsersDB.getUserById(customerId);
				CreditCard creditCard = CreditCardsDB.getCreditCardById(creditCardId);
				Address billingAddress = AddressesDB.getAddressById(billingAddressId);
				List<OrderItem> orderItems = OrdersDB.getOrderItemsByOrderId(orderId);
				
				Order anOrder = new Order(orderId, customer, totalCost, creditCard, billingAddress, orderDate, orderItems);
		        orders.add(anOrder);
		    }
			
		    stat.close();
		        
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		db.closeConnection();
		return orders;
	}

	private static List<OrderItem> getOrderItemsByOrderId(int orderId) {
		db.connectMeIn();
		String SQL = "SELECT * FROM OrderItems WHERE orderId = ?";
		
	    List<OrderItem> orderItems = new ArrayList<OrderItem>();
	    
		try {
			PreparedStatement stat = db.conn.prepareStatement(SQL);
			stat.setInt(1, orderId);
			ResultSet rs = stat.executeQuery();
			
			while (rs.next()){
				int orderItemId = rs.getInt(1);
				int performanceId = rs.getInt(3);
				int quantity = rs.getInt(4);
				boolean cancelled = rs.getBoolean(5);
				
				Performance performance = PerformancesDB.getPerformanceById(performanceId);
				//Order order = OrdersDB.getOrderById(orderId);
				OrderItem anOrderItem = new OrderItem(orderItemId, performance, quantity, cancelled);
				//OrderItem anOrderItem = new OrderItem(orderItemId, performance, quantity, order);
				
				orderItems.add(anOrderItem);
		    }
			
		    stat.close();
		        
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		db.closeConnection();
		return orderItems;
	}

	public static Order getOrderById(int orderId) {
		db.connectMeIn();
		String SQL = "SELECT * FROM Orders WHERE id = ?";
		
		Order anOrder = new Order();
	    
		try {
			PreparedStatement stat = db.conn.prepareStatement(SQL);
			stat.setInt(1, orderId);
			ResultSet rs = stat.executeQuery();
			
			while (rs.next()){
				int customerId = rs.getInt(2);
				double totalCost = rs.getDouble(3);
				int creditCardId = rs.getInt(4);
				int billingAddressId = rs.getInt(5);
				Date orderDate = rs.getDate(6);
				
				User customer = UsersDB.getUserById(customerId);
				CreditCard creditCard = CreditCardsDB.getCreditCardById(creditCardId);
				Address billingAddress = AddressesDB.getAddressById(billingAddressId);
				List<OrderItem> orderItems = OrdersDB.getOrderItemsByOrderId(orderId);
				
				anOrder = new Order(orderId, customer, totalCost, creditCard, billingAddress, orderDate, orderItems);
		    }
			
		    stat.close();
		        
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		db.closeConnection();
		return anOrder;
	}

	public static OrderItem getOrderItemById(int orderItemId) {
		db.connectMeIn();
		String SQL = "SELECT * FROM OrderItems WHERE id = ?";
		
		OrderItem anOrderItem = new OrderItem();
	    
		try {
			PreparedStatement stat = db.conn.prepareStatement(SQL);
			stat.setInt(1, orderItemId);
			ResultSet rs = stat.executeQuery();
			
			while (rs.next()){
				int orderId = rs.getInt(2);
				int	performanceId = rs.getInt(3);
				int quantity = rs.getInt(4);
				boolean cancelled = rs.getBoolean(5);
				
				Order order = OrdersDB.getOrderById(orderId);
				Performance performance = PerformancesDB.getPerformanceById(performanceId);
				
				anOrderItem = new OrderItem(orderItemId, performance, quantity, cancelled, order);				
		    }
			
		    stat.close();
		        
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		db.closeConnection();
		return anOrderItem;
	}

	public static void cancelOrderItem(OrderItem orderItemToCancel) {
		db.connectMeIn();
		Order containingOrder = orderItemToCancel.getContainingOrder();
		CreditCard creditCard = containingOrder.getCreditCard();
		double adjustment = (0-orderItemToCancel.getPrice());
		Performance performance = orderItemToCancel.getPerformance();
		int quantityAdjustment = (0-orderItemToCancel.getQuantity());
	
		try {
			String SQL = "select * from OrderItems where id = ?";
			PreparedStatement stat = db.conn.prepareStatement(SQL);
			stat.setInt(1, orderItemToCancel.getId());
			
			ResultSet rs = stat.executeQuery();
			
			if(rs.next()) {
				String SQL2 = "UPDATE OrderItems SET cancelled = 1 WHERE id = ?";
				PreparedStatement stat2 = db.conn.prepareStatement(SQL2);
				stat2.setInt(1, orderItemToCancel.getId());
				stat2.executeUpdate();
				
				CreditCardsDB.adjustCreditCardBalance(creditCard, adjustment);
				
				PerformancesDB.updateSeatsPurchased(performance, quantityAdjustment);
				
				stat2.close();
			}
			
			stat.close();
	
		} catch (SQLException e) {
			e.printStackTrace();
		}
		db.closeConnection();		
	}

}
