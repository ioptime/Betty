package com.ioptime.betty;

import java.util.ArrayList;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ioptime.adapters.NavDrawerListAdapter;
import com.ioptime.betty.model.Cart;
import com.ioptime.betty.model.NavDrawerItem;

public class MainMenu extends FragmentActivity {
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;

	// nav drawer title
	private CharSequence mDrawerTitle;

	// used to store app title
	private CharSequence mTitle;

	// slide menu items
	private String[] navMenuTitles;
	private TypedArray navMenuIcons;

	private ArrayList<NavDrawerItem> navDrawerItems;
	private NavDrawerListAdapter adapter;
	TextView tvCart;
	boolean doubleBackToExitPressedOnce;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_menu);
		mTitle = mDrawerTitle = getTitle();

		// load slide menu items
		navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);

		// nav drawer icons from resources
		navMenuIcons = getResources()
				.obtainTypedArray(R.array.nav_drawer_icons);

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.list_slidermenu);

		navDrawerItems = new ArrayList<NavDrawerItem>();

		// adding nav drawer items to array
		// Products
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons
				.getResourceId(0, -1)));
		// Categories
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons
				.getResourceId(1, -1)));
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons
				.getResourceId(2, -1)));
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], navMenuIcons
				.getResourceId(3, -1)));
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[4], navMenuIcons
				.getResourceId(4, -1)));
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[5], navMenuIcons
				.getResourceId(5, -1)));
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[6], navMenuIcons
				.getResourceId(6, -1)));

		// Recycle the typed array
		navMenuIcons.recycle();

		mDrawerList.setOnItemClickListener(new SlideMenuClickListener());

		// setting the nav drawer list adapter
		adapter = new NavDrawerListAdapter(getApplicationContext(),
				navDrawerItems);
		mDrawerList.setAdapter(adapter);

		// enabling action bar app icon and behaving it as toggle button
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.menu, // nav menu toggle icon
				R.string.app_name, // nav drawer open - description for
									// accessibility
				R.string.app_name // nav drawer close - description for
									// accessibility
		) {
			public void onDrawerClosed(View view) {
				getActionBar().setTitle(mTitle);
				// calling onPrepareOptionsMenu() to show action bar icons
				invalidateOptionsMenu();
			}

			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(mDrawerTitle);
				// calling onPrepareOptionsMenu() to hide action bar icons
				invalidateOptionsMenu();
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		if (savedInstanceState == null) {
			// on first time display view for first nav item
			displayView(0);
		}
	}

	/**
	 * Slide menu item click listener
	 * */
	private class SlideMenuClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// display view for selected nav drawer item
			displayView(position);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(final Menu menu) {

		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.menu.main, menu);
		final View menu_hotlist = menu.findItem(R.id.action_cart)
				.getActionView();
		tvCart = (TextView) menu_hotlist.findViewById(R.id.hotlist_hot);
		ArrayList<Cart> cart = Appconstants
				.getCartList(getApplicationContext());
		updateCartCount(cart.size());
		new MyMenuItemStuffListener(menu_hotlist, "Show hot message") {
			@Override
			public void onClick(View v) {
				// opencart here

				CartFragment newCartFrag = new CartFragment();
				Bundle bundle = new Bundle();

				newCartFrag.setArguments(bundle);
				getSupportFragmentManager().beginTransaction()
						.replace(R.id.frame_container, newCartFrag)
						.addToBackStack(null).commit();

			}
		};
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// toggle nav drawer on selecting action bar app icon/title
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		// Handle action bar actions click
		switch (item.getItemId()) {
		case R.id.action_cart:
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/* *
	 * Called when invalidateOptionsMenu() is triggered
	 */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// if nav drawer is opened, hide the action items
		// menu.findItem(R.id.action_cart).setTitle("12");
		return super.onPrepareOptionsMenu(menu);
	}

	/**
	 * Diplaying fragment view for selected nav drawer list item
	 * */
	private void displayView(int position) {
		// update the main content by replacing fragments
		Fragment fragment = null;
		switch (position) {
		case 0:
			fragment = new ProductListFragment();
			break;
		case 1:
			fragment = new CategoryFragment();
			break;
		case 2:
			fragment = new BlogFragment();
			break;
		case 3:
			fragment = new ShopFragment();
			break;
		case 4:
			fragment = new ProfileFragment();
			break;
		case 5:
			fragment = new MessagesFragment();
			break;

		case 6:
			fragment = new ContactFragment();
			break;

		default:
			break;
		}

		if (fragment != null) {
			FragmentManager fragmentManager = getSupportFragmentManager();
			fragmentManager.beginTransaction()
					.replace(R.id.frame_container, fragment)
					.addToBackStack("fragback").commit();

			// update selected item and title, then close the drawer
			mDrawerList.setItemChecked(position, true);
			mDrawerList.setSelection(position);
			setTitle(navMenuTitles[position]);
			mDrawerLayout.closeDrawer(mDrawerList);
		} else {
			// error in creating fragment
			Log.e("MainActivity", "Error in creating fragment");
		}
	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getActionBar().setTitle(mTitle);
	}

	/**
	 * When using the ActionBarDrawerToggle, you must call it during
	 * onPostCreate() and onConfigurationChanged()...
	 */

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	// call the updating code on the main thread,
	// so we can call this asynchronously
	public void updateCartCount(final int new_hot_number) {
		if (tvCart == null)
			return;
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				if (new_hot_number == 0)
					tvCart.setVisibility(View.INVISIBLE);
				else {
					tvCart.setVisibility(View.VISIBLE);
					tvCart.setText(Integer.toString(new_hot_number));
				}
			}
		});
	}

	static abstract class MyMenuItemStuffListener implements
			View.OnClickListener, View.OnLongClickListener {
		private String hint;
		private View view;

		MyMenuItemStuffListener(View view, String hint) {
			this.view = view;
			this.hint = hint;
			view.setOnClickListener(this);
			view.setOnLongClickListener(this);
		}

		@Override
		abstract public void onClick(View v);

		@Override
		public boolean onLongClick(View v) {
			final int[] screenPos = new int[2];
			final Rect displayFrame = new Rect();
			view.getLocationOnScreen(screenPos);
			view.getWindowVisibleDisplayFrame(displayFrame);
			final Context context = view.getContext();
			final int width = view.getWidth();
			final int height = view.getHeight();
			final int midy = screenPos[1] + height / 2;
			final int screenWidth = context.getResources().getDisplayMetrics().widthPixels;
			Toast cheatSheet = Toast
					.makeText(context, hint, Toast.LENGTH_SHORT);
			if (midy < displayFrame.height()) {
				cheatSheet.setGravity(Gravity.TOP | Gravity.RIGHT, screenWidth
						- screenPos[0] - width / 2, height);
			} else {
				cheatSheet.setGravity(Gravity.BOTTOM
						| Gravity.CENTER_HORIZONTAL, 0, height);
			}
			cheatSheet.show();
			return true;
		}
	}

	@Override
	public void onBackPressed() {
		FragmentManager fm = getSupportFragmentManager();

		if (fm.getBackStackEntryCount() > 1) {
			fm.popBackStack();
			
			// super.onBackPressed();
			// return;
		} else {
			if (doubleBackToExitPressedOnce) {
				fm.popBackStack();
				super.onBackPressed();
				return;
			}

			this.doubleBackToExitPressedOnce = true;
			Toast.makeText(this, "Press one more time to exit",
					Toast.LENGTH_SHORT).show();

			new Handler().postDelayed(new Runnable() {

				@Override
				public void run() {

					doubleBackToExitPressedOnce = false;
				}
			}, 3000);

		}
	}
//	@Override
//	public void onResume() {
//	    super.onResume();
//	    getActionBar().setTitle(mDrawerTitle);
//	}
}