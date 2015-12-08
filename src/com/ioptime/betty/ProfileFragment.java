package com.ioptime.betty;

import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.iopitme.betty.vendor.MainMenuVendor;
import com.ioptime.extendablelibrary.IoptimeFragment;


public class ProfileFragment extends IoptimeFragment {
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
	
	 mTabHost.addTab(
	 mTabHost.newTabSpec("tab1").setIndicator("WishList", null),
	 ProductListFragment.class, b);
	 mTabHost.addTab(
	 mTabHost.newTabSpec("tab2").setIndicator("Followed", null),
	 ShopFollowedFragment.class, b);
	 mTabHost.addTab(
	 mTabHost.newTabSpec("tab3").setIndicator("My Blogs", null),
	 MyBlogsFragment.class, b);
	
	 mTabHost.addTab(
	 mTabHost.newTabSpec("tab4").setIndicator("Profile", null),
	 ProfileEditFragment.class, b);
	
	 return rootView;
	 }
	 @Override
		public void onResume() {
			super.onResume();
			((MainMenu) getActivity()).getActionBar().setTitle("Profile");
		}
}