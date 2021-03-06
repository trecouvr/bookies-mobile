package ca.etsmtl.gti710.bookies;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import ca.etsmtl.gti710.adapters.OrderLineListAdapter;
import ca.etsmtl.gti710.bookies.model.AppContent;
import ca.etsmtl.gti710.bookies.model.Order;


/**
 * A fragment representing a single Item detail screen. This fragment is either
 * contained in a {@link ItemListActivity} in two-pane mode (on tablets) or a
 * {@link ItemDetailActivity} on handsets.
 */
public class ItemDetailFragment extends Fragment {
	/**
	 * The fragment argument representing the item ID that this fragment
	 * represents.
	 */
	public static final String ARG_ITEM_ID = "item_id";

	/**
	 * The dummy content this fragment is presenting.
	 */
	protected Order order;
	
	/**
	 * the list adapter to display lines
	 */
	protected OrderLineListAdapter adapter;

	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public ItemDetailFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (getArguments().containsKey(ARG_ITEM_ID)) {
			// Load the dummy content specified by the fragment
			// arguments. In a real-world scenario, use a Loader
			// to load content from a content provider.
			String id = getArguments().getString(ARG_ITEM_ID);
			for (Order pur : AppContent.orders) {
				if (pur.id.equals(id)) {
					order = pur;
				}
			}
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_item_detail,
				container, false);

		// Show the dummy content as text in a TextView.
		if (order != null) {
			((TextView) rootView.findViewById(R.id.title))
					.setText("Purchase #"+order.id);
			((TextView) rootView.findViewById(R.id.total))
					.setText(order.formattedTotal());
			((TextView) rootView.findViewById(R.id.customer_address))
					.setText(order.customer_name+"\n"+order.customer_address);
			
	        ListView lv = (ListView)rootView.findViewById(R.id.list);

	        adapter = new OrderLineListAdapter(getActivity(), 
	        		R.layout.orderline_list_item, order.lines);
	        
	        lv.setAdapter(adapter);
		}

		return rootView;
	}
}
