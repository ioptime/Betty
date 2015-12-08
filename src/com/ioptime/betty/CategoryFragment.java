package com.ioptime.betty;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.iopitme.betty.vendor.MainMenuVendor;
import com.ioptime.adapters.CategoryAdapter;
import com.ioptime.extendablelibrary.IoptimeFragment;

public class CategoryFragment extends IoptimeFragment {
	ListView listViewCat;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.categories, container, false);
		listViewCat = (ListView) rootView.findViewById(R.id.categoryList);
		populateListView();
		return rootView;
	}

	public void populateListView() {
		if (Appconstants.categoryList.size() > 0) {
			CategoryAdapter catAdapter = new CategoryAdapter(getActivity(),
					Appconstants.categoryList);
			listViewCat.setAdapter(catAdapter);
			catAdapter.notifyDataSetChanged();
			listViewCat.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					//

					SubCategoryFragment subCatFrag = new SubCategoryFragment();
					Bundle bundle = new Bundle();
					bundle.putInt("position", position);
					subCatFrag.setArguments(bundle);
					getFragmentManager().beginTransaction()
							.replace(R.id.frame_container, subCatFrag)
							.addToBackStack(null).commit();
				}
			});
		}

		else {
			Toast.makeText(getActivity(), "Check your internet connection",
					Toast.LENGTH_LONG).show();
		}
	}
	@Override
	public void onResume() {
		super.onResume();
		((MainMenu) getActivity()).getActionBar().setTitle("Categories");
	}
}
