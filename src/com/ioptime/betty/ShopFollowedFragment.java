package com.ioptime.betty;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ioptime.adapters.ShopFollowedAdapter;
import com.ioptime.betty.model.Shops;
import com.ioptime.extendablelibrary.IoptimeFragment;

public class ShopFollowedFragment extends IoptimeFragment {
	ListView listViewShop;
	boolean wish = false;
	ArrayList<Shops> arrayFollowd = new ArrayList<Shops>();
	ProgressBar shopsProgressBar;

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
		new GetShopsListFollowedBTTask().execute();
		return rootView;
	}

	private void populateListView() {
		if (arrayFollowd.size() > 0) {
			ShopFollowedAdapter catAdapter;
			catAdapter = new ShopFollowedAdapter(getActivity(), arrayFollowd,
					wish);
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
					bundle.putInt("id", arrayFollowd.get(position).getId());
					bundle.putString("name", arrayFollowd.get(position)
							.getName());
					subCatFrag.setArguments(bundle);
					getFragmentManager().beginTransaction()
							.replace(android.R.id.tabcontent, subCatFrag)
							.addToBackStack(null).commit();
				}
			});
		}

		else {

			Toast.makeText(getActivity(), "No shops followed",
					Toast.LENGTH_SHORT).show();

		}
	}

	private class GetShopsListFollowedBTTask extends
			AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
		}

		@Override
		protected Void doInBackground(Void... arg0) {
			try {
				ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
				nameValuePairs.add(new BasicNameValuePair("customer_id",
						Appconstants.user.getCustomer_id() + ""));

				String result = "";
				result = getJSONfromURL(Appconstants.Server
						+ "stores_followed_by_customer.php", nameValuePairs, 0);
				Log.d("result", "--" + result);
				if (result.equalsIgnoreCase("") | result.contains("empty")
						| result.contains("err")) {
					// not valid
				} else {
					arrayFollowd = new ArrayList<Shops>();
					JSONArray jArray = new JSONArray(result);
					for (int i = 0; i < jArray.length(); i++) {
						JSONObject json_data = jArray.getJSONObject(i);
						arrayFollowd.add(new Shops(Integer.parseInt(json_data
								.getString("store_id").trim()), (json_data
								.getString("name").trim()), (json_data
								.getString("url").trim()), json_data.getString(
								"ssl").trim(), true));

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
			populateListView();
			shopsProgressBar.setVisibility(View.GONE);
		}
	}
}