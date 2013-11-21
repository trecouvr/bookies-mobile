package ca.etsmtl.gti710.adapters;

import java.io.IOException;
import java.util.ArrayList;

import android.content.Context;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import ca.etsmtl.gti710.bookies.R;
import ca.etsmtl.gti710.bookies.model.OrderLine;

public class OrderLineListAdapter extends ArrayAdapter<OrderLine> {

	private static class ViewHolder {
		TextView name;
		TextView cost;
		ImageView icon;
	}

    public OrderLineListAdapter(Context context, int textViewResourceId, ArrayList<OrderLine> items) {
		super(context, textViewResourceId, items);
	}

	@Override
    public View getView(int position, View convertView, ViewGroup parent) {
    	//Log.d(LOG_TAG, "getView #"+position+" "+convertView);
		OrderLine item = getItem(position);
    	ViewHolder holder;
        if (convertView == null) {  // if it's not recycled, initialize some attributes
        	
        	LayoutInflater li = (LayoutInflater)this.getContext().getSystemService
        		      (Context.LAYOUT_INFLATER_SERVICE);
        	convertView = li.inflate(R.layout.orderline_list_item, null);
        	
        	holder = new ViewHolder();
			holder.icon = (ImageView) convertView.findViewById(R.id.item_image);
			holder.name = (TextView) convertView.findViewById(R.id.item_name);
			holder.cost = (TextView) convertView.findViewById(R.id.item_cost);
			
			convertView.setTag(holder);
			
        } else {
        	holder = (ViewHolder) convertView.getTag();
        }

		holder.name.setText(""+item.quantity+" x "+item.product.name);
		holder.cost.setText(item.formattedTotal());
		Context ctx = getContext();
		//holder.icon.setImageResource(ctx.getResources().getIdentifier("drawable/" + "ic_launcher", null, ctx.getPackageName()));
		int resId = ctx.getResources().getIdentifier("drawable/item_"+item.product.id, null, ctx.getPackageName());
		holder.icon.setImageResource(resId);
        return convertView;
    }
	
	protected class LoadBitmap extends AsyncTask<OrderLine, String, OrderLine> {
		@Override
		protected OrderLine doInBackground(OrderLine... params) {
			OrderLine line = params[0];
			if (line.img == null) {
				try {
					line.loadImage();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		    return line;
		}

		@Override
		protected void onPostExecute(OrderLine line) {
			notifyDataSetChanged();
		}
	}
}
