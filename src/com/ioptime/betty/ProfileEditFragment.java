package com.ioptime.betty;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.image.loader.ImageLoader;
import com.ioptime.betty.model.Product;
import com.ioptime.betty.model.User;
import com.ioptime.extendablelibrary.IoptimeFragment;

public class ProfileEditFragment extends IoptimeFragment {
	EditText etFirstName, etLastName, etEmail, etTagLine;
	Button editBTSignUp;
	ArrayList<EditText> editTextArray = new ArrayList<EditText>();
	ProgressDialog progressDialog;
	ImageLoader imageloader;
	int serverResponseCode = 0;
	Spinner countrySpinner;
	File fileImage;
	ImageView profilePic;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.profile_edit, container,
				false);

		etFirstName = (EditText) rootView.findViewById(R.id.editETFName);
		editTextArray.add(etFirstName);
		etLastName = (EditText) rootView.findViewById(R.id.editETLName);
		editTextArray.add(etLastName);
		etEmail = (EditText) rootView.findViewById(R.id.editETEmail);
		editTextArray.add(etEmail);
		etTagLine = (EditText) rootView.findViewById(R.id.editETTagLine);
		TextView tvTopic = (TextView) rootView.findViewById(R.id.topicTV);
		editBTSignUp = (Button) rootView.findViewById(R.id.editBTSignUp);
		profilePic = (ImageView) rootView.findViewById(R.id.CustimgIV);
		imageloader = new ImageLoader(getActivity(), R.drawable.cust_logo);
		imageloader.DisplayImage("http://sandbox.baitymall.com/image/data/"
				+ Appconstants.user.getCustomer_id() + ".png", profilePic,
				ImageLoader.NORMAL);
		profilePic.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				openAlert();
			}
		});
		editBTSignUp.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (inputAll()) {
					if (isConnectedToInternet(getActivity())) {
						new UpdateUserBT().execute();
					} else {
						Toast.makeText(getActivity(),
								"Please check your internet connectivity",
								Toast.LENGTH_LONG).show();
					}
				}
			}
		});
		tvTopic.setText("" + Appconstants.user.getTopic());
		etFirstName.setText("" + Appconstants.user.getFirstName());
		etLastName.setText("" + Appconstants.user.getLastname());
		etEmail.setText("" + Appconstants.user.getEmail());
		etTagLine.setText("" + Appconstants.user.getTopic());

		return rootView;
	}

	public boolean inputAll() {
		for (EditText edit : editTextArray) {
			if (TextUtils.isEmpty(edit.getText().toString().trim())) {
				edit.setError(edit.getHint() + " can not be empty");
				return false;
			} else {
				edit.setError(null, null);
			}

		}
		return true;
	}

	private class UpdateUserBT extends AsyncTask<Void, Void, Void> {

		int userid = 0;
		String result;
		ArrayList<Product> productsTemp;

		@Override
		protected void onPreExecute() {
			progressDialog = ProgressDialog.show(getActivity(),
					"Please wait ...", "Updating profile ...", true);
			progressDialog.setCancelable(true);
		}

		@Override
		protected Void doInBackground(Void... arg0) {

			ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("firstname", etFirstName
					.getText().toString()));
			nameValuePairs.add(new BasicNameValuePair("lastname", etLastName
					.getText().toString()));
			nameValuePairs.add(new BasicNameValuePair("email", etEmail
					.getText().toString()));
			nameValuePairs.add(new BasicNameValuePair("topic", etTagLine
					.getText().toString()));
			nameValuePairs.add(new BasicNameValuePair("customer_id",
					Appconstants.user.getCustomer_id() + ""));
			productsTemp = Appconstants.user.getWishlist();
			try {

				//
				result = "";
				result = getJSONfromURL(Appconstants.Server
						+ "customer_profile_update.php", nameValuePairs, 0);
				Log.d("result", "--" + result);
				if (result.equalsIgnoreCase("") | result.contains("empty")
						| result.contains("err")) {
				} else {
					userid = 0;
					Appconstants.user = new User();
					Appconstants.user.setCustomer_id(0);

					JSONArray jArray = new JSONArray(result);

					for (int i = 0; i < jArray.length(); i++) {
						JSONObject json_data = jArray.getJSONObject(i);
						Appconstants.user = new User(Integer.parseInt(json_data
								.getString("customer_id").trim()),
								Integer.parseInt(json_data
										.getString("store_id").trim()),
								json_data.getString("firstname").trim(),
								json_data.getString("lastname").trim(),
								json_data.getString("email").trim(), json_data
										.getString("telephone").trim(),
								json_data.getString("fax").trim(), json_data
										.getString("cart").trim(), null, null,
								json_data.getString("newsletter").trim(),
								Integer.parseInt(json_data.getString(
										"address_id").trim()),
								Integer.parseInt(json_data.getString(
										"customer_group_id").trim()), json_data
										.getString("ip").trim(),
								Integer.parseInt(json_data.getString("status")
										.trim()), Integer.parseInt(json_data
										.getString("approved").trim()),
								json_data.getString("logo").trim(), json_data
										.getString("pay_status").trim(),
								json_data.getString("topic").trim());

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
			if (Appconstants.user.getCustomer_id() != 0) {

				if (result.equalsIgnoreCase("") | result.contains("empty")
						| result.contains("err")) {
					Toast.makeText(getActivity(), "Error while updating",
							Toast.LENGTH_SHORT).show();

				} else {
					Toast.makeText(getActivity(), "Successful update",
							Toast.LENGTH_SHORT).show();
					new BTSavePic().execute();
				}

				etEmail.setText("" + Appconstants.user.getEmail());
				etFirstName.setText("" + Appconstants.user.getFirstName());
				etLastName.setText("" + Appconstants.user.getLastname());
				etTagLine.setText("" + Appconstants.user.getTopic());
				Appconstants.user.setWishlist(productsTemp);
			}

			progressDialog.cancel();

		}

	}

	@Override
	public void onResume() {
		super.onResume();
		((MainMenu) getActivity()).getActionBar().setTitle("Profile");
	}

	private void openAlert() {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				getActivity());

		alertDialogBuilder.setTitle("Adding Picture");
		alertDialogBuilder.setMessage("Upload your picture");
		// set positive button: Yes message
		alertDialogBuilder.setPositiveButton("From Camera",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						Intent cameraIntent = new Intent(
								android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
						startActivityForResult(cameraIntent, 2);

					}
				});
		// set negative button: No message
		alertDialogBuilder.setNegativeButton("From Gallery---",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						Intent photoPickerIntent = new Intent(
								Intent.ACTION_PICK);
						photoPickerIntent.setType("image/*");
						startActivityForResult(photoPickerIntent, 1);
					}
				});

		AlertDialog alertDialog = alertDialogBuilder.create();
		// show alert
		alertDialog.show();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		Log.d("activityreult", requestCode + "aaaa");
		if (requestCode == 1 && resultCode == Activity.RESULT_OK) {

			try {

				String file_path = Environment.getExternalStorageDirectory()
						.getAbsolutePath() + "/Betty";
				File dir = new File(file_path);
				if (!dir.exists())
					dir.mkdirs();

				fileImage = new File(dir, "temp" + ".jpeg");

				InputStream inputStream = getActivity().getContentResolver()
						.openInputStream(data.getData());
				FileOutputStream fileOutputStream = new FileOutputStream(
						fileImage);
				copyStream(inputStream, fileOutputStream);

				Bitmap bMap = BitmapFactory.decodeFile(fileImage.getPath());
				bMap = Bitmap.createScaledBitmap(bMap, 640, 640, true);
				try {
					fileOutputStream = new FileOutputStream(fileImage);
					bMap.compress(Bitmap.CompressFormat.JPEG, 50,
							fileOutputStream);
					profilePic.setImageBitmap(bMap);

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				fileOutputStream.close();
				inputStream.close();

			} catch (Exception e) {

				Log.e("adding pictre from gallery",
						"Error while creating temp file", e);
			}
		}

		if (requestCode == 2 && resultCode == Activity.RESULT_OK) {

			// get the cropped bitmap
			Bitmap bitmap = (Bitmap) data.getExtras().get("data");

			String file_path = Environment.getExternalStorageDirectory()
					.getAbsolutePath() + "/Betty";
			File dir = new File(file_path);
			if (!dir.exists())
				dir.mkdirs();

			fileImage = new File(dir, "temp" + ".jpeg");
			FileOutputStream fOut;
			try {
				fOut = new FileOutputStream(fileImage);
				bitmap.compress(Bitmap.CompressFormat.JPEG, 50, fOut);

				bitmap = Bitmap.createScaledBitmap(bitmap, 640, 640, true);
				profilePic.setImageBitmap(bitmap);
				fOut.flush();
				fOut.close();

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	public static void copyStream(InputStream input, OutputStream output)
			throws IOException {

		byte[] buffer = new byte[1024];
		int bytesRead;
		while ((bytesRead = input.read(buffer)) != -1) {
			output.write(buffer, 0, bytesRead);
		}
	}

	private class BTSavePic extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {

		}

		@Override
		protected Void doInBackground(Void... arg0) {
			if (fileImage != null) {
				String file_path = Environment.getExternalStorageDirectory()
						.getAbsolutePath() + "/Betty";
				File dir = new File(file_path);
				if (!dir.exists())
					dir.mkdirs();
				// userid_name.jpeg
				File fileImageTag = new File(dir, ""
						+ Appconstants.user.getCustomer_id() + ".png");
				fileImage.renameTo(fileImageTag);
				fileImage.delete();
				uploadFile(fileImageTag.getPath());
			}
			return null;
		}
	}

	public int uploadFile(String sourceFileUri) {

		String fileName = sourceFileUri;

		HttpURLConnection conn = null;
		DataOutputStream dos = null;
		String lineEnd = "\r\n";
		String twoHyphens = "--";
		String boundary = "*****";
		int bytesRead, bytesAvailable, bufferSize;
		byte[] buffer;
		int maxBufferSize = 1 * 1024 * 1024;
		File sourceFile = new File(sourceFileUri);

		if (!sourceFile.isFile()) {

			getActivity().runOnUiThread(new Runnable() {
				public void run() {

				}
			});

			return 0;

		} else {
			try {

				// open a URL connection to the Servlet
				FileInputStream fileInputStream = new FileInputStream(
						sourceFile);
				URL url = new URL(Appconstants.Server
						+ "profile_image_upload.php");

				// Open a HTTP connection to the URL
				conn = (HttpURLConnection) url.openConnection();
				conn.setDoInput(true); // Allow Inputs
				conn.setDoOutput(true); // Allow Outputs
				conn.setUseCaches(false); // Don't use a Cached Copy
				conn.setRequestMethod("POST");
				conn.setRequestProperty("Connection", "Keep-Alive");
				conn.setRequestProperty("ENCTYPE", "multipart/form-data");
				conn.setRequestProperty("Content-Type",
						"multipart/form-data;boundary=" + boundary);
				conn.setRequestProperty("uploaded_file", fileName);

				dos = new DataOutputStream(conn.getOutputStream());

				dos.writeBytes(twoHyphens + boundary + lineEnd);
				dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\""
						+ fileName + "\"" + lineEnd);

				dos.writeBytes(lineEnd);

				// create a buffer of maximum size
				bytesAvailable = fileInputStream.available();

				bufferSize = Math.min(bytesAvailable, maxBufferSize);
				buffer = new byte[bufferSize];

				// read file and write it into form...
				bytesRead = fileInputStream.read(buffer, 0, bufferSize);

				while (bytesRead > 0) {

					dos.write(buffer, 0, bufferSize);
					bytesAvailable = fileInputStream.available();
					bufferSize = Math.min(bytesAvailable, maxBufferSize);
					bytesRead = fileInputStream.read(buffer, 0, bufferSize);

				}

				// send multipart form data necesssary after file data...
				dos.writeBytes(lineEnd);
				dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

				// Responses from the server (code and message)
				serverResponseCode = conn.getResponseCode();
				String serverResponseMessage = conn.getResponseMessage();

				Log.i("uploadFile", "HTTP Response is : "
						+ serverResponseMessage + ": " + serverResponseCode);

				if (serverResponseCode == 200) {

					getActivity().runOnUiThread(new Runnable() {
						public void run() {

							Toast.makeText(getActivity(),
									"File Upload Complete.", Toast.LENGTH_SHORT)
									.show();
						}
					});
				}

				// close the streams //
				fileInputStream.close();
				dos.flush();
				dos.close();

			} catch (MalformedURLException ex) {

				ex.printStackTrace();

				getActivity().runOnUiThread(new Runnable() {
					public void run() {
						Toast.makeText(getActivity(), "MalformedURLException",
								Toast.LENGTH_SHORT).show();
					}
				});

				Log.e("Upload file to server", "error: " + ex.getMessage(), ex);
			} catch (Exception e) {

				e.printStackTrace();

				getActivity().runOnUiThread(new Runnable() {
					public void run() {
						Toast.makeText(getActivity(),
								"Got Exception : see logcat ",
								Toast.LENGTH_SHORT).show();
					}
				});
				Log.e("Upload file to server Exception",
						"Exception : " + e.getMessage(), e);
			}
			return serverResponseCode;

		} // End else block
	}
}
