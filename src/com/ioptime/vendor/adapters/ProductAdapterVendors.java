package com.ioptime.vendor.adapters;

import java.util.ArrayList;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.image.loader.ImageLoader;
import com.ioptime.betty.R;
import com.ioptime.betty.model.Product;

public class ProductAdapterVendors extends BaseAdapter {

	private Activity activity;
	private static LayoutInflater inflater = null;
	public boolean wish;
	public ImageLoader imageLoader;
	ArrayList<Product> productsList;

	public ProductAdapterVendors(Activity a, boolean iswish,
			ArrayList<Product> products) {
		activity = a;
		wish = iswish;
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
			vi = inflater.inflate(R.layout.item_product, null);

		//
		try {

			LinearLayout layout = (LinearLayout) vi
					.findViewById(R.id.profileList);
			TextView textName = (TextView) vi
					.findViewById(R.id.productListName);
			TextView textStoreName = (TextView) vi
					.findViewById(R.id.productListStore);
			TextView textPrice = (TextView) vi
					.findViewById(R.id.productListPrice);
			ImageView image = (ImageView) vi.findViewById(R.id.listImage);
			ImageView wishList = (ImageView) vi
					.findViewById(R.id.productListWishList);
			wishList.setVisibility(View.INVISIBLE);
			wishList.setClickable(false);

			textName.setText(productsList.get(position).getName());
			textStoreName.setText(productsList.get(position).getStoreName());
			textPrice.setText("" + productsList.get(position).getPrice());
			imageLoader.DisplayImage(productsList.get(position).getImage(),
					image, R.drawable.ic_launcher);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return vi;
	}

}
