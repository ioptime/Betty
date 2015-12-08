package com.ioptime.adapters;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.image.loader.ImageLoader;
import com.ioptime.betty.R;
import com.ioptime.betty.model.Message;

public class MessageInboxAdapter extends BaseAdapter {

	private Activity activity;
	private ArrayList<Message> messageArray;
	private static LayoutInflater inflater = null;

	public ImageLoader imageLoader;

	public MessageInboxAdapter(Activity a, ArrayList<Message> i) {
		activity = a;
		messageArray = i;
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		imageLoader = new ImageLoader(activity.getApplicationContext(),
				R.drawable.ic_launcher);
	}

	public int getCount() {
		return messageArray.size();
	}

	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View vi = convertView;
		if (convertView == null) {
			vi = inflater.inflate(R.layout.item_inbox, null);
		}
		//
		TextView textMessage = (TextView) vi.findViewById(R.id.inboxTVMessage);
		TextView textHeading = (TextView) vi.findViewById(R.id.inboxTVHeading);

		textMessage.setText(messageArray.get(position).getMessage_subject());
		textHeading.setText(messageArray.get(position).getSenderName());
		return vi;
	}
}