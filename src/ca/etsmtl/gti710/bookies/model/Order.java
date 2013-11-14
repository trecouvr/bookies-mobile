package ca.etsmtl.gti710.bookies.model;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;

public class Order {
	
	public String id;
	public Date created_at;
	public String customer_name;
	public String customer_address;
	public double total;
	public ArrayList<OrderLine> lines;
	
	public Order() {}
	public Order(String id) {
		this.id = id;
	}
	@Override
	public String toString() {
		return "Order(id=" + id 
				+ ", date=" + created_at 
				+ ", total=" + total + "$"
				+ ", lines=" + lines
				+ ")";
	}
	
	public String formattedTotal() {
		DecimalFormat formatter = new DecimalFormat("0.00");
		return "$"+formatter.format(total);
	}
}
