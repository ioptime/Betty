package com.iopitme.betty.vendor;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
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
import com.ioptime.extendablelibrary.IoptimeFragment;

public class BlogNewVendorFragments extends IoptimeFragment {
	EditText etText, etIntroText, etTitle;
	Button btUpdate;
	ProgressDialog progressDialog;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.blog_edit, container, false);

		etText = (EditText) rootView.findViewById(R.id.editblogEtText);
		etIntroText = (EditText) rootView.findViewById(R.id.editblogEtIntro);
		etTitle = (EditText) rootView.findViewById(R.id.editblogEtTitle);

		btUpdate = (Button) rootView.findViewById(R.id.editblogBtUpdate);
		btUpdate.setText("Create");
		btUpdate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new BlogNewBT().execute();
			}
		});

		return rootView;
	}

	private class BlogNewBT extends AsyncTask<Void, Void, Void> {
		String result;

		@Override
		protected void onPreExecute() {
			progressDialog = ProgressDialog.show(getActivity(),
					"Please wait ...", "Creating ...", true);
			progressDialog.setCancelable(true);
		}

		@Override
		protected Void doInBackground(Void... arg0) {
			try {
				ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
				nameValuePairs.add(new BasicNameValuePair("creator_id",
						Appconstants.vendor.getUser_id() + ""));
				// language id 1 for arabic and 2 for english , just using 2 now
				nameValuePairs.add(new BasicNameValuePair("language_id", "2"));
				// is_vendor 1 for vendor and 0 for customer, 0 as in customer
				nameValuePairs.add(new BasicNameValuePair("is_vendor", "1"));
				nameValuePairs.add(new BasicNameValuePair("title", etTitle
						.getText().toString()));
				nameValuePairs.add(new BasicNameValuePair("intro_text",
						etIntroText.getText().toString()));
				nameValuePairs.add(new BasicNameValuePair("text", etText
						.getText().toString()));

				//
				result = "";
				result = getJSONfromURL(
						Appconstants.Server + "blog_create.php",
						nameValuePairs, 0);
				Log.d("result", "--" + result);
				if (result.contains("empty") | result.contains("err")) {
					// not valid
				} else {
					JSONArray jArray = new JSONArray(result);
					for (int i = 0; i < jArray.length(); i++) {
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
			if (result.equalsIgnoreCase("")) {
				Toast.makeText(getActivity(), "Blog Created",
						Toast.LENGTH_SHORT).show();
				MyBlogsVendorFragment blogFrags = new MyBlogsVendorFragment();
				getFragmentManager().beginTransaction()
						.replace(android.R.id.tabcontent, blogFrags)
						.addToBackStack(null).commit();

			} else {
				Toast.makeText(getActivity(), "Update Failure",
						Toast.LENGTH_LONG).show();
			}
		}
	}
}
