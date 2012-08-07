package com.adb.services;

import java.util.Random;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

/**
 * 
 * @author Plenar001
 *	 here's a service that provides clients access to methods in the service through a Binder implementation:
 */


public class LocalService extends Service {
		
	private String TAG = LocalService.class.getCanonicalName();
		
	 // Binder given to clients
    private final IBinder mBinder = new LocalBinder();
	
	// Random number generator
    private final Random mGenerator = new Random();
	
	/**
     * Class used for the client Binder.  Because we know this service always
     * runs in the same process as its clients, we don't need to deal with IPC.
     */
    public class LocalBinder extends Binder {
    	
        public LocalService getService() {
            // Return this instance of LocalService so clients can call public methods which is show in this service getRandomNumber
            return LocalService.this;
        }
    }
	
    
    public LocalService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        // IF You define Binder Object then return Object of Binder
    	// else case uncommit following code
    		//throw new UnsupportedOperationException("Not yet implemented");
    	Log.d(TAG,"[ ON BIND CALLED ]");
    	Toast.makeText(getApplicationContext(), "binding", Toast.LENGTH_SHORT).show();
    	return mBinder;
    }
    
    
    /** method for clients */
    public int getRandomNumber() {
      return mGenerator.nextInt(100);
    }
}
