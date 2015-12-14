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

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ioptime.extendablelibrary.IoptimeActivity;

public class SignUp extends IoptimeActivity {

	EditText signETAddress, signETNName, signETFName, signETLName,
			signETPassword, signETCPassword, signETEmail, signETphone,
			signETCity;
	int userid = 0;
	int serverResponseCode = 0;
	ProgressDialog progressDialog;
	ArrayList<EditText> editTextArray = new ArrayList<EditText>();
	Spinner countrySpinner;
	File fileImage = null;
	ImageView ivProfile;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		countrySpinner = (Spinner) findViewById(R.id.signSpinnerCountry);
		ivProfile = (ImageView) findViewById(R.id.signupIVPic);
		TextView link = (TextView) findViewById(R.id.linkTV);
		link.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(SignUp.this, TermsActivity.class);
				startActivity(intent);
			}
		});
		if (savedInstanceState != null) {
			Appconstants.worldlist = savedInstanceState
					.getStringArrayList("countries");
			Appconstants.world = savedInstanceState
					.getParcelableArrayList("world");
		} // Spinner adapter
		try {
			Log.d("countries", "" + Appconstants.worldlist.size());
			countrySpinner.setAdapter(new ArrayAdapter<String>(SignUp.this,
					android.R.layout.simple_spinner_dropdown_item,
					Appconstants.worldlist));
		} catch (Exception e) {
			e.printStackTrace();
			finish();
			Toast.makeText(getApplicationContext(),
					"Unexpected connectivity issue", Toast.LENGTH_LONG).show();
		}
		// Spinner on item click listener
		countrySpinner
				.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int position, long arg3) {
						// TODO Auto-generated method stub
						// Locate the textviews in activity_main.xml
						if(arg0!=null)
						((TextView) arg0.getChildAt(0)).setTextColor(Color.rgb(
								0, 0, 0));
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub
						((TextView) arg0.getChildAt(0)).setTextColor(Color.rgb(
								0, 0, 0));
					}
				});

		signETNName = (EditText) findViewById(R.id.signETNicName);
		editTextArray.add(signETNName);
		signETFName = (EditText) findViewById(R.id.signETFName);
		editTextArray.add(signETFName);
		signETLName = (EditText) findViewById(R.id.signETLName);
		editTextArray.add(signETLName);
		signETEmail = (EditText) findViewById(R.id.signETEmail);
		editTextArray.add(signETEmail);
		signETphone = (EditText) findViewById(R.id.signETPhoneNumb);
		editTextArray.add(signETphone);
		signETPassword = (EditText) findViewById(R.id.signETPassword);
		editTextArray.add(signETPassword);
		signETCPassword = (EditText) findViewById(R.id.signETConfirmPassword);
		editTextArray.add(signETCPassword);
		signETCity = (EditText) findViewById(R.id.signETCity);
		editTextArray.add(signETCity);
		signETAddress = (EditText) findViewById(R.id.signETAddress);
		editTextArray.add(signETAddress);

	}

	public void signUp(View v) {
		if (inputAll()) {
			if (signETPassword
					.getText()
					.toString()
					.trim()
					.equalsIgnoreCase(
							signETCPassword.getText().toString().trim())) {
				if (isConnectedToInternet(SignUp.this)) {
					new SaveUsetBTTask().execute();
				} else {
					finish();
					Toast.makeText(getApplicationContext(),
							"Please check your internet connectivity",
							Toast.LENGTH_LONG).show();
				}
			} else {
				Toast.makeText(getApplicationContext(),
						"Passwords don't match", Toast.LENGTH_LONG).show();
			}
		}
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

	private class SaveUsetBTTask extends AsyncTask<Void, Void, Void> {
		@Override
		protected void onPreExecute() {
			progressDialog = ProgressDialog.show(SignUp.this,
					"Please wait ...", "SignUp ...", true);
			progressDialog.setCancelable(true);
		}

		@Override
		protected Void doInBackground(Void... arg0) {

			ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("firstname", signETFName
					.getText().toString()));
			nameValuePairs.add(new BasicNameValuePair("lastname", signETLName
					.getText().toString()));
			nameValuePairs.add(new BasicNameValuePair("email", signETEmail
					.getText().toString()));
			nameValuePairs.add(new BasicNameValuePair("telephone", signETphone
					.getText().toString()));
			nameValuePairs.add(new BasicNameValuePair("city", signETCity
					.getText().toString()));
			nameValuePairs.add(new BasicNameValuePair("country_id",
					Appconstants.world.get(
							countrySpinner.getSelectedItemPosition()).getId()
							+ ""));
			nameValuePairs.add(new BasicNameValuePair("password",
					signETPassword.getText().toString()));
			nameValuePairs.add(new BasicNameValuePair("address", signETAddress
					.getText().toString()));
			nameValuePairs.add(new BasicNameValuePair("ip",
					getIPofDevicePublic()));
			nameValuePairs.add(new BasicNameValuePair("nickname", signETNName
					.getText().toString()));
			try {

				//
				String result = "";
				result = getJSONfromURL(Appconstants.Server
						+ "signup_customer.php", nameValuePairs, 0);
				Log.d("result", "--" + result);
				if (result.equalsIgnoreCase("") | result.contains("empty")
						| result.contains("err")) {
					// not valid
					userid = 0;
				} else {
					JSONArray jArray = new JSONArray(result);

					for (int i = 0; i < jArray.length(); i++) {
						JSONObject json_data = jArray.getJSONObject(i);
						userid = Integer.parseInt(json_data.getString(
								"customer_id").trim());

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
			if (userid != 0) {
				Appconstants.user.setCustomer_id(userid);
				savePref();
				progressDialog.cancel();
				Toast.makeText(getApplicationContext(), "Successful login",
						Toast.LENGTH_LONG).show();
				new BTSavePic().execute();
				finish();
				startActivitySwipe(SignUp.this, new Intent(SignUp.this,
						Login.class));

			} else {
				Toast.makeText(getApplicationContext(),
						"Error while signing up, try again", Toast.LENGTH_LONG)
						.show();
				progressDialog.cancel();

			}
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
				File fileImageTag = new File(dir, "" + userid + ".png");
				fileImage.renameTo(fileImageTag);
				fileImage.delete();
				uploadFile(fileImageTag.getPath());
			}
			return null;
		}
	}

	//
	// private void startMain() {
	// finish();
	// startActivitySwipe(SignUp.this, new Intent(getApplicationContext(),
	// MainActivity.class));
	//
	// }

	private void savePref() {
		SharedPreferences settings;
		settings = getSharedPreferences(Appconstants.PREFNAME,
				Context.MODE_PRIVATE);
		// set the sharedpref
		Editor editor = settings.edit();
		editor.putInt("userid", userid);
		editor.putString("email", signETEmail.getText().toString());
		editor.commit();

		Toast.makeText(getApplicationContext(), "Registred", Toast.LENGTH_SHORT)
				.show();
	}

	@Override
	protected int getLayoutResourceId() {
		// TODO Auto-generated method stub
		return R.layout.signup;
	}

	public void takePic(View v) {
		openAlert();
	}

	private void openAlert() {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				SignUp.this);

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
		alertDialogBuilder.setNegativeButton("From Gallery",
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
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (requestCode == 1 && resultCode == RESULT_OK) {

			try {

				String file_path = Environment.getExternalStorageDirectory()
						.getAbsolutePath() + "/Betty";
				File dir = new File(file_path);
				if (!dir.exists())
					dir.mkdirs();

				fileImage = new File(dir, "temp" + ".jpeg");

				InputStream inputStream = getContentResolver().openInputStream(
						data.getData());
				FileOutputStream fileOutputStream = new FileOutputStream(
						fileImage);
				copyStream(inputStream, fileOutputStream);

				Bitmap bMap = BitmapFactory.decodeFile(fileImage.getPath());
				bMap = Bitmap.createScaledBitmap(bMap, 640, 640, true);
				try {
					fileOutputStream = new FileOutputStream(fileImage);
					bMap.compress(Bitmap.CompressFormat.JPEG, 50,
							fileOutputStream);
					ivProfile.setImageBitmap(bMap);

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

		if (requestCode == 2 && resultCode == RESULT_OK) {

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
				ivProfile.setImageBitmap(bitmap);
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

			runOnUiThread(new Runnable() {
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

					runOnUiThread(new Runnable() {
						public void run() {

							Toast.makeText(SignUp.this,
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

				runOnUiThread(new Runnable() {
					public void run() {
						Toast.makeText(SignUp.this, "MalformedURLException",
								Toast.LENGTH_SHORT).show();
					}
				});

				Log.e("Upload file to server", "error: " + ex.getMessage(), ex);
			} catch (Exception e) {

				e.printStackTrace();

				runOnUiThread(new Runnable() {
					public void run() {
						Toast.makeText(SignUp.this,
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

	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		super.onSaveInstanceState(savedInstanceState);
		// Save UI state changes to the savedInstanceState.
		// This bundle will be passed to onCreate if the process is
		// killed and restarted.
		savedInstanceState.putStringArrayList("countries",
				Appconstants.worldlist);

		savedInstanceState.putParcelableArrayList("world", Appconstants.world);

	}

}
