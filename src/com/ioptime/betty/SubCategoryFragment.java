package com.ioptime.betty;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.ioptime.adapters.CategoryAdapter;
import com.ioptime.betty.model.Category;
import com.ioptime.extendablelibrary.IoptimeFragment;

public class SubCategoryFragment extends IoptimeFragment {
	ProgressDialog progressDialog;
	int position;
	ListView listViewCategories;
	public ArrayList<Category> subCatList = new ArrayList<Category>();

	public SubCategoryFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		position = getArguments().getInt("position");

		View rootView = inflater.inflate(R.layout.categories, container, false);
		listViewCategories = (ListView) rootView
				.findViewById(R.id.categoryList);
		if (Appconstants.categoryList.size() == 0) {
			// new ProductListBT().execute();

			Toast.makeText(getActivity(), "Check your internet connection",
					Toast.LENGTH_LONG).show();
		} else {
			new SubCatListBT().execute();
		}
		return rootView;
	}

	private class SubCatListBT extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
			progressDialog = ProgressDialog.show(getActivity(),
					"Please wait ...", "Getting sub categories ...", true);
			progressDialog.setCancelable(true);
		}

		@Override
		protected Void doInBackground(Void... arg0) {
			try {
				ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("category_id", ""
						+ Appconstants.categoryList.get(position)
								.getCategoryId()));//
				String result = "";
				result = getJSONfromURL(Appconstants.Server
						+ "category_get_sub.php", params, 0);
				Log.d("result", "--" + result);
				if (result.equalsIgnoreCase("") | result.contains("empty")
						| result.contains("err")) {
					// not valid
				} else {
					JSONArray jArray = new JSONArray(result);
					for (int i = 0; i < jArray.length(); i++) {
						JSONObject json_data = jArray.getJSONObject(i);
						subCatList.add(new Category(Integer.parseInt(json_data
								.getString("category_id").trim()),
								Appconstants.ImageUrl
										+ json_data.getString("image").trim(),
								Integer.parseInt(json_data.getString(
										"parent_id").trim()), Integer
										.parseInt(json_data.getString("top")
												.trim()), Integer
										.parseInt(json_data.getString("column")
												.trim()), Integer
										.parseInt(json_data.getString(
												"sort_order").trim()), Integer
										.parseInt(json_data.getString("status")
												.trim()), json_data.getString(
										"date_added").trim(), json_data
										.getString("date_modified").trim(),
								Integer.parseInt(json_data.getString(
										"language_id").trim()), json_data
										.getString("name").trim(), json_data
										.getString("description").trim(),
								json_data.getString("meta_description").trim(),
								json_data.getString("meta_keyword").trim()));

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
			progressDialog.cancel();
			populateListView();
		}
	}

	public void populateListView() {
		if (subCatList.size() > 0) {
			CategoryAdapter catAdapter = new CategoryAdapter(getActivity(),
					subCatList);
			listViewCategories.setAdapter(catAdapter);
			catAdapter.notifyDataSetChanged();
			listViewCategories
					.setOnItemClickListener(new OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> parent,
								View view, int position, long id) {
							//
							// ProductDetailFragment prodFrag = new
							// ProductDetailFragment();
							// Bundle bundle = new Bundle();
							// bundle.putInt("position", position);
							// prodFrag.setArguments(bundle);
							// getFragmentManager().beginTransaction()
							// .replace(R.id.frame_container, prodFrag)
							// .addToBackStack(null).commit();
						}
					});
		} else {

			Toast.makeText(getActivity(),
					"There are no sub categories to show", Toast.LENGTH_LONG)
					.show();

		}
	}
}
