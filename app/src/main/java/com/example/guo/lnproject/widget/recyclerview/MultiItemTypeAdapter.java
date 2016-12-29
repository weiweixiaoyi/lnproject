/**
 * Created by zhanghao on 2016-09-04 下午4:29.
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
package com.example.guo.lnproject.widget.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.example.guo.lnproject.widget.recyclerview.base.ItemViewDelegate;
import com.example.guo.lnproject.widget.recyclerview.base.ItemViewDelegateManager;
import com.example.guo.lnproject.widget.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Mutli item view adapter
 *
 * @author zhanghao
 * @version 1.0
 */
public class MultiItemTypeAdapter<M> extends RecyclerView.Adapter<ViewHolder>
{

    protected Context mContext;

    protected List<M> mDatas;

    protected ItemViewDelegateManager<M> mItemViewDelegateManager;

    protected OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener
    {
        void onItemClick (View view, RecyclerView.ViewHolder holder, int position);

        boolean onItemLongClick (View view, RecyclerView.ViewHolder holder, int position);
    }

    public MultiItemTypeAdapter (Context context, List<M> datas)
    {

        mContext = context;
        mDatas = datas;
        mItemViewDelegateManager = new ItemViewDelegateManager<>();
    }

    @Override
    @SuppressWarnings("unchecked")
    public int getItemViewType (int position)
    {

        if (!useItemViewDelegateManager())
        {
            return super.getItemViewType(position);
        }
        return mItemViewDelegateManager.getItemViewType(mDatas.get(position), position);
    }

    /**
     * 是否使用ItemViewDelegateManager
     *
     * @return true/false
     */
    protected boolean useItemViewDelegateManager ()
    {

        return mItemViewDelegateManager.getItemViewDelegateCount() > 0;
    }

    @Override
    public ViewHolder onCreateViewHolder (ViewGroup parent, int viewType)
    {

        ItemViewDelegate itemViewDelegate = mItemViewDelegateManager.getItemViewDelegate(viewType);
        int layoutId = itemViewDelegate.getItemViewLayoutId();
        ViewHolder holder = ViewHolder.createViewHolder(mContext, parent, layoutId);
        setListener(parent, holder, viewType);
        return holder;
    }

    protected void setListener (final ViewGroup parent, final ViewHolder viewHolder, int viewType)
    {

        if (!isEnabled(viewType))
            return;
        viewHolder.getConvertView().setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View v)
            {

                if (mOnItemClickListener != null)
                {
                    int position = viewHolder.getAdapterPosition();
                    mOnItemClickListener.onItemClick(v, viewHolder, position);
                }
            }
        });

        viewHolder.getConvertView().setOnLongClickListener(new View.OnLongClickListener()
        {
            @Override
            public boolean onLongClick (View v)
            {

                if (mOnItemClickListener != null)
                {
                    int position = viewHolder.getAdapterPosition();
                    return mOnItemClickListener.onItemLongClick(v, viewHolder, position);
                }
                return false;
            }
        });
    }

    protected boolean isEnabled (int viewType)
    {

        return true;
    }

    @Override
    public void onBindViewHolder (ViewHolder holder, int position)
    {

        convert(holder, mDatas.get(position));
    }

    @SuppressWarnings("unchecked")
    public void convert (ViewHolder holder, M m)
    {

        mItemViewDelegateManager.convert(holder, m, holder.getAdapterPosition());
    }

    @Override
    public int getItemCount ()
    {

        return mDatas.size();
    }

    public MultiItemTypeAdapter addItemViewDelegate (ItemViewDelegate<M> itemViewDelegate)
    {

        mItemViewDelegateManager.addDelegate(itemViewDelegate);
        return this;
    }

    public MultiItemTypeAdapter addItemViewDelegate (
            int viewType, ItemViewDelegate<M> itemViewDelegate)
    {

        mItemViewDelegateManager.addDelegate(viewType, itemViewDelegate);
        return this;
    }

    public List<M> getDatas ()
    {

        return mDatas;
    }

    public void setOnItemClickListener (OnItemClickListener onItemClickListener)
    {

        this.mOnItemClickListener = onItemClickListener;
    }
}
