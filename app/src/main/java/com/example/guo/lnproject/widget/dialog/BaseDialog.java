package com.example.guo.lnproject.widget.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;

import com.example.guo.lnproject.R;
/**
 * 对话框基类
 *
 * @author gaoxiaoduo
 * @version 1.0
 * @date 15/8/4下午6:55
 */
public abstract class BaseDialog extends Dialog
{
    public Activity activity = null;

    private boolean isMatchWidth;

    protected abstract int contentView ();

    /**
     * 构造方法
     *
     * @param activity 启动Activity
     */
    public BaseDialog (Activity activity)
    {

        this(activity, false);
    }

    /**
     * 构造方法
     *
     * @param activity 启动Activity
     */
    public BaseDialog (Activity activity, boolean isMatchWidth)
    {

        this(activity, R.style.dialog_style, isMatchWidth);
    }

    /**
     * 构造方法
     *
     * @param activity 启动Activity
     * @param theme    式样
     */
    private BaseDialog (Activity activity, int theme, boolean isMatchWidth)
    {

        super(activity, theme);
        this.activity = activity;
        this.isMatchWidth = isMatchWidth;
        setCancelable(false);// 不可用返回键
        setCanceledOnTouchOutside(false);
        setContentView(contentView());
    }

    @Override
    public void setContentView (int layoutResID)
    {

        super.setContentView(layoutResID);
        Window window = this.getWindow();
        window.setWindowAnimations(R.style.dialog_anim);
        DisplayMetrics dm = new DisplayMetrics();
        window.getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        WindowManager.LayoutParams p = window.getAttributes();


        if (isMatchWidth)
        {
            p.width = width;
        }
        else
        {
            p.width = Math.min((int) (width * 0.9), 900);
        }

        window.setAttributes(p);
    }

    @Override
    public void show ()
    {
        //判断activity是否已被finished
        if (!activity.isFinishing())
        {
            super.show();
        }
    }
}
