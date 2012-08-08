package com.adb.gcm.services;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.adb.gcm.R;

public class RegisteringGCMService extends Service {
    
	public String TAG = RegisteringGCMService.class.getSimpleName();
	
	public RegisteringGCMService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
    
    @Override
    public void onCreate() {
    	super.onCreate();
    	
    	
		Log.d(TAG," [** START RegisteringGCMService **]");
		
		Intent registration = new Intent( "com.google.android.c2dm.intent.REGISTER" );

		registration.putExtra( "app", PendingIntent.getBroadcast( this, 0, new Intent(), PendingIntent.FLAG_UPDATE_CURRENT ) );
		registration.putExtra( "sender", getString( R.string.gcm_sender_id ) );
		Log.d(TAG,"SENDER ID [ PROJECT ID ]:  " + getString(R.string.gcm_sender_id));
		startService( registration );

    	
    }
    
    @Override
    public void onDestroy() {
    	super.onDestroy();
    	
    	Log.d(TAG," [** Stop RegisteringGCMService  **]");
		Intent unregister = new Intent( "com.google.android.c2dm.intent.UNREGISTER" );
		unregister.putExtra( "app", PendingIntent.getBroadcast( this, 0, new Intent(), PendingIntent.FLAG_UPDATE_CURRENT ) );
		startService( unregister );

    }
}
