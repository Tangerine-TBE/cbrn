package com.dangkang.cbrn.bean;

/**
 * @author:Administrator
 * @date:
 */
public class CwaBean {
    public String charts;//字符
    public int icon; //图标
    public String chemicalName; //制剂或化学品名称
    public String minValue; //报警下限
    public String midValue; //报警中限
    public String maxValue;//报警上限
    public String unit; //单位

    public CwaBean(String charts, int icon, String chemicalName, String minValue, String midValue, String maxValue, String unit) {
        this.charts = charts;
        this.icon = icon;
        this.chemicalName = chemicalName;
        this.minValue = minValue;
        this.midValue = midValue;
        this.maxValue = maxValue;
        this.unit = unit;
    }
    public CwaBean(){

    }
}
