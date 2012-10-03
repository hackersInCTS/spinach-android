package com.hackersInCTS.spinach;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import org.apache.cordova.*;

import com.google.android.gcm.GCMRegistrar;

public class MainActivity extends DroidGap {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.v(TAG, "onCreate Starts");
		super.onCreate(savedInstanceState);
		GCMRegistrar.checkDevice(this);
		GCMRegistrar.checkManifest(this);
		super.loadUrl("file:///android_asset/www/xpsrc/index.html");
		Log.v(TAG, "onCreate Ends");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main_class, menu);
		return true;
	}

	@Override
	public void onDestroy() {
		Log.v(TAG, "onDestroy Starts");
		Log.d(TAG + ":onDestroy", "MainClass is being destroyed!");
		GCMRegistrar.onDestroy(this);
		super.onDestroy();
		Log.v(TAG, "onDestroy Ends");
	}
	
	@Override
	protected void onStart() {
		Log.v(TAG, "onStart Starts");
		super.onStart();
		SpinachApp.activityStarted();
		Log.v(TAG, "onStart End");
	};
	
	@Override 
	protected void onStop() {
		Log.v(TAG, "onStop Starts");
		super.onStop();
		SpinachApp.activityStopped();
		Log.v(TAG, "onStop Ends");
	};
	
	@Override 
	protected void onPause() {
		Log.v(TAG, "onPause Starts");
		super.onPause();
		SpinachApp.activityPaused();
		Log.v(TAG, "onPause Ends");
	};
	
	@Override 
	protected void onResume() {
		Log.v(TAG, "onResume Starts");
		super.onResume();
		SpinachApp.activityResumed();
		Log.v(TAG, "onResume Starts");
	};	
}
