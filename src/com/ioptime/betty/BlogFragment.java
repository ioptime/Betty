package com.ioptime.betty;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.iopitme.betty.vendor.MainMenuVendor;
import com.ioptime.adapters.BlogAdapter;
import com.ioptime.betty.model.Blog;
import com.ioptime.betty.model.Product;
import com.ioptime.extendablelibrary.IoptimeFragment;

public class BlogFragment extends IoptimeFragment {
	ImageView ivAdd;
	ListView listBLog;
	EditText blogEtSearch;
	Button blogBtSearch;
	ProgressBar blogProgressBar;

	public BlogFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.blog, container, false);
		listBLog = (ListView) rootView.findViewById(R.id.blogList);
		ivAdd = (ImageView) rootView.findViewById(R.id.blogIVAddNew);
		blogProgressBar = (ProgressBar) rootView
				.findViewById(R.id.blogProgressBar);
		ivAdd.setVisibility(View.GONE);
		if (Appconstants.blogArray.size() == 0) {
			if (isConnectedToInternet(getActivity())) {
				new BlogListBT().execute();
			} else {
				Toast.makeText(getActivity(), "Check your internet connection",
						Toast.LENGTH_LONG).show();
			}
		} else if (Appconstants.blogArray.size() > 0) {
			populateListView();
		}

		blogEtSearch = (EditText) rootView.findViewById(R.id.blogETSearch);

		blogEtSearch.addTextChangedListener(new TextWatcher() {

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

				if (blogEtSearch.getText().toString().equalsIgnoreCase("")) {
					new BlogListBT().execute();

				}
			}
		});

		blogBtSearch = (Button) rootView.findViewById(R.id.blogBtSearch);
		blogBtSearch.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (blogEtSearch.getText().toString().trim()
						.equalsIgnoreCase("")) {
					blogEtSearch.setError("Search field cannot be empty");
				} else {
					String search = blogEtSearch.getText().toString();
					new BlogListSearchBT(search).execute();

				} // TODO Auto-generated method stub

			}
		});

		return rootView;
	}

	public class BlogListBT extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
			blogProgressBar.setVisibility(View.VISIBLE);
		}

		@Override
		protected Void doInBackground(Void... arg0) {
			try {

				//
				String result = "";
				result = getJSONfromURL(Appconstants.Server + "blog_get.php",
						null, 0);
				Log.d("result", "--" + result);
				if (result.equalsIgnoreCase("")
						| result.equalsIgnoreCase("empty")) {
					// not valid
				} else {
					JSONArray jArray = new JSONArray(result);

					for (int i = 0; i < jArray.length(); i++) {
						JSONObject json_data = jArray.getJSONObject(i);
						Appconstants.blogArray
								.add(new Blog(Integer.parseInt(json_data
										.getString("blog_id")),
										Integer.parseInt(json_data
												.getString("status")),
										Integer.parseInt(json_data
												.getString("sort_order")),
										json_data.getString("create_time"),
										json_data.getString("update_time"),
										json_data.getString("date"),
										Integer.parseInt(json_data
												.getString("language_id")),
										json_data.getString("title"), json_data
												.getString("intro_text"),
										json_data.getString("text"), json_data
												.getString("meta_description"),
										json_data.getString("meta_keyword")));

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
			blogProgressBar.setVisibility(View.GONE);
			populateListView();
		}
	}

	public void populateListView() {
		if (Appconstants.blogArray.size() > 0) {
			BlogAdapter blogAdapter = new BlogAdapter(getActivity(),
					Appconstants.blogArray);
			listBLog.setAdapter(blogAdapter);
			blogAdapter.notifyDataSetChanged();
			listBLog.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					//
					BlogDetailFragments blogFrags = new BlogDetailFragments();
					Bundle bundle = new Bundle();
					bundle.putInt("position", position);
					blogFrags.setArguments(bundle);
					getFragmentManager().beginTransaction()
							.replace(R.id.frame_container, blogFrags)
							.addToBackStack(null).commit();
				}
			});
		} else {
			Toast.makeText(getActivity(), "No blogs to show at the moment",
					Toast.LENGTH_LONG).show();
		}
	}

	private class BlogListSearchBT extends AsyncTask<Void, Void, Void> {
		String search;
		String result = "";

		BlogListSearchBT(String search) {
			this.search = search;
		}

		@Override
		protected void onPreExecute() {
			blogProgressBar.setVisibility(View.VISIBLE);
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
				result = getJSONfromURL(
						Appconstants.Server + "blog_search.php", params, 0);
				Log.d("result", "--" + result);
				if (result.equalsIgnoreCase("")
						| result.equalsIgnoreCase("empty")) {
					// not valid
				} else {
					JSONArray jArray = new JSONArray(result);
					Appconstants.blogArray = new ArrayList<Blog>();
					for (int i = 0; i < jArray.length(); i++) {
						JSONObject json_data = jArray.getJSONObject(i);

						Appconstants.blogArray
								.add(new Blog(Integer.parseInt(json_data
										.getString("blog_id")),
										Integer.parseInt(json_data
												.getString("status")),
										Integer.parseInt(json_data
												.getString("sort_order")),
										json_data.getString("create_time"),
										json_data.getString("update_time"),
										json_data.getString("date"),
										Integer.parseInt(json_data
												.getString("language_id")),
										json_data.getString("title"), json_data
												.getString("intro_text"),
										json_data.getString("text"), json_data
												.getString("meta_description"),
										json_data.getString("meta_keyword")));

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
			blogProgressBar.setVisibility(View.GONE);
			populateListView();
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		((MainMenu) getActivity()).getActionBar().setTitle("Blog");
	}
}
