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
public class BiologicalDevice implements DataConverter {
    @Override
    public int formatData(byte[] data, int type) {
        /*1.校验数据*/
        if (type == 0) {
            /*广播解析*/
            byte[] bytes = new byte[]{data[13]};
            String value = new String(bytes,Charsets.US_ASCII);
            L.e(value);
            return Integer.parseInt(StringUtil.byte2HexStr(bytes));
        } else {

        }
        return -1;
    }

    @Override
    public byte[] getData(int type, int status) {
        /*指令操作
         * 1.滴液前状态变更*/
        byte cmdType;
        byte cmdStatus;
        if (type == 0) {
            cmdType = 0x01;
            if (status == 0) {
                cmdStatus = 0x01;
            } else {
                cmdStatus = 0x02;
            }
        } else {
            cmdType = 0x02;
            if (status == 1) {
                cmdStatus = 0x01;
            } else if (status == 2) {
                cmdStatus = 0x02;
            } else if (status == 3) {
                cmdStatus = 0x03;
            } else {
                cmdStatus = 0x04;
            }
        }
        byte[] cmd = new byte[]{(byte) 0xDC, cmdType, cmdStatus, (byte) 0xCD};
        L.e(StringUtil.byte2HexStr(cmd));
        return cmd;
    }


}
