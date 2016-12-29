package com.example.guo.lnproject.utils;

import android.util.SparseIntArray;

import java.util.Calendar;

/**
 * 日期工具类
 * Created by gmw on 16/12/28.
 */
public class CalendarUtil
{
    /**
     * 获取当前日期
     *
     * @return SparseIntArray
     */
    public static SparseIntArray getCurrentDate ()
    {

        SparseIntArray date = new SparseIntArray();
        date.put(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR));
        //默认月份从0开始，此处需要+1
        date.put(Calendar.MONTH, Calendar.getInstance().get(Calendar.MONTH) + 1);
        date.put(Calendar.DAY_OF_MONTH, Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        return date;
    }
}
