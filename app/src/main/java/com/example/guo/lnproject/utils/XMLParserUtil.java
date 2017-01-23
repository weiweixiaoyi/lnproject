package com.example.guo.lnproject.utils;

import android.util.Xml;

import com.example.guo.lnproject.LNApplication;
import com.example.guo.lnproject.bean.ProvinceEntity;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Pull解析XML
 * Created by gmw on 16/12/28.
 */
public class XMLParserUtil
{
    private static XMLParserUtil mInstance = null;

    private static List<ProvinceEntity> mProvinceList = null;

    private XMLParserUtil ()
    {

    }

    public static XMLParserUtil getInstance ()
    {

        if (mInstance == null)
        {
            mInstance = new XMLParserUtil();
        }
        return mInstance;
    }

    private void parseXML (InputStream inStream)
    {

        ProvinceEntity provinceEntity = null;
        List<String> cities = null;
        XmlPullParser parser = Xml.newPullParser();
        try
        {
            parser.setInput(inStream, "UTF-8");
            int eventType = parser.getEventType();// 产生第一个事件
            while (eventType != XmlPullParser.END_DOCUMENT)
            {// 只要不是文档结束事件
                switch (eventType)
                {
                    case XmlPullParser.START_DOCUMENT:
                        mProvinceList = new ArrayList<>();
                        break;

                    case XmlPullParser.START_TAG:
                        String name = parser.getName();// 获取解析器当前指向的元素的名称
                        if ("province".equals(name))
                        {
                            provinceEntity = new ProvinceEntity();
                            cities = new ArrayList<>();
                            provinceEntity.setName(parser.getAttributeValue(0));
                        }
                        if (provinceEntity != null)
                        {
                            if ("city".equals(name))
                            {
                                cities.add(parser.getAttributeValue(0));
                            }

                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if ("province".equals(parser.getName()))
                        {
                            provinceEntity.setCities(cities);
                            mProvinceList.add(provinceEntity);
                            provinceEntity = null;
                            cities = null;
                        }
                        break;
                }
                eventType = parser.next();
            }
        } catch (XmlPullParserException | IOException e)
        {
            e.printStackTrace();
        }
    }

    public List<ProvinceEntity> getProvinceList ()
    {

        if (mProvinceList == null)
        {
            try
            {
                parseXML(LNApplication.getInstance().getResources().getAssets().open("city.xml"));
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        return mProvinceList;
    }
}

