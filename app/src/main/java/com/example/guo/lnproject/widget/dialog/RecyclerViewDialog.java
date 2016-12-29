package com.example.guo.lnproject.widget.dialog;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.guo.lnproject.R;
import com.example.guo.lnproject.widget.recyclerview.CommonAdapter;
import com.example.guo.lnproject.widget.recyclerview.MultiItemTypeAdapter;
import com.example.guo.lnproject.widget.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by gmw on 16/12/28.
 */

public class RecyclerViewDialog extends BaseDialog
{
    @Bind(R.id.tv_back)
    TextView tvBack;

    @Bind(R.id.tv_title)
    TextView tvTitle;

    @Bind(R.id.iv_title)
    ImageView ivTitle;

    @Bind(R.id.rv)
    RecyclerView rv;

    private List<String> mDatas;

    private MultiItemTypeAdapter<String> mAdapter;

    private DialogCallBack mCallBack;

    public RecyclerViewDialog (Activity activity)
    {

        super(activity);
        initData();
    }

    private void initData ()
    {

        ButterKnife.bind(this);
        mDatas = new ArrayList<>();
        rv.setLayoutManager(new LinearLayoutManager(activity));
        mAdapter = new CommonAdapter<String>(activity, R.layout.item_recyclerview_dialog, mDatas)
        {
            @Override
            protected void convert (ViewHolder holder, String str, int position)
            {

                TextView tv = holder.getView(R.id.tv);
                tv.setText(str);
            }
        };
        rv.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener()
        {
            @Override
            public void onItemClick (View view, RecyclerView.ViewHolder holder, int position)
            {
                dismiss();
                if (mCallBack != null)
                {
                    mCallBack.onItemClickListener(view, position);
                }
            }

            @Override
            public boolean onItemLongClick (View view, RecyclerView.ViewHolder holder, int position)
            {

                return false;
            }
        });
    }

    public void setCallBack (DialogCallBack callBack)
    {

        this.mCallBack = callBack;
    }

    public void refreshRecyclerViewData (List<String> datas)
    {

        mDatas.clear();
        mDatas.addAll(datas);
        mAdapter.notifyDataSetChanged();
    }

    public void resetRecyclerViewSelection(){
        rv.scrollToPosition(0);
    }


    @Override
    protected int contentView ()
    {

        return R.layout.dialog_recyclerview;
    }

    public interface DialogCallBack
    {
        void onCompleteCancel ();

        void onItemClickListener (View view, int position);
    }

    @OnClick(R.id.tv_back)
    public void onClick (View view)
    {

        if (view.getId() == R.id.tv_back)
        {
            dismiss();
            if (mCallBack != null)
            {
                mCallBack.onCompleteCancel();
            }
        }
    }


}
