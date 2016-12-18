package com.example.guo.lnproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.example.guo.lnproject.activity.SuperActivity;
import com.example.guo.lnproject.alarm.AlarmClockManager;
import com.example.guo.lnproject.fragment.DrinkFragment;
import com.example.guo.lnproject.fragment.HeadFragment;
import com.example.guo.lnproject.fragment.PillFragment;
import com.example.guo.lnproject.utils.LogUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

public class MainActivity extends SuperActivity {

    private static final String TAG = "MainActivity";
    @Bind(R.id.drawer_content)
    public DrawerLayout mDrawerLayout;
    @Bind(R.id.toolbar)
    public Toolbar toolbar;
    private Fragment drinkFragment;
    private Fragment headFragment;
    private Fragment pillFragment;
    private int currType;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        toolbar.setBackgroundColor(getResources().getColor(R.color.toolbar_bg));
        toolbar.setTitle("喝水时间");
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currType == 0)
                    ((DrinkFragment) drinkFragment).drinkWaterDialog.show();
            }
        });
        initView();
        iniFragment();

        Intent intent = getIntent();
        int type = 0;
        if(intent != null){
            type  = intent.getIntExtra("alarmtype", 0);
        }
        changeFragment(type);
        AlarmClockManager.getInstance().resetAllAlarms(this);
        AlarmClockManager.getInstance().setNextAlarm(this);
    }

    private void iniFragment() {
        FragmentTransaction fTransaction = getSupportFragmentManager().beginTransaction();
        drinkFragment = new DrinkFragment();
        headFragment = new HeadFragment();
        pillFragment = new PillFragment();
        fTransaction.add(R.id.container_content,drinkFragment);
        fTransaction.add(R.id.container_content,headFragment);
        fTransaction.add(R.id.container_content,pillFragment);
        fTransaction.commit();
    }


    private void changeFragment(int position) {
        currType = position;
        FragmentTransaction fTransaction = getSupportFragmentManager().beginTransaction();
        if(drinkFragment != null)
        fTransaction.hide(drinkFragment);
        if(headFragment != null)
        fTransaction.hide(headFragment);
        if(pillFragment != null)
        fTransaction.hide(pillFragment);
        switch(position){
            case 0:
                toolbar.setTitle("喝水时间");
                fTransaction.show(drinkFragment);
                fab.setVisibility(View.VISIBLE);
                break;
            case 1:
                toolbar.setTitle("吃药提醒");
                fTransaction.show(pillFragment);
                fab.setVisibility(View.GONE);
                break;
            case 2:
                toolbar.setTitle("颈椎健康");
                fTransaction.show(headFragment);
                fab.setVisibility(View.GONE);
                break;
        }
        fTransaction.commitAllowingStateLoss();
    }



    private void initView() {
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout
        ,toolbar,R.string.drink_title,R.string.drink_title){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        mDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_more:

                        break;
                }
                return true;
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @OnClick({R.id.drawer_loginLayout,R.id.drawer_drinkLayout,R.id.drawer_pillLayout,R.id.drawer_headLayout})
    public void onClick(View view){
        switch(view.getId()){
            case R.id.drawer_loginLayout:
                break;
            case R.id.drawer_drinkLayout:
                changeFragment(0);
                mDrawerLayout.closeDrawers();
                break;
            case R.id.drawer_pillLayout:
                mDrawerLayout.closeDrawers();
                changeFragment(1);
                break;
            case R.id.drawer_headLayout:
                mDrawerLayout.closeDrawers();
                changeFragment(2);
                break;
        }
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        if(intent != null){
            int type = intent.getIntExtra("alarmtype", 0);
            LogUtils.i(TAG,"onNewIntent---type = "+type);
            changeFragment(type);
        }
    }
}
