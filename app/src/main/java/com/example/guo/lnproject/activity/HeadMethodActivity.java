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

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;

public class HeadMethodActivity extends BaseActivity {

    public static final int TYPE_LAOZHEN = 1;
    public static final int TYPE_SHUBIAOSHOU = 2;
    public static final int TYPE_BOZISUANTENG = 3;
    public static final int TYPE_JIANBENJIANGYING = 4;

    @Bind(R.id.headmethod_titleTv)
    public TextView titleTv;
    @Bind(R.id.headmethod_typeTv)
    public TextView typeTv;
    @Bind(R.id.headmethod_typedescTv)
    public TextView typedescTv;
    @Bind(R.id.method1_nameTv)
    public TextView method1NameTv;
    @Bind(R.id.method2_nameTv)
    public TextView method2NameTv;
    @Bind(R.id.method3_nameTv)
    public TextView method3NameTv;

    private Resources resources;
    private int type;

    private Map<Integer,Integer> method1Map;
    private Map<Integer,Integer> method2Map;
    private Map<Integer,Integer> method3Map;

    public static void startAction(Context context,int type){
        Intent intent = new Intent(context,HeadMethodActivity.class);
        intent.putExtra("type",type);
        context.startActivity(intent);
    }

    @Override
    protected int setUpContentView ()
    {

        return R.layout.activity_headmethod;
    }

    @Override
    protected void initPresenter ()
    {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = getIntent().getIntExtra("type",1);
        resources = getResources();
        initMaps();
        loadData(type);
    }

    private void initMaps() {
        method1Map = new HashMap<Integer,Integer>();
        method2Map = new HashMap<Integer,Integer>();
        method3Map = new HashMap<Integer,Integer>();
        method1Map.put(1,1);
        method1Map.put(2,4);
        method1Map.put(3,7);
        method1Map.put(4,9);
        method2Map.put(1,2);
        method2Map.put(2,5);
        method2Map.put(3,8);
        method2Map.put(4,2);
        method3Map.put(1,3);
        method3Map.put(2,6);
        method3Map.put(3,9);
        method3Map.put(4,10);
    }

    @OnClick({R.id.method1Layout,R.id.method2Layout,R.id.method3Layout,R.id.headmethod_backImg})
    public void onClick(View view){
        switch(view.getId()){
            case R.id.method1Layout:
                MethodDetailActivity.startAction(HeadMethodActivity.this,method1Map.get(type));
                break;
            case R.id.method2Layout:
                MethodDetailActivity.startAction(HeadMethodActivity.this,method2Map.get(type));
                break;
            case R.id.method3Layout:
                MethodDetailActivity.startAction(HeadMethodActivity.this,method3Map.get(type));
                break;
            case R.id.headmethod_backImg:
                utils.getAppManager().finishActivity(HeadMethodActivity.this);
                break;
        }
    }

    private void loadData(int type) {
        switch (type){
            case TYPE_LAOZHEN:
                loadLaozhenData();
                break;
            case TYPE_SHUBIAOSHOU:
                loadShubiaoshouData();
                break;
            case TYPE_BOZISUANTENG:
                loadBozisuantentData();
                break;
            case TYPE_JIANBENJIANGYING:
                loadJianbeijiangyingData();
                break;
        }
    }


    private void loadLaozhenData(){
        titleTv.setText(resources.getString(R.string.head_laozhen));
        typeTv.setText(resources.getString(R.string.head_laozhen));
        typedescTv.setText(resources.getString(R.string.head_laozhen_desc));
        method1NameTv.setText(resources.getString(R.string.laozhen_title1));
        method2NameTv.setText(resources.getString(R.string.laozhen_title2));
        method3NameTv.setText(resources.getString(R.string.laozhen_title3));
    }

    private void loadShubiaoshouData(){
        titleTv.setText(resources.getString(R.string.head_shubiaoshou));
        typeTv.setText(resources.getString(R.string.head_shubiaoshou));
        typedescTv.setText(resources.getString(R.string.head_shubiaoshou_desc));
        method1NameTv.setText(resources.getString(R.string.shubiaoshou_title1));
        method2NameTv.setText(resources.getString(R.string.shubiaoshou_title2));
        method3NameTv.setText(resources.getString(R.string.shubiaoshou_title3));
    }

    private void loadBozisuantentData(){
        titleTv.setText(resources.getString(R.string.head_bozisuantong));
        typeTv.setText(resources.getString(R.string.head_bozisuantong));
        typedescTv.setText(resources.getString(R.string.head_bozisuantongfajin_desc));
        method1NameTv.setText(resources.getString(R.string.bozisuantong_title1));
        method2NameTv.setText(resources.getString(R.string.bozisuantong_title2));
        method3NameTv.setText(resources.getString(R.string.bozisuantong_title3));
    }

    private void loadJianbeijiangyingData(){
        titleTv.setText(resources.getString(R.string.head_jianbeijiangying));
        typeTv.setText(resources.getString(R.string.head_jianbeijiangying));
        typedescTv.setText(resources.getString(R.string.head_jianbeijiangying_desc));
        method1NameTv.setText(resources.getString(R.string.jianbeijiangying_title1));
        method2NameTv.setText(resources.getString(R.string.jianbeijiangying_title2));
        method3NameTv.setText(resources.getString(R.string.jianbeijiangying_title3));
    }
}
