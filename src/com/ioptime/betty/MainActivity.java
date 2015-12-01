package com.ioptime.betty;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ioptime.extendablelibrary.IoptimeActivity;

public class MainActivity extends IoptimeActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	public void signUp(View v) {
		finish();
		startActivitySwipe(MainActivity.this, new Intent(MainActivity.this,
				SignUp.class));
	}

	public void login(View v) {
		finish();
		startActivitySwipe(MainActivity.this, new Intent(MainActivity.this,
				Login.class));
	}

	@Override
	protected int getLayoutResourceId() {
		// TODO Auto-generated method stub
		return R.layout.activity_main;
	}
}
