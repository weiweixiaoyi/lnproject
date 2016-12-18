package com.example.guo.lnproject.alarm;

import java.util.Calendar;
import java.util.List;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.guo.lnproject.utils.Contacts;
import com.example.guo.lnproject.utils.LogUtils;
import com.example.guo.lnproject.utils.ToastUtils;

public class AlarmClockManager {

	private static final String TAG = "AlarmClockManager";
	private static AlarmClockManager mInstance;
	private AlarmManager aManager;
	private Calendar calendar;
	private AlarmClockManager(){
		
		calendar = Calendar.getInstance();
	} 
	public static synchronized AlarmClockManager getInstance() {
		if (mInstance == null) {
			mInstance = new AlarmClockManager();
		}
		return mInstance;
	}
	
	public void setAlarm(Alarm alarm,Context context){
		long timeMillis = time2Millis(alarm.getHour(), alarm.getMinute());
		ContentValues values = new ContentValues();
		values.put(AlarmColumns.NEXTMILLIS, timeMillis);
		AlarmDBManager.getInstance().updateAlarm(alarm.getId(), values);
		setNextAlarm(context);
		formatTip(context,timeMillis);
	}

	public void resetAlarm(Alarm alarm,Context context){
		long timeMillis = time2Millis(alarm.getHour(), alarm.getMinute());
		ContentValues values = new ContentValues();
		values.put(AlarmColumns.NEXTMILLIS, timeMillis);
		AlarmDBManager.getInstance().updateAlarm(alarm.getId(), values);
	}
	
	public void setNextAlarm(Context context){
		Alarm alarm = AlarmDBManager.getInstance().getNextAlarm();
		if(alarm != null){
			LogUtils.i(TAG, "setNextAlarm--hour = " + alarm.getHour() + ",minute = " + alarm.getMinute() + ",nextMillions = " + alarm.getNextMillis());
			Intent intent = new Intent(Contacts.ALARM_ACTION);
			Bundle bundle = new Bundle();
			bundle.putSerializable("alarm",alarm);
			intent.putExtras(bundle);
			PendingIntent pi = PendingIntent.getBroadcast(context, alarm.getId(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
			aManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
			aManager.set(AlarmManager.RTC_WAKEUP, alarm.getNextMillis(), pi);
		}
	}

	public void cancelNextAlarm(Context context,Alarm alarm){
		Intent intent = new Intent(Contacts.ALARM_ACTION);
		Bundle bundle = new Bundle();
		bundle.putSerializable("alarm",alarm);
		intent.putExtras(bundle);
		PendingIntent pi = PendingIntent.getBroadcast(context,alarm.getId(),intent,PendingIntent.FLAG_UPDATE_CURRENT);
		aManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		aManager.cancel(pi);
		setNextAlarm(context);
	}
	
	public  void cancelAlarm(Context context , int id ){
		Intent intent = new Intent("android.intent.action.ALARM_RECEIVER");
		PendingIntent pi = PendingIntent.getBroadcast(context, id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		aManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		aManager.cancel(pi);
		setNextAlarm(context);
	}
	
	private void formatTip(Context context ,long timeMillis) {
	    long delta = timeMillis - System.currentTimeMillis();
		LogUtils.i(TAG,"时间间隔 = "+delta);
		long hours = delta / (1000 * 60 * 60);
        long minutes = delta / (1000 * 60) % 60;
        long days = hours / 24;
        hours = hours % 24;
        String daySeq = (days == 0) ? "" : days+"天";
        String hourSeq = (hours == 0) ? "" : hours + "小时";
        String minSeq = (minutes == 0) ? "1分钟" : minutes + "分钟";
		ToastUtils.showToast(context, "已将闹钟设置为从现在起" + daySeq + hourSeq + minSeq + "后提醒");
	}
	
	public Long time2Millis(int hour , int minute){
		calendar.setTimeInMillis(System.currentTimeMillis());
		calendar.set(Calendar.HOUR_OF_DAY, hour);
		calendar.set(Calendar.MINUTE, minute);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		if (calendar.getTimeInMillis() - System.currentTimeMillis() <= 0) {//如果闹钟时间小于或者等于当前系统时间，则闹钟向后推迟一天
			LogUtils.i(TAG,"闹钟millis = "+calendar.getTimeInMillis()+"闹钟时间小于当前时间..."+"当前时间= "+System.currentTimeMillis());
			calendar.roll(Calendar.DATE, 1);
		}
		return calendar.getTimeInMillis();
	}

	public void resetAllAlarms(Context context){
		List<Alarm> alarms = AlarmDBManager.getInstance().getAllAlarm();
		if(alarms != null && alarms.size()>0){
			for (int i = 0; i < alarms.size(); i++) {
				Alarm alarm = alarms.get(i);
				resetAlarm(alarm, context);
			}
		}
	}
}
