package ca.etsmtl.gti710.bookies;

import java.util.ArrayList;

import retrofit.http.GET;
import retrofit.http.Path;
import ca.etsmtl.gti710.bookies.model.Product;
import ca.etsmtl.gti710.bookies.model.Order;

public interface BookiesService {
	  @GET("/orders.json")
	  ArrayList<Order> listPurchases();
	  
	  @GET("/purchases/{id}.json")
	  Order getPurchase(@Path("id") int id);
	  
	  @GET("/products/{id}.json")
	  Product getProduct(@Path("id") int id);
}
