package ca.etsmtl.gti710.adapters;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import ca.etsmtl.gti710.bookies.R;
import ca.etsmtl.gti710.bookies.model.Order;

public class OrderListAdapter extends ArrayAdapter<Order>{
	private static class ViewHolder {
		TextView id;
		TextView customer_name;
		TextView total;
		TextView date;
	}

    public OrderListAdapter(Context context, int textViewResourceId, ArrayList<Order> items) {
		super(context, textViewResourceId, items);
	}

	@Override
    public View getView(int position, View convertView, ViewGroup parent) {
    	//Log.d(LOG_TAG, "getView #"+position+" "+convertView);
		Order item = getItem(position);
    	ViewHolder holder;
        if (convertView == null) {  // if it's not recycled, initialize some attributes
        	
        	LayoutInflater li = (LayoutInflater)this.getContext().getSystemService
        		      (Context.LAYOUT_INFLATER_SERVICE);
        	convertView = li.inflate(R.layout.order_list_item, null);
        	
        	holder = new ViewHolder();
			holder.id = (TextView) convertView.findViewById(R.id.item_id);
			holder.customer_name = (TextView) convertView.findViewById(R.id.item_customer_name);
			holder.total = (TextView) convertView.findViewById(R.id.item_total);
			holder.date = (TextView) convertView.findViewById(R.id.item_date);
			
			convertView.setTag(holder);
			
        } else {
        	holder = (ViewHolder) convertView.getTag();
        }

		holder.id.setText("#"+item.id);
		holder.customer_name.setText(""+item.customer_name);
		holder.total.setText(item.formattedTotal());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
		holder.date.setText(sdf.format(item.created_at));
        
        return convertView;
    }
}
