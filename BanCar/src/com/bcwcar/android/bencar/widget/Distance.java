package com.bcwcar.android.bencar.widget;

import java.text.DecimalFormat;

public class Distance {

	 private static final double EARTH_RADIUS = 6378137.0;  
	 // 返回单位是米  
	 
	
	 public static String getDistance(String lo1, String la1,  
			 String lo2, String la2) {  
		 double longitude1=Double.parseDouble(lo1);
		 double latitude1=Double.parseDouble(la1);
		 double longitude2=Double.parseDouble(lo2);
		 double latitude2=Double.parseDouble(la2);
		 
	  double Lat1 = rad(latitude1);  
	  double Lat2 = rad(latitude2);  
	  double a = Lat1 - Lat2;  
	  double b = rad(longitude1) - rad(longitude2);  
	  double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)  
	    + Math.cos(Lat1) * Math.cos(Lat2)  
	    * Math.pow(Math.sin(b / 2), 2)));  
	  s = s * EARTH_RADIUS;  
	  s = Math.round(s * 10000) / 10000;  
	  DecimalFormat    df   = new DecimalFormat("######0.00"); 
	  
	  return df.format(s/1000); 
	 
	 }  
	 private static double rad(double d) {  
	  return d * Math.PI / 180.0;  
	 }  
	} 