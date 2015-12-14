package com.ioptime.betty;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.iopitme.betty.vendor.MainMenuVendor;
import com.ioptime.betty.model.User;
import com.ioptime.betty.model.Vendor;
import com.ioptime.extendablelibrary.IoptimeActivity;

public class Login extends IoptimeActivity {

	EditText etPass, etEmail;
	ArrayList<EditText> editTextArray = new ArrayList<EditText>();
	ProgressDialog progressDialog;
	ToggleButton tbCustOrVend;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		etPass = (EditText) findViewById(R.id.loginETPass);
		editTextArray.add(etPass);
		etEmail = (EditText) findViewById(R.id.loginETEmail);
		editTextArray.add(etEmail);

		tbCustOrVend = (ToggleButton) findViewById(R.id.loginTBCustOrVend);
		tbCustOrVend.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (tbCustOrVend.getText().toString()
						.equalsIgnoreCase("Customer")) {
					etPass.setText("qwert");
					etEmail.setText("mharoon");
				}
				if (tbCustOrVend.getText().toString()
						.equalsIgnoreCase("Vendor")) {
					etPass.setText("123456");
					etEmail.setText("rasheed");
				}
			}
		});

	}

	@Override
	protected int getLayoutResourceId() {
		// TODO Auto-generated method stub
		return R.layout.login;
	}

	public void login(View v) {
		if (inputAll()) {
			if (isConnectedToInternet(Login.this)) {
				if (tbCustOrVend.getText().toString()
						.equalsIgnoreCase("Customer")) {

					new LoginUserBT().execute();
				} else if (tbCustOrVend.getText().toString()
						.equalsIgnoreCase("Vendor")) {
					new LoginVendorBT().execute();

				}

			} else {
				Toast.makeText(getApplicationContext(),
						"Check your internet connection", Toast.LENGTH_LONG)
						.show();
			}

		} else {

		}
	}

	public boolean inputAll() {
		for (EditText edit : editTextArray) {
			if (TextUtils.isEmpty(edit.getText().toString().trim())) {
				edit.setError(edit.getHint() + " can not be empty");
				return false;
			} else {
				edit.setError(null, null);
			}

		}
		return true;
	}

	private class LoginUserBT extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
			progressDialog = ProgressDialog.show(Login.this, "Please wait ...",
					"Logging in ...", true);
			progressDialog.setCancelable(true);
		}

		@Override
		protected Void doInBackground(Void... arg0) {

			ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("email", etEmail
					.getText().toString()));
			nameValuePairs.add(new BasicNameValuePair("password", etPass
					.getText().toString()));
			nameValuePairs.add(new BasicNameValuePair("ip",
					getIPofDevicePublic()));

			try {

				//
				String result = "";
				result = getJSONfromURL(Appconstants.Server
						+ "login_customer.php", nameValuePairs, 0);
				Log.d("result", "--" + result);
				if (result.equalsIgnoreCase("") | result.contains("empty")
						| result.contains("err")) {
					// not valid
					Appconstants.user = new User();
					Appconstants.user.setCustomer_id(0);
				} else {
					JSONArray jArray = new JSONArray(result);

					for (int i = 0; i < jArray.length(); i++) {
						JSONObject json_data = jArray.getJSONObject(i);
						Appconstants.user = new User(Integer.parseInt(json_data
								.getString("customer_id").trim()),
								Integer.parseInt(json_data
										.getString("store_id").trim()),
								json_data.getString("firstname").trim(),
								json_data.getString("lastname").trim(),
								json_data.getString("email").trim(), json_data
										.getString("telephone").trim(),
								json_data.getString("fax").trim(), json_data
										.getString("cart").trim(), null, null,
								json_data.getString("newsletter").trim(),
								Integer.parseInt(json_data.getString(
										"address_id").trim()),
								Integer.parseInt(json_data.getString(
										"customer_group_id").trim()), json_data
										.getString("ip").trim(),
								Integer.parseInt(json_data.getString("status")
										.trim()), Integer.parseInt(json_data
										.getString("approved").trim()),
								json_data.getString("logo").trim(), json_data
										.getString("pay_status").trim(),
								json_data.getString("topic").trim());

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
			if (progressDialog.isShowing()) {
				progressDialog.cancel();
			}
			if (Appconstants.user.getCustomer_id() != 0) {
				savePref(Appconstants.user.getCustomer_id());
				Toast.makeText(getApplicationContext(), "Successful login",
						Toast.LENGTH_LONG).show();
				startActivitySwipe(Login.this, new Intent(Login.this,
						MainMenu.class));
				finish();
			} else {
				Toast.makeText(getApplicationContext(),
						"Error while login in, try again", Toast.LENGTH_LONG)
						.show();

			}
		}
	}

	private class LoginVendorBT extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
			progressDialog = ProgressDialog.show(Login.this, "Please wait ...",
					"Logging in ...", true);
			progressDialog.setCancelable(true);
		}

		@Override
		protected Void doInBackground(Void... arg0) {

			ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("username", etEmail
					.getText().toString()));
			nameValuePairs.add(new BasicNameValuePair("password", etPass
					.getText().toString()));
			nameValuePairs.add(new BasicNameValuePair("ip",
					getIPofDevicePublic()));

			try {

				//
				String result = "";
				result = getJSONfromURL(Appconstants.Server
						+ "login_vendor_admin.php", nameValuePairs, 0);
				Log.d("result", "--Vender--" + result);
				if (result.equalsIgnoreCase("")
						| result.equalsIgnoreCase("empty")) {
					// not valid
					Appconstants.vendor = new Vendor();
					Appconstants.vendor.setUser_id(0);
				} else {
					JSONArray jArray = new JSONArray(result);

					for (int i = 0; i < jArray.length(); i++) {
						JSONObject json_data = jArray.getJSONObject(i);
						Appconstants.vendor = new Vendor(
								Integer.parseInt(json_data.getString("user_id")
										.trim()), json_data.getString(
										"firstname").trim(), json_data
										.getString("lastname").trim(),
								json_data.getString("email").trim(), json_data
										.getString("telephone").trim(),
								json_data.getString("topic").trim(), json_data
										.getString("vendor_image").trim(),
								json_data.getString("username".trim()));
						Log.d("vederID",
								""
										+ Integer.parseInt(json_data.getString(
												"user_id").trim()));

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
			if (progressDialog.isShowing()) {
				progressDialog.cancel();
			}

			if (Appconstants.vendor.getUser_id() != 0) {
				savePref(Appconstants.vendor.getUser_id());
				Toast.makeText(getApplicationContext(), "Successful login",
						Toast.LENGTH_LONG).show();
				startActivitySwipe(Login.this, new Intent(Login.this,
						MainMenuVendor.class));
				Appconstants.isVendor = true;
				finish();
			} else {
				Toast.makeText(getApplicationContext(),
						"Error while login in, try again", Toast.LENGTH_LONG)
						.show();

			}
		}
	}

	private void savePref(int userid) {
		SharedPreferences settings;
		settings = getSharedPreferences(Appconstants.PREFNAME,
				Context.MODE_PRIVATE);
		// set the sharedpref
		Editor editor = settings.edit();
		editor.putInt("userid", userid);
		editor.putString("email", etEmail.getText().toString());
		editor.commit();

	}

}
