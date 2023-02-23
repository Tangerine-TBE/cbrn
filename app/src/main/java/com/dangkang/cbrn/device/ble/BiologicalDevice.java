package com.dangkang.cbrn.device.ble;

import com.dangkang.cbrn.device.DataConverter;
import com.dangkang.core.utils.L;
import com.dangkang.core.utils.StringUtil;

import java.nio.charset.Charset;

import kotlin.text.Charsets;

/**
 * @author:Administrator
 * @date:2023/2/1
 */
public class BiologicalDevice  {
    public int formatData(byte[] data, int type) {
        /*1.校验数据*/
        if (type == 0) {
            /*广播解析*/
            byte[] bytes = new byte[]{data[13]};
            String value = new String(bytes,Charsets.US_ASCII);
            return Integer.parseInt(value);
        } else {

        }
        return -1;
    }

    public byte[] getData(int type, int status) {
        /*指令操作
         * 1.滴液前状态变更*/
        /*复位指令*/
        byte cmdType;
        byte cmdStatus;
        if (type == 0) {
            cmdType = 0x01;
            if (status == 0) {
                cmdStatus = 0x01;//未滴液
            } else {
                cmdStatus = 0x02;//滴液后
            }
        } else if (type == 1){
            cmdType = 0x02;
            if (status == 1) {
                cmdStatus = 0x01;//阴性
            } else if (status == 2) {
                cmdStatus = 0x02;//阳性
            } else if (status == 3) {
                cmdStatus = 0x03;//弱阳性
            } else {
                cmdStatus = 0x04;//无效
            }
        }else{
            cmdType =0x00; //重置
            cmdStatus = 0x00;
        }
        byte[] cmd = new byte[]{(byte) 0xDC, cmdType, cmdStatus, (byte) 0xCD};
        L.e(StringUtil.byte2HexStr(cmd));
        return cmd;
    }


}
