package com.ioptime.adapters;

import java.util.ArrayList;
import java.util.Collections;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.image.loader.ImageLoader;
import com.ioptime.betty.Appconstants;
import com.ioptime.betty.R;
import com.ioptime.betty.model.Product;
import com.ioptime.extendablelibrary.IoptimeRoot;

public class ShopProductAdapter extends BaseAdapter {

	private Activity activity;
	private static LayoutInflater inflater = null;
	ArrayList<Product> shopProducts;
	public ImageLoader imageLoader;

	public ShopProductAdapter(Activity a, ArrayList<Product> productsList) {
		activity = a;
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		shopProducts = productsList;
		imageLoader = new ImageLoader(activity.getApplicationContext(),
				R.drawable.ic_launcher);
		if (Appconstants.user.getWishlist().size() > 0) {
			for (int i = 0; i < shopProducts.size(); i++) {

				for (int j = 0; j < Appconstants.user.getWishlist().size(); j++) {
					shopProducts.get(i).setInWIsh(false);
					if (shopProducts.get(i).getProductId() == Appconstants.user
							.getWishlist().get(j).getProductId()) {
						shopProducts.get(i).setInWIsh(true);
						break;
					}
				}
			}
		}
	}

	@Override
	public void notifyDataSetChanged() {
		super.notifyDataSetChanged();
	}

