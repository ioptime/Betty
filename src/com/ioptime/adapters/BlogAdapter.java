package com.ioptime.adapters;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.image.loader.ImageLoader;
import com.ioptime.betty.R;

import com.ioptime.betty.model.Blog;

public class BlogAdapter extends BaseAdapter {

	private Activity activity;
	private ArrayList<Blog> blogArray;
	private static LayoutInflater inflater = null;

	public ImageLoader imageLoader;

	public BlogAdapter(Activity a, ArrayList<Blog> i) {
		activity = a;
		blogArray = i;
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		imageLoader = new ImageLoader(activity.getApplicationContext(),
				ImageLoader.OTHER);
	}

	public int getCount() {
		return blogArray.size();
	}

	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View vi = convertView;
		if (convertView == null)
			vi = inflater.inflate(R.layout.item_blog, null);

		//
		TextView textName = (TextView) vi.findViewById(R.id.blogListName);
		TextView textDesc = (TextView) vi.findViewById(R.id.blogListDesc);
		TextView textAuthor = (TextView) vi.findViewById(R.id.blogListAuthor);
		ImageView image = (ImageView) vi.findViewById(R.id.blogListImage);
		textName.setText(blogArray.get(position).getTitle());

		textDesc.setText(Html.fromHtml(""
				+ Html.fromHtml(blogArray.get(position).getIntro_text())));

		// textAuthor.setText("" + blogArray.get(position).getIntro_text());
		// imageLoader.DisplayImage(blogArray.get(position).getImage(),
		// image);
		return vi;
	}
}