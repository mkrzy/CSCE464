package models;

public class Venue {
	private int id;
	private String name;
	private int availableSeats;
	private Address address;
	private User owner;

	public Venue() {
		super();
	}

	public Venue(int id, String name, int availableSeats, Address address, User owner) {
		super();
		this.id = id;
		this.name = name;
		this.availableSeats = availableSeats;
		this.address = address;
		this.owner = owner;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAvailableSeats() {
		return availableSeats;
	}

	public void setAvailableSeats(int availableSeats) {
		this.availableSeats = availableSeats;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

}
