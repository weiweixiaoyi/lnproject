package com.example.guo.lnproject.utils;

import android.util.Log;

public class LogUtils {
	private static boolean DEBUG = true;
	
	public static void d(String tag, String msg){
		if (DEBUG){
			Log.d(tag, msg);
		}
	}
	
	public static void e(String tag, String msg){
		if (DEBUG){
			Log.e(tag, msg);
		}
	}
	
	public static void i(String tag, String msg){
		if (DEBUG){
			Log.i(tag,msg);
		}
	}
}
