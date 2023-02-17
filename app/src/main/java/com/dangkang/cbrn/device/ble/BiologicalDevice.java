package com.dangkang.cbrn.device.ble;

import com.dangkang.cbrn.device.DataConverter;

/**
 * @author:Administrator
 * @date:2023/2/1
 */
public class BiologicalDevice implements DataConverter {
    @Override
    public int formatData(byte[] data) {
        /*1.校验数据*/
        if (data[0] == -36 &&  data[data.length-1] == -51){
           String status =  String.valueOf(data[6]);
           return Integer.parseInt(status);
        }
        return -1;
    }

    @Override
    public byte[] writeData() {
        return new byte[0];
    }
}
