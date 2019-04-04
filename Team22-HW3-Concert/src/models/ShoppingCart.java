package models;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class ShoppingCart {
	private List<OrderItem> orderItems;
	private double totalCost;
	
	public ShoppingCart(List<OrderItem> orderItems) {
		super();
		this.orderItems = orderItems;
		this.setTotalCost(orderItems);
	}
	
	public ShoppingCart() {
		super();
	}

	public List<OrderItem> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
		setTotalCost(orderItems);
	}
	
	public double getTotalCost() {
		double tCost = 0;
		for (OrderItem orderItem : this.orderItems) {
			tCost += orderItem.getPrice();
		}
		return tCost;
	}
	
	public void setTotalCost(List<OrderItem> orderItems) {
		double totalCost = 0;
		for (OrderItem orderItem : this.orderItems) {
			totalCost += orderItem.getPrice();
		}
		this.totalCost = totalCost;
	}

	public String getPrettyPrice() {
		return NumberFormat.getCurrencyInstance(new Locale("en", "US")).format(totalCost);
	}
}
