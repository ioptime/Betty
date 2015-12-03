package com.ioptime.adapters;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.image.loader.ImageLoader;
import com.ioptime.betty.Appconstants;
import com.ioptime.betty.R;
import com.ioptime.betty.model.Message;

public class MessageThreadAdapter extends BaseAdapter {

	private Activity activity;
	private ArrayList<Message> messageArray;
	private static LayoutInflater inflater = null;

	public ImageLoader imageLoader;

	public MessageThreadAdapter(Activity a, ArrayList<Message> i) {
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
		if (convertView == null)
			vi = inflater.inflate(R.layout.item_thread, null);

		//
		TextView textMessage = (TextView) vi
				.findViewById(R.id.threadTVMessageContent);
		TextView textHeading = (TextView) vi
				.findViewById(R.id.threadTVMessageSubject);
		TextView textDate = (TextView) vi
				.findViewById(R.id.threadTVMessageDate);
		TextView textTime = (TextView) vi
				.findViewById(R.id.threadTVMessageTime);
		ImageView IVlogo = (ImageView) vi
				.findViewById(R.id.IVLogo);
		LinearLayout llMain = (LinearLayout) vi.findViewById(R.id.threadLLMain);
		LinearLayout ll = (LinearLayout) vi.findViewById(R.id.threadLL);
		textMessage.setText(messageArray.get(position).getContent());
		textHeading.setText(messageArray.get(position).getMessage_subject());
		textDate.setText(messageArray.get(position).getMessage_created_date());
		textTime.setText(messageArray.get(position).getMessage_created_time());
		// checking for user id
		if (Integer.parseInt(messageArray.get(position)
				.getMessage_customer_id()) == Appconstants.getUserID(activity)) {
			// this message is sent from our side
			IVlogo.setImageResource(R.drawable.person1);
			ll.setBackground(null);
			// llMain.setBackgroundColor(Color.BLUE);
			llMain.setBackground(activity.getResources().getDrawable(
					R.drawable.balloon_incoming_normal));
			ll.setGravity(Gravity.LEFT);
		} else if (Integer.parseInt(messageArray.get(position)
				.getMessage_customer_id()) != Appconstants.getUserID(activity)) {
			IVlogo.setImageResource(R.drawable.person2);
			// this message is recieved
			ll.setBackground(null);
			// llMain.setBackgroundColor(Color.RED);
			llMain.setBackground(activity.getResources().getDrawable(
					R.drawable.balloon_outgoing_normal));
			ll.setGravity(Gravity.RIGHT);
		}
		// textAuthor.setText("" + blogArray.get(position).getIntro_text());
		// imageLoader.DisplayImage(blogArray.get(position).getImage(),
		// image);
		return vi;
	}
}