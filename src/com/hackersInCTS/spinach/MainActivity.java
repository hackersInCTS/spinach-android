package com.hackersInCTS.spinach;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import org.apache.cordova.*;

import com.google.android.gcm.GCMRegistrar;

public class MainActivity extends DroidGap {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		GCMRegistrar.checkDevice(this);
		GCMRegistrar.checkManifest(this);
		super.loadUrl("file:///android_asset/www/xpsrc/index.html");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main_class, menu);
		return true;
	}

	@Override
	public void onDestroy() {
		Log.d(TAG + ":onDestroy", "MainClass is being destroyed!");
		GCMRegistrar.onDestroy(this);
		super.onDestroy();
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		SpinachApp.activityStarted();
	};
	
	@Override 
	protected void onStop() {
		super.onStop();
		SpinachApp.activityStopped();
	};
	
	@Override 
	protected void onPause() {
		super.onPause();
		SpinachApp.activityPaused();
	};
	
	@Override 
	protected void onResume() {
		super.onResume();
		SpinachApp.activityResumed();
	};	
}
