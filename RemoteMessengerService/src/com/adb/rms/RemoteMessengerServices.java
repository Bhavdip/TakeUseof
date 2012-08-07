package com.adb.rms;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;

import com.adb.services.RemoteService;

public class RemoteMessengerServices extends Activity {
	
	private String TAG = RemoteMessengerServices.class.getCanonicalName();
	
	TextView mCallbackText;
	
	Messenger mService = null;
	
	boolean isBind;
	
	/**
	 * Handler of incoming messages from service.
	 */
	private class IncomingHandler extends Handler {
	    @Override
	    public void handleMessage(Message msg) {
	        switch (msg.what) {
	            case RemoteService.MSG_SET_VALUE:
	                mCallbackText.setText("Received from service: " + msg.arg1);
	                break;
	            default:
	                super.handleMessage(msg);
	        }
	    }
	}
	
	/**
	 * Target we publish for clients to send messages to IncomingHandler.
	 */
	final Messenger mMessenger = new Messenger(new IncomingHandler());
	
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remote_messenger_services);
        mCallbackText = (TextView)findViewById(R.id.mCallbackText);
        
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_remote_messenger_services, menu);
        return true;
    }
    
    @Override
    protected void onStart() {
    	super.onStart();
    	Log.d(TAG, "[ onStart ]");
    	doBindService();
    }
    
    @Override
    protected void onStop() {
    	super.onStop();
    	Log.d(TAG, "[ onStop ]");
    	doUnBindService();
    	
    }
    
    void doBindService(){
    	 // Establish a connection with the service.  We use an explicit
        // class name because there is no reason to be able to let other
        // applications replace our component.
    	
    	mCallbackText.setText("Binding.");
    	isBind = true;
    	bindService(new Intent(RemoteMessengerServices.this,RemoteService.class), mConnection, Context.BIND_AUTO_CREATE);
    	
    }
    
    void doUnBindService(){
    	if(isBind){
    		
    		// If we have received the service, and hence registered with
            // it, then now is the time to unregister.
            if (mService != null) {
                try {
                    Message msg = Message.obtain(null,
                            RemoteService.MSG_UNREGISTER_CLIENT);
                    msg.replyTo = mService;
                    mService.send(msg);
                } catch (RemoteException e) {
                    // There is nothing special we need to do if the service
                    // has crashed.
                }
            }

    		unbindService(mConnection);
    		isBind = false;
    		mService = null;
			mCallbackText.setText("Unbinding.");
    		
    	}
    }
    
    private ServiceConnection mConnection = new ServiceConnection() {
		
		@Override
		public void onServiceDisconnected(ComponentName name) {
			Log.d(TAG, "[ onServiceDisconnected ]");
			mCallbackText.setText("Disconnected.");
			isBind = false;
			mService = null;
		}
		
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			Log.d(TAG, "[ onServiceConnected ]");
			mCallbackText.setText("Attached.");
			mService = new Messenger(service);
			isBind = true;
			
			   // We want to monitor the service for as long as we are
	        // connected to it.
	        try {
	            Message msg = Message.obtain(null,
	                    RemoteService.MSG_REGISTER_CLIENT);
	            msg.replyTo = mMessenger;
	            mService.send(msg);

	            // Give it some value as an example.
	            msg = Message.obtain(null,
	                    RemoteService.MSG_SET_VALUE, this.hashCode(), 0);
	            mService.send(msg);
	        } catch (RemoteException e) {
	            // In this case the service has crashed before we could even
	            // do anything with it; we can count on soon being
	            // disconnected (and then reconnected if it can be restarted)
	            // so there is no need to do anything here.
	        }
		}
	};


}
