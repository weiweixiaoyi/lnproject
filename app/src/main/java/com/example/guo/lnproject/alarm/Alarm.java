package com.example.guo.lnproject.alarm;

import java.io.Serializable;

import android.database.Cursor;

@SuppressWarnings("serial")
public class Alarm implements Serializable{

	private int id;
	private int hour;
	private int minute;
	private String label;
	private int enabled;
	private long nextMillis;
	private int type;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getHour() {
		return hour;
	}
	public void setHour(int hour) {
		this.hour = hour;
	}
	public int getMinute() {
		return minute;
	}
	public void setMinute(int minute) {
		this.minute = minute;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public int getEnabled() {
		return enabled;
	}
	public void setEnabled(int enabled) {
		this.enabled = enabled;
	}
	public long getNextMillis() {
		return nextMillis;
	}
	public void setNextMillis(long nextMillis) {
		this.nextMillis = nextMillis;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public Alarm() {}
	public Alarm(Cursor cursor){
		id = cursor.getInt(AlarmColumns.ID_INDEX);
		hour = cursor.getInt(AlarmColumns.HOUR_INDEX);
		minute = cursor.getInt(AlarmColumns.MINUTE_INDEX);
		label = cursor.getString(AlarmColumns.LABEL_INDEX);
		enabled = cursor.getInt(AlarmColumns.ENABLED_INDEX);
		nextMillis = cursor.getLong(AlarmColumns.NEXTMILLIS_INDEX);
		type = cursor.getInt(AlarmColumns.TYPE_INDEX);
	}
	
}
