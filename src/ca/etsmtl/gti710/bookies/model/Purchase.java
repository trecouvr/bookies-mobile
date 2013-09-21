package ca.etsmtl.gti710.bookies.model;

import java.util.ArrayList;
import java.util.Date;

public class Purchase {
	
	public String id;
	public Date date;
	int totalPrice;
	public ArrayList<PurchaseLine> lines;
	
	public Purchase() {}
	public Purchase(String id) {
		this.id = id;
	}
	@Override
	public String toString() {
		return "Purchase(id=" + id 
				+ ", date='" + date 
				+ ", totalPrice='" + totalPrice 
				+ "', lines=" + lines
				+ ")";
	}
	
}
