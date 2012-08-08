package com.adb.gcm.broadcastreceivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.adb.gcm.services.RegisteringGCMService;
import com.adb.gcm.utility.Constance;


public class ShutdownReceiver extends BroadcastReceiver {

	@Override
	public void onReceive( Context context, Intent intent ) {
		Log.d( getClass().getSimpleName(), " [ Stopping Google Cloud Messaging service. ]" );
		Constance.CONTEXT = context;
		Constance.CONTEXT.stopService(new Intent(context, RegisteringGCMService.class));
	}

}
