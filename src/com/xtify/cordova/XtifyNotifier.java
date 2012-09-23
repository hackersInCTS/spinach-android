package com.xtify.cordova;

import android.content.Context;
import android.os.Bundle;

import com.xtify.sdk.api.XtifyBroadcastReceiver;

public class XtifyNotifier extends XtifyBroadcastReceiver {
	@Override
	protected void onMessage(Context context, Bundle msgExtras) {
	}

	@Override
	protected void onRegistered(Context context) {
	}

	@Override
	protected void onC2dmError(Context context, String errorId) {
		context.getSharedPreferences(XtifyCordovaPlugin.PREFS_NAME, Context.MODE_PRIVATE).edit().putString(XtifyCordovaPlugin.KEY_C2DM_ERROR_ID, errorId).commit();
	}

}
