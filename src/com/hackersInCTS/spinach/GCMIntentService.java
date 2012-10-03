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
			try {
				JSONObject json;
				json = new JSONObject(extras.getString("payload")).put("event",
						"message");

				String message = json.get("message").toString();

				Log.v(TAG + ":onMessage JSON", json.toString());

				generateNotification(context, message);

				GCMPlugin.sendJavascript(json, JS_CALLBACK_METHOD);
			} catch (JSONException e) {
				Log.e(TAG + ":onMessage", "JSON exception");
			}
		}
	}

	@Override
	public void onError(Context context, String errorId) {
		Log.i(TAG + ":onError", "Received error: " + errorId);
	}

	@Override
	protected void onDeletedMessages(Context context, int total) {
		Log.i(TAG + ":onDeletedMessages",
				"Received deleted messages notification");
		String message = String.format(
				"From GCM: server deleted %1$d pending messages!", total);
		generateNotification(context, message);
	}

	@Override
	protected boolean onRecoverableError(Context context, String errorId) {
		// log message
		Log.i(TAG + ":onRecoverableError", "Received recoverable error: "
				+ errorId);
		return super.onRecoverableError(context, errorId);
	}

	/**
	 * Issues a notification to inform the user that server has sent a message.
	 */
	@SuppressWarnings("deprecation")
	private static void generateNotification(Context context, String message) {
		// don't show if app is running and in the foreground
		if (SpinachApp.isActivityRunning() && SpinachApp.isActivityVisible()) {
			Log.v(TAG + ":generateNotification",
					"Skipping statusbar notification as app is in FG.");
			return;
		}
		int icon = R.drawable.ic_stat_gcm;
		long when = System.currentTimeMillis();
		NotificationManager notificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		String title = context.getString(R.string.app_name);
		Intent notificationIntent = new Intent(context, MainActivity.class);
		notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
				| Intent.FLAG_ACTIVITY_NEW_TASK
				| Intent.FLAG_ACTIVITY_SINGLE_TOP);
		
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
