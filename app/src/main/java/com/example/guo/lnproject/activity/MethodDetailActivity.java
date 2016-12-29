package com.example.guo.lnproject.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.guo.lnproject.R;
import com.example.guo.lnproject.base.BaseActivity;
import com.example.guo.lnproject.utils.utils;
import com.example.guo.lnproject.utils.Contacts;

import butterknife.Bind;
import butterknife.OnClick;

public class MethodDetailActivity extends BaseActivity
{
    @Bind(R.id.methoddetail_titleTv)
    public TextView titleTv;
    @Bind(R.id.methoddetail_focusTv)
    public TextView focusTv;
    @Bind(R.id.methoddetail_principleTv)
    public TextView principleTv;
    @Bind(R.id.methoddetail_feelingTv)
    public TextView feelingTv;
    @Bind(R.id.methoddetail_sourceTv)
    public TextView sourceTv;
    private int type;
    private Resources resources;
    public static void startAction(Context context,int type){
        Intent intent = new Intent(context,MethodDetailActivity.class);
        intent.putExtra("type", type);
        context.startActivity(intent);
    }

    @Override
    protected int setUpContentView ()
    {

        return R.layout.activity_methoddetail;
    }

    @Override
    protected void initPresenter ()
    {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    private void initData() {
        type = getIntent().getIntExtra("type",1);
        resources = getResources();
        switch (type){
            case Contacts.METHOD1:
                titleTv.setText(resources.getString(R.string.laozhen_title1));
                break;
            case Contacts.METHOD2:
                titleTv.setText(resources.getString(R.string.laozhen_title2));
                break;
            case Contacts.METHOD3:
                titleTv.setText(resources.getString(R.string.laozhen_title3));
                break;
            case Contacts.METHOD4:
                titleTv.setText(resources.getString(R.string.shubiaoshou_title1));
                break;
            case Contacts.METHOD5:
                titleTv.setText(resources.getString(R.string.shubiaoshou_title2));
                break;
            case Contacts.METHOD6:
                titleTv.setText(resources.getString(R.string.shubiaoshou_title3));
                break;
            case Contacts.METHOD7:
                titleTv.setText(resources.getString(R.string.bozisuantong_title1));
                break;
            case Contacts.METHOD8:
                titleTv.setText(resources.getString(R.string.bozisuantong_title2));
                break;
            case Contacts.METHOD9:
                titleTv.setText(resources.getString(R.string.bozisuantong_title3));
                break;
            case Contacts.METHOD1A:
                titleTv.setText(resources.getString(R.string.jianbeijiangying_title3));
                break;
        }
        focusTv.setText(resources.getString(R.string.laozhen_focus1)+"");
        principleTv.setText(resources.getString(R.string.laozhen_principle1)+"");
        sourceTv.setText(resources.getString(R.string.source)+"");
        feelingTv.setText(resources.getString(R.string.laozhen_feeling1)+"");
    }

    @OnClick({R.id.methoddetail_backImg ,R.id.methoddetail_clickToActionBtn})
     public void onClick(View view){
        switch (view.getId()){
            case R.id.methoddetail_backImg:
                utils.getAppManager().finishActivity(this);
                break;
            case R.id.methoddetail_clickToActionBtn:
                MethodActionActivity.startAction(MethodDetailActivity.this,type);
                break;
        }
    }
}
