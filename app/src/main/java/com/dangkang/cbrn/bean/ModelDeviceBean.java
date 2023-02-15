package com.dangkang.cbrn.bean;

public class ModelDeviceBean {
    private int model;// 1 = 化学 2 = 生物 3 = 辐射
    private String modelName;//
    private int power;//对于化学，辐射这里是电池电量 对于生物这里是可用试剂盒数量
    private int connect_way;//连接方式

    public ModelDeviceBean(int model, int power, int connect_way) {
        this.model = model;
        if (model == 1){
            this.modelName = "化学制剂模拟检测仪";
        }else if (model == 2){
            this.modelName = "核辐射模拟检测仪";
        }else{
            this.modelName = "生物制剂模拟检测仪";
        }
        this.power = power;
        this.connect_way = connect_way;
    }

    public int getModel() {
        return model;
    }
    public void setModel(int model) {
        this.model = model;
    }
    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }
    public int getPower() {
        return power;
    }
    public void setPower(int power) {
        this.power = power;
    }
    public int getConnect_way() {
        return connect_way;
    }
    public void setConnect_way(int connect_way) {
        this.connect_way = connect_way;
    }
}
