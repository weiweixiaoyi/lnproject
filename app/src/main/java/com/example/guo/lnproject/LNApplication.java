package com.example.guo.lnproject;

import android.app.Application;

import com.example.guo.lnproject.alarm.AlarmDBHelper;
import com.example.guo.lnproject.alarm.AlarmDBManager;

/**
 * Created by Administrator on 2016/4/11 0011.
 */
public class LNApplication  extends Application{

    private static LNApplication mApp;

    public static LNApplication getInstance(){
        return mApp;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    private void init() {
        AlarmDBHelper dbHelper = AlarmDBHelper.getInstance(this);
        AlarmDBManager.initializeInstance(dbHelper);
        mApp = this;
    }
}
