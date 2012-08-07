package com.adb.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.adb.localserviceipc.LocalServiceActivity;
import com.adb.localserviceipc.R;

public class LocalService extends Service {
	
	private String TAG = LocalService.class.getCanonicalName();
	
	private NotificationManager mNM;
	  
	 // Unique Identification Number for the Notification.
    // We use it on Notification start, and to cancel it.
    private int NOTIFICATION = R.string.title_activity_local_service;
	  
	// This is the object that receives interactions from clients.
    private IBinder mBinder = new LocalBinder();
	
	 /**
     * Class for clients to access.  Because we know this service always
     * runs in the same process as its clients, we don't need to deal with
     * IPC.
     */
    public class LocalBinder extends Binder {
    	
       public LocalService getService() {
            return LocalService.this;
        }
    }
	
    public LocalService() {
    }
    
    /**
     * Called by the system when the service is first created. Do not call this method directly.
     */
    @Override
    public void onCreate() {
    	super.onCreate();
    	 mNM = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
    	 showNotification();
    }
    
	
    @Override
    public void onDestroy() {
    	super.onDestroy();
    	 // Cancel the persistent notification.
        mNM.cancel(NOTIFICATION);

    }
    /**
     * Called by the system every time a client explicitly starts the service by calling startService(Intent), 
     * providing the arguments it supplied and a unique integer token representing the start request. 
     * Do not call this method directly. 
     */
    
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
    	
    	 Log.i("LocalService", "Received start id " + startId + ": " + intent);
         // We want this service to continue running until it is explicitly
         // stopped, so return sticky.
    	return START_STICKY;
    }
    @Override
    public IBinder onBind(Intent intent) {
    	Log.d(TAG,"[Local service | onBind]");
        return mBinder;
    }
    
    /** Public Method which is callback by Activity **/
    
    public void showNotification(){
    	
    	
    	Context context = getApplicationContext();
    	
    	CharSequence contentTitle = "Local service example";
    	CharSequence contentText = "Hi this is Local service";
    	// Set the icon, scrolling text and timestamp
        Notification notification = new Notification(R.drawable.ic_launcher,contentTitle,
                System.currentTimeMillis());
        
    	Intent notificationIntent = new Intent(this,LocalServiceActivity.class);
    	PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

    	notification.setLatestEventInfo(context, contentTitle, contentText, contentIntent);
    	
    	   // Send the notification.
        mNM.notify(NOTIFICATION, notification);
    }
    
}
