package com.example.guo.lnproject.module.drink;

import com.example.guo.lnproject.base.BasePresenter;

/**
 * Created by gmw on 16/12/27.
 */

public interface DrinkPresenter extends BasePresenter
{
    void loadWeatherData(String province, String city);
}
