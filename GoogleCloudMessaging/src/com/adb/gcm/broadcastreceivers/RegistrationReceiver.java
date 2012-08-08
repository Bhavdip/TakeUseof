package com.adb.gcm.broadcastreceivers;

import java.util.Random;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;

import com.adb.gcm.utility.Constance;
import com.adb.gcm.utility.Pref;

public class RegistrationReceiver extends BroadcastReceiver {

	private String TAG = getClass().getSimpleName();
	
	private String registrationId;

	public static final String TOKEN =
	        Long.toBinaryString(new Random().nextLong());
	
	public boolean isRegistered() {
		return registrationId != null;
	}

	@Override
	public void onReceive( final Context context, Intent intent ) {
		
		Log.d(TAG,"[ onReceive ] ");
		
		String strMessage = "";
		
		final String registration = intent.getStringExtra( "registration_id" );
		
		if( intent.getStringExtra( "error" ) != null ) {
			
			 if ( intent.getStringExtra( "error" ).equals("SERVICE_NOT_AVAILABLE")) {
				 
				 strMessage = "SERVICE_NOT_AVAILABLE";
				 
				 long backoffTimeMs = Long.valueOf(Pref.getValue(Constance.PREF_BACKOFF_TIMEMS,String.valueOf(System.currentTimeMillis())));
				 
				 long nextAttempt = SystemClock.elapsedRealtime() + backoffTimeMs;
				   Intent retryIntent = new Intent(Constance.INTENT_REGISTRATION_RETRY);
				   retryIntent.putExtra("token", TOKEN);
				   PendingIntent retryPendingIntent =
				       PendingIntent.getBroadcast(context, 0, retryIntent, 0);
				   AlarmManager am = (AlarmManager)   
				       context.getSystemService(Context.ALARM_SERVICE);
				   am.set(AlarmManager.ELAPSED_REALTIME, nextAttempt, retryPendingIntent);
				   backoffTimeMs *= 2; // Next retry should wait longer.
				   // update back-off time on shared preferences
				   Pref.setValue(Constance.PREF_BACKOFF_TIMEMS,String.valueOf(backoffTimeMs));
						   
			 }
			 
			 
			
		} else if( intent.getStringExtra( "unregistered" ) != null ) {
			Pref.setValue(Constance.PREF_REGISTRATION_ID,String.valueOf(""));
			strMessage = "unregistered";
			 
		} else if( registration != null ) {
			
			strMessage = registration;
			
			
			Log.d(TAG, "[ REGISTRATION ID ] " + registration);
			//TODO You may call to third party server 
			Log.d(TAG, String.format("REGISTRATION ID: %s", registration));
			
			Pref.setValue(Constance.PREF_REGISTRATION_ID,String.valueOf(registration));
			
		}
		
		
		// SEND TO Google Cloud activity to update UI
		   Intent mIntentUI = new Intent(Constance.INTENT_UPDATE_UI);
		   mIntentUI.putExtra("Message",strMessage);
		   context.sendBroadcast(mIntentUI);
		
		
		
	}

}
