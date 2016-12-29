package com.example.guo.lnproject.widget.recyclerview.base;

/**
 * item view delegate
 *
 * @param <M>
 * @author zhanghao
 */
public interface ItemViewDelegate<M>
{
    /**
     * item view id
     *
     * @return layout id
     */
    int getItemViewLayoutId ();

    /**
     * item view type
     *
     * @param item     M
     * @param position item position
     * @return true/false
     */
    boolean isForViewType (M item, int position);

    /**
     * convert view
     *
     * @param holder   ViewHolder
     * @param item     M
     * @param position item position
     */
    void convert (ViewHolder holder, M item, int position);

}
