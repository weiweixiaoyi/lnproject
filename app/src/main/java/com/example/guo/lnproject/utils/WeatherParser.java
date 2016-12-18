package com.example.guo.lnproject.utils;

import android.text.TextUtils;
import android.util.Log;

import com.example.guo.lnproject.bean.MyWeatherEntity;
import com.example.guo.lnproject.utils.Contacts;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2016/3/6 0006.
 */
public class WeatherParser {
    public MyWeatherEntity parse(String data){
        MyWeatherEntity entity = new MyWeatherEntity();
        try {
            JSONObject object = new JSONObject(data);
            String code = object.getString("retCode");
            if(TextUtils.equals(code, Contacts.RESPONSE_SUCCEESS)) {
                JSONObject obj = object.getJSONArray("result").getJSONObject(0);
                entity.setCity(obj.getString("city"));
                Log.i("DrinkFragment",obj.getString("temperature"));
                entity.setNow_temp(obj.getString("temperature"));
                entity.setHumidity(obj.getString("humidity"));
                entity.setWeather(obj.getString("weather"));
                entity.setDay_temp(obj.getJSONArray("future").getJSONObject(0).getString("temperature"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return entity;
    }
}
