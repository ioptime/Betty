package com.ioptime.betty;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.iopitme.betty.vendor.MainMenuVendor;
import com.ioptime.adapters.ShopProductAdapter;
import com.ioptime.betty.model.Product;
import com.ioptime.extendablelibrary.IoptimeFragment;

public class ShopProductsFragment extends IoptimeFragment {
	ProgressDialog progressDialog;
	int id;
	String name;
	ListView listViewCategories;
	TextView tvFollowers, tvName;
	public ArrayList<Product> productList = new ArrayList<Product>();

	public ShopProductsFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		id = getArguments().getInt("id");
		name = getArguments().getString("name");

		View rootView = inflater.inflate(R.layout.shop_products, container,
				false);
		listViewCategories = (ListView) rootView
				.findViewById(R.id.shopProductsList);

		tvFollowers = (TextView) rootView
				.findViewById(R.id.shopProductTVShopFollowers);
		tvName = (TextView) rootView.findViewById(R.id.shopProductTVShopName);
		tvName.setText("" + name);

		new ProductListBT().execute();
		new FollowersBT().execute();
		// }
		return rootView;
	}

	private class ProductListBT extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
			progressDialog = ProgressDialog.show(getActivity(),
					"Please wait ...", "Getting products ...", true);
			progressDialog.setCancelable(true);
		}

		@Override
		protected Void doInBackground(Void... arg0) {
			try {
				ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("store_id", "" + id));//
				String result = "";
				result = getJSONfromURL(Appconstants.Server
						+ "products_get_by_store.php", params, 0);
				Log.d("result", "--" + result);
				if (result.equalsIgnoreCase("") | result.contains("empty")
						| result.contains("err")) {
					// not valid
				} else {
					productList = new ArrayList<Product>();
					JSONArray jArray = new JSONArray(result);
					for (int i = 0; i < jArray.length(); i++) {
						JSONObject json_data = jArray.getJSONObject(i);
						productList.add(new Product(Integer.parseInt(json_data
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
			progressDialog.cancel();
			populateListView();
		}
	}

	private class FollowersBT extends AsyncTask<Void, Void, Void> {
		int followers = 0;

		@Override
		protected Void doInBackground(Void... arg0) {
			try {
				ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("store_id", "" + id));//
				String result = "";
				result = getJSONfromURL(Appconstants.Server
						+ "number_of_followers_store.php", params, 0);
				Log.d("result", "--" + result);
				if (result.equalsIgnoreCase("") | result.contains("empty")
						| result.contains("err")) {
					// not valid
				} else {
					JSONArray jArray = new JSONArray(result);
					for (int i = 0; i < jArray.length(); i++) {
						JSONObject json_data = jArray.getJSONObject(i);
						followers = Integer.parseInt(json_data.getString(
								"number_of_followers").trim());

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
			tvFollowers.setText(followers + " Followers");
		}
	}

	public void populateListView() {
		if (productList.size() > 0) {
			ShopProductAdapter catAdapter = new ShopProductAdapter(
					getActivity(), productList);
			listViewCategories.setAdapter(catAdapter);
			catAdapter.notifyDataSetChanged();
			listViewCategories
					.setOnItemClickListener(new OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> parent,
								View view, int position, long id) {
							//
							ProductDetailFragment prodFrag = new ProductDetailFragment();
							Bundle bundle = new Bundle();
							bundle.putInt("position", position);
							prodFrag.setArguments(bundle);
							getFragmentManager().beginTransaction()
									.replace(R.id.frame_container, prodFrag)
									.addToBackStack(null).commit();
						}
					});
		} else {

			Toast.makeText(getActivity(), "There are no products to show",
					Toast.LENGTH_LONG).show();

		}
	}

	@Override
	public void onResume() {
		super.onResume();
		((MainMenu) getActivity()).getActionBar().setTitle(
				"Shop Products");
	}
}
