package com.adb.gcm.broadcastreceivers;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.adb.gcm.GoogleCloud;
import com.adb.gcm.R;

public class MessageReceiver extends BroadcastReceiver {
	
	private String TAG = getClass().getSimpleName();
	
	
    public MessageReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        	
    	Bundle extras = intent.getExtras();
		
		if(extras != null){
			Log.d(TAG,"[ *************  MessageReceiver **************** ] ");
			showNotification( context, R.drawable.ic_launcher, "GCM Test Message", extras.getString( "message" ), GoogleCloud.class );
		}
    }
    
    public void showNotification( Context context, int iconResourceId, String title, String content, Class<? extends Activity> activityToLaunch ) {

		Notification notification = new Notification( iconResourceId, content, System.currentTimeMillis() );

		notification.flags |= Notification.FLAG_AUTO_CANCEL;
		notification.defaults |= Notification.DEFAULT_ALL;

		PendingIntent pendingIntent = PendingIntent.getActivity( context, 0, new Intent( context, activityToLaunch ), PendingIntent.FLAG_UPDATE_CURRENT );

		notification.setLatestEventInfo( context, title, content, pendingIntent );

		NotificationManager notificationManager = (NotificationManager) context.getSystemService( Context.NOTIFICATION_SERVICE );
		notificationManager.notify( 1, notification );

	}

}
