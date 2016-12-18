package com.example.guo.lnproject.alarm;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;

import com.example.guo.lnproject.MainActivity;
import com.example.guo.lnproject.R;
import com.example.guo.lnproject.utils.CommonSPManager;
import com.example.guo.lnproject.utils.Contacts;
import com.example.guo.lnproject.utils.LogUtils;


public class AlarmNotificationManager {

	private static final String TAG = "AlarmNotificationManager";

	@SuppressLint("NewApi")
	public static void showNotification(Context context,Alarm alarm){
		NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		String title = context.getResources().getString(R.string.app_name);
		String hourStr = (alarm.getHour()+"").length() == 1 ? "0" + alarm.getHour() : alarm.getHour() + "";
		String minutesStr = (alarm.getMinute()+"").length() == 1 ? "0" + alarm.getMinute() : alarm.getMinute() + "";
		String str = hourStr + ":" + minutesStr + "\t" + alarm.getLabel();
		int bellType = CommonSPManager.getBell(context);
		Uri uri = Uri.parse("android.resource://" + context.getPackageName() + "/" + (bellType == CommonSPManager.BELL_LONG?R.raw.bell_long:R.raw.bell_short));
		Notification.Builder mBuilder = new Notification.Builder(context)
				.setContentTitle(title)
				.setContentIntent(getDefalutIntent(context,alarm))
				.setSmallIcon(R.mipmap.logo)
				.setContentText(str)
				.setSound(uri)
				.setWhen(System.currentTimeMillis());
		Notification notification = mBuilder.getNotification();
		notification.flags |= Notification.FLAG_AUTO_CANCEL;
		notificationManager.notify(0, notification);
	}

	public static PendingIntent getDefalutIntent(Context context,Alarm alarm){
//		Intent intent = new Intent(Contacts.NOTIFICATION_ACTION);
		Intent intent = new Intent(context,MainActivity.class);
		intent.putExtra("alarmtype", alarm.getType());
		LogUtils.i(TAG,"alarmtype---"+alarm.getType());
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
				Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
		PendingIntent pendingIntent= PendingIntent.getActivity(context, alarm.getId(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
		return pendingIntent;
	}
	
	
	/*
	 * 取消状态栏通知图标
	 */
	public static void cancelNotification(Context context){
		NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		if(notificationManager != null){
			notificationManager.cancelAll();
		}
	}
}