	public int getCount() {
		return shopProducts.size();
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
			TextView textName = (TextView) vi
					.findViewById(R.id.productListName);
			TextView textStoreName = (TextView) vi
					.findViewById(R.id.productListStore);
			TextView textPrice = (TextView) vi
					.findViewById(R.id.productListPrice);
			ImageView image = (ImageView) vi.findViewById(R.id.listImage);
			ImageView wishList = (ImageView) vi
					.findViewById(R.id.productListWishList);
			if (shopProducts.get(position).getInWish()) {
				wishList.setImageResource(R.drawable.inwishlist);
			} else if (!shopProducts.get(position).getInWish()) {
				wishList.setImageResource(R.drawable.notinwishlist);

			}

			wishList.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if (shopProducts.get(position).getInWish()) {

						shopProducts.get(position).setInWIsh(false);
						changeWishStatus(position, Appconstants.productsList,
								false);
						new RemoveFromWishList(shopProducts.get(position)
								.getProductId(), position).execute();
					} else if (!shopProducts.get(position).getInWish()) {
						// adding to wish list locally
						shopProducts.get(position).setInWIsh(true);
						changeWishStatus(position, Appconstants.productsList,
								true);
						new AddToWishBTTask(shopProducts.get(position)
								.getProductId(), position).execute();

					}
					notifyDataSetChanged();

				}
			});

			textName.setText(shopProducts.get(position).getName());
			textStoreName.setText(shopProducts.get(position).getStoreName());
			textPrice.setText("" + shopProducts.get(position).getPrice());
			imageLoader.DisplayImage(shopProducts.get(position).getImage(),
					image, R.drawable.ic_launcher);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return vi;
	}

	private class AddToWishBTTask extends AsyncTask<Void, Void, Void> {
		int productid;
		String result;
		int position;

		AddToWishBTTask(int productid, int pos) {
			this.productid = productid;
			this.position = pos;
		}

		@Override
		protected Void doInBackground(Void... arg0) {

			ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("product_id", ""
					+ productid));
			nameValuePairs.add(new BasicNameValuePair("customer_id", ""
					+ Appconstants.user.getCustomer_id()));

			try {

				//
				result = "";
				result = IoptimeRoot.getJSONfromURL(Appconstants.Server
						+ "product_wishlist_add.php", nameValuePairs, 0);
				Log.d("result", "--" + result);
				if (result.equalsIgnoreCase("") | result.contains("empty")
						| result.contains("err")) {
					// not valid

				} else {
					// JSONArray jArray = new JSONArray(result);
					//
					// for (int i = 0; i < jArray.length(); i++) {
					// JSONObject json_data = jArray.getJSONObject(i);
					// userid = Integer.parseInt(json_data.getString(
					// "customer_id").trim());
					//
					// }

				}
			} catch (Exception e) {

				e.printStackTrace();
				return null;
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void params) {
			if (!result.equalsIgnoreCase("")) {
				Toast.makeText(activity.getApplicationContext(),
						"Successfully added", Toast.LENGTH_SHORT).show();
			} else {
				// if failed removing

				Toast.makeText(activity.getApplicationContext(),
						"Error while adding", Toast.LENGTH_LONG).show();
				shopProducts.get(position).setInWIsh(false);

				changeWishStatus(position, Appconstants.productsList, false);
				notifyDataSetChanged();
			}
		}
	}

	private class RemoveFromWishList extends AsyncTask<Void, Void, Void> {
		int productid;
		String result;
		int position;

		RemoveFromWishList(int productid, int pos) {
			this.productid = productid;
			position = pos;
		}

		@Override
		protected Void doInBackground(Void... arg0) {

			ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("product_id", ""
					+ productid));
			nameValuePairs.add(new BasicNameValuePair("customer_id", ""
					+ Appconstants.user.getCustomer_id()));

			try {

				//
				result = "";
				result = IoptimeRoot.getJSONfromURL(Appconstants.Server
						+ "product_wishlist_remove.php", nameValuePairs, 0);
				Log.d("result", "--" + result);
				if (result.equalsIgnoreCase("") | result.contains("empty")
						| result.contains("err")) {

				} else {
					// JSONArray jArray = new JSONArray(result);
					//
					// for (int i = 0; i < jArray.length(); i++) {
					// JSONObject json_data = jArray.getJSONObject(i);
					// userid = Integer.parseInt(json_data.getString(
					// "customer_id").trim());
					//
					// }

				}
			} catch (Exception e) {

				e.printStackTrace();
				return null;
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void params) {
			if (!result.equalsIgnoreCase("")) {
				Toast.makeText(activity.getApplicationContext(), "Removed",
						Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(activity.getApplicationContext(),
						"Error while removing", Toast.LENGTH_LONG).show();
				// if failed removing
				shopProducts.get(position).setInWIsh(true);
				changeWishStatus(position, Appconstants.productsList, true);

				notifyDataSetChanged();
			}
		}
	}

	private void changeWishStatus(int position, ArrayList<Product> products,
			boolean change) {
		if (Appconstants.user.getWishlist().size() > 0) {
			// wish list already existed
			int index = Collections.binarySearch(
					Appconstants.user.getWishlist(),
					new Product(products.get(position).getProductId(), false),
					Product.c);
			if (index >= 0) { // finding the item was already in wishlist so
								// will change
								// what ever it is in wishlist
				Appconstants.user.getWishlist().get(index).setInWIsh(change);
				// checking for product id in public arraylist and making the
				// change reflect there as well
				int innerindex = Collections.binarySearch(
						Appconstants.productsList,
						new Product(products.get(position).getProductId(),
								false), Product.c);

				if (innerindex >= 0)
					Appconstants.productsList.get(innerindex).setInWIsh(change);
			} else {
				// item was not in wish list so adding and changing
				Appconstants.user.getWishlist().add(
						new Product(products.get(position).getProductId(),
								change));
				// checking for product id in public arraylist and making the
				// change reflect there as well
				int innerindex = Collections.binarySearch(
						Appconstants.productsList,
						new Product(products.get(position).getProductId(),
								false), Product.c);

				if (innerindex >= 0)
					Appconstants.productsList.get(innerindex).setInWIsh(change);

			}

		} else {
			// no product in wish list
			// init the wishlist

			Appconstants.user.setWishlist(new ArrayList<Product>());
			Appconstants.user.getWishlist().add(
					new Product(products.get(position).getProductId(), change));
		}
	}

}
