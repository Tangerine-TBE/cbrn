package com.dangkang.cbrn.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * @author:Administrator
 * @date:2023/1/11
 */
@Entity
public class DeviceInfo {
    @Id(autoincrement = true)
    private Long id;
    private String brand; //试剂盒Id
    private String result; //试剂盒期望结果
    private String type;//试剂盒检测类型
    private int status ;//状态

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Generated(hash = 624190010)
    public DeviceInfo(Long id, String brand, String result, String type, int status) {
        this.id = id;
        this.brand = brand;
        this.result = result;
        this.type = type;
        this.status = status;
    }

    @Generated(hash = 2125166935)
    public DeviceInfo() {
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
