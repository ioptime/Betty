package com.ioptime.adapters;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.image.loader.ImageLoader;
import com.ioptime.betty.ProductDetailFragment;
import com.ioptime.betty.R;
import com.ioptime.betty.model.Category;

public class CategoryAdapter extends BaseAdapter {

	private Activity activity;
	private ArrayList<Category> notifArray;
	private static LayoutInflater inflater = null;

	public ImageLoader imageLoader;

	public CategoryAdapter(Activity a, ArrayList<Category> i) {
		activity = a;
		notifArray = i;
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		imageLoader = new ImageLoader(activity.getApplicationContext(),
				R.drawable.ic_launcher);
	}

	public int getCount() {
		return notifArray.size();
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
			vi = inflater.inflate(R.layout.item_category, null);

		//
		TextView textName = (TextView) vi.findViewById(R.id.categoryListName);
		ImageView image = (ImageView) vi.findViewById(R.id.categoryListImage);
		textName.setText(notifArray.get(position).getName());
		ImageView imageCat = (ImageView) vi.findViewById(R.id.categoryListSub);
		imageCat.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

			}
		});
		imageLoader.DisplayImage(notifArray.get(position).getImage(), image,
				R.drawable.ic_launcher);
		return vi;
	}
}