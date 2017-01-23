package com.example.guo.lnproject.widget.dialog;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.guo.lnproject.R;
import com.example.guo.lnproject.bean.ProvinceEntity;
import com.example.guo.lnproject.utils.DensityUtil;
import com.example.guo.lnproject.utils.XMLParserUtil;
import com.example.guo.lnproject.widget.WeelView.TosAdapterView;
import com.example.guo.lnproject.widget.WeelView.TosGallery;
import com.example.guo.lnproject.widget.WeelView.WheelView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 城市选择对话框
 * Created by gmw on 17/1/12.
 */

public class CityPickerDialog extends BaseDialog
{
    @Bind(R.id.wheelview_province)
    WheelView wheelviewProvince;

    @Bind(R.id.wheelview_city)
    WheelView wheelviewCity;

    private List<ProvinceEntity> provinceEntityList;

    private List<String> provinceStringList;

    private List<String> cityStringList;

    private NumberAdapter provinceAdapter;

    private NumberAdapter cityAdapter;

    private String mCityName;

    private String mProvinceName;

    private int mPosition;

    private CallBackDialog mCallback;

    private TosAdapterView.OnItemSelectedListener mListener = new TosAdapterView
            .OnItemSelectedListener()
    {
        @Override
        public void onItemSelected (TosAdapterView<?> parent, View view, int position, long id)
        {

            if (parent == wheelviewProvince)
            {
                mPosition = position;
                mProvinceName = provinceStringList.get(position);
                mCityName = null;
                updateCityList(position);
            }
            else if (parent == wheelviewCity)
            {
                mCityName = cityStringList.get(position);
            }
        }

        @Override
        public void onNothingSelected (TosAdapterView<?> parent)
        {

        }
    };

    public void setCallback (CallBackDialog callback)
    {

        this.mCallback = callback;
    }

    public interface CallBackDialog
    {
        void completedConfirm ();

        void completedCancel ();
    }

    private void updateCityList (int position)
    {

        cityStringList.clear();
        cityStringList.addAll(provinceEntityList.get(position).getCities());
        cityAdapter.notifyDataSetChanged();
    }

    public CityPickerDialog (Activity activity)
    {

        super(activity);
        initDatas();
        initView();
    }

    private void initDatas ()
    {

        provinceEntityList = XMLParserUtil.getInstance().getProvinceList();
        provinceStringList = new ArrayList<>();
        cityStringList = new ArrayList<>();
        if (provinceEntityList != null && provinceEntityList.size() > 0)
        {
            provinceStringList = new ArrayList<>();
            for (int i = 0; i < provinceEntityList.size(); i++)
            {
                provinceStringList.add(provinceEntityList.get(i).getName());
            }
        }
    }

    private void initView ()
    {

        ButterKnife.bind(this);
        wheelviewProvince.setScrollCycle(false);
        wheelviewCity.setScrollCycle(false);
        provinceAdapter = new NumberAdapter(provinceStringList);
        cityAdapter = new NumberAdapter(cityStringList);
        wheelviewProvince.setAdapter(provinceAdapter);
        wheelviewCity.setAdapter(cityAdapter);
        wheelviewProvince.setSelection(0, true);
        wheelviewCity.setSelection(0, true);
        wheelviewProvince.setOnItemSelectedListener(mListener);
        wheelviewCity.setOnItemSelectedListener(mListener);
        wheelviewProvince.setUnselectedAlpha(0.5f);
        wheelviewCity.setUnselectedAlpha(0.5f);
    }

    public void resetDatas(){
        wheelviewProvince.setSelection(0, false);
        wheelviewCity.setSelection(0, false);
    }

    @Override
    protected int contentView ()
    {

        return R.layout.dialog_city_picker;
    }

    public String getCityName ()
    {
        if(mCityName == null){
            List<String> cities = provinceEntityList.get(mPosition).getCities();
            if(cities != null && cities.size()>0){
                mCityName = cities.get(0);
            }else{
                mCityName = mProvinceName;
            }
        }
        return mCityName;
    }

    public String getProvinceName ()
    {

        return mProvinceName;
    }


    @OnClick({R.id.tv_confirm, R.id.tv_back})
    public void onClick (View view)
    {

        switch (view.getId())
        {
            case R.id.tv_confirm:
                dismiss();
                if (mCallback != null)
                    mCallback.completedConfirm();
                break;
            case R.id.tv_back:
                dismiss();
                if (mCallback != null)
                    mCallback.completedCancel();
                break;
        }
    }

    private class NumberAdapter extends BaseAdapter
    {
        int mHeight = 50;

        List<String> mData = null;

        public NumberAdapter (List<String> data)
        {

            mHeight = DensityUtil.dip2px(activity, mHeight);
            this.mData = data;
        }

        @Override
        public int getCount ()
        {

            return (mData == null) ? 0 : mData.size();
        }

        @Override
        public View getItem (int position)
        {

            return getView(position, null, null);
        }

        @Override
        public long getItemId (int position)
        {

            return position;
        }

        public void setData (List<String> data)
        {

            this.mData = data;
            notifyDataSetChanged();
        }

        @Override
        public View getView (int position, View convertView, ViewGroup parent)
        {

            TextView textView = null;

            if (null == convertView)
            {
                convertView = LayoutInflater.from(activity).inflate(R.layout
                        .item_recyclerview_dialog, parent, false);
                convertView.setLayoutParams(new TosGallery.LayoutParams(-1, mHeight));
                textView = (TextView) convertView;
            }
            String text = mData.get(position);
            textView.setText(text);
            return convertView;
        }
    }

}
