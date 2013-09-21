package ca.etsmtl.gti710.bookies.model;

public class Product {
	public String id;
	public String name;
	public int price;
	
	public Product() {};
	
	public String toString() {
		return "Product("+id+", "+name+", "+price+")";
	}
}
