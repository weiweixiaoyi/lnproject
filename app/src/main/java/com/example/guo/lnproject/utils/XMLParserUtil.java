package com.example.guo.lnproject.utils;

import android.util.Log;
import android.util.Xml;

import com.example.guo.lnproject.bean.ProvinceEntity;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gmw on 16/12/28.
 */
public class XMLParserUtil
{
    private static XMLParserUtil ourInstance = new XMLParserUtil();

    public static XMLParserUtil getInstance ()
    {

        return ourInstance;
    }

    private XMLParserUtil ()
    {

    }

    public List<ProvinceEntity> parseXML(InputStream inStream){
        List<ProvinceEntity> persons = null;
        ProvinceEntity person = null;
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
                        persons = new ArrayList<>();
                        break;

                    case XmlPullParser.START_TAG:
                        String name = parser.getName();// 获取解析器当前指向的元素的名称
                        if ("province".equals(name))
                        {
                            person = new ProvinceEntity();
                            cities = new ArrayList<>();
                            person.setName(parser.getAttributeValue(0));
                        }
                        if (person != null)
                        {
                            if ("city".equals(name))
                            {
                                cities.add(parser.getAttributeValue(0));
                                // person.setName(parser.nextText());//获取解析器当前指向元素的下一个文本节点的值
                            }

                        }
                        break;
                    case XmlPullParser.END_TAG:
                        Log.i("info", parser.getName());
                        if ("province".equals(parser.getName()))
                        {
                            person.setCities(cities);
                            persons.add(person);
                            person = null;
                            cities = null;
                        }
                        break;
                }
                eventType = parser.next();
            }
        }catch (XmlPullParserException | IOException e){
            e.printStackTrace();
        }
        return persons;
    }
}

