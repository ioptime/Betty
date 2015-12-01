package com.iopitme.betty.vendor;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
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

import com.ioptime.adapters.MessageInboxAdapter;
import com.ioptime.betty.Appconstants;
import com.ioptime.betty.MessageThreadFragment;
import com.ioptime.betty.R;
import com.ioptime.betty.model.Message;
import com.ioptime.extendablelibrary.IoptimeFragment;

public class MessagesVendorFragment extends IoptimeFragment {
	EditText etFirstName, etLastName, etEmail, etTagLine;
	Button editBTSignUp;
	ImageView inboxIVNew;
	ArrayList<EditText> editTextArray = new ArrayList<EditText>();
	ProgressBar progressBar;
	ArrayList<Message> inboxMessageArray = new ArrayList<Message>();
	MessageInboxAdapter messageAdapter;
	ListView listInbox;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.inbox, container, false);
		progressBar = (ProgressBar) rootView
				.findViewById(R.id.inboxProgressBar);
		inboxIVNew = (ImageView) rootView.findViewById(R.id.inboxIVNew);
		inboxIVNew.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				NewMessageVendorFragment newMsgFrag = new NewMessageVendorFragment();
				Bundle bundle = new Bundle();

				newMsgFrag.setArguments(bundle);
				getFragmentManager().beginTransaction()
						.replace(R.id.frame_container, newMsgFrag)
						.addToBackStack(null).commit();

			}
		});
		listInbox = (ListView) rootView.findViewById(R.id.inboxList);

		new GetMessageBTTask().execute();

		return rootView;
	}

	private class GetMessageBTTask extends AsyncTask<Void, Void, Void> {
		String result = "";

		@Override
		protected void onPreExecute() {
			progressBar.setVisibility(View.VISIBLE);
		}

		@Override
		protected Void doInBackground(Void... arg0) {

			ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("reciever_id",
					Appconstants.vendor.getUser_id() + ""));

			try {

				//
				result = "";
				result = getJSONfromURL(Appconstants.Server
						+ "messages_get.php", nameValuePairs, 15);
				Log.d("result", "--" + result);
				if (result.equalsIgnoreCase("") | result.contains("empty")
						| result.contains("err")) {
					// not valid
				} else {
					JSONArray jArray = new JSONArray(result);
					inboxMessageArray = new ArrayList<Message>();
					for (int i = 0; i < jArray.length(); i++) {
						JSONObject json_data = jArray.getJSONObject(i);
						inboxMessageArray.add(new Message(Integer
								.parseInt(json_data.getString("message_id")
										.trim()), Integer.parseInt(json_data
								.getString("message_to").trim()), json_data
								.getString("message_subject"), json_data
								.getString("message_status"), json_data
								.getString("group_message"), json_data
								.getString("group_message_id"), json_data
								.getString("message_created").trim(), json_data
								.getString("message_reference_id"), json_data
								.getString("message_sub_reference_id"),
								json_data.getString("message_customer_id"),
								json_data.getString("userid"), json_data
										.getString("message_order_id"),
								json_data.getString("message_last_update"),
								json_data.getString("message_last_modified"),
								Integer.parseInt(json_data.getString(
										"customer_read").trim()), Integer
										.parseInt(json_data.getString(
												"fcustomer_read").trim()),
								Integer.parseInt(json_data.getString(
										"support_read").trim()), Integer
										.parseInt(json_data.getString(
												"user_type").trim()), Integer
										.parseInt(json_data.getString(
												"admin_status").trim()),
								Integer.parseInt(json_data.getString(
										"vendor_status").trim()), Integer
										.parseInt(json_data.getString(
												"customer_status").trim()),
								Integer.parseInt(json_data.getString(
										"transfer_status").trim()), Integer
										.parseInt(json_data.getString(
												"transfer_message_id").trim()),
								json_data.getString("Attachment"), Integer
										.parseInt(json_data.getString(
												"content_id").trim()),
								json_data.getString("content"), Integer
										.parseInt(json_data
												.getString("is_user").trim()),
								Integer.parseInt(json_data.getString(
										"sender_id").trim()), Integer
										.parseInt(json_data.getString(
												"reply_status").trim()),
								Integer.parseInt(json_data.getString(
										"message_read_customer").trim()),
								Integer.parseInt(json_data.getString(
										"message_read_support").trim()),
								json_data.getString("created"), json_data
										.getString("file")));
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
			if (MessagesVendorFragment.this.isVisible())
				populateListView();

			progressBar.setVisibility(View.GONE);

		}
	}

	private void populateListView() {
		if (inboxMessageArray.size() > 0) {
			messageAdapter = new MessageInboxAdapter(getActivity(),
					inboxMessageArray);
			listInbox.setAdapter(messageAdapter);
			messageAdapter.notifyDataSetChanged();
			listInbox.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					MessageThreadFragment messageFrag = new MessageThreadFragment();
					Bundle bundle = new Bundle();
					bundle.putString("sender_id",
							inboxMessageArray.get(position)
									.getMessage_user_id());
					bundle.putString("reciever_id",
							inboxMessageArray.get(position)
									.getMessage_customer_id());
					messageFrag.setArguments(bundle);
					getFragmentManager().beginTransaction()
							.replace(R.id.frame_container, messageFrag)
							.addToBackStack(null).commit();
				}
			});
		}
	}

}