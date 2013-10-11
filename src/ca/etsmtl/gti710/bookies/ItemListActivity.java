package ca.etsmtl.gti710.bookies;

import java.text.DateFormat;
import java.util.ArrayList;

import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.converter.GsonConverter;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import ca.etsmtl.gti710.bookies.model.AppContent;
import ca.etsmtl.gti710.bookies.model.Order;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * An activity representing a list of Items. This activity has different
 * presentations for handset and tablet-size devices. On handsets, the activity
 * presents a list of items, which when touched, lead to a
 * {@link ItemDetailActivity} representing item details. On tablets, the
 * activity presents the list of items and item details side-by-side using two
 * vertical panes.
 * <p>
 * The activity makes heavy use of fragments. The list of items is a
 * {@link ItemListFragment} and the item details (if present) is a
 * {@link ItemDetailFragment}.
 * <p>
 * This activity also implements the required {@link ItemListFragment.Callbacks}
 * interface to listen for item selections.
 */
public class ItemListActivity extends FragmentActivity implements
		ItemListFragment.Callbacks {

	public static String SECRET = "abc";
	//public static String END_POINT = "https://dl.dropboxusercontent.com/u/12920251/";
	public static String END_POINT = "http://10.196.113.44/bookies/web/app_dev.php/api/";
	protected BookiesService service;
	protected GetDataTask task = null;
	protected ProgressDialog progressDialog = null;
	
	/**
	 * Whether or not the activity is in two-pane mode, i.e. running on a tablet
	 * device.
	 */
	private boolean mTwoPane;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_item_list);

		if (findViewById(R.id.item_detail_container) != null) {
			// The detail container view will be present only in the
			// large-screen layouts (res/values-large and
			// res/values-sw600dp). If this view is present, then the
			// activity should be in two-pane mode.
			mTwoPane = true;

			// In two-pane mode, list items should be given the
			// 'activated' state when touched.
			((ItemListFragment) getSupportFragmentManager().findFragmentById(
					R.id.item_list)).setActivateOnItemClick(true);
		}

		// TODO: If exposing deep links into your app, handle intents here.
		
		//
		
		Gson gson = new GsonBuilder()
		 //.setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
		 .setDateFormat("yyyy-MM-dd HH:mm:ss")
		 .create();
		
		RestAdapter restAdapter = new RestAdapter.Builder()
	    				.setServer(END_POINT)
	    				.setConverter(new GsonConverter(gson))
	    				.build();

		service = restAdapter.create(BookiesService.class);
		
		AppContent.orders.clear();
		
		startGetDataTask();
	}

	/**
	 * Callback method from {@link ItemListFragment.Callbacks} indicating that
	 * the item with the given ID was selected.
	 */
	@Override
	public void onItemSelected(String id) {
		if (mTwoPane) {
			// In two-pane mode, show the detail view in this activity by
			// adding or replacing the detail fragment using a
			// fragment transaction.
			Bundle arguments = new Bundle();
			arguments.putString(ItemDetailFragment.ARG_ITEM_ID, id);
			ItemDetailFragment fragment = new ItemDetailFragment();
			fragment.setArguments(arguments);
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.item_detail_container, fragment).commit();
		} else {
			// In single-pane mode, simply start the detail activity
			// for the selected item ID.
			Intent detailIntent = new Intent(this, ItemDetailActivity.class);
			detailIntent.putExtra(ItemDetailFragment.ARG_ITEM_ID, id);
			startActivity(detailIntent);
		}
	}
	
	protected void startGetDataTask() {
		cancelTask();
		cancelProgress();
		task = new GetDataTask();
		task.execute();
		showProgress();
	}

	protected void showProgress() {
		cancelProgress();
		progressDialog = ProgressDialog.show(this, "Synchronisation",
			    "Be patient", true, true, new OnCancelListener() {
					@Override
					public void onCancel(DialogInterface dialog) {
						cancelTask();
					}
				});
	}
	
	protected void cancelProgress() {
		if (progressDialog != null) {
			progressDialog.cancel();
			progressDialog = null;
		}
	}
	
	protected void cancelTask() {
		if (task != null) {
			task.cancel(true);
			task = null;
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
	    switch (item.getItemId()) {
	    case R.id.action_refresh:
	        startGetDataTask();
	        return true;
	    default:
	        return super.onOptionsItemSelected(item);
	    }
	}
	
	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
	    //savedInstanceState.putString("auth", SECRET);
	    
	    // Always call the superclass so it can save the view hierarchy state
	    super.onSaveInstanceState(savedInstanceState);
	}
	
	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
	    // Always call the superclass so it can restore the view hierarchy
	    super.onRestoreInstanceState(savedInstanceState);
	   
	    // Restore state members from saved instance
	    //SECRET = savedInstanceState.getString("auth");
	}

	protected class GetDataTask extends AsyncTask<Void, Void, Boolean> {

		@Override
		protected Boolean doInBackground(Void... params) {
			try {
				ArrayList<Order> data = service.listPurchases();
				AppContent.orders.clear();
				AppContent.orders.addAll(data);
			}
			catch (RetrofitError e) {
				e.printStackTrace();
				return false;
			}
			return true;
		}
		
		@Override
		protected void onPostExecute(final Boolean success) {
			task = null;
			cancelProgress();
			if (success) {
				for (Order order : AppContent.orders) {
					Log.d("coucou", order.toString());
				}
				ItemListFragment frag = ((ItemListFragment) getSupportFragmentManager().findFragmentById(
						R.id.item_list));
				frag.refresh();
			}
		}

		@Override
		protected void onCancelled() {
			task = null;
			cancelProgress();
		}
		
	}
}
