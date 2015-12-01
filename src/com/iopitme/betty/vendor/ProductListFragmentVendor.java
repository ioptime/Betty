package com.iopitme.betty.vendor;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ioptime.betty.Appconstants;
import com.ioptime.betty.R;
import com.ioptime.betty.model.Product;
import com.ioptime.extendablelibrary.IoptimeFragment;
import com.ioptime.vendor.adapters.ProductAdapterVendors;

public class ProductListFragmentVendor extends IoptimeFragment {
	ProgressBar progressDialog;
	EditText pdEtSearch;
	Button pdBtSearch;
	ListView listViewProducts;
	ProductAdapterVendors productAdapter;

	public ProductListFragmentVendor() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.product, container, false);
		listViewProducts = (ListView) rootView.findViewById(R.id.productList);
		progressDialog = (ProgressBar) rootView
				.findViewById(R.id.productProgressBar);

		if (Appconstants.productsList.size() == 0) {
			Log.d("pop", "size-" + Appconstants.productsList.size());
			new ProductListBT().execute();
		} else {
			populateListView();
		}
		pdEtSearch = (EditText) rootView.findViewById(R.id.pdETSearch);

		pdEtSearch.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

				if (pdEtSearch.getText().toString().equalsIgnoreCase("")) {
					new ProductListBT().execute();

				}
			}
		});

		pdBtSearch = (Button) rootView.findViewById(R.id.pdBtSearch);
		pdBtSearch.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (pdEtSearch.getText().toString().trim().equalsIgnoreCase("")) {
					pdEtSearch.setError("Search field cannot be empty");
				} else {
					String search = pdEtSearch.getText().toString();
					new ProductListSearchBT(search).execute();

				} // TODO Auto-generated method stub

			}
		});

		return rootView;
	}

	private class ProductListBT extends AsyncTask<Void, Void, Void> {
		String result = "";

		@Override
		protected void onPreExecute() {
			progressDialog.setVisibility(View.VISIBLE);
		}

		@Override
		protected Void doInBackground(Void... arg0) {
			try {
				ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
				nameValuePairs.add(new BasicNameValuePair("user_id", "185"));
				result = "";
				result = getJSONfromURL(Appconstants.Server
						+ "products_get_vendor.php", nameValuePairs, 0);
				Log.d("result", "--" + result);
				if (result.equalsIgnoreCase("") | result.contains("empty")
						| result.contains("err")) {
					// not valid
				} else {
					JSONArray jArray = new JSONArray(result);
					Appconstants.productsList = new ArrayList<Product>();
					for (int i = 0; i < jArray.length(); i++) {
						JSONObject json_data = jArray.getJSONObject(i);
						Appconstants.productsList.add(new Product(Integer
								.parseInt(json_data.getString("product_id")
										.trim()), json_data.getString("model")
								.trim(),
								json_data.getString("location").trim(),
								Appconstants.ImageUrl
										+ json_data.getString("image").trim(),
								json_data.getString("date_available").trim(),
								Integer.parseInt(json_data.getString("status")
										.trim()), json_data.getString(
										"date_added").trim(), json_data
										.getString("product_name").trim(),
								json_data.getString("description").trim(),
								Integer.parseInt(json_data
										.getString("store_id").trim()),
								json_data.getString("store_name").trim(),
								json_data.getString("url").trim(), Float
										.parseFloat(json_data
												.getString("price").trim()),
								false));

					}

				}
			} catch (Exception e) {

				e.printStackTrace();
				return null;
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void params) {
			progressDialog.setVisibility(View.GONE);
			populateListView();

		}
	}

	private void populateListView() {
		if (Appconstants.productsList != null) {
			if (Appconstants.productsList.size() > 0) {
				productAdapter = new ProductAdapterVendors(getActivity(),
						false, Appconstants.productsList);
				listViewProducts.setAdapter(productAdapter);
				productAdapter.notifyDataSetChanged();
				listViewProducts
						.setOnItemClickListener(new OnItemClickListener() {

							@Override
							public void onItemClick(AdapterView<?> parent,
									View view, int position, long id) {
								ProductDetailFragmentVendor prodFrag = new ProductDetailFragmentVendor();
								Bundle bundle = new Bundle();
								bundle.putInt("position", position);
								prodFrag.setArguments(bundle);
								getFragmentManager()
										.beginTransaction()
										.replace(R.id.frame_container, prodFrag)
										.addToBackStack(null).commit();
							}
						});
			}

		}
	}

	private class ProductListSearchBT extends AsyncTask<Void, Void, Void> {
		String search;
		String result = "";

		ProductListSearchBT(String search) {
			this.search = search;
		}

		@Override
		protected void onPreExecute() {
			progressDialog.setVisibility(View.VISIBLE);
		}

		@Override
		protected Void doInBackground(Void... arg0) {
			try {

				ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("timestamp", ""
						+ System.currentTimeMillis()));
				params.add(new BasicNameValuePair("search", "" + search));

				//
				result = "";
				result = getJSONfromURL(Appconstants.Server
						+ "products_search.php", params, 0);
				Log.d("result", "--" + result);
				if (result.equalsIgnoreCase("") | result.contains("empty")
						| result.contains("err")) {
					// not valid
				} else {
					JSONArray jArray = new JSONArray(result);
					Appconstants.productsList.clear();

					for (int i = 0; i < jArray.length(); i++) {
						JSONObject json_data = jArray.getJSONObject(i);
						Appconstants.productsList.add(new Product(Integer
								.parseInt(json_data.getString("product_id")
										.trim()), json_data.getString("model")
								.trim(),
								json_data.getString("location").trim(),
								Appconstants.ImageUrl
										+ json_data.getString("image").trim(),
								json_data.getString("date_available").trim(),
								Integer.parseInt(json_data.getString("status")
										.trim()), json_data.getString(
										"date_added").trim(), json_data
										.getString("product_name").trim(),
								json_data.getString("description").trim(),
								Integer.parseInt(json_data
										.getString("store_id").trim()),
								json_data.getString("store_name").trim(),
								json_data.getString("url").trim(), Float
										.parseFloat(json_data
												.getString("price").trim()),
								false));

					}

				}
			} catch (Exception e) {

				e.printStackTrace();
				return null;
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void params) {
			progressDialog.setVisibility(View.GONE);
			if (result.equalsIgnoreCase("") | result.contains("empty")
					| result.contains("err")) {
				Toast.makeText(getActivity(), "No resut to show ",
						Toast.LENGTH_LONG).show();

			} else {
				populateListView();

			}

		}
	}

	// private class GetWishListBTTask extends AsyncTask<Void, Void, Void> {
	// int customer_id;
	//
	// public GetWishListBTTask() {
	// // TODO Auto-generated constructor stub
	// customer_id = Appconstants.user.getCustomer_id();
	// }
	//
	// @Override
	// protected Void doInBackground(Void... arg0) {
	//
	// ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	// nameValuePairs.add(new BasicNameValuePair("customer_id",
	// customer_id + ""));
	//
	// try {
	//
	// //
	// String result = "";
	// result = getJSONfromURL(Appconstants.Server
	// + "product_wishlist_get.php", nameValuePairs, 0);
	// Log.d("result", "--" + result);
	// ArrayList<Product> arrayWish = new ArrayList<Product>();
	// Appconstants.user.setWishlist(arrayWish);
	// if (result.equalsIgnoreCase("") | result.contains("empty")
	// | result.contains("err")) {
	// // not valid
	//
	// } else {
	// JSONArray jArray = new JSONArray(result);
	// for (int i = 0; i < jArray.length(); i++) {
	// JSONObject json_data = jArray.getJSONObject(i);
	// arrayWish.add(new Product(Integer.parseInt(json_data
	// .getString("product_id").trim()), json_data
	// .getString("model").trim(), json_data
	// .getString("location").trim(),
	// Appconstants.ImageUrl
	// + json_data.getString("image").trim(),
	// json_data.getString("date_available").trim(),
	// Integer.parseInt(json_data.getString("status")
	// .trim()), json_data.getString(
	// "date_added").trim(), json_data
	// .getString("product_name").trim(),
	// json_data.getString("description").trim(),
	// Integer.parseInt(json_data
	// .getString("store_id").trim()),
	// json_data.getString("store_name").trim(),
	// json_data.getString("url").trim(), Float
	// .parseFloat(json_data
	// .getString("price").trim()),
	// true));
	//
	// }
	// }
	// Appconstants.user.setWishlist(arrayWish);
	// } catch (Exception e) {
	//
	// e.printStackTrace();
	// return null;
	// }
	// return null;
	// }
	//
	// @Override
	// protected void onPostExecute(Void params) {
	// if (productAdapter != null) {
	// makeWishListLocal(Appconstants.productsList);
	// productAdapter.notifyDataSetChanged();
	// } else {
	// if (ProductListFragment.this.isVisible()) {
	// populateListView();
	// }
	// }
	// }
	// }

}
