package com.example.guo.lnproject.alarm;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.guo.lnproject.utils.Contacts;

public class AlarmDBManager {

	private static AlarmDBManager mInstance;
	private static SQLiteOpenHelper mDatabaseHelper;
	private static SQLiteDatabase mDatabase;
	private static final String TAG = "AlarmDBManager";
	private AlarmDBManager(){
		
	}
	
	public static synchronized void initializeInstance(SQLiteOpenHelper helper) {
		if (mInstance == null) {
			mDatabaseHelper = helper;
			mInstance = new AlarmDBManager();
			mDatabase = mDatabaseHelper.getWritableDatabase();
		}
	}  
	
	public static synchronized AlarmDBManager getInstance() {
		if (mInstance == null) {
			throw new IllegalStateException(
					AlarmDBManager.class.getSimpleName()
							+ " is not initialized, call initializeInstance(..) method first.");
		}
		return mInstance;
	}
	
	public synchronized void addAlarm(Alarm alarm){
		long id = mDatabase.insert(Contacts.TABLE_NAME, null, alarm2ContentValues(alarm));
		alarm.setId((int) id);
	}
	
	public synchronized void deleteAlarm(int alarmId){
		mDatabase.delete(Contacts.TABLE_NAME, "_id=?", new String[]{alarmId + ""});
	}
	
	public synchronized void deleteAllAlarm(){
		mDatabase.delete(Contacts.TABLE_NAME,null,null);
	}
	
	public synchronized void updateAlarm(int alarmId,ContentValues values){
		mDatabase.update(Contacts.TABLE_NAME, values, "_id=?", new String[]{alarmId + ""});
	}
	
	public synchronized Alarm getAlarmById(int alarmId){
		Cursor cursor = mDatabase.query(Contacts.TABLE_NAME, AlarmColumns.ALARM_QUERY_COLUMNS, "_id=?", new String[]{""+alarmId}, null, null, null);
		Alarm alarm = null;
		if(cursor != null){
			if(cursor.moveToFirst()){
				alarm = new Alarm(cursor);
			}
		}
		cursor.close();
		return alarm;
	}

	public synchronized Alarm getAlarmByTime(int hour,int minute){
		Cursor cursor = mDatabase.query(Contacts.TABLE_NAME, AlarmColumns.ALARM_QUERY_COLUMNS, "hour=? and minute=?", new String[]{""+hour,""+minute}, null, null, null);
		Alarm alarm = null;
		if(cursor != null){
			if(cursor.moveToFirst()){
				alarm = new Alarm(cursor);
			}
		}
		cursor.close();
		return alarm;
	}
	
	//Cursor android.database.sqlite.SQLiteDatabase.query(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy)
	//获取最近可用
	public synchronized Alarm getNextAlarm(){
		Cursor cursor = mDatabase.query(Contacts.TABLE_NAME, 
					   AlarmColumns.ALARM_QUERY_COLUMNS,
					  AlarmColumns.ENABLED_WHER,null, null,null,
					   AlarmColumns.ENABLED_SORT_ORDER);
		Alarm alarm = null;
		if(cursor != null){
			if(cursor.moveToFirst()){
				alarm = new Alarm(cursor);
			}
		}
		cursor.close();
		return alarm;
	}
	
	public List<Alarm> getAllAlarm(int type){
		Log.i(TAG, "getAllAlarm");
		List<Alarm> alarms = new ArrayList<Alarm>();
		Cursor cursor = mDatabase.query( Contacts.TABLE_NAME, AlarmColumns.ALARM_QUERY_COLUMNS, "type = ?", new String[]{""+type}, null, null, AlarmColumns.DEFAULT_SORT_ORDER);
		if(cursor != null){
			Log.i(TAG, "cursor != null");
			while(cursor.moveToNext()){
				alarms.add(new Alarm(cursor));
			}
		}
		cursor.close();
		return alarms;
	}

	public List<Alarm> getAllAlarm(){
		Log.i(TAG, "getAllAlarm");
		List<Alarm> alarms = new ArrayList<Alarm>();
		Cursor cursor = mDatabase.query( Contacts.TABLE_NAME, AlarmColumns.ALARM_QUERY_COLUMNS, AlarmColumns.ENABLED_WHER, null,null, null, AlarmColumns.DEFAULT_SORT_ORDER);
		if(cursor != null){
			Log.i(TAG, "cursor != null");
			while(cursor.moveToNext()){
				alarms.add(new Alarm(cursor));
			}
		}
		cursor.close();
		return alarms;
	}
	
	private ContentValues alarm2ContentValues(Alarm alarm) {
		
		Log.i("tag", "alarm--hour = "+alarm.getHour()+",minute = "+alarm.getMinute());
		ContentValues values = new ContentValues();
		values.put(AlarmColumns.HOUR, alarm.getHour());
		values.put(AlarmColumns.MINUTE ,alarm.getMinute() );
		values.put(AlarmColumns.LABEL, alarm.getLabel());
		values.put(AlarmColumns.ENABLED,alarm.getEnabled());
		values.put(AlarmColumns.TYPE,alarm.getType());
		return values;
	}
	
}
