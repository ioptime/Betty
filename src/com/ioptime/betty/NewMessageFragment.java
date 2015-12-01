package com.ioptime.betty;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ioptime.extendablelibrary.IoptimeFragment;

public class NewMessageFragment extends IoptimeFragment {

	ImageView ivCross;
	EditText etMessage, etSubject;
	AutoCompleteTextView etToSearchView;
	TextView tvTo;
	Spinner spinnerTo;
	Button btSend;
	ArrayAdapter<String> customerAdapter;
	ArrayAdapter<String> vendorAdapter;
	int position = -1;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater
				.inflate(R.layout.new_message, container, false);

		etMessage = (EditText) rootView.findViewById(R.id.newmsgETMessage);
		etSubject = (EditText) rootView.findViewById(R.id.newmsgETSubject);

		etToSearchView = (AutoCompleteTextView) rootView
				.findViewById(R.id.newmsgAutoCompleteName);

		// using imageview with autocomplete textview so that user can only
		// select a valid recipent and can not change it later. if he later
		// tries to change it , he wont be , after selecting the image of cross
		// will be visible and user can only delete it not edit it.

		ivCross = (ImageView) rootView.findViewById(R.id.newmsgIVCross);
		ivCross.setVisibility(View.GONE);
		ivCross.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				etToSearchView.setInputType(EditorInfo.TYPE_CLASS_TEXT);
				etToSearchView.setText("");
				ivCross.setVisibility(View.GONE);
				position = -1;
			}
		});

		etToSearchView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View arg1, int pos,
					long id) {
				String selection = (String) parent.getItemAtPosition(pos);

				if (spinnerTo.getSelectedItemPosition() == 1) {
					// vendors here

					for (int i = 0; i <= Appconstants.vendors; i++) {
						Log.d("selected is " + selection, ""
								+ Appconstants.messageNamesArray.get(i)
										.getName());
						if (Appconstants.messageNamesArray.get(i).getName()
								.equals(selection)) {
							position = i;
							break;
						}
					}
				} else if (spinnerTo.getSelectedItemPosition() == 2) {
					// customers here
					for (int i = Appconstants.vendors + 1; i < Appconstants.customers + 1; i++) {
						if (Appconstants.messageNamesArray.get(i).getName()
								.equals(selection)) {
							position = i;
							break;
						}
					}
				}

				etToSearchView.setInputType(EditorInfo.TYPE_NULL);
				ivCross.setVisibility(View.VISIBLE);
			}
		});

		ArrayList<String> customerName = new ArrayList<String>();
		ArrayList<String> vendorName = new ArrayList<String>();
		for (int i = 0; i < Appconstants.messageNamesArray.size(); i++) {
			if (Appconstants.messageNamesArray.get(i).getStore_id() == 0) {
				// its a customer as shop id=0
				customerName.add(""
						+ Appconstants.messageNamesArray.get(i).getName());
			} else if (Appconstants.messageNamesArray.get(i).getStore_id() != 0) {
				// its a vendor as shop id != 0
				vendorName.add(""
						+ Appconstants.messageNamesArray.get(i).getName());
			}
		}
		etToSearchView.showDropDown();
		customerAdapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_1, customerName);

		vendorAdapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_1, vendorName);

		tvTo = (TextView) rootView.findViewById(R.id.newmsgTVName);
		btSend = (Button) rootView.findViewById(R.id.newmsgBtSend);
		spinnerTo = (Spinner) rootView.findViewById(R.id.newmsgSpinnerType);

		btSend.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (etMessage.getText().toString().equalsIgnoreCase("")
						&& etSubject.getText().toString().equalsIgnoreCase("")) {
					Toast.makeText(getActivity(), "All fields are required",
							Toast.LENGTH_SHORT).show();
				} else {
					int reciever_id = 0, reciever = 0;
					Log.d("pos", Appconstants.messageNamesArray.size()
							+ " pos is " + position);
					if (spinnerTo.getSelectedItemPosition() == 0) {
						// Admin is here
						reciever = 1;
						reciever_id = 1;
						new SendMessageBTTask(reciever_id, reciever).execute();
					} else if (spinnerTo.getSelectedItemPosition() == 1) {
						if (ivCross.isShown()) {

							// vendors here
							reciever = 2;
							reciever_id = Appconstants.messageNamesArray.get(
									position).getUser_id();
							new SendMessageBTTask(reciever_id, reciever)
									.execute();
						} else {
							Toast.makeText(getActivity(),
									"Please select a valid recipent",
									Toast.LENGTH_SHORT).show();
						}
					} else if (spinnerTo.getSelectedItemPosition() == 2) {
						// customers here
						if (ivCross.isShown()) {

							reciever = 3;
							reciever_id = Appconstants.messageNamesArray.get(
									position).getUser_id();
							new SendMessageBTTask(reciever_id, reciever)
									.execute();
						} else {
							Toast.makeText(getActivity(),
									"Please select a valid recipent",
									Toast.LENGTH_SHORT).show();
						}
					}
				}

			}
		});

		spinnerTo.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				((TextView) parent.getChildAt(0)).setTextColor(Color.rgb(0, 0,
						0));

				if (position == 0) {
					// Admin selected
					tvTo.setVisibility(View.INVISIBLE);
					etToSearchView.setVisibility(View.INVISIBLE);
				} else if (position == 1) {

					// Vendor selected
					tvTo.setVisibility(View.VISIBLE);
					tvTo.setText("Vendor");
					etToSearchView.setVisibility(View.VISIBLE);
					etToSearchView.setAdapter(vendorAdapter);
				} else if (position == 2) {
					// customer selected
					tvTo.setVisibility(View.VISIBLE);
					tvTo.setText("Customer");
					etToSearchView.setVisibility(View.VISIBLE);
					etToSearchView.setAdapter(customerAdapter);
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				((TextView) parent.getChildAt(0)).setTextColor(Color.rgb(0, 0,
						0));

			}
		});

		return rootView;
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
		}

		@Override
		protected Void doInBackground(Void... arg0) {

			ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("reciever_id", "" + reciever_id));//
			params.add(new BasicNameValuePair("reciever", "" + reciever));//

			params.add(new BasicNameValuePair("sender_id", ""
					+ Appconstants.user.getCustomer_id()));
			params.add(new BasicNameValuePair("message", ""
					+ etMessage.getText().toString()));
			params.add(new BasicNameValuePair("subject", ""
					+ etSubject.getText().toString()));

			result = getJSONfromURL(Appconstants.Server + "messages_send.php",
					params, 0);
			if (result.equalsIgnoreCase("") | result.contains("empty")
					| result.contains("err")) {
				// not valid
			} else {
			}
			Log.d("result", "--" + result);
			return null;
		}

		@Override
		protected void onPostExecute(Void params) {
			btSend.setClickable(true);
			if (NewMessageFragment.this.isVisible()) {
				Toast.makeText(getActivity(), "Message sent",
						Toast.LENGTH_SHORT).show();
			}
		}
	}

}
