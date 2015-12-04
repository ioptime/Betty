package com.iopitme.betty.vendor;

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

import com.ioptime.betty.Appconstants;
import com.ioptime.betty.R;
import com.ioptime.betty.model.Product;
import com.ioptime.betty.model.User;
import com.ioptime.betty.model.Vendor;
import com.ioptime.extendablelibrary.IoptimeFragment;

public class ProfileEditVendorFragment extends IoptimeFragment {
	EditText etFirstName, etLastName, etEmail, etTopic;
	Button editBTSignUp;
	ArrayList<EditText> editTextArray = new ArrayList<EditText>();
	ProgressDialog progressDialog;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.profile_edit_vendor,
				container, false);

		etFirstName = (EditText) rootView.findViewById(R.id.editETFName);
		editTextArray.add(etFirstName);
		etLastName = (EditText) rootView.findViewById(R.id.editETLName);
		editTextArray.add(etLastName);
		etEmail = (EditText) rootView.findViewById(R.id.editETEmail);
		editTextArray.add(etEmail);
		etTopic = (EditText) rootView.findViewById(R.id.editETTopic);
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

		etFirstName.setText("" + Appconstants.vendor.getFirstname());
		etLastName.setText("" + Appconstants.vendor.getLastname());
		etEmail.setText("" + Appconstants.vendor.getEmail());
		etTopic.setText("" + Appconstants.vendor.getTopic());

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
			nameValuePairs.add(new BasicNameValuePair("topic", etTopic
					.getText().toString()));
			nameValuePairs.add(new BasicNameValuePair("topic",
					Appconstants.vendor.getTelephone().toString()));
			nameValuePairs.add(new BasicNameValuePair("user_id",
					Appconstants.vendor.getUser_id() + ""));
			try {

				//
				result = "";
				result = getJSONfromURL(Appconstants.Server
						+ "vendor_profile_update.php", nameValuePairs, 0);
				Log.d("result", "--" + result);
				if (result.equalsIgnoreCase("") | result.contains("empty")
						| result.contains("err")) {
				} else {
					userid = 0;
					Appconstants.vendor = new Vendor();
					Appconstants.vendor.setUser_id(0);

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
										.getString("vendor_image").trim());
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
			if (Appconstants.vendor.getUser_id() != 0) {

				if (result.equalsIgnoreCase("") | result.contains("empty")
						| result.contains("err")) {
					Toast.makeText(getActivity(), "Error while updating",
							Toast.LENGTH_SHORT).show();

				} else {
					Toast.makeText(getActivity(), "Successful update",
							Toast.LENGTH_SHORT).show();

				}

				etEmail.setText("" + Appconstants.vendor.getEmail());
				etFirstName.setText("" + Appconstants.vendor.getFirstname());
				etLastName.setText("" + Appconstants.vendor.getLastname());
				etTopic.setText("" + Appconstants.vendor.getTopic());
			}

			progressDialog.cancel();

		}

	}

}
