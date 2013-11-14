package ca.etsmtl.gti710.bookies.model;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;


public class OrderLine {
	
	final static String IMAGE_SERVER = "";
	
	public String id;
	public double unit_cost;
	public int quantity;
	public Product product;
	public Bitmap img;
	
	public OrderLine() {}

	@Override
	public String toString() {
		return "PurchaseLine(id=" + id + ", unitPrice=" + unit_cost + ", qty="
				+ quantity + ", product=" + product + ")";
	};
	
	public void loadImage() throws IOException {
		URL newurl = new URL(getImgUrl()); 
		img = BitmapFactory.decodeStream(newurl.openConnection().getInputStream());
	}
	
	public String getImgUrl() {
		return IMAGE_SERVER+id+".jpg";
	}
	
	public String formattedTotal() {
		DecimalFormat formatter = new DecimalFormat("0.00");
		return "$"+formatter.format(unit_cost*quantity);
	}
}
