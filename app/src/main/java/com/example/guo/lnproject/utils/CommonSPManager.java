package com.example.guo.lnproject.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.SparseIntArray;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Administrator on 2016/4/8 0008.
 */
public class CommonSPManager
{
    public static final int BELL_LONG = 1;

    public static final int BELL_SHORT = 0;

    private static final String BELL_KEY = "bell";

    private static final String DRINK_GOAL = "drink_goal";

    private static final String DRINK_WATER = "drink_water";

    private static final String TIME_YEAR = "year";

    private static final String TIME_MONTH = "month";

    private static final String TIME_DAY = "day";

    public static final String KEY_CITY = "city";

    public static final String KEY_PROVINCE = "province";

    public static final int DRINK_GOAL_DEFAULT = 1500;//系统默认喝水目标值

    public static final int DRINK_WATER_DEFAULT = 0;//系统默认已完成喝水量


    public static void setBell (Context context, int bell_type)
    {

        SPUtil.put(context, BELL_KEY, bell_type);
    }

    public static int getBell (Context context)
    {

        return (Integer) SPUtil.get(context, BELL_KEY, BELL_SHORT);
    }

    public static void setDrinkGoal (Context context, int drinkGoal)
    {

        SPUtil.put(context, DRINK_GOAL, drinkGoal);
    }

    public static int getDrinkGoal (Context context)
    {

        return (Integer) SPUtil.get(context, DRINK_GOAL, DRINK_GOAL_DEFAULT);
    }

    public static void setDrinkWater (Context context, int drinkWater)
    {

        SPUtil.put(context, DRINK_WATER, drinkWater);
    }

    public static int getDrinkWater (Context context)
    {

        return (Integer) SPUtil.get(context, DRINK_WATER, DRINK_WATER_DEFAULT);
    }

    public static void setLocation (Context context, String province, String city)
    {

        SharedPreferences.Editor edit = context.getSharedPreferences(SPUtil.FILE_NAME, Context
                .MODE_PRIVATE).edit();
        edit.putString(KEY_CITY, city);
        edit.putString(KEY_PROVINCE, province);
        SPUtil.SharedPreferencesCompat.apply(edit);
    }

    public static List<String> getLocation (Context context)
    {

        List<String> list = new ArrayList<>();
        list.add((String) SPUtil.get(context, KEY_PROVINCE, "北京"));
        list.add((String) SPUtil.get(context, KEY_CITY, "北京"));
        return list;
    }

    public static void setDrinkTime (Context context, SparseIntArray date)
    {

        SharedPreferences.Editor edit = context.getSharedPreferences(SPUtil.FILE_NAME, Context
                .MODE_PRIVATE).edit();
        edit.putInt(TIME_YEAR, date.get(Calendar.YEAR));
        edit.putInt(TIME_MONTH, date.get(Calendar.MONTH));
        edit.putInt(TIME_DAY, date.get(Calendar.DAY_OF_MONTH));
        edit.putInt(DRINK_WATER, 0);
        SPUtil.SharedPreferencesCompat.apply(edit);
    }

    public static SparseIntArray getDrinkTime (Context context)
    {

        SparseIntArray date = new SparseIntArray();
        date.put(Calendar.YEAR, (Integer) SPUtil.get(context, TIME_YEAR, 0));
        date.put(Calendar.MONTH, (Integer) SPUtil.get(context, TIME_MONTH, 1));
        date.put(Calendar.DAY_OF_MONTH, (Integer) SPUtil.get(context, TIME_DAY, 1));
        return date;
    }

    public static void clear (Context context)
    {

        SPUtil.clear(context);
    }
}
