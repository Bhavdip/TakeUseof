package com.adb.gcm.broadcastreceivers;

import com.adb.gcm.services.RegisteringGCMService;
import com.adb.gcm.utility.Constance;
import com.adb.gcm.utility.Pref;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class RetryRegistrationReceiver extends BroadcastReceiver {
	
	private String TAG = getClass().getSimpleName();
	
    public RetryRegistrationReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
    	
    	Log.d(TAG,"[ onReceive ] ");
		final String token = intent.getStringExtra("token");
		
		if( intent.getStringExtra( "token" ) != null ) {
			
			 if (RegistrationReceiver.TOKEN.equals(token)) {
				 
		 			String registrationId =Pref.getValue(Constance.PREF_REGISTRATION_ID,null); 
		 			Log.d(TAG,"[ On Retry Registration ID ] " + registrationId);
		 			if (registrationId != null) {
		 				
		 					Log.d(TAG,"[last operation was attempt to unregister; send UNREGISTER intent again ]");
		 					context.stopService(new Intent(Constance.CONTEXT, RegisteringGCMService.class));
		 					
					    } else{
					    	
		 					Log.d(TAG,"[last operation was attempt to Register; send Register intent again ]");
					    	context.startService(new Intent(Constance.CONTEXT, RegisteringGCMService.class));
					    	
					    }
			 }
		}
		
		
    }
}
