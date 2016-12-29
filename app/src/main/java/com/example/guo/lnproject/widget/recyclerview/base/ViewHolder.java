/**
 * Created by zhanghao on 2016-09-04 下午4:30.
 * <p>
 * # # # # # # # # # # # # # # # # # # # # # # # # # # #
 * #                                                   #
 * #                       _oo0oo_                     #
 * #                      o8888888o                    #
 * #                      88" . "88                    #
 * #                      (| -_- |)                    #
 * #                      0\  =  /0                    #
 * #                    ___/`---'\___                  #
 * #                  .' \\|     |# '.                 #
 * #                 / \\|||  :  |||# \                #
 * #                / _||||| -:- |||||- \              #
 * #               |   | \\\  -  #/ |   |              #
 * #               | \_|  ''\---/''  |_/ |             #
 * #               \  .-\__  '-'  ___/-. /             #
 * #             ___'. .'  /--.--\  `. .'___           #
 * #          ."" '<  `.___\_<|>_/___.' >' "".         #
 * #         | | :  `- \`.;`\ _ /`;.`/ - ` : | |       #
 * #         \  \ `_.   \_ __\ /__ _/   .-` /  /       #
 * #     =====`-.____`.___ \_____/___.-`___.-'=====    #
 * #                       `=---='                     #
 * #     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~   #
 * #                                                   #
 * #               佛祖保佑         永无BUG              #
 * #                                                   #
 * # # # # # # # # # # # # # # # # # # # # # # # # # # #
 */
package com.example.guo.lnproject.widget.recyclerview.base;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 通用ViewHolder
 * ViewHolder只作View的缓存,不关心数据内容
 *
 * @author zhanghao
 * @version 1.0
 */
public class ViewHolder extends RecyclerView.ViewHolder
{

    private Context mContext;

    private SparseArray<View> viewHolder;

    private View mConvertView;

    public ViewHolder (Context mContext, View itemView)
    {

        super(itemView);
        this.mConvertView = itemView;
        this.mContext = mContext;
        viewHolder = new SparseArray<>();
    }

    /**
     * 创建ViewHolder
     *
     * @param context  上下文
     * @param itemView View
     * @return ViewHolder
     */
    public static ViewHolder createViewHolder (Context context, View itemView)
    {

        return new ViewHolder(context, itemView);
    }

    /**
     * 创建ViewHolder
     *
     * @param context  上下文
     * @param parent   ViewGroup
     * @param layoutId layout id
     * @return ViewHolder
     */
    public static ViewHolder createViewHolder (
            Context context, ViewGroup parent, @LayoutRes int layoutId)
    {

        View itemView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        return new ViewHolder(context, itemView);
    }

    /**
     * 通过viewId获取控件
     *
     * @param viewId view id
     * @return View
     */
    @SuppressWarnings("unchecked")
    public <T extends View> T getView (@IdRes int viewId)
    {

        View view = viewHolder.get(viewId);
        if (view == null)
        {
            view = mConvertView.findViewById(viewId);
            viewHolder.put(viewId, view);
        }
        return (T) view;
    }

    /**
     * 获取ConvertView
     *
     * @return View
     */
    public View getConvertView ()
    {

        return mConvertView;
    }
}
