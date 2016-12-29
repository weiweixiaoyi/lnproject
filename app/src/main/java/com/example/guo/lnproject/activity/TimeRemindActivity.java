package com.example.guo.lnproject.activity;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.guo.lnproject.R;
import com.example.guo.lnproject.adapter.BaseHolderAdapter;
import com.example.guo.lnproject.adapter.ViewHolder;
import com.example.guo.lnproject.alarm.Alarm;
import com.example.guo.lnproject.alarm.AlarmColumns;
import com.example.guo.lnproject.alarm.AlarmDBManager;
import com.example.guo.lnproject.base.BaseActivity;
import com.example.guo.lnproject.utils.CommonSPManager;
import com.example.guo.lnproject.utils.DensityUtil;
import com.example.guo.lnproject.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/3/19.
 */
public class TimeRemindActivity extends BaseActivity {
    private static final String TAG ="TimeRemindActivity" ;
    @Bind(R.id.timeremind_musicImg)
    public ImageView musicImg;
    @Bind(R.id.timeremind_listview)
    public ListView listView;
    private BaseHolderAdapter<Alarm> adapter;
    private List<Alarm> datas;
    private Resources resources;

    public static final int UPDATE_ALARM = 1;
    public static final int NEW_ALARM = 0;
    private Dialog deleteDialog;
    private int alarmType;
    private int currentItemLongPosition;
    private int bellType;


    public static void startAction(Context context,int alarmType){
        Intent intent = new Intent(context,TimeRemindActivity.class);
        intent.putExtra("alarmType",alarmType);
        context.startActivity(intent);
    }


    @Override
    protected int setUpContentView ()
    {

        return R.layout.activity_timeremind;
    }

    @Override
    protected void initPresenter ()
    {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        resources = getResources();
        initData();
        initView();
    }

    private void initData() {
        bellType = CommonSPManager.getBell(TimeRemindActivity.this);
        alarmType = getIntent().getIntExtra("alarmType", 0);
        LogUtils.i(TAG,"alarmType = "+alarmType);
    }

    private void loadData() {
        List<Alarm> lists = AlarmDBManager.getInstance().getAllAlarm(alarmType);
        if (lists != null && lists.size() > 0) {
            datas.clear();
            datas.addAll(lists);
            adapter.notifyDataSetChanged();
        } else {
            Log.i("tag", "lists--null");
        }
    }

    private void initView() {
        datas = new ArrayList<Alarm>();
        adapter = new BaseHolderAdapter<Alarm>(TimeRemindActivity.this, datas, R.layout.item_listview_timeremind) {
            @Override
            public void convert(ViewHolder helper, final Alarm item, long position) {
                TextView time = helper.getView(R.id.timerremind_timeTv);
                final ImageView switchImg = helper.getView(R.id.timeremind_switchImg);
                time.setText((item.getHour() < 10 ? "0" + item.getHour() : item.getHour()) + ":" + (item.getMinute() < 10 ? "0" + item.getMinute() : item.getMinute()));
                if (item.getEnabled() == 1) {
                    switchImg.setImageResource(R.mipmap.btn_on);
                } else {
                    switchImg.setImageResource(R.mipmap.btn_off);
                }
                switchImg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ContentValues values = new ContentValues();
                        values.put(AlarmColumns.ENABLED, item.getEnabled() == 1 ? 0 : 1);
                        AlarmDBManager.getInstance().updateAlarm(item.getId(), values);
                        if (item.getEnabled() == 1) {
                            item.setEnabled(0);
                            switchImg.setImageResource(R.mipmap.btn_off);
                        } else {
                            item.setEnabled(1);
                            switchImg.setImageResource(R.mipmap.btn_on);
                        }
//                        AlarmClockManager.getInstance().setNextAlarm(TimeRemindActivity.this);
                    }
                });
            }
        };
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Alarm alarm = datas.get(position);
                NewAlarmActivity.startAction(TimeRemindActivity.this, alarmType, UPDATE_ALARM, alarm.getHour(), alarm.getMinute(), alarm.getLabel(), alarm.getId());
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                currentItemLongPosition = position;
                if (deleteDialog == null) {
                    initDeleteDialog();
                } else {
                    deleteDialog.show();
                }
                return true;
            }

        });
        musicImg.setImageResource(bellType==1?R.mipmap.audio_long:R.mipmap.audio_short);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    private void initDeleteDialog() {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_alarm_delete, null);
        view.findViewById(R.id.delete_backTv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteDialog.dismiss();
            }
        });
        view.findViewById(R.id.delete_confirmTv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Alarm alarm = datas.get(currentItemLongPosition);
                AlarmDBManager.getInstance().deleteAlarm(alarm.getId());
                datas.remove(currentItemLongPosition);
                adapter.notifyDataSetChanged();
                deleteDialog.dismiss();
            }
        });
        deleteDialog = new Dialog(this, R.style.location_dialog);
        deleteDialog.setContentView(view);
        deleteDialog.setCanceledOnTouchOutside(false);// 设置点击Dialog外部任意区域关闭Dialog
        WindowManager.LayoutParams params = deleteDialog.getWindow()
                .getAttributes();
        params.width = DensityUtil.getScreenMetrics(this)[0];
        params.height = DensityUtil.getScreenMetrics(this)[1];
        deleteDialog.getWindow().setAttributes(params);
        deleteDialog.show();
    }

    @OnClick({R.id.timeremind_backImg, R.id.timeremind_newalarmImg, R.id.timeremind_musicImg})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.timeremind_backImg:
                finish();
                break;
            case R.id.timeremind_newalarmImg:
                NewAlarmActivity.startAction(TimeRemindActivity.this, alarmType, NEW_ALARM);
                break;
            case R.id.timeremind_musicImg:
                bellType = (bellType == 1?0:1);
                CommonSPManager.setBell(TimeRemindActivity.this,bellType);
                musicImg.setImageResource(bellType==1?R.mipmap.audio_long:R.mipmap.audio_short);
                break;
        }
    }
}
