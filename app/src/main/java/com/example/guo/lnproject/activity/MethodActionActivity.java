package com.example.guo.lnproject.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.guo.lnproject.R;
import com.example.guo.lnproject.base.BaseActivity;
import com.example.guo.lnproject.utils.AppManager;
import com.example.guo.lnproject.utils.Contacts;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MethodActionActivity extends BaseActivity {

    @Bind(R.id.methodaction_gifImg)
    public ImageView gifImg;
    @Bind(R.id.methodaction_descTv)
    public TextView descTv;
    @Bind(R.id.methodaction_titleTv)
    public TextView titleTv;
    private Resources resources;

    public static void startAction(Context context,int type){
        Intent intent = new Intent(context,MethodActionActivity.class);
        intent.putExtra("type", type);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_methodaction);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        Intent intent = getIntent();
        resources = getResources();
        int type = intent.getIntExtra("type",1);
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
        Glide.with(this).load(R.drawable.method1).into(gifImg);
        descTv.setText(resources.getString(R.string.laozhen_method1));
    }

    @OnClick({R.id.methodaction_backImg})
    public void onClick(View view){
        switch(view.getId()){
            case R.id.methodaction_backImg:
                AppManager.getAppManager().finishActivity(MethodActionActivity.this);
                break;
        }
    }
}
