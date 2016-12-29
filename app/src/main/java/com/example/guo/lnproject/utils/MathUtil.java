package com.example.guo.lnproject.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * Created by gmw on 16/12/28.
 */

public class MathUtil
{
    /**
     * 四舍五入取整
     */
    public static int roundInt (double expend)
    {

        BigDecimal bd = new BigDecimal(expend);
        return bd.setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
    }

    /**
     * 小数点后几位四舍五入
     *
     * @return double
     */
    public static double roundDecimalPlace (double expend, int scale)
    {

        BigDecimal bd = new BigDecimal(expend);
        return bd.setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 小数点后几位不做四舍五入,截断
     *
     * @return double
     */
    public static double roundDecimal (double expend, int scale)
    {

        BigDecimal bd = new BigDecimal(expend);
        return bd.setScale(scale, BigDecimal.ROUND_HALF_DOWN).doubleValue();
    }

    /**
     * 设置小数点后几位
     *
     * @param expend  String
     * @param pattern eg:"##.##"
     * @return String
     */
    public static String roundDecimal (String expend, String pattern)
    {

        DecimalFormat format = new DecimalFormat(pattern);
        return format.format(expend);
    }

    /**
     * 设置小数点后几位
     *
     * @param expend  String
     * @param pattern eg:"##.##"
     * @return String
     */
    public static String roundDecimal (double expend, String pattern)
    {

        DecimalFormat format = new DecimalFormat(pattern);
        return format.format(expend);
    }
}
