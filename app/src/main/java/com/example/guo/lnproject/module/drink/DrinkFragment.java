package com.example.guo.lnproject.module.drink;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.guo.lnproject.R;
import com.example.guo.lnproject.activity.RecommendDrinkActivity;
import com.example.guo.lnproject.activity.TimeRemindActivity;
import com.example.guo.lnproject.bean.ProvinceEntity;
import com.example.guo.lnproject.bean.RecommendDrinkEntity;
import com.example.guo.lnproject.utils.CommonSPManager;
import com.example.guo.lnproject.utils.Contacts;
import com.example.guo.lnproject.utils.DensityUtil;
import com.example.guo.lnproject.utils.LogUtils;
import com.example.guo.lnproject.utils.WeatherParser;
import com.example.guo.lnproject.bean.MyWeatherEntity;
import com.example.guo.lnproject.widget.DonutProgress;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.xmlpull.v1.XmlPullParser;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by Administrator on 2016/3/1 0001.
 */
public class DrinkFragment extends Fragment {
    @Bind(R.id.drink_weather_locationTv)
    public TextView weather_locationTv;
    @Bind(R.id.drink_weather_statusTv)
    public TextView weather_statusTv;
    @Bind(R.id.drink_weather_temperatureTv)
    public TextView weather_temperatureTv;
    @Bind(R.id.drink_weather_wetTv)
    public TextView weather_wetTv;

    @Bind(R.id.drink_needWaterTv)
    public TextView needWaterTv;
    @Bind(R.id.donut_progress)
    public DonutProgress drinkProgress;

    private static final String TAG = "DrinkFragment";
    private View convertView;

