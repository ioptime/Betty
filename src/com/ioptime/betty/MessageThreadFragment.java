package com.ioptime.betty;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
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
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ioptime.adapters.MessageThreadAdapter;
import com.ioptime.betty.model.Message;
import com.ioptime.extendablelibrary.IoptimeFragment;

public class MessageThreadFragment extends IoptimeFragment {

	int sender_id, reciever_id;
	Boolean again = false;
	ListView listMessagesThread;
	ProgressBar progressBar;
	ArrayList<Message> arrayThread = new ArrayList<Message>();
	Button btSend;
	EditText etMessage;
	int rec_id, rec_type, count;
	boolean IsStarted;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		sender_id = Integer.parseInt(getArguments().getString("sender_id"));
		reciever_id = Integer.parseInt(getArguments().getString("reciever_id"));
		count = getArguments().getInt("count");

		View rootView = inflater.inflate(R.layout.messages_thread, container,
				false);
		IsStarted = true;
		listMessagesThread = (ListView) rootView
				.findViewById(R.id.messageThreadList);

		progressBar = (ProgressBar) rootView
				.findViewById(R.id.messageThreadProgressBar);
		btSend = (Button) rootView.findViewById(R.id.buttonSend);
		etMessage = (EditText) rootView.findViewById(R.id.replyETMessageThread);
		new GetMessageThreadBT().execute();
		btSend.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				new SendMessageBTTask(sender_id, rec_type).execute();
			}
		});
		return rootView;
	}

	private class GetMessageThreadBT extends AsyncTask<Void, Void, Void> {

		String result = "-";

		@Override
		protected void onPreExecute() {
			if (IsStarted)
				progressBar.setVisibility(View.VISIBLE);

		}

		@Override
		protected Void doInBackground(Void... arg0) {

			ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("sender_id", "" + sender_id));//
			params.add(new BasicNameValuePair("reciever_id", "" + reciever_id));
			result = getJSONfromURL(Appconstants.Server
					+ "messages_get_conversation.php", params, 0);
			Log.d("result", "--" + result + " for " + sender_id);
			if (result.equalsIgnoreCase("") | result.contains("empty")
					| result.equalsIgnoreCase("ERROR GETTING RESULTS IOPTIME")) {
				// not valid
			} else {
				JSONArray jArray;
				try {
					jArray = new JSONArray(result);
					arrayThread = new ArrayList<Message>();
					for (int i = 0; i < jArray.length(); i++) {
						JSONObject json_data = jArray.getJSONObject(i);
						arrayThread.add(new Message(Integer.parseInt(json_data
								.getString("message_id").trim()), Integer
								.parseInt(json_data.getString("message_to")
										.trim()), json_data
								.getString("message_subject"), json_data
								.getString("message_status"), json_data
								.getString("group_message"), json_data
								.getString("group_message_id"), json_data
								.getString("message_created").trim(), json_data
								.getString("message_reference_id"), json_data
								.getString("message_sub_reference_id"),
								json_data.getString("message_customer_id"),
								json_data.getString("message_user_id"),
								json_data.getString("message_order_id"),
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

						Log.d("receiver", "-use id"
								+ arrayThread.get(i).getMessage_user_id()
								+ "-customer id-"
								+ arrayThread.get(i).getMessage_customer_id()
								+ "---" + Appconstants.user.getCustomer_id());
						rec_type = arrayThread.get(0).getUser_type();
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
			if (result.equalsIgnoreCase("") | result.contains("empty")
					| result.equalsIgnoreCase("ERROR GETTING RESULTS IOPTIME")
					&& !again) {
				// not valid
				new GetMessageThreadBT().execute();
				again = true;

				Toast.makeText(getActivity(),
						"Wonky Internet connection , trying again",
						Toast.LENGTH_SHORT).show();
			} else {
				populateListView();
				progressBar.setVisibility(View.GONE);
			}
		}

	}

	private void populateListView() {
		if (arrayThread.size() > 0) {
			MessageThreadAdapter messageAdapter = new MessageThreadAdapter(
					getActivity(), arrayThread);
			listMessagesThread.setAdapter(messageAdapter);
			messageAdapter.notifyDataSetChanged();
			listMessagesThread
					.setOnItemClickListener(new OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> parent,
								View view, int position, long id) {
							new ReadOrUnreadBTTask(arrayThread.get(position)
									.getMessage_id()).execute();
							// MessageThreadFragment messageFrag = new
							// MessageThreadFragment();
							// Bundle bundle = new Bundle();
							// bundle.putString("sender_id", inboxMessageArray
							// .get(position).getMessage_customer_id());
							// messageFrag.setArguments(bundle);
							// getFragmentManager().beginTransaction()
							// .replace(R.id.frame_container, messageFrag)
							// .addToBackStack(null).commit();
						}
					});

		} else {

			Toast.makeText(getActivity(), "Check your internet connection",
					Toast.LENGTH_SHORT).show();

		}
	}

	private class SendMessageBTTask extends AsyncTask<Void, Void, Void> {

		String result = "-";
		int reciever_id;
		int reciever;

		SendMessageBTTask(int recv_id, int recv) {
			reciever_id = recv_id;
			reciever = recv;
		}

		@Override
		protected void onPreExecute() {
			Toast.makeText(getActivity(), "Sending message", Toast.LENGTH_SHORT)
					.show();
			btSend.setClickable(false);
			IsStarted = false;
		}

		@Override
		protected Void doInBackground(Void... arg0) {

			ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("reciever_id", "" + reciever_id));//
			params.add(new BasicNameValuePair("reciever", "" + reciever));//
			if (count == 1) {
				params.add(new BasicNameValuePair("sender_id", ""
						+ Appconstants.user.getCustomer_id()));
			}
			else if (count == 0) {
				params.add(new BasicNameValuePair("sender_id", ""
						+ Appconstants.vendor.getUser_id()));
			}
			params.add(new BasicNameValuePair("message", ""
					+ etMessage.getText().toString()));
			params.add(new BasicNameValuePair("subject", ""));

			result = getJSONfromURL(Appconstants.Server + "messages_send.php",
					params, 0);
			if (result.equalsIgnoreCase("") | result.contains("empty")
					| result.contains("err")) {
				// not valid
			} else {
			}
			Log.d("result Message", "--" + result);
			return null;
		}

		@Override
		protected void onPostExecute(Void params) {
			btSend.setClickable(true);
			if (MessageThreadFragment.this.isVisible()) {
				Toast.makeText(getActivity(), "Message sent",
						Toast.LENGTH_SHORT).show();
				new GetMessageThreadBT().execute();
				etMessage.setText("");
			}
		}
	}

	private class ReadOrUnreadBTTask extends AsyncTask<Void, Void, Void> {
		int msg_id;

		public ReadOrUnreadBTTask(int msg_id) {
			// TODO Auto-generated constructor stub
			this.msg_id = msg_id;
		}

		@Override
		protected Void doInBackground(Void... arg0) {
			// TODO Auto-generated method stub
			ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("message_id", "" + msg_id));//
			String result = getJSONfromURL(Appconstants.Server
					+ "message_read.php", params, 0);
			if (result.equalsIgnoreCase("") | result.contains("empty")
					| result.contains("err")) {
				// not valid
			} else {
			}
			Log.d("result Message", "--" + result);
			return null;
		}
	}
}
