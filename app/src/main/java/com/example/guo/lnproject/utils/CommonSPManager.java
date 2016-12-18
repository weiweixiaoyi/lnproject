package com.example.guo.lnproject.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;

/**
 * Created by Administrator on 2016/4/8 0008.
 */
public class CommonSPManager {
    public static final String SP_NAME = "lnset";

    public static final String SP_BELLSET = "bellset";
    public static final int BELL_LONG = 1;
    public static final int BELL_SHORT = 0;
    public static final String BELL_KEY = "bell";

    public static final String DRINK_GOAL = "drink_goal";
    public static final String DRINK_WATER = "drink_water";
    public static final String TIME_YEAR = "year";
    public static final String TIME_MONTH = "month";
    public static final String TIME_DAY = "day";

    public static final int DRINK_GOAL_DEFAULT = 1500;//系统默认喝水目标值
    public static final int DRINK_WATER_DEFAULT = 0;//系统默认已完成喝水量


    public static void setBell(Context context,int bell_type){
       context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE).edit().putInt(SP_BELLSET,bell_type).commit();
    }

    public static int getBell(Context context){
        return context.getSharedPreferences(SP_NAME,Context.MODE_PRIVATE).getInt(SP_BELLSET,BELL_SHORT);
    }

    public static void setDrinkGoal(Context context,int drinkGoal){
        context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE).edit().putInt(DRINK_GOAL,drinkGoal).commit();
    }

    public static int getDrinkGoal(Context context){
        return context.getSharedPreferences(SP_NAME,Context.MODE_PRIVATE).getInt(DRINK_GOAL,DRINK_GOAL_DEFAULT);
    }

    public static void setDrinkWater(Context context,int drinkWater){
        context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE).edit().putInt(DRINK_WATER,drinkWater).commit();
    }

    public static int getDrinkWater(Context context){
        return context.getSharedPreferences(SP_NAME,Context.MODE_PRIVATE).getInt(DRINK_WATER,DRINK_WATER_DEFAULT);
    }

    public static void setDrinkTime(Context context,int[] times){
        SharedPreferences.Editor edit = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE).edit();
        edit.putInt(TIME_YEAR,times[0]);
        edit.putInt(TIME_MONTH,times[1]);
        edit.putInt(TIME_DAY,times[2]);
        edit.putInt(DRINK_WATER,0);
        edit.commit();
    }

    public static int[] getDrinkTime(Context context){
        SharedPreferences preferences = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        int[] arr = new int[3];
        arr[0] = preferences.getInt(TIME_YEAR,0);
        arr[1] = preferences.getInt(TIME_MONTH,0);
        arr[2] = preferences.getInt(TIME_DAY,0);
        return arr;
    }

    public static void clear(Context context){
        context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE).edit().clear().commit();
    }
}
