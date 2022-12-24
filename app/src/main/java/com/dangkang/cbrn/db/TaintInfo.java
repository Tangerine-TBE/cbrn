package com.dangkang.cbrn.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

@Entity
public class TaintInfo {
    @Id(autoincrement = true)
    private Long id;
    private int type; //化学，核辐射
    private int taint_num; //污点编号
    private String taint_loc; //污点位置
    private String taint_sim; //模拟污点
    private String taint_dis; //辐射距离
    private String taint_max; // 中心峰值

    @Generated(hash = 1646616816)
    public TaintInfo() {
    }


    @Generated(hash = 243811105)
    public TaintInfo(Long id, int type, int taint_num, String taint_loc, String taint_sim, String taint_dis, String taint_max) {
        this.id = id;
        this.type = type;
        this.taint_num = taint_num;
        this.taint_loc = taint_loc;
        this.taint_sim = taint_sim;
        this.taint_dis = taint_dis;
        this.taint_max = taint_max;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getTaint_num() {
        return taint_num;
    }

    public void setTaint_num(int taint_num) {
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
}
