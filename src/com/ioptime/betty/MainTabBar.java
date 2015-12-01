//package com.ioptime.betty;
//
//import android.animation.Animator;
//import android.animation.Animator.AnimatorListener;
//import android.animation.AnimatorListenerAdapter;
//import android.animation.ObjectAnimator;
//import android.app.TabActivity;
//import android.content.Intent;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.view.Window;
//import android.widget.LinearLayout;
//import android.widget.TabHost;
//import android.widget.TabHost.OnTabChangeListener;
//
//public class MainTabBar extends TabActivity implements OnTabChangeListener {
//
//	/** Called when the activity is first created. */
//	TabHost tabHost;
//	LinearLayout mainLLMenu;
//
//	@Override
//	public void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
//		setContentView(R.layout.tab_main);
//		mainLLMenu = (LinearLayout) findViewById(R.id.mainLLMenu);
//		// Get TabHost Refference
//		tabHost = getTabHost();
//
//		// Set TabChangeListener called when tab changed
//		tabHost.setOnTabChangedListener(this);
//
//		TabHost.TabSpec spec;
//		Intent intent;
//
//		/************* TAB1 ************/
//		// Create Intents to launch an Activity for the tab (to be reused)
//		intent = new Intent().setClass(this, LatestQuestion.class);
//		spec = tabHost.newTabSpec("First").setIndicator("").setContent(intent);
//
//		// Add intent to tab
//		tabHost.addTab(spec);
//
//		/************* TAB2 ************/
//		intent = new Intent().setClass(this, People.class);
//		spec = tabHost.newTabSpec("Second").setIndicator("").setContent(intent);
//		tabHost.addTab(spec);
//		tabHost.getTabWidget().getChildAt(0)
//				.setBackgroundResource(R.drawable.tab_questions);
//		tabHost.getTabWidget().getChildAt(1)
//				.setBackgroundResource(R.drawable.tab_people);
//		// Set Tab1 as Default tab and change image
//		tabHost.getTabWidget().setCurrentTab(0);
//
//	}
//
//	@Override
//	public void onTabChanged(String tabId) {
//
//		/************ Called when tab changed *************/
//
//		// ********* Check current selected tab and change according images
//		// *******/
//		//
//		// for (int i = 0; i < tabHost.getTabWidget().getChildCount(); i++) {
//		// if (i == 0)
//		// tabHost.getTabWidget().getChildAt(i)
//		// .setBackgroundResource(R.drawable.button);
//		// else if (i == 1)
//		// tabHost.getTabWidget().getChildAt(i)
//		// .setBackgroundResource(R.drawable.button);
//		// }
//
//		Log.i("tabs", "CurrentTab: " + tabHost.getCurrentTab());
//
//		// if (tabHost.getCurrentTab() == 0)
//		// tabHost.getTabWidget().getChildAt(tabHost.getCurrentTab())
//		// .setBackgroundResource(R.drawable.tab1_over);
//		// else if (tabHost.getCurrentTab() == 1)
//		// tabHost.getTabWidget().getChildAt(tabHost.getCurrentTab())
//		// .setBackgroundResource(R.drawable.tab2_over);
//		// else if (tabHost.getCurrentTab() == 2)
//		// tabHost.getTabWidget().getChildAt(tabHost.getCurrentTab())
//		// .setBackgroundResource(R.drawable.tab3_over);
//
//	}
//
//	public void openMenu(View v) {
//		// startActivity(new Intent(MainTabBar.this, MenuDrop.class));
//		if (!mainLLMenu.isShown()) {
//			SlideToDown(mainLLMenu);
//		} else {
//			SlideToAbove(mainLLMenu);
//		}
//	}
//
//	public void SlideToDown(final View view) {
//		view.setVisibility(View.VISIBLE);
//
//		// Start the animation
//		ObjectAnimator mSlidInAnimator = ObjectAnimator.ofFloat(view,
//				"translationY", 0);
//		mSlidInAnimator.setDuration(200);
//		mSlidInAnimator.start();
//		mSlidInAnimator.addListener(new AnimatorListener() {
//
//			@Override
//			public void onAnimationStart(Animator animation) {
//				// TODO Auto-generated method stub
//			}
//
//			@Override
//			public void onAnimationRepeat(Animator animation) {
//				// TODO Auto-generated method stub
//
//			}
//
//			@Override
//			public void onAnimationEnd(Animator animation) {
//				// TODO Auto-generated method stub
//				view.setVisibility(View.VISIBLE);
//			}
//
//			@Override
//			public void onAnimationCancel(Animator animation) {
//				// TODO Auto-generated method stub
//
//			}
//		});
//	}
//
//	public void SlideToAbove(final View view) {
//		ObjectAnimator mSlidOutAnimator = ObjectAnimator.ofFloat(view,
//				"translationY", -100);
//		mSlidOutAnimator.setDuration(200);
//		mSlidOutAnimator.start();
//		mSlidOutAnimator.addListener(new AnimatorListener() {
//
//			@Override
//			public void onAnimationStart(Animator animation) {
//				// TODO Auto-generated method stub
//
//			}
//
//			@Override
//			public void onAnimationRepeat(Animator animation) {
//				// TODO Auto-generated method stub
//
//			}
//
//			@Override
//			public void onAnimationEnd(Animator animation) {
//				// TODO Auto-generated method stub
//				view.setVisibility(View.GONE);
//			}
//
//			@Override
//			public void onAnimationCancel(Animator animation) {
//				// TODO Auto-generated method stub
//
//			}
//		});
//	}
//
//	public void close(View v) {
//		finish();
//
//	}
//
//	public void editProfile(View v) {
//		startActivity(new Intent(MainTabBar.this, Edit_Profile.class));
//
//	}
//
//	public void help(View v) {
//		startActivity(new Intent(MainTabBar.this, Help.class));
//
//	}
//
//	public void account(View v) {
//		startActivity(new Intent(MainTabBar.this, Profile.class));
//	}
//
//	public void ask(View v) {
//		// startActivity(new Intent(MenuDrop.this, Ask.class));
//
//	}
//
//	public void settings(View v) {
//		startActivity(new Intent(MainTabBar.this, Settings.class));
//
//	}
//
// }