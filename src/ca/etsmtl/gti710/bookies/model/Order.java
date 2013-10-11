package ca.etsmtl.gti710.bookies.model;

import java.util.ArrayList;
import java.util.Date;

public class Order {
	
	public String id;
	public Date created_at;
	public String customer_name;
	int total_price;
	public ArrayList<OrderLine> lines;
	
	public Order() {}
	public Order(String id) {
		this.id = id;
	}
	@Override
	public String toString() {
		return "Order(id=" + id 
				+ ", date=" + created_at 
				+ ", totalPrice=" + total_price 
				+ "', lines=" + lines
				+ ")";
	}
	
}
