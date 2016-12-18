package com.example.guo.lnproject.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;

/**
 * 像素和dp之间的转化
 * @author Administrator
 *
 */
public class DensityUtil {
	 /** 
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素) 
     */  
    public static int dip2px(Context context, float dpValue) {  
        final float scale = context.getResources().getDisplayMetrics().density;  
        return (int) (dpValue * scale + 0.5f);  
    }  
  
    /** 
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp 
     */  
    public static int px2dip(Resources resources, float pxValue) {
        final float scale = resources.getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);  
    }
    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Resources resources, float dpValue) {
        final float scale = resources.getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     */
    public static int px2sp(Context context, float pxValue) { 
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity; 
        return (int) (pxValue / fontScale + 0.5f); 
    } 
   
    /**
     * 将sp值转换为px值，保证文字大小不变
     */
    public static int sp2px(Context context, float spValue) { 
    	if(context==null){
    		return 0;
    	}
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity; 
        return (int) (spValue * fontScale + 0.5f); 
    }
    /**
     * 将px值转换为sp值，保证文字大小不变
     */
    public static int px2sp(Resources resources, float pxValue) {
        final float fontScale = resources.getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     */
    public static int sp2px(Resources resources, float spValue) {
        if(resources==null){
            return 0;
        }
        final float fontScale =resources.getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 获取屏幕的宽度和高度
     * @param ctx
     * @return
     */ 
    public static int[] getScreenMetrics(Activity ctx){
    	int[] results = new int[2];
    	DisplayMetrics outMetrics = new DisplayMetrics();
    	ctx.getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
    	results[0] = outMetrics.widthPixels;
    	results[1] = outMetrics.heightPixels;
    	
    	return results;
    }
}
