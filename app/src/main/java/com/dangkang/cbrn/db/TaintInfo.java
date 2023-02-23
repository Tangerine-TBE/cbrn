package com.dangkang.cbrn.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
/**
 * 这个类是核辐射和化学需要存储的污点*/
@Entity
public class TaintInfo {
    @Id(autoincrement = true)
    private Long id;
    private int type; //化学，核辐射
    private String taint_num; //污点编号
    private String taint_loc; //污点位置
    private String taint_sim; //模拟污点
    private String taint_sim_dis;//模拟污点距离
    private String taint_dis; //辐射距离 / 核辐射        制剂名称  /  化学
    private String taint_max; // 中心峰值
    private String taint_unit;//单位
    private long create_time;//创建时间
    public boolean normal;
    @Generated(hash = 1646616816)
    public TaintInfo() {
    }
    @Generated(hash = 1522749848)
    public TaintInfo(Long id, int type, String taint_num, String taint_loc, String taint_sim, String taint_sim_dis, String taint_dis, String taint_max, String taint_unit, long create_time,
            boolean normal) {
        this.id = id;
        this.type = type;
        this.taint_num = taint_num;
        this.taint_loc = taint_loc;
        this.taint_sim = taint_sim;
        this.taint_sim_dis = taint_sim_dis;
        this.taint_dis = taint_dis;
        this.taint_max = taint_max;
        this.taint_unit = taint_unit;
        this.create_time = create_time;
        this.normal = normal;
    }
    public String getTaint_sim_dis() {
        return taint_sim_dis;
    }

    public void setTaint_sim_dis(String taint_sim_dis) {
        this.taint_sim_dis = taint_sim_dis;
    }

    public String getTaint_unit() {
        return taint_unit;
    }

    public void setTaint_unit(String taint_unit) {
        this.taint_unit = taint_unit;
    }

    public long getCreate_time() {
        return create_time;
    }

    public void setCreate_time(long create_time) {
        this.create_time = create_time;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTaint_num() {
        return taint_num;
    }

    public void setTaint_num(String taint_num) {
        this.taint_num = taint_num;
    }

    public String getTaint_loc() {
        return taint_loc;
    }
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setTaint_loc(String taint_loc) {
        this.taint_loc = taint_loc;
    }

    public String getTaint_sim() {
        return taint_sim;
    }

    public void setTaint_sim(String taint_sim) {
        this.taint_sim = taint_sim;
    }

    public String getTaint_dis() {
        return taint_dis;
    }

    public void setTaint_dis(String taint_dis) {
        this.taint_dis = taint_dis;
    }

    public String getTaint_max() {
        return taint_max;
    }

    public void setTaint_max(String taint_max) {
        this.taint_max = taint_max;
    }
    public boolean getNormal() {
        return this.normal;
    }
    public void setNormal(boolean normal) {
        this.normal = normal;
    }
}
