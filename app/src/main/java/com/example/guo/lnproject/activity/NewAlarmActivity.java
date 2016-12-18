package com.example.guo.lnproject.activity;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.guo.lnproject.R;
import com.example.guo.lnproject.alarm.Alarm;
import com.example.guo.lnproject.alarm.AlarmClockManager;
import com.example.guo.lnproject.alarm.AlarmColumns;
import com.example.guo.lnproject.alarm.AlarmDBManager;
import com.example.guo.lnproject.utils.DensityUtil;
import com.example.guo.lnproject.utils.LogUtils;
import com.example.guo.lnproject.utils.ToastUtils;
import com.example.guo.lnproject.widget.PickerView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/3/27.
 */
public class NewAlarmActivity extends SuperActivity {

    private static final String TAG = "NewAlarmActivity";
    @Bind(R.id.newalarm_hour_pikerView)
    public PickerView hPickerView;
    @Bind(R.id.newalarm_minute_pickerView)
    public PickerView mPickerView;
    @Bind(R.id.newalarm_editLabelLayout)
    public RelativeLayout editLabelLayout;
    @Bind(R.id.newalarm_commitImg)
    public ImageView commitImg;
    @Bind(R.id.newalarm_labelTv)
    public TextView labelTv;
    private Dialog editLabelDialog;
    private String labelString;
    private String hour, minute;
    private int alarmType;
    private int eventType;
    private Alarm mAlarm;
    private int alarm_id;

    public static void startAction(Context context, int alarmType, int eventType) {
        Intent intent = new Intent(context, NewAlarmActivity.class);
        intent.putExtra("eventType", eventType);
        intent.putExtra("alarmType", alarmType);
        context.startActivity(intent);
    }
    public static void startAction(Context context, int alarmType, int eventType,int hour,int minute,String label,int id) {
        Intent intent = new Intent(context, NewAlarmActivity.class);
        intent.putExtra("eventType", eventType);
        intent.putExtra("alarmType", alarmType);
        intent.putExtra("hour", hour);
        intent.putExtra("minute", minute);
        intent.putExtra("label", label);
        intent.putExtra("id", id);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newalarm);
        ButterKnife.bind(this);
        initData();
    }

    @OnClick({R.id.newalarm_editLabelLayout, R.id.newalarm_commitImg, R.id.newalarm_backImg})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.newalarm_editLabelLayout:
                if (editLabelDialog == null) {
                    initDialog();
                } else {
                    editLabelDialog.show();
                }
                break;
            case R.id.newalarm_commitImg:
                commitAlarmData();
                break;
            case R.id.newalarm_backImg:
                finish();
                break;
        }
    }

    private void commitAlarmData() {
            int hourInt = Integer.parseInt(hour);
            int minuteInt = Integer.parseInt(minute);
            LogUtils.i(TAG,"hourInt = "+hourInt +",minuteInt = " +minuteInt);
            Alarm alarm = null;
            if(eventType == TimeRemindActivity.NEW_ALARM){
                alarm = AlarmDBManager.getInstance().getAlarmByTime(hourInt, minuteInt);
                if(alarm == null) {
                    alarm = new Alarm();
                    alarm.setEnabled(1);
                    alarm.setHour(hourInt);
                    alarm.setMinute(minuteInt);
                    alarm.setLabel(labelString == null ? "" : labelString);
                    alarm.setType(alarmType);
                    alarm.setNextMillis(0);
                    AlarmDBManager.getInstance().addAlarm(alarm);
                    AlarmClockManager.getInstance().setAlarm(alarm, this);
                    finish();
                }else{
                    ToastUtils.showToast(NewAlarmActivity.this, "当前时刻闹钟已经被设定，请更改时间");
                }
            }else{
                if(mAlarm != null) {
                    LogUtils.i(TAG,"hour---"+hourInt+",minute---"+minuteInt);
                    alarm = AlarmDBManager.getInstance().getAlarmByTime(hourInt, minuteInt);
                    if (alarm == null) {
                        ContentValues values = new ContentValues();
                        values.put(AlarmColumns.HOUR, hourInt);
                        values.put(AlarmColumns.MINUTE, minuteInt);
                        values.put(AlarmColumns.LABEL, labelString == null ? "" : labelString);
                        values.put(AlarmColumns.ENABLED, 1);
                        AlarmDBManager.getInstance().updateAlarm(mAlarm.getId(), values);
                        mAlarm.setHour(hourInt);
                        mAlarm.setMinute(minuteInt);
                        AlarmClockManager.getInstance().setAlarm(mAlarm, this);
                        finish();
                    } else {
                        ToastUtils.showToast(NewAlarmActivity.this, "当前时刻闹钟已经被设定，请更改时间");
                    }
                }
            }
    }

    private void initDialog() {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_edit_label, null);
        final EditText editText = (EditText) view.findViewById(R.id.editlabel_edittext);
        TextView backTv = (TextView) view.findViewById(R.id.editlabel_backTv);
        TextView confirmTv = (TextView) view.findViewById(R.id.editlabel_confirmTv);
        confirmTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editLabelDialog.dismiss();
                labelString = editText.getText().toString();
                if(labelString != null  && labelString.length()>0 )
                labelTv.setText(labelString);
            }
        });
        backTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editLabelDialog.dismiss();
            }
        });
        editLabelDialog = new Dialog(this, R.style.location_dialog);
        editLabelDialog.setContentView(view);
        editLabelDialog.setCanceledOnTouchOutside(false);// 设置点击Dialog外部任意区域关闭Dialog
        WindowManager.LayoutParams params = editLabelDialog.getWindow()
                .getAttributes();
        params.width = DensityUtil.getScreenMetrics(this)[0];
        params.height = DensityUtil.getScreenMetrics(this)[1];
        editLabelDialog.getWindow().setAttributes(params);
        editLabelDialog.show();
    }


    private void initData() {
        Intent intent = getIntent();
        int hour_intent = 0;
        int minute_intent = 0;
        String label_intent = "";
        if (intent != null) {
            alarmType = intent.getIntExtra("alarmType", 0);
            eventType = intent.getIntExtra("eventType", 0);
//            LogUtils.i(TAG,"eventType---"+eventType);
            if(eventType == TimeRemindActivity.UPDATE_ALARM){
                hour_intent = intent.getIntExtra("hour",0);
                minute_intent = intent.getIntExtra("minute", 0);
                label_intent = intent.getStringExtra("label");
                alarm_id = intent.getIntExtra("id", 0);
                mAlarm = AlarmDBManager.getInstance().getAlarmById(alarm_id);
                LogUtils.i(TAG,"hour_intent--"+hour_intent+",minute_intent---"+minute_intent);
            }
        }
        List<String> hData = new ArrayList<String>();
        List<String> mData = new ArrayList<String>();
        for (int i = 0; i < 24; i++) {
            hData.add(i < 10 ? "0" + i : "" + i);
        }
        for (int i = 0; i < 60; i++) {
            mData.add(i < 10 ? "0" + i : "" + i);
        }
        hPickerView.setData(hData);
        hPickerView.setOnSelectListener(new PickerView.onSelectListener() {

            @Override
            public void onSelect(String text) {
                hour = text;
            }
        });
        mPickerView.setData(mData);
        mPickerView.setOnSelectListener(new PickerView.onSelectListener() {

            @Override
            public void onSelect(String text) {
                minute = text;
            }
        });
        hPickerView.setSelected(hour_intent);
        mPickerView.setSelected(minute_intent);
        hour = hour_intent+"";
        minute = minute_intent+"";
        labelTv.setText(label_intent);
    }
}
