package com.ioptime.betty;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.image.loader.ImageLoader;
import com.ioptime.adapters.BlogCommentAdapter;
import com.ioptime.betty.model.Blog;
import com.ioptime.betty.model.BlogComment;
import com.ioptime.extendablelibrary.IoptimeFragment;

public class BlogDetailFragments extends IoptimeFragment {
	TextView tvDesc, tvAuthor, tvName, tvShare;
	EditText etComment;
	ImageView ivImage;
	int position = 0;
	ImageLoader imageLoader;
	ArrayList<BlogComment> commentArray = new ArrayList<BlogComment>();
	ListView commentList;
	Button btComment;

	public BlogDetailFragments() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		position = getArguments().getInt("position");
		View rootView = inflater
				.inflate(R.layout.blog_detail, container, false);
		tvDesc = (TextView) rootView.findViewById(R.id.blogTVDesc);
		tvName = (TextView) rootView.findViewById(R.id.blogTVName);
		tvAuthor = (TextView) rootView.findViewById(R.id.blogTVAuthor);
		tvShare = (TextView) rootView.findViewById(R.id.blogTVShare);
		ivImage = (ImageView) rootView.findViewById(R.id.blogIVImage);
		etComment = (EditText) rootView.findViewById(R.id.blogETComments);
		commentList = (ListView) rootView.findViewById(R.id.blogListComments);
		Blog blog = Appconstants.blogArray.get(position);
		imageLoader = new ImageLoader(getActivity(), R.drawable.ic_launcher);
		// imageLoader.DisplayImage(blog.getImage(), pdIVImage);
		tvDesc.setText(Html.fromHtml("" + Html.fromHtml("" + blog.getText())));
		tvName.setText(Html.fromHtml("" + blog.getTitle()));
		// pdTVPrice.setText("" + product.getPrice());
		new GetCommentsBTTask().execute();

		btComment = (Button) rootView.findViewById(R.id.blogBTAddComment);
		btComment.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (etComment.getText().toString().trim().equalsIgnoreCase("")) {
					etComment.setError("Comment field cannot be empty");

				} else {
					String comment = etComment.getText().toString();
					// adding comment to show locally while the comment get
					// updated from backend
					commentArray.add(0,
							new BlogComment(0, 0, comment, 0, "",
									Appconstants.user.getFirstName(),
									Appconstants.user.getEmail()));
					populateListView();

					etComment.setText("");
					btComment.setClickable(false);
					new PostCommentBTTask(comment).execute();
				}

			}
		});

		return rootView;
	}

	private class GetCommentsBTTask extends AsyncTask<Void, Void, Void> {

		String result = "-";

		@Override
		protected void onPreExecute() {

		}

		@Override
		protected Void doInBackground(Void... arg0) {

			ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("timestamp", ""
					+ System.currentTimeMillis()));
			params.add(new BasicNameValuePair("blog_id", ""
					+ Appconstants.blogArray.get(position).getBlog_id()));//
			result = getJSONfromURL(Appconstants.Server
					+ "comments_get_blog.php", params, 0);
			if (result.equalsIgnoreCase("") | result.contains("empty")
					| result.contains("err")) {
				// not valid
			} else {
				JSONArray jArray;
				try {
					commentArray.clear();
					jArray = new JSONArray(result);

					for (int i = 0; i < jArray.length(); i++) {
						JSONObject json_data = jArray.getJSONObject(i);
						commentArray.add(new BlogComment(parseInt(json_data
								.getString("comment_id")), parseInt(json_data
								.getString("blog_id")), json_data
								.getString("comment"), parseInt(json_data
								.getString("status")), json_data
								.getString("created"), json_data
								.getString("user"), json_data
								.getString("email")));
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();

				}

			}
			return null;
		}

		@Override
		protected void onPostExecute(Void params) {
			populateListView();
			btComment.setClickable(true);

		}

	}

	private class PostCommentBTTask extends AsyncTask<Void, Void, Void> {

		String result = "-";
		String comment;

		public PostCommentBTTask(String comment) {
			// TODO Auto-generated constructor stub
			this.comment = comment;
		}

		@Override
		protected void onPreExecute() {

		}

		@Override
		protected Void doInBackground(Void... arg0) {
			ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();

			params.add(new BasicNameValuePair("customer_id", ""
					+ Appconstants.getUserID(getActivity())));//
			params.add(new BasicNameValuePair("blog_id", ""
					+ Appconstants.blogArray.get(position).getBlog_id()));//
			params.add(new BasicNameValuePair("comment", "" + comment));
			params.add(new BasicNameValuePair("email", ""
					+ Appconstants.user.getEmail()));
			result = getJSONfromURL(Appconstants.Server + "comment_blog.php",
					params, 0);
			return null;
		}

		@Override
		protected void onPostExecute(Void params) {
			try {
				if (result.trim().equalsIgnoreCase("")) {
					Toast.makeText(getActivity(), "Comment posted",
							Toast.LENGTH_SHORT).show();
					etComment.setText("");
					new GetCommentsBTTask().execute();
				} else {
					Toast.makeText(getActivity(),
							"Error while posting comments, Try again",
							Toast.LENGTH_LONG).show();

				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	public void populateListView() {
		if (commentArray.size() > 0) {
			BlogCommentAdapter commentAdapter = new BlogCommentAdapter(
					getActivity(), commentArray);
			commentList.setAdapter(commentAdapter);
			commentAdapter.notifyDataSetChanged();
			commentList.invalidateViews();
			commentList.refreshDrawableState();
			commentList.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					//
					// do something if required on click of comment
				}
			});
		}
	}
}
