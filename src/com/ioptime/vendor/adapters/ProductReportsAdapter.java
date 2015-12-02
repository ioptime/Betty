package com.ioptime.vendor.adapters;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.image.loader.ImageLoader;
import com.ioptime.betty.R;
import com.ioptime.betty.model.ProductReportsModel;

public class ProductReportsAdapter extends BaseAdapter {

	private Activity activity;
	private static LayoutInflater inflater = null;
	public boolean wish;
	public ImageLoader imageLoader;
	ArrayList<ProductReportsModel> productsList;

	public ProductReportsAdapter(Activity a,
			ArrayList<ProductReportsModel> products) {
		activity = a;
		productsList = products;
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		imageLoader = new ImageLoader(activity.getApplicationContext(),
				R.drawable.ic_launcher);
	}

	@Override
	public void notifyDataSetChanged() {
		super.notifyDataSetChanged();
	}

	public int getCount() {
		return productsList.size();
	}

	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		View vi = convertView;
		if (convertView == null)
			vi = inflater.inflate(R.layout.custom_product_reports, null);

		//
		try {

			LinearLayout layout = (LinearLayout) vi.findViewById(R.id.ll);
			TextView textPName = (TextView) vi.findViewById(R.id.PNameReports);
			TextView textPModel = (TextView) vi
					.findViewById(R.id.PModelReports);
			TextView textPViewed = (TextView) vi
					.findViewById(R.id.PViewedReports);
			TextView textPPercentage = (TextView) vi
					.findViewById(R.id.PPercentageReports);
			TextView textPQuantity = (TextView) vi
					.findViewById(R.id.PQuantityReports);
			TextView textPOrderID = (TextView) vi
					.findViewById(R.id.POrdereIDReports);

			ProductReportsModel data = productsList.get(position);
			textPName.setText(data.getProductName());
			textPModel.setText(data.getpModel());
			textPViewed.setText("" + data.getViewed());
			textPPercentage.setText(data.getPerecent());
			textPQuantity.setText("" + data.getQuantity());
			Log.d("values", "" + data.getQuantity() + R.id.PModelReports);
			StringBuilder builder = new StringBuilder();
			String[] arr = data.getOrderID();
			StringBuilder sb = new StringBuilder();
			for (String n : arr) { 
			    if (sb.length() > 0) sb.append(',');
			    sb.append("").append(n).append("");
			    textPOrderID.setText(sb.toString());
			}
//			for (String s : arr) {
//				builder.append(s);
//				builder.append(",");
//
//				textPOrderID.setText(builder.toString());
//
//			}
//			builder.deleteCharAt(builder.length() - 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return vi;
	}

}
