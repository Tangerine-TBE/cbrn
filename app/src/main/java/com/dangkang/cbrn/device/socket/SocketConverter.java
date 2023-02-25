package com.dangkang.cbrn.device.socket;

import com.dangkang.cbrn.socket.AbstractDevice;
import com.dangkang.cbrn.socket.SocketServer;

public class SocketConverter {

    public void data(byte[] data,String ip) {
        AbstractDevice abstractDevice =  SocketServer.Companion.getInstance().findDeviceByIp(ip);
        byte status1 = data[2];
        byte status2 = data[3];
        if (status1 == 0x30 && status2 == 0x31){
            //心跳包
           if (abstractDevice != null){
               abstractDevice.write(new byte[]{0x01});
            //解析包
           }
        }
    }

}
