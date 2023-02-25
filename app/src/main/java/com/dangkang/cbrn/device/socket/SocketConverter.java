package com.dangkang.cbrn.device.socket;

import com.blankj.utilcode.util.ArrayUtils;
import com.dangkang.cbrn.socket.AbstractDevice;
import com.dangkang.cbrn.socket.SocketServer;
import com.dangkang.core.utils.L;
import com.dangkang.core.utils.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import kotlin.text.Charsets;

public class SocketConverter {

    public void data(byte[] data, String ip) {
        AbstractDevice abstractDevice = SocketServer.Companion.getInstance().findDeviceByIp(ip);
        byte status1 = data[0];
        byte status2 = data[1];
        if (status1 == 0x64 && status2 == 0x63) {
            status1 = data[2];
            status2 = data[3];
            if (status1 == 0x30 && status2 == 0x31) {
                //心跳包
                if (abstractDevice != null) {
                    abstractDevice.write(new byte[]{0x01});
                    //解析包
                    status1 = data[6];
                    status2 = data[7];
                    if (status1 == 0x30 && status2 == 0x31) {
                        StringBuilder str = new StringBuilder();
                        StringBuilder can = new StringBuilder();
                        for (int i = 8; i < data.length - 2; i++) {
                            if (i <= 11) {
                                str.append(new String(new byte[]{data[i]}, Charsets.US_ASCII));
                            } else {
                                can.append(new String(new byte[]{data[i]}, Charsets.US_ASCII));
                            }
                        }
                        //设备ID解析
                        L.e(StringUtil.convertHexToString(str.toString())+ can);
                        //设备电量解析
                        status1 = data[4];
                        status2 = data[5];

                    } else {
                        //核辐射电量

                    }

                }
            }
        }
    }

}
