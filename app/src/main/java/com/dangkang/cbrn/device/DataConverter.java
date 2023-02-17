package com.dangkang.cbrn.device;

/**
 * @author:Administrator
 * @date:2023/2/1
 */
public interface DataConverter {
    int formatData(byte[] data);
    byte[] writeData();
}
