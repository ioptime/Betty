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
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ioptime.adapters.ShopAdapter;
import com.ioptime.betty.model.Product;
import com.ioptime.betty.model.Shops;
import com.ioptime.extendablelibrary.IoptimeFragment;

public class ShopFragment extends IoptimeFragment {
	ListView listViewShop;
	boolean wish = false;
	ArrayList<Shops> shopArray = new ArrayList<Shops>();
	ProgressBar shopsProgressBar;
	EditText shopEtSearch;
	Button shopBtSearch;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (getArguments() != null && getArguments().containsKey("wish"))
			wish = getArguments().getBoolean("wish");

		View rootView = inflater.inflate(R.layout.shops, container, false);
		listViewShop = (ListView) rootView.findViewById(R.id.shopList);
		shopsProgressBar = (ProgressBar) rootView
				.findViewById(R.id.shopsProgressBar);
		shopsProgressBar.setVisibility(View.VISIBLE);
		new GetShopsListBTTask().execute();
		shopEtSearch = (EditText) rootView.findViewById(R.id.shopsETSearch);

		shopEtSearch.addTextChangedListener(new TextWatcher() {

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

				if (shopEtSearch.getText().toString().equalsIgnoreCase("")) {
					new GetShopsListBTTask().execute();

				}
			}
		});

		shopBtSearch = (Button) rootView.findViewById(R.id.shopsBtSearch);
		shopBtSearch.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (shopEtSearch.getText().toString().trim()
						.equalsIgnoreCase("")) {
					shopEtSearch.setError("Search field cannot be empty");
				} else {
					String search = shopEtSearch.getText().toString();
					new ShopListSearchBT(search).execute();

				}
			}
		});

		return rootView;
	}

	private void populateListView() {
		if (shopArray.size() > 0) {
			ShopAdapter catAdapter;

			catAdapter = new ShopAdapter(getActivity(), shopArray, wish);
			listViewShop.setAdapter(catAdapter);
			catAdapter.notifyDataSetChanged();
			listViewShop.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					//
					// Open the products of the store from clicking here
					ShopProductsFragment subCatFrag = new ShopProductsFragment();
					Bundle bundle = new Bundle();
					bundle.putInt("id", shopArray.get(position).getId());
					bundle.putString("name", shopArray.get(position).getName());

					subCatFrag.setArguments(bundle);
					getFragmentManager().beginTransaction()
							.replace(R.id.frame_container, subCatFrag)
							.addToBackStack(null).commit();
				}
			});
		}

		else {

		}
	}

	private class GetShopsListBTTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
		}

		@Override
		protected Void doInBackground(Void... arg0) {
			try {

				//
				String result = "";
				result = getJSONfromURL(Appconstants.Server + "store_get.php",
						null, 0);
				Log.d("result", "--" + result);
				if (result.equalsIgnoreCase("") | result.contains("empty")
						| result.contains("err")) {
					// not valid
				} else {
					shopArray = new ArrayList<Shops>();
					JSONArray jArray = new JSONArray(result);
					for (int i = 0; i < jArray.length(); i++) {
						JSONObject json_data = jArray.getJSONObject(i);
						shopArray.add(new Shops(Integer.parseInt(json_data
								.getString("store_id").trim()), (json_data
								.getString("name").trim()), (json_data
								.getString("url").trim()), json_data.getString(
								"ssl").trim(), false));

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
			new GetFollowStoreListBTTask(Appconstants.user.getCustomer_id())
					.execute();
		}
	}

	private class GetFollowStoreListBTTask extends AsyncTask<Void, Void, Void> {
		int customer_id;

		public GetFollowStoreListBTTask(int cust_id) {
			// TODO Auto-generated constructor stub
			customer_id = cust_id;
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
						+ "stores_followed_by_customer.php", nameValuePairs, 0);
				Log.d("result", "--" + result);
				ArrayList<Shops> arrayFollow = new ArrayList<Shops>();
				Appconstants.user.setFollowList(arrayFollow);
				if (result.equalsIgnoreCase("") | result.contains("empty")
						| result.contains("err")) {
					// not valid

				} else {
					JSONArray jArray = new JSONArray(result);
					for (int i = 0; i < jArray.length(); i++) {
						JSONObject json_data = jArray.getJSONObject(i);
						arrayFollow.add(new Shops(Integer.parseInt(json_data
								.getString("store_id").trim()), (json_data
								.getString("name").trim()), (json_data
								.getString("url").trim()), json_data.getString(
								"ssl").trim(), true));

					}
				}
				Appconstants.user.setFollowList(arrayFollow);
			} catch (Exception e) {

				e.printStackTrace();
				return null;
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void params) {
			shopsProgressBar.setVisibility(View.GONE);
			populateListView();
		}
	}

	private class ShopListSearchBT extends AsyncTask<Void, Void, Void> {
		String search;
		String result = "";

		ShopListSearchBT(String search) {
			this.search = search;
		}

		@Override
		protected void onPreExecute() {
			shopsProgressBar.setVisibility(View.VISIBLE);
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
						+ "store_search.php", params, 0);
				Log.d("result", "--" + result);
				if (result.equalsIgnoreCase("") | result.contains("empty")
						| result.contains("err")) {
					// not valid
				} else {
					shopArray = new ArrayList<Shops>();
					JSONArray jArray = new JSONArray(result);
					for (int i = 0; i < jArray.length(); i++) {
						JSONObject json_data = jArray.getJSONObject(i);
						shopArray.add(new Shops(Integer.parseInt(json_data
								.getString("store_id").trim()), (json_data
								.getString("name").trim()), (json_data
								.getString("url").trim()), json_data.getString(
								"ssl").trim(), false));

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
			shopsProgressBar.setVisibility(View.GONE);
			if (result.equalsIgnoreCase("") | result.contains("empty")
					| result.contains("err")) {
				Toast.makeText(getActivity(), "No resut to show ",
						Toast.LENGTH_LONG).show();

			} else {
				new GetFollowStoreListBTTask(Appconstants.user.getCustomer_id())
						.execute();

			}

		}
	}

}
