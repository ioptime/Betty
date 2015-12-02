package com.ioptime.betty;

import java.util.ArrayList;

import com.ioptime.betty.model.ProductReportsModel;
import com.ioptime.extendablelibrary.IoptimeFragment;
import com.ioptime.vendor.adapters.ProductReportsAdapter;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class ProductReportsFragment extends IoptimeFragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.product_reports_fragment,
				container, false);

		String[] OID = new String[3];
		OID[0] = "1";
		OID[1] = "5";
		OID[2] = "2";

		ArrayList<ProductReportsModel> pdata = new ArrayList<ProductReportsModel>();
		for (int i = 0; i < OID.length; i++) {
			ProductReportsModel Ritems = new ProductReportsModel(0,
					"rasasasasklopasasas sdhasjkhdjka", "ras007", 3, "60%", 5,
					OID);
			pdata.add(Ritems);
		}
		ListView pRecordsLV = (ListView) rootView.findViewById(R.id.pReportsLV);
		ProductReportsAdapter adapter = new ProductReportsAdapter(
				getActivity(), pdata);
		pRecordsLV.setAdapter(adapter);
		return rootView;
	}
}
