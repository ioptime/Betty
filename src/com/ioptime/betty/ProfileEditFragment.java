package com.ioptime.betty;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.iopitme.betty.vendor.MainMenuVendor;
import com.ioptime.betty.model.Product;
import com.ioptime.betty.model.User;
import com.ioptime.extendablelibrary.IoptimeFragment;

public class ProfileEditFragment extends IoptimeFragment {
	EditText etFirstName, etLastName, etEmail, etTagLine;
	Button editBTSignUp;
	ArrayList<EditText> editTextArray = new ArrayList<EditText>();
	ProgressDialog progressDialog;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.profile_edit, container,
				false);

		etFirstName = (EditText) rootView.findViewById(R.id.editETFName);
		editTextArray.add(etFirstName);
		etLastName = (EditText) rootView.findViewById(R.id.editETLName);
		editTextArray.add(etLastName);
		etEmail = (EditText) rootView.findViewById(R.id.editETEmail);
		editTextArray.add(etEmail);
		etTagLine = (EditText) rootView.findViewById(R.id.editETTagLine);
		editBTSignUp = (Button) rootView.findViewById(R.id.editBTSignUp);

		editBTSignUp.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (inputAll()) {
					if (isConnectedToInternet(getActivity())) {
						new UpdateUserBT().execute();
					} else {
						Toast.makeText(getActivity(),
								"Please check your internet connectivity",
								Toast.LENGTH_LONG).show();
					}
				}
			}
		});

		etFirstName.setText("" + Appconstants.user.getFirstName());
		etLastName.setText("" + Appconstants.user.getLastname());
		etEmail.setText("" + Appconstants.user.getEmail());
		etTagLine.setText("" + Appconstants.user.getTopic());

		return rootView;
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

	private class UpdateUserBT extends AsyncTask<Void, Void, Void> {

		int userid = 0;
		String result;
		ArrayList<Product> productsTemp;

		@Override
		protected void onPreExecute() {
			progressDialog = ProgressDialog.show(getActivity(),
					"Please wait ...", "Updating profile ...", true);
			progressDialog.setCancelable(true);
		}

		@Override
		protected Void doInBackground(Void... arg0) {

			ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("firstname", etFirstName
					.getText().toString()));
			nameValuePairs.add(new BasicNameValuePair("lastname", etLastName
					.getText().toString()));
			nameValuePairs.add(new BasicNameValuePair("email", etEmail
					.getText().toString()));
			nameValuePairs.add(new BasicNameValuePair("address", etTagLine
					.getText().toString()));
			nameValuePairs.add(new BasicNameValuePair("customer_id",
					Appconstants.user.getCustomer_id() + ""));
			productsTemp = Appconstants.user.getWishlist();
			try {

				//
				result = "";
				result = getJSONfromURL(Appconstants.Server
						+ "customer_profile_update.php", nameValuePairs, 0);
				Log.d("result", "--" + result);
				if (result.equalsIgnoreCase("") | result.contains("empty")
						| result.contains("err")) {
				} else {
					userid = 0;
					Appconstants.user = new User();
					Appconstants.user.setCustomer_id(0);

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
			if (Appconstants.user.getCustomer_id() != 0) {

				if (result.equalsIgnoreCase("") | result.contains("empty")
						| result.contains("err")) {
					Toast.makeText(getActivity(), "Error while updating",
							Toast.LENGTH_SHORT).show();

				} else {
					Toast.makeText(getActivity(), "Successful update",
							Toast.LENGTH_SHORT).show();

				}

				etEmail.setText("" + Appconstants.user.getEmail());
				etFirstName.setText("" + Appconstants.user.getFirstName());
				etLastName.setText("" + Appconstants.user.getLastname());
				etTagLine.setText("" + Appconstants.user.getTopic());
				Appconstants.user.setWishlist(productsTemp);
			}

			progressDialog.cancel();

		}

	}
	@Override
	public void onResume() {
		super.onResume();
		((MainMenu) getActivity()).getActionBar().setTitle("Profile");
	}
}
