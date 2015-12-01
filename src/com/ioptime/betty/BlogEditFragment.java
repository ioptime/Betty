package com.ioptime.betty;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ioptime.extendablelibrary.IoptimeFragment;

 

public class BlogEditFragment extends IoptimeFragment {
	EditText etText, etIntroText, etTitle;
	Button btUpdate;
	ProgressDialog progressDialog;
	int blog_id;
	String title;
	String intro_text;
	String text;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		blog_id = getArguments().getInt("blog_id");
		text = getArguments().getString("text");
		intro_text = getArguments().getString("intro_text");
		title = getArguments().getString("title");

		View rootView = inflater.inflate(R.layout.blog_edit, container, false);

		etText = (EditText) rootView.findViewById(R.id.editblogEtText);
		etIntroText = (EditText) rootView.findViewById(R.id.editblogEtIntro);
		etTitle = (EditText) rootView.findViewById(R.id.editblogEtTitle);
		etText.setText("" + Html.fromHtml(text));
		etIntroText.setText("" + Html.fromHtml(intro_text));
		etTitle.setText("" + Html.fromHtml(title));
		btUpdate = (Button) rootView.findViewById(R.id.editblogBtUpdate);
		btUpdate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new BlogListBT().execute();
			}
		});

		return rootView;
	}

	private class BlogListBT extends AsyncTask<Void, Void, Void> {
		String result;

		@Override
		protected void onPreExecute() {
			progressDialog = ProgressDialog.show(getActivity(),
					"Please wait ...", "Updating ...", true);
			progressDialog.setCancelable(true);
		}

		@Override
		protected Void doInBackground(Void... arg0) {
			try {
				ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
				nameValuePairs.add(new BasicNameValuePair("blog_id", blog_id
						+ ""));
				// language id 1 for arabic and 2 for english , just using 2 now
				nameValuePairs.add(new BasicNameValuePair("language_id", "2"));
				nameValuePairs.add(new BasicNameValuePair("title", etTitle
						.getText().toString()));
				nameValuePairs.add(new BasicNameValuePair("intro_text",
						etIntroText.getText().toString()));
				nameValuePairs.add(new BasicNameValuePair("text", etText
						.getText().toString()));

				//
				result = "";
				result = getJSONfromURL(Appconstants.Server + "blog_edit.php",
						nameValuePairs, 0);
				Log.d("result", "--" + result);
				if (result.equalsIgnoreCase("") | result.contains("empty")
						| result.contains("err")) {
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
			if (result.contains("success")) {
				Toast.makeText(getActivity(), "Blog Updated",
						Toast.LENGTH_SHORT).show();
				MyBlogsFragment blogFrags = new MyBlogsFragment();
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
