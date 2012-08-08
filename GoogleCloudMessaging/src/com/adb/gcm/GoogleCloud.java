package com.adb.gcm;

import com.adb.gcm.services.RegisteringGCMService;
import com.adb.gcm.utility.Constance;
import com.adb.gcm.utility.Pref;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;

public class GoogleCloud extends Activity {
	
	private String TAG = GoogleCloud.class.getCanonicalName();
	private TextView textView_registrationID;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_cloude);
        Constance.CONTEXT = this;
        textView_registrationID = (TextView)findViewById(R.id.textView_registrationID);
        textView_registrationID.setText(Pref.getValue(Constance.PREF_REGISTRATION_ID,""));
        Log.d(TAG,"[ onCreate ]");
        startService( new Intent(getApplicationContext(),RegisteringGCMService.class ) );
    }
    
    @Override
    protected void onStart() {
    	super.onStart();
    	Log.d(TAG,"[ onStart ]");
    	IntentFilter iff = new IntentFilter();
    	iff.addAction(Constance.INTENT_UPDATE_UI);
    	registerReceiver(mUIReceiver, iff);
    }
    
    
    @Override
    protected void onPause() {
    	super.onPause();
    	Log.d(TAG,"[ onPause ]");
    	unregisterReceiver(mUIReceiver);
    	
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_google_cloude, menu);
        return true;
    }
    
    private BroadcastReceiver mUIReceiver = new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			
			final String meesage = intent.getStringExtra("Message");
			
			if(intent.getStringExtra("Message") != null ){
				Log.d(TAG,"[ ********** UI UPDATE ***********]");
				runOnUiThread(new Runnable() {
					
					public void run() {
						if(meesage != null)
						textView_registrationID.setText(meesage);
					}
				});
			}
			
		}
	};
}
