package com.example.guo.lnproject.module.drink;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.View;
import android.widget.TextView;

import com.example.guo.lnproject.R;
import com.example.guo.lnproject.activity.RecommendDrinkActivity;
import com.example.guo.lnproject.activity.TimeRemindActivity;
import com.example.guo.lnproject.base.BaseFragment;
import com.example.guo.lnproject.bean.MyWeatherEntity;
import com.example.guo.lnproject.bean.ProvinceEntity;
import com.example.guo.lnproject.utils.CalendarUtil;
import com.example.guo.lnproject.utils.CommonSPManager;
import com.example.guo.lnproject.utils.Contacts;
import com.example.guo.lnproject.utils.MathUtil;
import com.example.guo.lnproject.utils.WeatherParser;
import com.example.guo.lnproject.utils.XMLParserUtil;
import com.example.guo.lnproject.widget.DonutProgress;
import com.example.guo.lnproject.widget.dialog.AdjustDialog;
import com.example.guo.lnproject.widget.dialog.EditTextDialog;
import com.example.guo.lnproject.widget.dialog.RecyclerViewDialog;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by Administrator on 2016/3/1 0001.
 */
public class DrinkFragment extends BaseFragment
{
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

    private Activity activity;

    private String province = "北京";

    private String city = "北京";

    private int provinceIndex;

    private RecyclerViewDialog locationDialog;

    private List<ProvinceEntity> provinceList;

    private List<String> provinceStringList;

    private AdjustDialog adjustDialog;

    private int drinkGoal;

    private int drinkWater;

    private int needWater;

    public EditTextDialog drinkWaterDialog;

    @Override
    protected int setUpContentView ()
    {

        return R.layout.fragment_drink;
    }

    @Override
    protected void initPresenter ()
    {

    }

    @Override
    public void onViewCreated (View view, @Nullable Bundle savedInstanceState)
    {

        super.onViewCreated(view, savedInstanceState);
        initDatas();
    }

    private void loadDrinkData ()
    {

        SparseIntArray tempDate = CommonSPManager.getDrinkTime(activity);
        SparseIntArray todayDate = CalendarUtil.getCurrentDate();
        if (!isEqualDate(tempDate, todayDate))
        {
            CommonSPManager.setDrinkTime(activity, todayDate);
        }
        drinkGoal = CommonSPManager.getDrinkGoal(activity);
        drinkWater = CommonSPManager.getDrinkWater(activity);
        needWater = (drinkGoal - drinkWater) > 0 ? (drinkGoal - drinkWater) : 0;
        needWaterTv.setText(needWater + "ml");
        drinkProgress.setProgress(getProgressValue());
    }

    @Override
    public void onActivityCreated (Bundle savedInstanceState)
    {

        super.onActivityCreated(savedInstanceState);
        activity = getActivity();
        getProvinceList();
        loadDrinkData();
    }

    /**
     * 判断两个数组的日期是否一致
     *
     * @param date1 数组1
     * @param date2 数组2
     * @return boolean
     */
    private boolean isEqualDate (SparseIntArray date1, SparseIntArray date2)
    {

        return (date1.get(Calendar.YEAR) == date2.get(Calendar.YEAR)) && (date1.get(Calendar
                .MONTH) == date2.get(Calendar.MONTH)) && (date1.get(Calendar.DAY_OF_MONTH) ==
                date2.get(Calendar.DAY_OF_MONTH));
    }

