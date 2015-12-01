package com.iopitme.betty.vendor;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.ioptime.adapters.BlogAdapter;
import com.ioptime.betty.Appconstants;
import com.ioptime.betty.R;
import com.ioptime.betty.model.Blog;
import com.ioptime.extendablelibrary.IoptimeFragment;

public class MyBlogsVendorFragment extends IoptimeFragment {
	ProgressDialog progressDialog;
	ListView listBlog;
	ArrayList<Blog> blogArray;
	static String TAG = "MYBLOGFRAGMENT";
	ImageView ivAdd;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.blog, container, false);

		listBlog = (ListView) rootView.findViewById(R.id.blogList);
		ivAdd = (ImageView) rootView.findViewById(R.id.blogIVAddNew);
		ivAdd.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				BlogNewVendorFragments blogFrags = new BlogNewVendorFragments();
				Bundle bundle = new Bundle();
				blogFrags.setArguments(bundle);
				getFragmentManager().beginTransaction()
						.replace(android.R.id.tabcontent, blogFrags)
						.addToBackStack(null).commit();

			}
		});

		if (isConnectedToInternet(getActivity())) {
			new BlogListBT().execute();
		} else {
			Toast.makeText(getActivity(), "Check your internet connection",
					Toast.LENGTH_LONG).show();
		}

		return rootView;
	}

	public class BlogListBT extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
			progressDialog = ProgressDialog.show(getActivity(),
					"Please wait ...", "Getting blogs ...", true);
			progressDialog.setCancelable(true);
		}

		@Override
		protected Void doInBackground(Void... arg0) {
			try {
				ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
				nameValuePairs.add(new BasicNameValuePair("vendor_id",
						Appconstants.vendor.getUser_id() + ""));

				//
				String result = "";
				result = getJSONfromURL(Appconstants.Server
						+ "blog_get_vendor.php", nameValuePairs, 0);
				Log.d("result", "--" + result);
				blogArray = new ArrayList<Blog>();

				if (result.equalsIgnoreCase("")
						| result.equalsIgnoreCase("empty")) {
					// not valid
				} else {
					JSONArray jArray = new JSONArray(result);
					for (int i = 0; i < jArray.length(); i++) {
						JSONObject json_data = jArray.getJSONObject(i);
						blogArray.add(new Blog(Integer.parseInt(json_data
								.getString("blog_id")), Integer
								.parseInt(json_data.getString("status")),
								Integer.parseInt(json_data
										.getString("sort_order")), json_data
										.getString("create_time"), json_data
										.getString("update_time"), json_data
										.getString("date"), Integer
										.parseInt(json_data
												.getString("language_id")),
								json_data.getString("title"), json_data
										.getString("intro_text"), json_data
										.getString("text"), json_data
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
			progressDialog.cancel();
			populateListView();
		}
	}

	public void populateListView() {
		if (blogArray.size() > 0) {
			BlogAdapter blogAdapter = new BlogAdapter(getActivity(), blogArray);
			listBlog.setAdapter(blogAdapter);
			blogAdapter.notifyDataSetChanged();
			listBlog.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					//
					BlogEditFragment blogFrags = new BlogEditFragment();
					Bundle bundle = new Bundle();
					bundle.putInt("blog_id", blogArray.get(position)
							.getBlog_id());
					bundle.putString("title", blogArray.get(position)
							.getTitle());
					bundle.putString("intro_text", blogArray.get(position)
							.getIntro_text());
					bundle.putString("text", blogArray.get(position).getText());
					blogFrags.setArguments(bundle);
					getFragmentManager().beginTransaction()
							.replace(android.R.id.tabcontent, blogFrags)
							.addToBackStack(null).commit();
				}
			});
		} else {
			Toast.makeText(getActivity(), "No blogs to show at the moment",
					Toast.LENGTH_LONG).show();
		}
	}

}
