package com.adb.gcm.utility;

import android.content.Context;

public class Constance {

	// APPLICATION PREFERENCE FILE NAME
	public static String PREF_FILE = "PREF_GCM_STORE";
	public static Context CONTEXT;
	
	final public static String PREF_BACKOFF_TIMEMS = "PREF_BACKOFF_TIMEMS";
	final public static String PREF_REGISTRATION_ID = "REGISTRATION_ID";
	
	final public static String INTENT_REGISTRATION_RETRY = "com.adb.gcm.intent.RETRY";
	final public static String INTENT_UPDATE_UI = "com.adb.gcm.intent.UPDATE.UI";

}
