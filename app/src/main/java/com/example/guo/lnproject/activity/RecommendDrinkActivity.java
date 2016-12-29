package com.example.guo.lnproject.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.example.guo.lnproject.R;
import com.example.guo.lnproject.adapter.BaseHolderAdapter;
import com.example.guo.lnproject.adapter.ViewHolder;
import com.example.guo.lnproject.base.BaseActivity;
import com.example.guo.lnproject.bean.CookMethodEntity;
import com.example.guo.lnproject.bean.RecommendDrinkEntity;
import com.example.guo.lnproject.utils.utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by Administrator on 2016/3/9.
 */
public class RecommendDrinkActivity extends BaseActivity {

    private static final String TAG = "RecommendDrinkActivity";
    List<RecommendDrinkEntity> recommendList;
    private BaseHolderAdapter<RecommendDrinkEntity> adapter;
    @Bind(R.id.recommenddrink_listview)
    public ListView mListView;
    @Bind(R.id.recommenddrink_backImg)
    public ImageView backImg;
    @Bind(R.id.recommenddrink_titleTv)
    public TextView titleTv;
    private Resources resources;
    public static final int FROM_RECOMMEND = 0;
    public static final int FROM_CUP = 1;
    public static final int FROM_PILL = 2;
    public static void startAction(Context context,int from){
        Intent intent = new Intent(context,RecommendDrinkActivity.class);
        intent.putExtra("from",from);
        context.startActivity(intent);
    }

    public static void startAction(Context context,int from,String recipe,String title,String resource){
        Intent intent = new Intent(context,RecommendDrinkActivity.class);
        intent.putExtra("from",from);
        intent.putExtra("method",recipe);
        intent.putExtra("title",title);
        intent.putExtra("resource",resource);
        context.startActivity(intent);
    }

    @Override
    protected int setUpContentView ()
    {

        return R.layout.activity_recommenddrink;
    }

    @Override
    protected void initPresenter ()
    {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recommendList = new ArrayList<>();
        mListView = ((ListView) findViewById(R.id.recommenddrink_listview));
        backImg.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                utils.getAppManager().finishActivity(RecommendDrinkActivity.this);
            }
        });
        resources = getResources();
        int from = getIntent().getIntExtra("from",0);
        if(from == FROM_RECOMMEND) {
            initRecommendDatas();
        }else if(from == FROM_CUP){
            initCupDatas();
        }else if(from == FROM_PILL){
            initCookDetails();
        }
    }

    private void initRecommendDatas() {
        titleTv.setText("专家推荐喝水计划");
        adapter = new BaseHolderAdapter<RecommendDrinkEntity>(this, recommendList, R.layout.item_listview_drink_recommend) {
            @Override
            public void convert(ViewHolder helper, RecommendDrinkEntity item, long position) {
                TextView titleTv = helper.getView(R.id.recommenddrink_titleTv);
                TextView contentTv = helper.getView(R.id.recommenddrink_contentTv);
                titleTv.setText(item.getTitle());
                contentTv.setText(item.getContent());
            }
        };
        mListView.setAdapter(adapter);
        for (int i = 1; i < 9; i++) {
            recommendList.add(new RecommendDrinkEntity(resources.getIdentifier("recommend_title"+i, "string", getPackageName()), resources.getIdentifier("recommend_content"+i, "string", getPackageName())));
        }
        adapter.notifyDataSetChanged();
    }


    private void initCupDatas(){
        titleTv.setText("水杯容量参考");
        adapter = new BaseHolderAdapter<RecommendDrinkEntity>(this,recommendList,R.layout.item_listview_drink_cup) {
            @Override
            public void convert(ViewHolder helper, RecommendDrinkEntity item, long position) {
                TextView titleTv = helper.getView(R.id.cup_title);
                TextView contentTv = helper.getView(R.id.cup_content);
                ImageView imageView = helper.getView(R.id.cup_img);
                titleTv.setText(item.getTitle());
                contentTv.setText(item.getContent());
                imageView.setImageDrawable(resources.getDrawable(item.getImage()));
            }
        };
        mListView.setAdapter(adapter);
        for (int i = 1; i < 9; i++) {
            recommendList.add(new RecommendDrinkEntity(resources.getIdentifier("cup_title"+i,"string",getPackageName()),resources.getIdentifier("cup_content" + i, "string", getPackageName()),resources.getIdentifier("drink_cup" + i, "drawable", getPackageName())));
        }
        adapter.notifyDataSetChanged();
    }

    private void initCookDetails(){
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String method = intent.getStringExtra("method");
        String resource = intent.getStringExtra("resource");
        Log.i(TAG,"title--"+title+",method---"+method+",resource--"+resource);
        if(resource != null && resource.length()>4){
            resource = resource.substring(2,resource.length()-1);
            resource = resource.replace("\"","");
        }
        titleTv.setText(title);
        List<CookMethodEntity> cookMethodList = new ArrayList<>();
        BaseHolderAdapter<CookMethodEntity> adapter;
        adapter = new BaseHolderAdapter<CookMethodEntity>(this,cookMethodList,R.layout.item_listview_cookdetail) {
            @Override
            public void convert(ViewHolder helper, CookMethodEntity item, long position) {
                TextView titleTv = helper.getView(R.id.cookdetail_title);
                ImageView imageView = helper.getView(R.id.cookdetail_image);
                titleTv.setText(item.getStep());
                if(item.getImg()!=null && item.getImg().length()>0) {
                    imageView.setVisibility(View.VISIBLE);
                    Glide.with(RecommendDrinkActivity.this).load(item.getImg()).into(imageView);
                }else{
                    imageView.setVisibility(View.GONE);
                }
            }
        };
        View view = LayoutInflater.from(this).inflate(R.layout.header_listview_recommenddrink,null);
        TextView tv = ((TextView) view.findViewById(R.id.cookdetail_resourceTv));
        tv.setText(resource);
        mListView.addHeaderView(view);
        mListView.setAdapter(adapter);
        List<CookMethodEntity> cookMethodEntities = JSON.parseArray(method, CookMethodEntity.class);
        if(cookMethodEntities != null)
        cookMethodList.addAll(cookMethodEntities);
        adapter.notifyDataSetChanged();
    }

}