    private void getProvinceList ()
    {

        try
        {
            provinceList = XMLParserUtil.getInstance().parseXML(activity.getResources().getAssets
                    ().open("city.xml"));
            if (provinceList != null && provinceList.size() > 0)
            {
                provinceStringList = new ArrayList<>();
                for (int i = 0; i < provinceList.size(); i++)
                {
                    provinceStringList.add(provinceList.get(i).getName());
                }
            }

        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }


    private int getProgressValue ()
    {

        double d = (double) drinkWater / drinkGoal;
        int i = MathUtil.roundInt(d * 100);
        return i > 100 ? 100 : i;
    }

    private void showLocationDialog ()
    {

        if (locationDialog == null)
        {
            locationDialog = new RecyclerViewDialog(activity);
            locationDialog.refreshRecyclerViewData(provinceStringList);
            locationDialog.setCallBack(new RecyclerViewDialog.DialogCallBack()
            {
                @Override
                public void onCompleteCancel ()
                {

                }

                @Override
                public void onItemClickListener (View view, int position)
                {

                    if (position < provinceList.size())
                    {
                        if (province == null)
                        {
                            provinceIndex = position;
                            province = provinceList.get(position).getName();
                            if (provinceList.get(position).getCities() != null && provinceList
                                    .get(position).getCities().size() > 0)

                            {

                                locationDialog.refreshRecyclerViewData(provinceList.get(position)
                                        .getCities());
//                                locationDialog.resetRecyclerViewSelection();
                                locationDialog.show();
                            }
                            else
                            {
                                city = province;
                                initDatas();
                            }
                        }
                        else
                        {
                            city = provinceList.get(provinceIndex).getCities().get(position);
                            Log.i("info", "province---" + province + ",city--" + city);
                            initDatas();
                        }

                    }
                }
            });
        }
        locationDialog.show();
    }

    private void showAdjustDialog ()
    {

        if (adjustDialog == null)
        {
            adjustDialog = new AdjustDialog(activity);
            adjustDialog.setCurrGoalText(drinkGoal + "");
            adjustDialog.setAdjustGoalText(drinkGoal + "");
            adjustDialog.setCallback(new AdjustDialog.CallBackDialog()
            {
                @Override
                public void completedConfirm ()
                {

                    String editGoalString = adjustDialog.getAdjustGoal();
                    drinkGoal = Integer.parseInt(editGoalString);
                    CommonSPManager.setDrinkGoal(activity, drinkGoal);
                    needWater = drinkGoal - drinkWater;
                    needWaterTv.setText(needWater + "ml");
                    drinkProgress.setProgress(getProgressValue());
                }

                @Override
                public void completedCancel ()
                {

                }
            });
        }
        adjustDialog.show();
    }

    public void showDrinkWaterDialog ()
    {

        if (drinkWaterDialog == null)
        {
            drinkWaterDialog = new EditTextDialog(activity);
            drinkWaterDialog.setTitle(R.string.please_input_drink_water);
            drinkWaterDialog.setCallback(new EditTextDialog.CallBackDialog()
            {
                @Override
                public void completedConfirm ()
                {

                    String drinkWaterString = drinkWaterDialog.getEditTextText();
                    int myDrinkWater = Integer.parseInt((drinkWaterString != null &&
                            drinkWaterString.length() > 0) ? drinkWaterString : "0");

                    CommonSPManager.setDrinkWater(activity, drinkWater + myDrinkWater);
                    needWater = needWater - myDrinkWater;
                    drinkWater = drinkWater + myDrinkWater;
                    needWaterTv.setText(needWater + "ml");
                    drinkProgress.setProgress(getProgressValue());
                }

                @Override
                public void completedCancel ()
                {

                }
            });
        }
        drinkWaterDialog.show();
    }

    private void resetLocationDialog ()
    {

        province = null;
        city = null;
        provinceIndex = 0;
        if (locationDialog != null)
        {
            locationDialog.refreshRecyclerViewData(provinceStringList);
        }
    }

    @OnClick({R.id.drink_weather_layout, R.id.drink_recommendLayout, R.id.drink_cupLayout, R.id
            .drink_adjustLayout, R.id.drink_remindLayout})
    public void onClick (View view)
    {

        switch (view.getId())
        {
            case R.id.drink_weather_layout:
                resetLocationDialog();
                showLocationDialog();
                break;
            case R.id.drink_recommendLayout:
                RecommendDrinkActivity.startAction(activity, RecommendDrinkActivity.FROM_RECOMMEND);
                break;
            case R.id.drink_cupLayout:
                RecommendDrinkActivity.startAction(activity, RecommendDrinkActivity.FROM_CUP);
                break;
            case R.id.drink_adjustLayout:
                showAdjustDialog();
                break;
            case R.id.drink_remindLayout:
                TimeRemindActivity.startAction(activity, Contacts.TYPE_DRINK);
                break;
        }
    }

    private void initDatas ()
    {

        OkHttpUtils.get().url(Contacts.WEATHER_URL).addParams("key", Contacts.MOB_KEY).addParams
                ("city", city).addParams("province", province).build().execute(new StringCallback()
        {

            @Override
            public void onError (Call call, Exception e)
            {

                Snackbar.make(getView(), R.string.error_string, Snackbar.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse (String response)
            {

                Log.i(TAG, "response--" + response);
                MyWeatherEntity entity = new WeatherParser().parse(response);
                Log.i(TAG, "entity" + entity.toString());
                if (entity != null)
                {
                    fillWeatherData(entity);
                }
            }
        });
    }

    private void fillWeatherData (MyWeatherEntity entity)
    {

        weather_locationTv.setText(entity.getCity() + "  " + (entity.getNow_temp() == null ?
                "暂无数据" : entity.getNow_temp()));
        weather_statusTv.setText(entity.getWeather() == null ? "暂无数据" : entity.getWeather());
        weather_temperatureTv.setText(entity.getDay_temp() == null ? "暂无数据" : entity.getDay_temp());
        weather_wetTv.setText(entity.getHumidity() == null ? "暂无数据" : entity.getHumidity());
    }

}
