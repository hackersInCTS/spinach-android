package com.hackersInCTS.spinach;

import com.google.android.gcm.*;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.plugin.GCM.GCMPlugin;

import static com.hackersInCTS.spinach.CommonUtilities.JS_CALLBACK_METHOD;

public class GCMIntentService extends GCMBaseIntentService {

	private static final String TAG = "GCMIntentService";

	public GCMIntentService() {
		super("GCMIntentService");
	}

	@Override
	public void onRegistered(Context context, String registrationId) {
		Log.i(TAG, "Device registered: regId = " + registrationId);

		JSONObject json;
		try {
			json = new JSONObject().put("event", "registered");
			json.put("regid", registrationId);

			Log.v(TAG + ":onRegistered", json.toString());

			GCMPlugin.sendJavascript(json, JS_CALLBACK_METHOD);
		} catch (JSONException e) {
			Log.e(TAG + ":onRegistered", "JSON exception");
		}
	}

	@Override
	public void onUnregistered(Context context, String registrationId) {
		Log.i(TAG, "Device unregistered: regId = " + registrationId);

		JSONObject json;
		try {
			json = new JSONObject().put("event", "unregistered");
			json.put("regid", registrationId);

			Log.v(TAG + ":onUnregistered", json.toString());

			GCMPlugin.sendJavascript(json, JS_CALLBACK_METHOD);
		} catch (JSONException e) {
			Log.e(TAG + ":onUnregistered", "JSON exception");
		}
	}

	@Override
	protected void onMessage(Context context, Intent intent) {
		Log.i(TAG, "Received message");

		Bundle extras = intent.getExtras();
		if (extras != null) {
			displayMessage(context, extras.getString("payload"));
		}
	}

	@Override
	public void onError(Context context, String errorId) {
		Log.e(TAG + ":onError", "Received error: " + errorId);
	}

	@Override
	protected void onDeletedMessages(Context context, int total) {
		Log.i(TAG + ":onDeletedMessages",
				"Received deleted messages notification");
		String message = String.format("There are %1$d pending messages!",
				total);
		displayMessage(context, message);
	}

	@Override
	protected boolean onRecoverableError(Context context, String errorId) {
		Log.w(TAG + ":onRecoverableError", "Received recoverable error: "
				+ errorId);
		return super.onRecoverableError(context, errorId);
	}

	private static void displayMessage(Context context, String message) {
		if (SpinachApp.isActivityRunning() && SpinachApp.isActivityVisible()) {
			displayMessageInActivity(context, message);
		} else {
			generateStatusNotification(context, message);
		}
	}

	private static void displayMessageInActivity(Context context, String message) {
		Log.d(TAG + ":displayMessageInActivity",
				"Context: " + context.toString());
		Intent intent = new Intent(context, MainActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
				| Intent.FLAG_ACTIVITY_NEW_TASK
				| Intent.FLAG_ACTIVITY_SINGLE_TOP);
		intent.putExtra("message", message);
		context.startActivity(intent);
	}

	@SuppressWarnings("deprecation")
	private static void generateStatusNotification(Context context,
			String messageJson) {
		int icon = R.drawable.ic_stat_gcm;
		long when = System.currentTimeMillis();
		NotificationManager notificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		String title = context.getString(R.string.app_name);
		Intent notificationIntent = new Intent(context, MainActivity.class);
		notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
				| Intent.FLAG_ACTIVITY_NEW_TASK
				| Intent.FLAG_ACTIVITY_SINGLE_TOP);
		notificationIntent.putExtra("message", messageJson);

		String message = "";
		try {
			message = new JSONObject(messageJson).get("message")
					.toString();
		} catch (JSONException e) {
			Log.e(TAG + ":generateStatusNotification", "JSON exception");
			return;
		}

		PendingIntent intent = PendingIntent.getActivity(context, 0,
				notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

		Notification notification = new Notification(icon, message, when);
		notification.defaults |= Notification.DEFAULT_SOUND;
		notification.defaults |= Notification.DEFAULT_VIBRATE;
		notification.flags |= Notification.FLAG_AUTO_CANCEL;
		notification.setLatestEventInfo(context, title, message, intent);
		notificationManager.notify((int) (when % Integer.MAX_VALUE),
				notification);
	}
}
