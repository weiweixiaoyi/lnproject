package com.example.guo.lnproject.bean;

/**
 * Created by Administrator on 2016/3/6 0006.
 */
public class MyWeatherEntity {
    private String city;
    private String now_temp;
    private String weather;
    private String humidity;
    private String day_temp;


    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getNow_temp() {
        return now_temp;
    }

    public void setNow_temp(String now_temp) {
        this.now_temp = now_temp;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getDay_temp() {
        return day_temp;
    }

    public void setDay_temp(String day_temp) {
        this.day_temp = day_temp;
    }

    @Override
    public String toString() {
        return "MyWeatherEntity{" +
                "city='" + city + '\'' +
                ", now_temp='" + now_temp + '\'' +
                ", weather='" + weather + '\'' +
                ", humidity='" + humidity + '\'' +
                ", day_temp='" + day_temp + '\'' +
                '}';
    }
}
