package com.ioptime.adapters;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.image.loader.ImageLoader;
import com.ioptime.betty.Appconstants;
import com.ioptime.betty.R;
import com.ioptime.betty.model.Shops;
import com.ioptime.extendablelibrary.IoptimeRoot;

public class ShopAdapter extends BaseAdapter {

	private Activity activity;
	private ArrayList<Shops> shopArray;
	private static LayoutInflater inflater = null;
	boolean wish;
	public ImageLoader imageLoader;

	public ShopAdapter(Activity a, ArrayList<Shops> shopsList, boolean wish) {
		activity = a;
		this.wish = wish;
		shopArray = shopsList;
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		imageLoader = new ImageLoader(activity.getApplicationContext(),
				R.drawable.ic_launcher);
		if (Appconstants.user.getFollowList().size() > 0) {
			for (int i = 0; i < shopArray.size(); i++) {
				for (int j = 0; j < Appconstants.user.getFollowList().size(); j++) {
					shopArray.get(i).setFollowed(false);
					if (shopArray.get(i).getId() == Appconstants.user
							.getFollowList().get(j).getId()) {
						shopArray.get(i).setFollowed(true);
						break;
					}
				}
			}
		}
	}

	public int getCount() {
		return shopArray.size();
	}

	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		View vi = convertView;
		if (convertView == null)
			vi = inflater.inflate(R.layout.item_shops, null);

		//

		RelativeLayout shopList = (RelativeLayout) vi
				.findViewById(R.id.shopList);
		TextView textName = (TextView) vi.findViewById(R.id.shopListName);
		ImageView image = (ImageView) vi.findViewById(R.id.shopListImage);
		textName.setText(shopArray.get(position).getName());
		imageLoader.DisplayImage(shopArray.get(position).getUrl(), image,
				R.drawable.ic_launcher);

		ImageView followList = (ImageView) vi.findViewById(R.id.shopListFollow);
		if (wish) {

			if (shopArray.get(position).isFollowed()) {
				followList.setImageResource(R.drawable.unfolllow);
			} else if (!shopArray.get(position).isFollowed()) {
				followList.setImageResource(R.drawable.follow);
				shopList.setVisibility(View.GONE);
				shopList.getLayoutParams().height = 0;

			}
		} else if (!wish) {

			if (shopArray.get(position).isFollowed()) {
				followList.setImageResource(R.drawable.unfolllow);
			} else if (!shopArray.get(position).isFollowed()) {
				followList.setImageResource(R.drawable.follow);

			}
		}
		followList.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (shopArray.get(position).isFollowed()) {

					shopArray.get(position).setFollowed(false);
					new RemoveFromFollowList(shopArray.get(position).getId(),
							position).execute();
				} else if (!shopArray.get(position).isFollowed()) {
					// adding to wish list locally
					shopArray.get(position).setFollowed(true);
					new AddToFollowList(shopArray.get(position).getId(),
							position).execute();

				}
				notifyDataSetChanged();

			}
		});

		return vi;
	}

	/** for adding a store in follow list **/
	private class AddToFollowList extends AsyncTask<Void, Void, Void> {
		int storeid;
		String result = "result";
		int position;

		AddToFollowList(int storeid, int pos) {
			this.storeid = storeid;
			this.position = pos;
		}

		@Override
		protected Void doInBackground(Void... arg0) {

			ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs
					.add(new BasicNameValuePair("store_id", "" + storeid));
			nameValuePairs.add(new BasicNameValuePair("customer_id", ""
					+ Appconstants.user.getCustomer_id()));

			try {

				//
				result = IoptimeRoot.getJSONfromURL(Appconstants.Server
						+ "follow_store.php", nameValuePairs, 0);
				Log.d("result", "--" + result);
				if (result.equalsIgnoreCase("") | result.contains("empty")
						| result.contains("err")) {
					// not valid

				} else {
					// JSONArray jArray = new JSONArray(result);
					//
					// for (int i = 0; i < jArray.length(); i++) {
					// JSONObject json_data = jArray.getJSONObject(i);
					// userid = Integer.parseInt(json_data.getString(
					// "customer_id").trim());
					//
					// }

				}
			} catch (Exception e) {

				e.printStackTrace();
				return null;
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void params) {
			if (result.equalsIgnoreCase("")) {
				Toast.makeText(activity.getApplicationContext(),
						"Successfully added", Toast.LENGTH_SHORT).show();
			} else {
				// if failed removing

				Toast.makeText(activity.getApplicationContext(),
						"Error while following", Toast.LENGTH_LONG).show();
				shopArray.get(position).setFollowed(false);
				notifyDataSetChanged();
			}
		}
	}

	/** removing from follow list **/
	private class RemoveFromFollowList extends AsyncTask<Void, Void, Void> {
		int storeid;
		String result = "result";
		int position;

		RemoveFromFollowList(int storeid, int pos) {
			this.storeid = storeid;
			position = pos;
		}

		@Override
		protected Void doInBackground(Void... arg0) {

			ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs
					.add(new BasicNameValuePair("store_id", "" + storeid));
			nameValuePairs.add(new BasicNameValuePair("customer_id", ""
					+ Appconstants.user.getCustomer_id()));

			try {

				//
				result = IoptimeRoot.getJSONfromURL(Appconstants.Server
						+ "unfollow_store.php", nameValuePairs, 0);
				Log.d("result", "--" + result);
				if (result.equalsIgnoreCase("") | result.contains("empty")
						| result.contains("err")) {

				} else {
					// JSONArray jArray = new JSONArray(result);
					//
					// for (int i = 0; i < jArray.length(); i++) {
					// JSONObject json_data = jArray.getJSONObject(i);
					// userid = Integer.parseInt(json_data.getString(
					// "customer_id").trim());
					//
					// }

				}
			} catch (Exception e) {

				e.printStackTrace();
				return null;
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void params) {
			if (result.equalsIgnoreCase("")) {
				Toast.makeText(activity.getApplicationContext(), "Removed",
						Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(activity.getApplicationContext(),
						"Error while removing", Toast.LENGTH_LONG).show();
				// if failed removing
				shopArray.get(position).setFollowed(true);
				notifyDataSetChanged();
			}
		}
	}

}
