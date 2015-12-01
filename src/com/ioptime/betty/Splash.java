package com.ioptime.betty;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.ioptime.betty.model.Category;
import com.ioptime.betty.model.CompanyM;
import com.ioptime.betty.model.WorldCountries;
import com.ioptime.extendablelibrary.IoptimeActivity;

public class Splash extends IoptimeActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (isConnectedToInternet(Splash.this)) {
			new CategoryListBT().execute();
		} else {
			finish();
			Toast.makeText(getApplicationContext(),
					"Please check your internet connectivity",
					Toast.LENGTH_LONG).show();
		}
	}

	@Override
	protected int getLayoutResourceId() {
		// TODO Auto-generated method stub
		return R.layout.splash;
	}

	private class GetCountriesBTTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {

		}

		@Override
		protected Void doInBackground(Void... arg0) {
			String result = getJSONfromURL(Appconstants.Server
					+ "country_get.php", null, 0);
			Log.d("result", "--" + result);
			Appconstants.world = new ArrayList<WorldCountries>();
			// Create an array to populate the spinner
			Appconstants.worldlist = new ArrayList<String>();
			JSONArray jArray;
			try {
				jArray = new JSONArray(result);

				for (int i = 0; i < jArray.length(); i++) {
					JSONObject json_data = jArray.getJSONObject(i);
					Appconstants.world.add(new WorldCountries(
							Integer.parseInt(json_data.getString("country_id")
									.trim()), json_data.getString("name")
									.trim(), json_data.getString("iso_code_3")
									.trim(), json_data.getString(
									"address_format").trim(), Integer
									.parseInt(json_data.getString(
											"postcode_required").trim()),
							Integer.parseInt(json_data.getString("status")
									.trim())));
					Appconstants.worldlist.add(json_data.getString("name")
							.trim());
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void params) {
			startActivitySwipe(Splash.this, new Intent(Splash.this,
					MainActivity.class));
			finish();

		}

	}

	public class CategoryListBT extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
		}

		@Override
		protected Void doInBackground(Void... arg0) {
			try {

				//
				String result = "";
				result = getJSONfromURL(Appconstants.Server
						+ "category_get.php", null, 0);
				Log.d("result", "--" + result);
				if (result.equalsIgnoreCase("") | result.contains("empty")
						| result.contains("err")) {
					// not valid
				} else {
					JSONArray jArray = new JSONArray(result);
					for (int i = 0; i < jArray.length(); i++) {
						JSONObject json_data = jArray.getJSONObject(i);
						Appconstants.categoryList.add(new Category(Integer
								.parseInt(json_data.getString("category_id")
										.trim()), Appconstants.ImageUrl
								+ json_data.getString("image").trim(), Integer
								.parseInt(json_data.getString("parent_id")
										.trim()), Integer.parseInt(json_data
								.getString("top").trim()),
								Integer.parseInt(json_data.getString("column")
										.trim()), Integer.parseInt(json_data
										.getString("sort_order").trim()),
								Integer.parseInt(json_data.getString("status")
										.trim()), json_data.getString(
										"date_added").trim(), json_data
										.getString("date_modified").trim(),
								Integer.parseInt(json_data.getString(
										"language_id").trim()), json_data
										.getString("name").trim(), json_data
										.getString("description").trim(),
								json_data.getString("meta_description").trim(),
								json_data.getString("meta_keyword").trim()));

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
			new MessageNameBT().execute();
		}
	}

	private class MessageNameBT extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
		}

		@Override
		protected Void doInBackground(Void... arg0) {
			try {

				//
				ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
				nameValuePairs.add(new BasicNameValuePair("timestamp", ""
						+ System.currentTimeMillis()));
				String result = "";
				result = getJSONfromURL(Appconstants.Server
						+ "store_name_get_send_message_vendor.php",
						nameValuePairs, 0);
				Log.d("resultVendor", "--" + result);
				if (result.equalsIgnoreCase("") | result.contains("empty")
						| result.contains("err")) {
					// not valid
				} else {
					JSONArray jArray = new JSONArray(result);
					for (int i = 0; i < jArray.length(); i++) {
						JSONObject json_data = jArray.getJSONObject(i);
						Appconstants.messageNamesArray.add(new CompanyM(Integer
								.parseInt(json_data.getString("store_id")
										.trim()), json_data
								.getString("store_name"),
								Integer.parseInt(json_data.getString("user_id")
										.trim())));
						Appconstants.vendors = i;
					}

					// calling for customer names
					result = getJSONfromURL(Appconstants.Server
							+ "company_name_get_send_message_customer.php",
							nameValuePairs, 0);
					Log.d("resultCustomer", "--" + result);
					if (result.equalsIgnoreCase("") | result.contains("empty")
							| result.contains("err")) {
						// not valid
					} else {
						jArray = new JSONArray(result);
						for (int i = 0; i < jArray.length(); i++) {
							JSONObject json_data = jArray.getJSONObject(i);
							Appconstants.messageNamesArray.add(new CompanyM(0,
									json_data.getString("company"), Integer
											.parseInt(json_data.getString(
													"customer_id").trim())));
							Appconstants.customers = i;
						}

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
			new GetCountriesBTTask().execute();

		}
	}

}
