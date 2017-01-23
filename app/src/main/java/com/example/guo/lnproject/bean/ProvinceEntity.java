package com.example.guo.lnproject.bean;

import java.util.List;

public class ProvinceEntity
{
    private String name;

    private List<String> cities;

    public String getName ()
    {

        return name;
    }

    public void setName (String name)
    {

        this.name = name;
    }

    public List<String> getCities ()
    {

        return cities;
    }

    public void setCities (List<String> cities)
    {

        this.cities = cities;
    }

    @Override
    public String toString ()
    {

        return "ProvinceEntity [name=" + name + ", cities=" + cities + ", getName()=" + getName() + ", getCities()=" + getCities() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
    }


}
