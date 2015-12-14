package com.iopitme.betty.vendor;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ioptime.betty.R;
import com.ioptime.extendablelibrary.IoptimeFragment;

public class ProfileVendorFragment extends IoptimeFragment {
	FragmentTabHost mTabHost;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.profile, container, false);

		mTabHost = (FragmentTabHost) rootView
				.findViewById(android.R.id.tabhost);
		mTabHost.setup(getActivity(), getChildFragmentManager(),
				android.R.id.tabcontent);

		Bundle b = new Bundle();
		b.putBoolean("wish", true);

		// mTabHost.addTab(
		// mTabHost.newTabSpec("tab1").setIndicator("WishList", null),
		// ProductListVendorFragment.class, b);
		// mTabHost.addTab(
		// mTabHost.newTabSpec("tab2").setIndicator("Followed", null),
		// ShopFollowedFragment.class, b);
		mTabHost.addTab(
				mTabHost.newTabSpec("tab3").setIndicator("My Blogs", null),
				MyBlogsVendorFragment.class, b);
		mTabHost.addTab(
				mTabHost.newTabSpec("tab4").setIndicator("Profile", null),
				ProfileEditVendorFragment.class, b);

		return rootView;
	}

	@Override
	public void onResume() {
		super.onResume();
		((MainMenuVendor) getActivity()).getActionBar().setTitle("Profile");
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		List<Fragment> fragments = getChildFragmentManager().getFragments();
		if (fragments != null) {
			for (Fragment fragment : fragments) {
				fragment.onActivityResult(requestCode, resultCode, data);
			}
		}
	}
}