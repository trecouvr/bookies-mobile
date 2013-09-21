package ca.etsmtl.gti710.bookies;

import java.util.ArrayList;

import retrofit.http.GET;
import retrofit.http.Path;
import ca.etsmtl.gti710.bookies.model.Product;
import ca.etsmtl.gti710.bookies.model.Purchase;

public interface BookiesService {
	  @GET("/purchases/list.json")
	  ArrayList<Purchase> listPurchases();
	  
	  @GET("/purchases/{id}.json")
	  Purchase getPurchase(@Path("id") int id);
	  
	  @GET("/products/{id}.json")
	  Product getProduct(@Path("id") int id);
}
