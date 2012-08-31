package com.adb.custom.text;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

public class DroidTextView extends TextView{

	private static final String TAG = DroidTextView.class.getCanonicalName();
	
	public DroidTextView(Context context) {
		super(context);
	}
	
	public DroidTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setCustomFont(context, attrs);
	}
	
	public DroidTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		setCustomFont(context, attrs);
	}
	
	public void setCustomFont(Context mContext, AttributeSet mAttrs){
			TypedArray mTypedArray = mContext.obtainStyledAttributes(mAttrs,R.styleable.DroidTextView);
			String customFont = mTypedArray.getString(R.styleable.DroidTextView_customFont);		//style name + attr name
			setCustomFont(mContext,customFont);
			mTypedArray.recycle();
			
	}
	public boolean setCustomFont(Context mContext, String mAsset){
		Typeface mTypeface = null;
		try{
			StringBuilder mBuilderPath = new StringBuilder("fonts/");
			mBuilderPath.append(mAsset);
			Log.d(TAG,"[ FETCH FONT NAME ]" + mAsset + "[ PATH ]" + mBuilderPath.toString());
			mTypeface = Typeface.createFromAsset(Constance.mContext.getAssets(),mBuilderPath.toString());
		}catch (Exception e) {
			e.printStackTrace();
			Log.d(TAG, "[ Could not load font family ]");
			return false;
		}
		setTypeface(mTypeface);
		return true;
	}
	

}
