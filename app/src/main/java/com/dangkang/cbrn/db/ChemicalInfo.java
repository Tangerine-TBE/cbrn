package com.dangkang.cbrn.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

@Entity
public class ChemicalInfo {
    @Id(autoincrement = true)
    private long id;
    private String charts;//字符
    private int icon; //图标
    private String chemicalName; //制剂或化学品名称
    private String minValue; //报警下限
    private String midValue; //报警中限
    private String maxValue;//报警上限
    private String unit; //单位
    @Generated(hash = 331286568)
    public ChemicalInfo(long id, String charts, int icon, String chemicalName, String minValue, String midValue, String maxValue, String unit) {
        this.id = id;
        this.charts = charts;
        this.icon = icon;
        this.chemicalName = chemicalName;
        this.minValue = minValue;
        this.midValue = midValue;
        this.maxValue = maxValue;
        this.unit = unit;
    }
    @Generated(hash = 1109499097)
    public ChemicalInfo() {
    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCharts() {
        return charts;
    }

    public void setCharts(String charts) {
        this.charts = charts;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getChemicalName() {
        return chemicalName;
    }

    public void setChemicalName(String chemicalName) {
        this.chemicalName = chemicalName;
    }

    public String getMinValue() {
        return minValue;
    }

    public void setMinValue(String minValue) {
        this.minValue = minValue;
    }

    public String getMidValue() {
        return midValue;
    }

    public void setMidValue(String midValue) {
        this.midValue = midValue;
    }

    public String getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(String maxValue) {
        this.maxValue = maxValue;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
