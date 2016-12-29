package com.example.guo.lnproject.module.pill;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.example.guo.lnproject.R;
import com.example.guo.lnproject.activity.RecommendDrinkActivity;
import com.example.guo.lnproject.activity.SearchPillActivity;
import com.example.guo.lnproject.activity.TimeRemindActivity;
import com.example.guo.lnproject.adapter.MyViewPagerAdapter;
import com.example.guo.lnproject.base.BaseFragment;
import com.example.guo.lnproject.bean.CookbookEntity;
import com.example.guo.lnproject.utils.Contacts;
import com.example.guo.lnproject.utils.DensityUtil;
import com.example.guo.lnproject.widget.CustomViewPager;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/3/16.
 */
public class PillFragment extends BaseFragment implements PillView

{
    private static final String TAG = "PillFragment";
    @Bind(R.id.pill_viewPager)
    public CustomViewPager mViewPager;
    @Bind(R.id.pill_viewpagerLayout)
    public RelativeLayout mViewPagerLayout;
    @Bind(R.id.pill_idot_container)
    public LinearLayout mIdotsContainer;

    private Activity activity;
    private static final int SIZE = 5;
    private List<View> views;
    private int size;
    private int curPage;
    private boolean isViewPagerVisible = false;
    private boolean isViewPagerScroll = true;
    private Handler mHandler;
    private final static int HANDLER_START = 0;
    private final static int HANDLER_DELAY_TIME = 3000;
    private List<CookbookEntity.ResultEntity.ListEntity> datas;
    private LayoutInflater inflater;
    private MyViewPagerAdapter adapter;
    private PillPresenter mPresenter;

    @Override
    protected int setUpContentView() {
        return R.layout.fragment_pill;
    }

    @Override
    protected void initPresenter() {
        mPresenter = new PillPresenterIml(this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mHandler = new MyHandler(this);
        activity = getActivity();
        views = new ArrayList<View>();
        inflater = LayoutInflater.from(activity);
        adapter = new MyViewPagerAdapter(activity, views);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            boolean isDragged = false;

            @Override
            public void onPageSelected(int postion) {
                changeIndicator(postion % size);
                curPage = postion;
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
                if (isViewPagerScroll) {
                    if (arg0 == ViewPager.SCROLL_STATE_DRAGGING) {
                        isDragged = true;
                        mHandler.removeMessages(HANDLER_START);
                    } else if (arg0 == ViewPager.SCROLL_STATE_IDLE) {
                        if (isDragged) {
                            mHandler.sendEmptyMessageDelayed(HANDLER_START,
                                    HANDLER_DELAY_TIME);
                            isDragged = false;
                        }
                    }
                }
            }
        });
        mViewPager.setAdapter(adapter);
        mPresenter.loadCookbookData();
    }

    @OnClick({R.id.pill_searchPillLayout,R.id.pill_timeRemindLayout})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.pill_searchPillLayout:
                startActivity(new Intent(activity, SearchPillActivity.class));
                break;
            case R.id.pill_timeRemindLayout:
                TimeRemindActivity.startAction(activity, Contacts.TYPE_PILL);
                break;
        }
    }

    private void changeIndicator(int position) {
        for (int i = 0; i < mIdotsContainer.getChildCount(); i++) {
            ImageView img = (ImageView) mIdotsContainer.getChildAt(i);
            if (i == position) {
                img.setImageResource(R.mipmap.focus_indicator);
            } else {
                img.setImageResource(R.mipmap.normal_indicator);
            }
        }
    }

    private void initViews() {
        for (int i = 0; i < size; i++) {
            final CookbookEntity.ResultEntity.ListEntity data = datas.get(i);
            View view = inflater.inflate(R.layout.item_viewpager, null);
            ImageView img = (ImageView) view.findViewById(R.id.viewpager_img);
            TextView titleTv = (TextView) view
                    .findViewById(R.id.viewpager_title);
            if(data.getRecipe().getImg() != null && data.getRecipe().getImg().length()>0) {
                Glide.with(this).load(data.getRecipe().getImg()).into(img);
            }else{
                img.setImageResource(R.mipmap.default_png);
            }
            titleTv.setText(data.getName());
            views.add(view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RecommendDrinkActivity.startAction(activity, RecommendDrinkActivity.FROM_PILL, data.getRecipe().getMethod(), data.getName(), data.getRecipe().getIngredients());
                }
            });
        }
        adapter.notifyDataSetChanged();
    }

    private void initIndicator() {
        mIdotsContainer.removeAllViews();
        for (int i = 0; i < size; i++) {
            ImageView imageView = new ImageView(activity);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                    DensityUtil.dip2px(activity, 12),
                    DensityUtil.dip2px(activity, 12));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setLayoutParams(params);
            imageView.setPadding(DensityUtil.dip2px(activity, 5), 10,
                    0, DensityUtil.dip2px(activity, 7));
            imageView.setImageResource(R.mipmap.normal_indicator);
            mIdotsContainer.addView(imageView);
        }
    }


    @Override
    public void onPause() {
        super.onPause();
        if(isViewPagerVisible && isViewPagerScroll){
            mHandler.removeMessages(HANDLER_START);
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        if(isViewPagerVisible && isViewPagerScroll){
            mHandler.sendEmptyMessageDelayed(HANDLER_START,HANDLER_DELAY_TIME);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mHandler.removeCallbacksAndMessages(null);
    }

    @Override
    public void updateBannerData(String data) {
        CookbookEntity entity = JSON.parseObject(data,
                CookbookEntity.class);
        if (entity != null
                && entity.getResult() != null
                && entity.getResult().getList() != null
                && entity.getResult().getList().size() > 0) {
            datas = entity.getResult().getList();
            size = datas.size() > 5 ? 5 : datas.size();
            initViews();
            Log.i(TAG, "entity---"+datas.size());
            if (datas.size() > 1) {
                isViewPagerScroll = true;
                initIndicator();
                mViewPager.setScanScroll(true);
                mViewPager.setCurrentItem(1);
                mHandler.sendEmptyMessageDelayed(HANDLER_START,
                        HANDLER_DELAY_TIME);
            } else {
                isViewPagerScroll = false;
                mHandler.removeMessages(HANDLER_START);
                mViewPager.setScanScroll(false);
            }
            mViewPagerLayout.setVisibility(View.VISIBLE);
            isViewPagerVisible = true;
        } else
        {
            mViewPagerLayout.setVisibility(View.GONE);
            isViewPagerVisible = false;
        }
    }
    private static class MyHandler extends Handler { // 第二步，将需要引用Activity的地方，改成弱引用。
        private WeakReference<PillFragment> atyInstance;

        public MyHandler(PillFragment aty) {
            this.atyInstance = new WeakReference<PillFragment>(aty);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            PillFragment aty = atyInstance == null ? null : atyInstance.get(); // 如果Activity被释放回收了，则不处理这些消息
            if (aty == null || aty.isDetached()) {
                return;
            }
            if (msg.what == HANDLER_START) {
                aty.curPage++;
                aty.mViewPager.setCurrentItem(aty.curPage % aty.size);
                aty.mHandler.sendEmptyMessageDelayed(HANDLER_START,
                        HANDLER_DELAY_TIME);
            }
        }
    }
}
