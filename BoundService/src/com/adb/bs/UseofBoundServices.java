package com.adb.bs;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.adb.services.LocalService;
import com.adb.services.LocalService.LocalBinder;

public class UseofBoundServices extends Activity implements OnClickListener{
	
	public String TAG = UseofBoundServices.class.getCanonicalName();
	
	/**
	 * Here's an activity that binds to LocalService and calls getRandomNumber() when a button is clicked:
	 */
	
	LocalService mLocalServices;
	boolean mBound = false;
	private Button btn_sayRandom;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"[ on create ]");
        setContentView(R.layout.activity_useof_bound_services);
        btn_sayRandom = (Button)findViewById(R.id.btn_sayRandom);
        btn_sayRandom.setOnClickListener(this);
        
        
    }
    
    @Override
    protected void onStart() {
    	super.onStart();
    	 // Bind to LocalService
		Log.d(TAG,"[ onStart ]");
		Intent intent = new Intent(this, LocalService.class);
		bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }
    
    @Override
    protected void onStop() {
    	super.onStop();
    	Log.d(TAG,"[ onStop ]");
    	// Unbind from the service
		  if (mBound) {
	          unbindService(mConnection);
	          mBound = false;
	      }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_useof_bound_services, menu);
        return true;
    }
    
    
    /** Defines callbacks for service binding, passed to bindService() */
    
    private ServiceConnection mConnection = new ServiceConnection() {
		
    	
    	
    	/**
    	 * Called when a connection to the Service has been lost
    	 */
		public void onServiceDisconnected(ComponentName name) {
				
			//Now turn is to set mBound to false
			Log.d(TAG,"[ onServiceDisconnected ]");
			mBound = false;
			
		}
		
		/**
		 * Called when a connection to the Service has been established, with the IBinder of the communication channel to the Service.
		 */
		public void onServiceConnected(ComponentName name, IBinder service) {
			 // We've bound to LocalService, cast the IBinder and get LocalService instance
			
			
			Log.d(TAG,"[ onServiceConnected ]");
			
			 LocalBinder binder = (LocalBinder) service;
			 mLocalServices = binder.getService();
			 mBound = true;
			 
			 
			 
			 
		}
	};

	
	public void onClick(View v) {
		
		switch (v.getId()) {
		case R.id.btn_sayRandom:
			
			 if (mBound) {
		            // Call a method from the LocalService.
		            // However, if this call were something that might hang, then this request should
		            // occur in a separate thread to avoid slowing down the activity performance.
		            int num = mLocalServices.getRandomNumber();
		            Toast.makeText(this, "number: " + num, Toast.LENGTH_SHORT).show();
		        }
			break;
		
		default:
			break;
		}
		
	}
    
}
