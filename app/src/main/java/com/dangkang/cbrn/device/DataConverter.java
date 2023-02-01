package com.dangkang.cbrn.device;

/**
 * @author:Administrator
 * @date:2023/2/1
 */
public interface DataConverter {
    Object formatData(byte[] data);
    byte[] writeData();
}
