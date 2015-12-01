package com.ioptime.betty;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
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
import android.widget.TextView;
import android.widget.Toast;

import com.image.loader.ImageLoader;
import com.ioptime.adapters.CommentAdapter;
import com.ioptime.betty.model.Cart;
import com.ioptime.betty.model.Comment;
import com.ioptime.betty.model.Product;
import com.ioptime.extendablelibrary.IoptimeFragment;

public class ProductDetailFragment extends IoptimeFragment {
	TextView pdTVName, pdTVPrice, pdTVShare, pdTVAddCart, pdTVDesc;
	EditText pdEtComment;
	Button pdBtComment;
	ImageView pdIVImage;
	int position = 0;
	ImageLoader imageLoader;
	ArrayList<Comment> commentArray = new ArrayList<Comment>();
	ListView commentList;

	public ProductDetailFragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		position = getArguments().getInt("position");
		View rootView = inflater.inflate(R.layout.product_detail, container,
				false);
		commentList = (ListView) rootView.findViewById(R.id.pdListComments);
		pdTVAddCart = (TextView) rootView.findViewById(R.id.pdTVAddCart);
		pdTVDesc = (TextView) rootView.findViewById(R.id.pdTVDesc);
		pdTVName = (TextView) rootView.findViewById(R.id.pdTVName);
		pdTVPrice = (TextView) rootView.findViewById(R.id.pdTVPrice);
		pdTVShare = (TextView) rootView.findViewById(R.id.pdTVShare);
		pdTVShare.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				try {
					String path = Environment.getExternalStorageDirectory()
							.toString();
					OutputStream fOut = null;
					File file = new File(path, "Betty" + ".jpg"); // the
																	// File
																	// to
																	// save
																	// to
					fOut = new FileOutputStream(file);

					Bitmap pictureBitmap = ((BitmapDrawable) pdIVImage
							.getDrawable()).getBitmap(); // obtaining the
					// Bitmap
					pictureBitmap.compress(Bitmap.CompressFormat.JPEG, 0, fOut); // saving
					fOut.flush();
					fOut.close();
					Intent shareIntent = new Intent(Intent.ACTION_SEND);
					shareIntent
							.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
					shareIntent.setType("*/*");

					// For a file in shared storage. For data in private
					// storage,
					// use a ContentProvider.
					Uri uri = Uri.fromFile(file);
					shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
					Log.d("sharing",
							""
									+ Appconstants.productsList.get(position)
											.getUrl());
					shareIntent.putExtra(Intent.EXTRA_TEXT,
							Appconstants.productsList.get(position).getUrl());
					startActivity(Intent.createChooser(shareIntent,
							"Share Product!"));

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		pdIVImage = (ImageView) rootView.findViewById(R.id.pdIVImage);
		pdEtComment = (EditText) rootView.findViewById(R.id.pdEtComment);
		pdBtComment = (Button) rootView.findViewById(R.id.pdBtAddComment);

		new GetCommentsBTTask().execute();

		Product product = Appconstants.productsList.get(position);
		imageLoader = new ImageLoader(getActivity(), R.drawable.ic_launcher);
		imageLoader.DisplayImage(product.getImage(), pdIVImage,
				R.drawable.ic_launcher);
		pdTVDesc.setText("" + product.getDescription());
		pdTVName.setText("" + product.getName());
		pdTVPrice.setText("" + product.getPrice());

		pdTVAddCart.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				addToCart();
				dialogToShow();
			}
		});

		pdBtComment.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (pdEtComment.getText().toString().trim()
						.equalsIgnoreCase("")) {
					pdEtComment.setError("Comment field cannot be empty");
				} else {
					commentArray.add(
							0,
							new Comment(0, 0, Appconstants.user
									.getCustomer_id(), pdEtComment.getText()
									.toString(), 0, "", 0, ""));
					String comment = pdEtComment.getText().toString();
					pdEtComment.setText("");
					pdBtComment.setClickable(false);
					new PostCommentBTTask(comment, Appconstants.productsList
							.get(position).getProductId()).execute();

				}

			}
		});

		return rootView;
	}

	protected void dialogToShow() {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				getActivity());
		AlertDialog alertDialog;
		// set title
		alertDialogBuilder.setTitle("Product Added in cart");

		// set dialog message
		alertDialogBuilder
				.setMessage("Ok")
				.setCancelable(false)
				.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								// if this button is clicked, close
								// current activity
								dialog.dismiss();
							}
						});

		// create alert dialog
		alertDialog = alertDialogBuilder.create();

		// show it
		alertDialog.show();

	}

	protected void addToCart() {
		// TODO Auto-generated method stub

		ArrayList<Cart> cart = Appconstants.getCartList(getActivity()
				.getApplicationContext());

		int product_id = Appconstants.productsList.get(position).getProductId();
		int customer_id = Appconstants.user.getCustomer_id();

		cart.add(new Cart(product_id, customer_id));
		Appconstants.setCartList(cart, getActivity().getApplicationContext());
		// For Fragment
		((MainMenu) getActivity()).updateCartCount(cart.size());
	}

	private class GetCommentsBTTask extends AsyncTask<Void, Void, Void> {

		String result = "-";

		@Override
		protected void onPreExecute() {

		}

		@Override
		protected Void doInBackground(Void... arg0) {

			ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("timestamp", ""
					+ System.currentTimeMillis()));
			params.add(new BasicNameValuePair("product_id", ""
					+ Appconstants.productsList.get(position).getProductId()));//
			result = getJSONfromURL(Appconstants.Server
					+ "comments_get_product.php", params, 0);
			if (result.equalsIgnoreCase("") | result.contains("empty")
					| result.contains("err")) {
				// not valid
			} else {
				JSONArray jArray;
				try {
					commentArray = new ArrayList<Comment>();
					;
					jArray = new JSONArray(result);

					for (int i = 0; i < jArray.length(); i++) {
						JSONObject json_data = jArray.getJSONObject(i);
						commentArray.add(new Comment(parseInt(json_data
								.getString("review_id").trim()),
								parseInt(json_data.getString("product_id")
										.trim()), parseInt(json_data.getString(
										"customer_id").trim()), (json_data
										.getString("text").trim()),
								parseInt(json_data.getString("rating").trim()),
								(json_data.getString("company").trim()),
								parseInt(json_data.getString("company_id")
										.trim()), (json_data
										.getString("author").trim())));
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
			populateListView();
			pdBtComment.setClickable(true);

		}

	}

	private class PostCommentBTTask extends AsyncTask<Void, Void, Void> {

		String result = "-";
		String comment;
		int productid;

		public PostCommentBTTask(String comment, int productid) {
			// TODO Auto-generated constructor stub
			this.comment = comment;
			this.productid = productid;
		}

		@Override
		protected void onPreExecute() {

		}

		@Override
		protected Void doInBackground(Void... arg0) {
			ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("customer_id", ""
					+ Appconstants.getUserID(getActivity())));//
			params.add(new BasicNameValuePair("product_id", "" + productid));//
			params.add(new BasicNameValuePair("comment", "" + comment));
			params.add(new BasicNameValuePair("timestamp", ""
					+ System.currentTimeMillis()));

			result = getJSONfromURL(
					Appconstants.Server + "comment_product.php", params, 0);
			Log.d("result", "--" + result);

			return null;
		}

		@Override
		protected void onPostExecute(Void params) {
			if (result.trim().equalsIgnoreCase("")) {
				Toast.makeText(getActivity(), "Comment posted",
						Toast.LENGTH_SHORT).show();
				pdEtComment.setText("");
				new GetCommentsBTTask().execute();
			} else {
				Toast.makeText(getActivity(),
						"Error while posting comments, Try again",
						Toast.LENGTH_LONG).show();

			}
		}

	}

	public void populateListView() {
		if (ProductDetailFragment.this.isVisible()) {
			if (commentArray.size() > 0) {

				CommentAdapter commentAdapter = new CommentAdapter(
						getActivity(), commentArray);
				commentList.setAdapter(commentAdapter);
				commentAdapter.notifyDataSetChanged();
				commentList.invalidateViews();
				commentList.refreshDrawableState();
				commentList.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						//
						// do something if required on click of comment
					}
				});
			}
		}
	}
}
