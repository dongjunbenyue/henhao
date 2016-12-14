package com.bcwcar.android.bencar.base;

import android.content.Context;
import io.rong.imkit.RongIM;

public class Model {
	private RongIM.LocationProvider.LocationCallback mLastLocationCallback;
	private static Model model;
	 public static void init(Context context) {
	        model = new Model();
	    }

	    public static Model getInstance() {

	        if (model == null) {
	        	model = new Model();
	        }
	        return model;
	    }
	    
	    
	  public RongIM.LocationProvider.LocationCallback getLastLocationCallback() {
	        return mLastLocationCallback;
	    }

	    public void setLastLocationCallback(RongIM.LocationProvider.LocationCallback lastLocationCallback) {
	        this.mLastLocationCallback = lastLocationCallback;
	    }

	
	
	
}
