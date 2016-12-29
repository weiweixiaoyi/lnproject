package com.example.guo.lnproject.alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.guo.lnproject.utils.Contacts;

public class AlarmReceiver extends BroadcastReceiver {

	private static final String TAG = "AlarmReceiver";

	@Override
	public void onReceive(Context context, Intent intent) {
		if(intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)){
			//开机启动
			AlarmClockManager.getInstance().setNextAlarm(context);
		}else if(intent.getAction().equals(Contacts.ALARM_ACTION)){
			Log.i(TAG , "接收到系统闹钟-------");
			Bundle bundle = intent.getExtras();
			Alarm alarm = (Alarm) bundle.getSerializable("alarm");
			if(alarm != null){
				Log.i(TAG, "系统闹钟不为空-------");
				AlarmNotificationManager.showNotification(context, alarm);
				AlarmClockManager.getInstance().resetAllAlarms(context);
				AlarmClockManager.getInstance().setNextAlarm(context);
			}

		}
//		else if(intent.getAction().equals(Contacts.NOTIFICATION_ACTION)){
//			LogUtils.i(TAG,"通知被点击了...");
//			int type = intent.getIntExtra("alarmType", 0);
//			Intent intent1 = new Intent(context,MainActivity.class);
//			intent1.putExtra("alarmType", type);
//			intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
//					Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
//			context.startActivity(intent1);
//		}
	}

}
