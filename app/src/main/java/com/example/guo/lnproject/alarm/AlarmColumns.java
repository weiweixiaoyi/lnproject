package com.example.guo.lnproject.alarm;

import android.provider.BaseColumns;

public class AlarmColumns implements BaseColumns{

	private int id;
	private int hour;
	private int minute;
	private String label;
	private int enabled;
	private long nextMillis;
	private int type;
	
	//表中的列名
	public static final String HOUR = "hour";
	public static final String MINUTE = "minute";
	public static final String LABEL = "label";
	public static final String ENABLED = "enabled";
	public static final String NEXTMILLIS = "nextMillis";
	public static final String TYPE = "type";
	
	//默认排序
	public static final String DEFAULT_SORT_ORDER = HOUR + ", " + MINUTE + " ASC";
	//有效闹钟排序
	public static final String ENABLED_SORT_ORDER = NEXTMILLIS + " ASC";
	//有效
	public static final String ENABLED_WHER = "enabled = 1";
	
	public static final String[] ALARM_QUERY_COLUMNS = {
		_ID,HOUR,MINUTE,LABEL,ENABLED,NEXTMILLIS,TYPE
	};
	
	public static final int ID_INDEX = 0;
	public static final int HOUR_INDEX = 1;
	public static final int MINUTE_INDEX = 2;
	public static final int LABEL_INDEX = 3;
	public static final int ENABLED_INDEX = 4;
	public static final int NEXTMILLIS_INDEX = 5;
	public static final int TYPE_INDEX = 6;
	
}
