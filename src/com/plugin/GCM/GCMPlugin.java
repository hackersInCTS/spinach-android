package com.plugin.GCM;

//import java.io.*;
//import java.util.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import org.apache.cordova.api.Plugin;
import org.apache.cordova.api.PluginResult;
import org.apache.cordova.api.PluginResult.Status;
import com.google.android.gcm.*;

public class GCMPlugin extends Plugin {

	public static final String ME = "GCMPlugin";

	public static final String REGISTER = "register";
	public static final String UNREGISTER = "unregister";

	public static Plugin gwebView;
	private static String gSenderID;

	@SuppressWarnings("deprecation")
	@Override
	public PluginResult execute(String action, JSONArray data, String callbackId) {

		PluginResult result = null;

		Log.v(ME + ":execute", "action=" + action);

		if (REGISTER.equals(action)) {

			Log.v(ME + ":execute", "data=" + data.toString());

			try {

				JSONObject jo = new JSONObject(data.toString().substring(1,
						data.toString().length() - 1));

				gwebView = this;

				Log.v(ME + ":execute", "jo=" + jo.toString());

				gSenderID = (String) jo.get("senderID");

				Log.v(ME + ":execute", "senderID=" + gSenderID);

				GCMRegistrar.register(this.cordova.getContext(), gSenderID);

				Log.v(ME + ":execute", "GCMRegistrar.register called ");

				result = new PluginResult(Status.OK);
			} catch (JSONException e) {
				Log.e(ME, "Got JSON Exception " + e.getMessage());
				result = new PluginResult(Status.JSON_EXCEPTION);
			}
		} else if (UNREGISTER.equals(action)) {

			GCMRegistrar.unregister(this.cordova.getContext());
			Log.v(ME + ":" + UNREGISTER, "GCMRegistrar.unregister called ");

		} else {
			result = new PluginResult(Status.INVALID_ACTION);
			Log.e(ME, "Invalid action : " + action);
		}

		return result;
	}

	public static void sendJavascript(JSONObject _json,
			String callbackMethodName) {
		String _d = "javascript:" + callbackMethodName + "(" + _json.toString()
				+ ")";
		Log.v(ME + ":sendJavascript", _d);
		if (gwebView != null) {
			gwebView.sendJavascript(_d);			
		} else {
			Log.d(ME + ":sendJavascript", "Failed as there is no instance of the plugin!");
		}
	}

}
