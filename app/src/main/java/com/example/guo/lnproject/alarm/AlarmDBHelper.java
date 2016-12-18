package com.example.guo.lnproject.alarm;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.guo.lnproject.utils.Contacts;
import com.example.guo.lnproject.utils.FileUtils;

public class AlarmDBHelper extends SQLiteOpenHelper{

	public static int VERSION = 1;
	private static String dbPathName;
	static{
		dbPathName = FileUtils.getDBPath(Contacts.TABLE_NAME);
	}
	private static AlarmDBHelper mInstance;
	
	private AlarmDBHelper (Context context){
		super(context, dbPathName, null, VERSION);
	}
	
	public synchronized static AlarmDBHelper getInstance(Context context){
		if(mInstance == null){
			mInstance = new AlarmDBHelper(context);
		}
		return mInstance;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
//		db.execSQL("drop table alarms");
		db.execSQL("create table alarms("
				  + "_id INTEGER PRIMARY KEY, hour INTEGER,"
				  + "minute INTEGER,"
				  + "label varchar(50),"
				  + "enabled INTEGER,"
				  + "nextMillis INTEGER,"
				  + "type INTEGER)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
	}
	
	@Override
	public synchronized SQLiteDatabase getWritableDatabase() {
		// TODO Auto-generated method stub
		return super.getWritableDatabase();
	}
	
	@Override
	public synchronized SQLiteDatabase getReadableDatabase() {
		// TODO Auto-generated method stub
		return super.getReadableDatabase();
	}

}
