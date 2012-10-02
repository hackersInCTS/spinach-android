package com.hackersInCTS.spinach;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import org.apache.cordova.*;

import com.google.android.gcm.GCMRegistrar;

import static com.hackersInCTS.spinach.CommonUtilities.DISPLAY_MESSAGE_ACTION;

public class MainClass extends DroidGap {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		GCMRegistrar.checkDevice(this);
		GCMRegistrar.checkManifest(this);
		registerReceiver(mHandleMessageReceiver, new IntentFilter(
				DISPLAY_MESSAGE_ACTION));
		super.loadUrl("file:///android_asset/www/xpsrc/index.html");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main_class, menu);
		return true;
	}

	private final BroadcastReceiver mHandleMessageReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String newMessage = intent.getExtras().getString("message");
			Log.d(TAG + ":onReceive", "Message received by mHandleMessageReceiver: "
					+ newMessage);
		}
	};

	@Override
	public void onDestroy() {
		Log.d(TAG + ":onDestroy", "MainClass is being destroyed!");
		unregisterReceiver(mHandleMessageReceiver);
		GCMRegistrar.onDestroy(this);
		super.onDestroy();
	}
}