    private Activity activity;
    private String province = "北京";
    private String city = "北京";
    private int provinceIndex;
    private ArrayAdapter<String> adapter;
    private Dialog locationDialog;
    private ListView locationListView;
    private List<ProvinceEntity> provinceList;
    private Dialog adjustDialog;
    private int drinkGoal;
    private int drinkWater;
    private int needWater;
    private EditText currGoalEt;
    public Dialog drinkWaterDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        convertView = inflater.inflate(R.layout.fragment_drink, null);
        ButterKnife.bind(this, convertView);
        initDatas();
        return convertView;
    }

    private void loadDrinkData() {
        int[] arr = CommonSPManager.getDrinkTime(activity);
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        LogUtils.i(TAG,"year = "+year +",month = "+month+",day = "+day);
        if(!(arr[0] == year && arr[1] == month && arr[2] == day))
       {
            arr[0] = year;
            arr[1] = month;
            arr[2] = day;
            CommonSPManager.setDrinkTime(activity, arr);
        }
        drinkGoal = CommonSPManager.getDrinkGoal(activity);
        drinkWater = CommonSPManager.getDrinkWater(activity);
        needWater = (drinkGoal - drinkWater) > 0 ?(drinkGoal - drinkWater):0;
        needWaterTv.setText(needWater + "ml");
        currGoalEt.setText(drinkGoal + "");
        drinkProgress.setProgress(getProgressValue());
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        activity = getActivity();
        initLocationDialog();
        initAdjustDialog();
        initDrinkWaterDialog();
        getProvinceList();
        loadDrinkData();
    }

    private void getProvinceList() {
        try {
            provinceList = getPersons(activity.getResources().getAssets().open("city.xml"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }


    private int getProgressValue(){
        double d = (double)drinkWater/drinkGoal;
        LogUtils.i(TAG,"drinkWater--"+drinkWater+",drinkGoal--"+drinkGoal+",d---"+d);
        DecimalFormat df = new DecimalFormat("0");
        String s = df.format(d*100);
        Integer i = Integer.parseInt(s);
        return i>100?100:i;
    }

    private void initLocationDialog() {
        View view = LayoutInflater.from(activity).inflate(
                R.layout.dialog_select_location, null);
        locationListView = (ListView) view.findViewById(R.id.location_listview);
        adapter = new ArrayAdapter<String>(activity,
                android.R.layout.simple_list_item_1);
        locationListView.setAdapter(adapter);
        locationDialog = new Dialog(activity, R.style.location_dialog);
        locationDialog.setContentView(view);
        locationDialog.setCanceledOnTouchOutside(false);// 设置点击Dialog外部任意区域关闭Dialog
        WindowManager.LayoutParams params = locationDialog.getWindow()
                .getAttributes();
        params.width = DensityUtil.getScreenMetrics(activity)[0];
        params.height = DensityUtil.getScreenMetrics(activity)[1];
        locationDialog.getWindow().setAttributes(params);

        locationListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @SuppressLint("NewApi")
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                locationDialog.dismiss();
                if (position < provinceList.size()) {
                    if (province == null) {
                        provinceIndex = position;
                        province = provinceList.get(position).getName();
                        if (provinceList.get(position).getCities() != null
                                && provinceList.get(position).getCities().size() > 0) {

                            adapter.clear();
                            adapter.addAll(provinceList.get(position).getCities());
                            locationListView.setSelection(0);
                            locationDialog.show();
                        } else {
                            city = province;
                            initDatas();
                        }
                    } else {
                        city = provinceList.get(provinceIndex).getCities().get(position);
                        Log.i("info", "province---" + province + ",city--" + city);
                        initDatas();
                    }

                }
            }
        });

        view.findViewById(R.id.loacation_backTv).setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        locationDialog.dismiss();
                    }
                });
    }

    private void initAdjustDialog() {
        View view = LayoutInflater.from(activity).inflate(
                R.layout.dialog_adjust_goal, null);
        currGoalEt = (EditText) view.findViewById(R.id.adjust_curr_goalEt);
        final EditText editGoalEt = (EditText) view.findViewById(R.id.adjust_edit_goalEt);
        TextView backTv = ((TextView) view.findViewById(R.id.adjust_backTv));
        TextView confirmTv = ((TextView) view.findViewById(R.id.adjust_confirmTv));

        backTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adjustDialog.dismiss();
            }
        });
        confirmTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adjustDialog.dismiss();
                String editGoalString = editGoalEt.getText().toString();
                drinkGoal = Integer.parseInt(editGoalString);
                CommonSPManager.setDrinkGoal(activity, drinkGoal);
                needWater = drinkGoal - drinkWater;
                needWaterTv.setText(needWater + "ml");
                drinkProgress.setProgress(getProgressValue());
            }
        });
        adjustDialog = new Dialog(activity, R.style.location_dialog);
        adjustDialog.setContentView(view);
        adjustDialog.setCanceledOnTouchOutside(false);// 设置点击Dialog外部任意区域关闭Dialog
        WindowManager.LayoutParams params = adjustDialog.getWindow()
                .getAttributes();
        params.width = DensityUtil.getScreenMetrics(activity)[0];
        params.height = DensityUtil.getScreenMetrics(activity)[1];
        adjustDialog.getWindow().setAttributes(params);
    }

    private void initDrinkWaterDialog(){
        View view = LayoutInflater.from(activity).inflate(
                R.layout.dialog_drink_water, null);
        final EditText drinkWaterEt = (EditText) view.findViewById(R.id.drinkwater_edittext);
        TextView backTv = ((TextView) view.findViewById(R.id.drinkwater_backTv));
        final TextView confirmTv = ((TextView) view.findViewById(R.id.drinkwater_confirmTv));

        backTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drinkWaterDialog.dismiss();
            }
        });
        confirmTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drinkWaterDialog.dismiss();
                String drinkWaterString = drinkWaterEt.getText().toString();
                int  myDrinkWater = Integer.parseInt((drinkWaterString!= null &&drinkWaterString.length()>0)?drinkWaterString:"0");
                CommonSPManager.setDrinkWater(activity,drinkWater+myDrinkWater);
                needWater = needWater - myDrinkWater;
                drinkWater = drinkWater + myDrinkWater;
                needWaterTv.setText(needWater+"ml");
                drinkProgress.setProgress(getProgressValue());
            }
        });
        drinkWaterDialog = new Dialog(activity, R.style.location_dialog);
        drinkWaterDialog.setContentView(view);
        drinkWaterDialog.setCanceledOnTouchOutside(false);// 设置点击Dialog外部任意区域关闭Dialog
        WindowManager.LayoutParams params = drinkWaterDialog.getWindow()
                .getAttributes();
        params.width = DensityUtil.getScreenMetrics(activity)[0];
        params.height = DensityUtil.getScreenMetrics(activity)[1];
        drinkWaterDialog.getWindow().setAttributes(params);
    }

    private void resetLocationDialog() {
        province = null;
        city = null;
        provinceIndex = 0;
        adapter.clear();
        for (int i = 0; i < provinceList.size(); i++) {
            adapter.add(provinceList.get(i).getName());
        }
        adapter.notifyDataSetChanged();
    }

    @OnClick({R.id.drink_weather_layout, R.id.drink_recommendLayout, R.id.drink_cupLayout, R.id.drink_adjustLayout, R.id.drink_remindLayout})
    public void onClick(View view) {
        if (view.getId() == R.id.drink_weather_layout) {
            province = null;
            city = null;
            provinceIndex = 0;
            resetLocationDialog();
            locationDialog.show();
        } else if (view.getId() == R.id.drink_recommendLayout) {
            RecommendDrinkActivity.startAction(activity, RecommendDrinkActivity.FROM_RECOMMEND);
        } else if (view.getId() == R.id.drink_cupLayout) {
            RecommendDrinkActivity.startAction(activity, RecommendDrinkActivity.FROM_CUP);
        } else if (view.getId() == R.id.drink_remindLayout) {
            TimeRemindActivity.startAction(activity,Contacts.TYPE_DRINK);
        } else if (view.getId() == R.id.drink_adjustLayout) {
            adjustDialog.show();
        }
    }

    private void initDatas() {
        OkHttpUtils
                .get()
                .url(Contacts.WEATHER_URL)
                .addParams("key", Contacts.MOB_KEY)
                .addParams("city", city)
                .addParams("province", province)
                .build()
                .execute(new StringCallback() {

                    @Override
                    public void onError(Call call, Exception e) {
                        Snackbar.make(convertView, R.string.error_string, Snackbar.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response) {
                        Log.i(TAG, "response--" + response);
                        MyWeatherEntity entity = new WeatherParser().parse(response);
                        Log.i(TAG, "entity" + entity.toString());
                        if (entity != null) {
                            fillWeatherData(entity);
                        }
                    }
                });
    }

    private void fillWeatherData(MyWeatherEntity entity) {
        weather_locationTv.setText(entity.getCity() + "  " + (entity.getNow_temp() == null ? "暂无数据" : entity.getNow_temp()));
        weather_statusTv.setText(entity.getWeather() == null ? "暂无数据" : entity.getWeather());
        weather_temperatureTv.setText(entity.getDay_temp() == null ? "暂无数据" : entity.getDay_temp());
        weather_wetTv.setText(entity.getHumidity() == null ? "暂无数据" : entity.getHumidity());
    }

    public static List<ProvinceEntity> getPersons(InputStream inStream)
            throws Throwable {
        Log.i("info", "getPersons");
        List<ProvinceEntity> persons = null;
        ProvinceEntity person = null;
        List<String> cities = null;
        XmlPullParser parser = Xml.newPullParser();
        parser.setInput(inStream, "UTF-8");
        int eventType = parser.getEventType();// 产生第一个事件
        while (eventType != XmlPullParser.END_DOCUMENT) {// 只要不是文档结束事件
            switch (eventType) {
                case XmlPullParser.START_DOCUMENT:
                    persons = new ArrayList<ProvinceEntity>();
                    break;

                case XmlPullParser.START_TAG:
                    String name = parser.getName();// 获取解析器当前指向的元素的名称
                    Log.i("info", name);
                    if ("province".equals(name)) {
                        person = new ProvinceEntity();
                        cities = new ArrayList<String>();
                        person.setName(parser.getAttributeValue(0));
                    }
                    if (person != null) {
                        if ("city".equals(name)) {
                            cities.add(parser.getAttributeValue(0));
                            // person.setName(parser.nextText());//获取解析器当前指向元素的下一个文本节点的值
                        }

                    }
                    break;
                case XmlPullParser.END_TAG:
                    Log.i("info", parser.getName());
                    if ("province".equals(parser.getName())) {
                        person.setCities(cities);
                        persons.add(person);
                        person = null;
                        cities = null;
                    }
                    break;
            }
            eventType = parser.next();
        }
        return persons;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

}
