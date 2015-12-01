package com.ioptime.adapters;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.image.loader.ImageLoader;
import com.ioptime.betty.R;
import com.ioptime.betty.model.BlogComment;
import com.ioptime.betty.model.Comment;

public class BlogCommentAdapter extends BaseAdapter {

	private Activity activity;
	private ArrayList<BlogComment> commentArray;
	private static LayoutInflater inflater = null;

	public ImageLoader imageLoader;

	public BlogCommentAdapter(Activity a, ArrayList<BlogComment> i) {
		activity = a;
		commentArray = i;
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		imageLoader = new ImageLoader(activity.getApplicationContext(),
				R.drawable.ic_launcher);
	}

	public int getCount() {
		return commentArray.size();
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
			vi = inflater.inflate(R.layout.comment_item, null);

		//
		TextView text = (TextView) vi.findViewById(R.id.commentTVText);
		TextView nameAuthor = (TextView) vi.findViewById(R.id.commentTVAuthor);
		ImageView image = (ImageView) vi.findViewById(R.id.commentIVImage);
		text.setText(commentArray.get(position).getText());
		nameAuthor.setText(commentArray.get(position).getUser());
		// imageLoader.DisplayImage(commentArray.get(position).getImage(),
		// image);
		return vi;
	}
}