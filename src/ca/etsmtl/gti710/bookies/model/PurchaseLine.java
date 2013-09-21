package ca.etsmtl.gti710.bookies.model;


public class PurchaseLine {
	public String id;
	public int unitPrice;
	public int qty;
	public String productName;
	public int productId;
	
	public PurchaseLine() {}

	@Override
	public String toString() {
		return "PurchaseLine(id=" + id + ", unitPrice=" + unitPrice + ", qty="
				+ qty + ", productName=" + productName + ", productId="
				+ productId + ")";
	};
}
