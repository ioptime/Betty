package com.ioptime.betty;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

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
import com.ioptime.extendablelibrary.IoptimeFragment;

public class ContactFragment extends IoptimeFragment {

	EditText etName, etPhone, etIssue;
	ArrayList<EditText> editTextArray = new ArrayList<EditText>();
	ProgressDialog progressDialog;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.contact, container, false);
		etName = (EditText) rootView.findViewById(R.id.contactETName);
		editTextArray.add(etName);
		etName.setText(Appconstants.user.getFirstName() + " "
				+ Appconstants.user.getFirstName());
		etPhone = (EditText) rootView.findViewById(R.id.contactETPhone);
		editTextArray.add(etPhone);
		etPhone.setText(Appconstants.user.getTelephone());
		etIssue = (EditText) rootView.findViewById(R.id.contactETIssue);
		editTextArray.add(etIssue);
		Button btSend = (Button) rootView.findViewById(R.id.contactBtSend);
		btSend.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (inputAll()) {
					if (isConnectedToInternet(getActivity())) {
						new ContactBTTask().execute();
					} else {
						Toast.makeText(getActivity(),
								"Check your internet connection",
								Toast.LENGTH_SHORT).show();
					}

				}
			}
		});

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

	private class ContactBTTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
			progressDialog = ProgressDialog.show(getActivity(),
					"Please wait ...", "Submitting issue ...", true);
			progressDialog.setCancelable(true);
		}

		@Override
		protected Void doInBackground(Void... arg0) {

			ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("email",
					Appconstants.user.getEmail()));
			nameValuePairs.add(new BasicNameValuePair("name", etName.getText()
					.toString()));
			nameValuePairs.add(new BasicNameValuePair("phone", etPhone
					.getText().toString()));
			nameValuePairs.add(new BasicNameValuePair("message", etIssue
					.getText().toString()));
			try {

				//
				String result = "";
				result = getJSONfromURL(Appconstants.Server + "contact.php",
						nameValuePairs, 0);
				Log.d("result", "--" + result);
				if (result.equalsIgnoreCase("") | result.contains("empty")
						| result.contains("err")) {
					// not valid
				} else {
					// JSONArray jArray = new JSONArray(result);

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
			progressDialog.cancel();
			Toast.makeText(getActivity(), "Issue submitted", Toast.LENGTH_LONG)
					.show();

		}
	}
	@Override
	public void onResume() {
		super.onResume();
		((MainMenu) getActivity()).getActionBar().setTitle("Contact Us");
	}
}
