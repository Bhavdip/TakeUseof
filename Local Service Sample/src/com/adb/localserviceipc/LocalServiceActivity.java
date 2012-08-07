package com.adb.localserviceipc;

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

import com.adb.services.LocalService;

public class LocalServiceActivity extends Activity implements OnClickListener{

	
	private String TAG = LocalServiceActivity.class.getCanonicalName();
	
	LocalService mBoundService = null;
	boolean mIsBound;
	private Button btn_showNotification;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_service);
        btn_showNotification = (Button)findViewById(R.id.btn_showNotification);
        btn_showNotification.setOnClickListener(this);
    }
    
    @Override
    protected void onStart() {
    	super.onStart();
    	Log.d(TAG,"[ onStart ]");
    	doBindService();
    }
    
    @Override
    protected void onStop() {
    	super.onStop();
    	Log.d(TAG,"[ onStop ]");
    	doUnbindService();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_local_service, menu);
        return true;
    }
    
    void doBindService(){
    	
    	Log.d(TAG,"[ doBindService ]");
    	
    	  // Establish a connection with the service.  We use an explicit
        // class name because we want a specific service implementation that
        // we know will be running in our own process (and thus won't be
        // supporting component replacement by other applications).
    	bindService(new Intent(LocalServiceActivity.this,LocalService.class),mConnection,Context.BIND_AUTO_CREATE);
    	
    	
    }
    void doUnbindService(){
    	
    	if(mIsBound){
    		
    		Log.d(TAG,"[ doUnbindService ]");
    		// Detach our existing connection.
    		unbindService(mConnection);
       	 	mIsBound = false;

    	}
    	   	 	
    }
    
    private ServiceConnection mConnection = new ServiceConnection() {
		
		public void onServiceDisconnected(ComponentName name) {
			// TODO Auto-generated method stub
			Log.d(TAG,"[ onServiceDisconnected ]");
			mBoundService = null;
			mIsBound = false;
		}
		
		public void onServiceConnected(ComponentName name, IBinder service) {
			// TODO Auto-generated method stub
			
			Log.d(TAG,"[ onServiceConnected ]");
			mBoundService = ((LocalService.LocalBinder)service).getService();
			mIsBound = true;
			
		}
	};

	public void onClick(View v) {
		
		if(mIsBound){
			mBoundService.showNotification();
		}
	}
	
	
}
