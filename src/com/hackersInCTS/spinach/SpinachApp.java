package com.hackersInCTS.spinach;

import android.app.Application;

public class SpinachApp extends Application {

	private static boolean uiRunning;

	public static boolean isActivityRunning() {
		return uiRunning;
	}

	public static void activityStarted() {
		uiRunning = true;
	}

	public static void activityStopped() {
		uiRunning = false;
	}

	private static boolean activityVisible;

	public static boolean isActivityVisible() {
		return activityVisible;
	}

	public static void activityResumed() {
		activityVisible = true;
	}

	public static void activityPaused() {
		activityVisible = false;
	}

}
