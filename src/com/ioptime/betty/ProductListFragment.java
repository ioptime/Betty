package com.ioptime.betty;

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
import android.widget.TableLayout;
import android.widget.Toast;

import com.iopitme.betty.vendor.MainMenuVendor;
import com.ioptime.adapters.ProductAdapter;
import com.ioptime.betty.model.Product;
import com.ioptime.extendablelibrary.IoptimeFragment;

public class ProductListFragment extends IoptimeFragment {
	ProgressBar progressDialog;
	EditText pdEtSearch;
	Button pdBtSearch;
	ListView listViewProducts;
	ProductAdapter productAdapter;
	boolean wish = false;

	public ProductListFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (getArguments() != null && getArguments().containsKey("wish"))
			wish = getArguments().getBoolean("wish");

		View rootView = inflater.inflate(R.layout.product, container, false);
		listViewProducts = (ListView) rootView.findViewById(R.id.productList);
		TableLayout tl = (TableLayout) rootView.findViewById(R.id.tl);
		progressDialog = (ProgressBar) rootView
				.findViewById(R.id.productProgressBar);
		tl.setVisibility(View.VISIBLE);
		if (Appconstants.productsList.size() == 0) {
			Log.d("pop", "size-" + Appconstants.productsList.size());
			new ProductListBT().execute();
		} else {
			populateListView();
			new GetWishListBTTask().execute();
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

				//
				result = "";
				result = getJSONfromURL(Appconstants.Server
						+ "products_get.php", null, 0);
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
			new GetWishListBTTask().execute();

		}
	}

	private void populateListView() {
		if (Appconstants.productsList != null) {
			if (Appconstants.productsList.size() > 0) {
				if (!wish) {
					productAdapter = new ProductAdapter(getActivity(), wish,
							Appconstants.productsList);
				} else if (wish) {
					productAdapter = new ProductAdapter(getActivity(), wish,
							Appconstants.user.getWishlist());

				}
				listViewProducts.setAdapter(productAdapter);
				productAdapter.notifyDataSetChanged();
				listViewProducts
						.setOnItemClickListener(new OnItemClickListener() {

							@Override
							public void onItemClick(AdapterView<?> parent,
									View view, int position, long id) {
								if (!wish) {
									ProductDetailFragment prodFrag = new ProductDetailFragment();
									Bundle bundle = new Bundle();
									bundle.putInt("position", position);
									prodFrag.setArguments(bundle);
									getFragmentManager()
											.beginTransaction()
											.replace(R.id.frame_container,
													prodFrag)
											.addToBackStack("CustPList")
											.commit();
								} else if (wish) {
									ProductDetailFragment prodFrag = new ProductDetailFragment();
									Bundle bundle = new Bundle();
									bundle.putInt("position", position);
									prodFrag.setArguments(bundle);
									getFragmentManager()
											.beginTransaction()
											.replace(android.R.id.tabcontent,
													prodFrag)
											.addToBackStack("CustPList")
											.commit();

								}
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
				new GetWishListBTTask().execute();

			}

		}
	}

	private class GetWishListBTTask extends AsyncTask<Void, Void, Void> {
		int customer_id;

		public GetWishListBTTask() {
			// TODO Auto-generated constructor stub
			customer_id = Appconstants.user.getCustomer_id();
		}

		@Override
		protected Void doInBackground(Void... arg0) {

			ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("customer_id",
					customer_id + ""));

			try {

				//
				String result = "";
				result = getJSONfromURL(Appconstants.Server
						+ "product_wishlist_get.php", nameValuePairs, 0);
				Log.d("result", "--" + result);
				ArrayList<Product> arrayWish = new ArrayList<Product>();
				Appconstants.user.setWishlist(arrayWish);
				if (result.equalsIgnoreCase("") | result.contains("empty")
						| result.contains("err")) {
					// not valid

				} else {
					JSONArray jArray = new JSONArray(result);
					for (int i = 0; i < jArray.length(); i++) {
						JSONObject json_data = jArray.getJSONObject(i);
						arrayWish.add(new Product(Integer.parseInt(json_data
								.getString("product_id").trim()), json_data
								.getString("model").trim(), json_data
								.getString("location").trim(),
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
								true));

					}
				}
				Appconstants.user.setWishlist(arrayWish);
			} catch (Exception e) {

				e.printStackTrace();
				return null;
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void params) {
			if (productAdapter != null) {
				makeWishListLocal(Appconstants.productsList);
				productAdapter.notifyDataSetChanged();
			} else {
				if (ProductListFragment.this.isVisible()) {
					populateListView();
				}
			}
		}
	}

	private void makeWishListLocal(ArrayList<Product> productsList) {
		// making everything false and setting again

		if (Appconstants.user.getWishlist().size() > 0) {
			for (int i = 0; i < productsList.size(); i++) {
				productsList.get(i).setInWIsh(false);

				for (int j = 0; j < Appconstants.user.getWishlist().size(); j++) {
					if (productsList.get(i).getProductId() == Appconstants.user
							.getWishlist().get(j).getProductId()) {
						productsList.get(i).setInWIsh(true);
						break;
					}
				}
			}
		}

	}
	@Override
	public void onResume() {
		super.onResume();
		((MainMenu) getActivity()).getActionBar().setTitle("Products");
	}
}
