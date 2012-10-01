package com.hackersInCTS.spinach;

import com.google.android.gcm.*;
import org.json.JSONException;
import org.json.JSONObject;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import com.plugin.GCM.GCMPlugin;

public class GCMIntentService extends GCMBaseIntentService {

	public static final String ME = "GCMReceiver";
	private static final String CordovaCallbackMethod = "Spinach.GCM.callback";

	public GCMIntentService() {
		super("GCMIntentService");
	}

	private static final String TAG = "GCMIntentService";

	@Override
	public void onRegistered(Context context, String regId) {

		Log.v(ME + ":onRegistered", "Registration ID arrived!");
		Log.v(ME + ":onRegistered", regId);

		JSONObject json;

		try {
			json = new JSONObject().put("event", "registered");
			json.put("regid", regId);

			Log.v(ME + ":onRegisterd", json.toString());

			// Send this JSON data to the JavaScript application above EVENT
			// should be set to the msg type
			// In this case this is the registration ID
			GCMPlugin.sendJavascript(json, CordovaCallbackMethod);

		} catch (JSONException e) {
			// No message to the user is sent, JSON failed
			Log.e(ME + ":onRegisterd", "JSON exception");
		}
	}

	@Override
	public void onUnregistered(Context context, String regId) {
		Log.d(TAG, "onUnregistered - regId: " + regId);
	}

	@Override
	protected void onMessage(Context context, Intent intent) {
		Log.d(TAG, "onMessage - context: " + context);

		// Extract the payload from the message
		Bundle extras = intent.getExtras();
		if (extras != null) {
			try {
				Log.v(ME + ":onMessage extras payload ",
						extras.getString("payload"));

				JSONObject json;
				json = new JSONObject(extras.getString("payload")).put("event",
						"message");

				Log.v(ME + ":onMessage ", json.toString());

				GCMPlugin.sendJavascript(json, CordovaCallbackMethod);
				// Send the MESSAGE to the Javascript application
			} catch (JSONException e) {
				Log.e(ME + ":onMessage", "JSON exception");
			}
		}

	}

	@Override
	public void onError(Context context, String errorId) {
		Log.e(TAG, "onError - errorId: " + errorId);
	}

}
