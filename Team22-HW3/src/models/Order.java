package models;

import java.sql.Date;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class Order {
	private int id;
	private User customer;
	private double totalCost;
	private CreditCard creditCard;
	private Address billingAddress;
	private Date orderDate;
	private List<OrderItem> orderItems;
	
	public Order() {
		super();
	}

	public Order(User customer, double totalCost, CreditCard creditCard, Address billingAddress, Date orderDate, List<OrderItem> orderItems) {
		super();
		this.customer = customer;
		this.totalCost = totalCost;
		this.creditCard = creditCard;
		this.billingAddress = billingAddress;
		this.orderDate = orderDate;
		this.orderItems = orderItems;
	}

	public Order(int orderId, User customer, double totalCost, CreditCard creditCard, Address billingAddress,
			Date orderDate, List<OrderItem> orderItems) {
		super();
		this.id = orderId;
		this.customer = customer;
		this.totalCost = totalCost;
		this.creditCard = creditCard;
		this.billingAddress = billingAddress;
		this.orderDate = orderDate;
		this.orderItems = orderItems;
	}

	public int getId() {
		return id;
	}

	public User getCustomer() {
		return customer;
	}

	public void setCustomer(User customer) {
		this.customer = customer;
	}

	public double getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
	}

	public CreditCard getCreditCard() {
		return creditCard;
	}

	public void setCreditCard(CreditCard creditCard) {
		this.creditCard = creditCard;
	}

	public Address getBillingAddress() {
		return billingAddress;
	}

	public void setBillingAddress(Address billingAddress) {
		this.billingAddress = billingAddress;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public List<OrderItem> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}
	
	public String getPrettyPrice() {
		return NumberFormat.getCurrencyInstance(new Locale("en", "US")).format(totalCost);
	}
}
