package com.example.guo.lnproject.widget.recyclerview.base;

import android.support.v4.util.SparseArrayCompat;


/**
 * Multi item view manager
 *
 * @param <M> data
 * @author zhanghao
 */
public class ItemViewDelegateManager<M>
{
    /**
     * multi item view delegate
     */
    SparseArrayCompat<ItemViewDelegate<M>> delegates = new SparseArrayCompat<>();

    /**
     * item view size
     *
     * @return size
     */
    public int getItemViewDelegateCount ()
    {

        return delegates.size();
    }

    /**
     * add item view delegate
     * add delegate on delegates last
     *
     * @param delegate ItemViewDelegate
     * @return ItemViewDelegateManager
     */
    public ItemViewDelegateManager<M> addDelegate (ItemViewDelegate<M> delegate)
    {

        if (delegate != null)
        {
            delegates.put(delegates.size(), delegate);
        }
        return this;
    }

    /**
     * add item view delegate
     * add delegate on view type
     *
     * @param delegate ItemViewDelegate
     * @return ItemViewDelegateManager
     */
    public ItemViewDelegateManager<M> addDelegate (int viewType, ItemViewDelegate<M> delegate)
    {

        if (delegates.get(viewType) != null)
        {
            throw new IllegalArgumentException("An ItemViewDelegate is already registered for the" +
                    " viewType = " + viewType + ". Already registered ItemViewDelegate is " +
                    delegates.get(viewType));
        }
        delegates.put(viewType, delegate);
        return this;
    }

    /**
     * remove item view delegate
     *
     * @param delegate ItemViewDelegate
     * @return ItemViewDelegateManager
     */
    public ItemViewDelegateManager<M> removeDelegate (ItemViewDelegate<M> delegate)
    {

        if (delegate == null)
        {
            throw new NullPointerException("ItemViewDelegate is null");
        }
        int indexToRemove = delegates.indexOfValue(delegate);

        if (indexToRemove >= 0)
        {
            delegates.removeAt(indexToRemove);
        }
        return this;
    }

    /**
     * remove item view delegate
     *
     * @param itemType int
     * @return ItemViewDelegateManager
     */
    public ItemViewDelegateManager<M> removeDelegate (int itemType)
    {

        int indexToRemove = delegates.indexOfKey(itemType);

        if (indexToRemove >= 0)
        {
            delegates.removeAt(indexToRemove);
        }
        return this;
    }

    /**
     * item view type
     *
     * @param item     data
     * @param position item position
     * @return item view type
     */
    public int getItemViewType (M item, int position)
    {

        int delegatesCount = delegates.size();
        for (int i = delegatesCount - 1; i >= 0; i--)
        {
            ItemViewDelegate<M> delegate = delegates.valueAt(i);
            if (delegate.isForViewType(item, position))
            {
                return delegates.keyAt(i);
            }
        }
        throw new IllegalArgumentException("No ItemViewDelegate added that matches position=" +
                position + " in data source");
    }

    /**
     * convert view
     *
     * @param holder   ViewHolder
     * @param item     data
     * @param position item position
     */
    public void convert (ViewHolder holder, M item, int position)
    {

        int delegatesCount = delegates.size();
        for (int i = 0; i < delegatesCount; i++)
        {
            ItemViewDelegate<M> delegate = delegates.valueAt(i);

            if (delegate.isForViewType(item, position))
            {
                delegate.convert(holder, item, position);
                return;
            }
        }
        throw new IllegalArgumentException("No ItemViewDelegateManager added that matches " +
                "position=" + position + " in data source");
    }


    /**
     * item view delegate
     *
     * @param viewType view type
     * @return ItemViewDelegate
     */
    public ItemViewDelegate getItemViewDelegate (int viewType)
    {

        return delegates.get(viewType);
    }

    /**
     * item view layout id
     *
     * @param viewType view type
     * @return layout id
     */
    public int getItemViewLayoutId (int viewType)
    {

        return getItemViewDelegate(viewType).getItemViewLayoutId();
    }

    /**
     * item view type
     *
     * @param itemViewDelegate ItemViewDelegate
     * @return item view type
     */
    public int getItemViewType (ItemViewDelegate<M> itemViewDelegate)
    {

        return delegates.indexOfValue(itemViewDelegate);
    }
}
