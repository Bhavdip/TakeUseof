package com.adb.messager;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.adb.services.MyService;

public class MainActivity extends Activity implements OnClickListener {

	private String TAG = MainActivity.class.getCanonicalName();
	
	private Button btn_IPC;
	
	 /** Messenger for communicating with the service. */
    Messenger mMessanger = null;
    
    /** Flag indicating whether we have called bind on the service. */
    boolean mBound;
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        btn_IPC = (Button)findViewById(R.id.btn_IPC);
        btn_IPC.setOnClickListener(this);
        
    }
    @Override
    protected void onStart() {
    	super.onStart();
    	Log.d(TAG,"[ on start]");
    	Log.d(TAG,"[ Binde to activity ]");
    	Intent mService = new Intent(getApplicationContext(),MyService.class);
    	bindService(mService,mConnection, Context.BIND_AUTO_CREATE);
    }
    
    @Override
    protected void onStop() {
    	super.onStop();
    	// Unbind from the service
        if (mBound) {
            unbindService(mConnection);
            mMessanger = null;
            mBound = false;
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    
	public void onClick(View v) {
		
		if (!mBound) return;
        // Create and send a message to the service, using a supported 'what' value
        Message msg = Message.obtain(null, MyService.MSG_SAY_HELLO, 0, 0);
        try {
            mMessanger.send(msg);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
		
	}
	
	/**
     * Class for interacting with the main interface of the service.
     */
	private ServiceConnection mConnection = new ServiceConnection() {
		
		public void onServiceDisconnected(ComponentName name) {
			Log.d(TAG, "[onServiceDisconnected ]");
			mMessanger = null;
			mBound = false;
			
		}
		
		public void onServiceConnected(ComponentName name, IBinder service) {
			Log.d(TAG, "[onServiceConnected ]");
			// This is called when the connection with the service has been
            // established, giving us the object we can use to
            // interact with the service.  We are communicating with the
            // service using a Messenger, so here we get a client-side
            // representation of that from the raw IBinder object.
			mMessanger = new Messenger(service);
			mBound = true;
			
		}
	};
	
}
