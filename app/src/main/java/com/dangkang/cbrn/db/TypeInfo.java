package com.dangkang.cbrn.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @author:Administrator
 * @date:2023/2/6
 */
@Entity
public class TypeInfo {
    private int type;
    private String name;
    @Generated(hash = 509720005)
    public TypeInfo(int type, String name) {
        this.type = type;
        this.name = name;
    }
    @Generated(hash = 1655550078)
    public TypeInfo() {
    }
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
