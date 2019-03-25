package models;

import java.text.NumberFormat;
import java.util.Locale;

public class OrderItem {
	private int id;
	private Performance performance;
	private int quantity;
	private double price;
	private boolean cancelled;
	private Order containingOrder;

	public OrderItem() {
	}

	public OrderItem(Performance performance, int quantity) {
		super();
		this.performance = performance;
		this.quantity = quantity;
		this.setPrice(performance, quantity);
	}

	public OrderItem(int orderItemId, Performance performance, int quantity, boolean cancelled, Order containingOrder) {
		super();
		this.id = orderItemId;
		this.performance = performance;
		this.quantity = quantity;
		this.setPrice(performance, quantity);
		this.cancelled = cancelled;
		this.containingOrder = containingOrder;
	}

	public OrderItem(int orderItemId, Performance performance, int quantity, boolean cancelled) {
		super();
		this.id = orderItemId;
		this.performance = performance;
		this.quantity = quantity;
		this.setPrice(performance, quantity);
		this.cancelled = cancelled;
	}

	public int getId() {
		return id;
	}

	public Performance getPerformance() {
		return performance;
	}

	public void setPerformance(Performance performance) {
		this.performance = performance;
		setPrice(performance, this.quantity);
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
		setPrice(this.performance, quantity);
	}
	
	public double getPrice() {
		return price;
	}
	
	public void setPrice(Performance performance, int quantity) {
		this.price = (performance.getPrice() * quantity);
	}

	public Order getContainingOrder() {
		return containingOrder;
	}

	public void setContainingOrder(Order containingOrder) {
		this.containingOrder = containingOrder;
	}

	public boolean getCancelled() {
		return cancelled;
	}

	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}
	
	public String getPrettyPrice() {
		return NumberFormat.getCurrencyInstance(new Locale("en", "US")).format(price);
	}

}
