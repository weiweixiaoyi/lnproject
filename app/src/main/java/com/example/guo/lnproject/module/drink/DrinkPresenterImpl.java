package com.example.guo.lnproject.module.drink;

import com.example.guo.lnproject.R;
import com.example.guo.lnproject.bean.MyWeatherEntity;
import com.example.guo.lnproject.utils.Contacts;
import com.example.guo.lnproject.utils.WeatherParser;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * DrinkPresenterImpl
 * Created by gmw on 16/12/27.
 */

public class DrinkPresenterImpl implements DrinkPresenter
{
    private DrinkView mView;

    public DrinkPresenterImpl (DrinkView mView)
    {

        this.mView = mView;
    }

    @Override
    public void loadWeatherData (String province, String city)
    {

        OkHttpUtils.get().url(Contacts.WEATHER_URL).addParams("key", Contacts.MOB_KEY).addParams
                ("city", city).addParams("province", province).build().execute(new StringCallback()
        {

            @Override
            public void onError (Call call, Exception e)
            {

                mView.showSnackBarInfo(R.string.error_string);
            }

            @Override
            public void onResponse (String response)
            {

                MyWeatherEntity entity = new WeatherParser().parse(response);
                if (entity != null)
                {
                    mView.updateWheatherInfo(entity);
                }
            }
        });
    }
}
