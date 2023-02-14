package com.dangkang.cbrn.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * @author:Administrator
 * @date:2023/2/6
 * 这个类的写入状态是在生物制剂类型显示时用户做添加动作，写出状态是在，设置中加载时，对生物制剂类型进行追加
 */
@Entity
public class TypeInfo {
    private long create_time;
    private int type;
    private String name;
    private int status;
    @Generated(hash = 1782066343)
    public TypeInfo(long create_time, int type, String name, int status) {
        this.create_time = create_time;
        this.type = type;
        this.name = name;
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Generated(hash = 1655550078)
    public TypeInfo() {
    }
    public TypeInfo(int type,String name) {
        this.type = type;
        this.name = name;

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

    public long getCreate_time() {
        return create_time;
    }

    public void setCreate_time(long create_time) {
        this.create_time = create_time;
    }
}
