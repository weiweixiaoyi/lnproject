package com.example.guo.lnproject.utils;

import android.os.Build;
import android.support.design.widget.Snackbar;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.example.guo.lnproject.R;

public class SnackbarUtil
{

    public static final int Info = 1;

    public static final int Warning = 2;

    public static final int WARNING_BG = 0XFFD0021B;

    public static final int WARNING_MSG = 0XFFFFFFFF;

    public static final int INFO_BG = 0XFF252D33;

    public static final int INFO_MSG = 0XFFFFFFFF;

    public static final float MSG_TEXT_SIZE = 16.0f;

    public static void showInfo (View view, String message, int duration)
    {

        Snackbar snackbar = Snackbar.make(view, message, duration);

        setSnackbar(snackbar, Info);

        snackbar.show();
    }

    public static void showInfo (View view, int resId, int duration)
    {

        Snackbar snackbar = Snackbar.make(view, resId, duration);

        setSnackbar(snackbar, Info);

        snackbar.show();
    }

    public static void showWarning (View view, String message, int duration)
    {

        Snackbar snackbar = Snackbar.make(view, message, duration);

        setSnackbar(snackbar, Warning);

        snackbar.show();
    }

    public static void showWarning (View view, int resId, int duration)
    {

        Snackbar snackbar = Snackbar.make(view, resId, duration);

        setSnackbar(snackbar, Warning);

        snackbar.show();
    }

    private static void setSnackbar (Snackbar snackbar, int type)
    {

        int bgColor = 0;
        int msgColor = 0;
        switch (type)
        {
            case Info:
                bgColor = INFO_BG;
                msgColor = INFO_MSG;
                break;
            case Warning:
                bgColor = WARNING_BG;
                msgColor = WARNING_MSG;
                break;
            default:
                break;
        }
        View view = snackbar.getView();
        view.setBackgroundColor(bgColor);
        TextView tvMsg = ((TextView) view.findViewById(R.id.snackbar_text));
        tvMsg.setTextColor(msgColor);
        tvMsg.setTextSize(TypedValue.COMPLEX_UNIT_SP, MSG_TEXT_SIZE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)
            tvMsg.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        else
            tvMsg.setGravity(Gravity.CENTER_HORIZONTAL);
    }

}