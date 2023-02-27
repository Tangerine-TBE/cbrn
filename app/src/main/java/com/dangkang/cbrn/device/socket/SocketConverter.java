package com.dangkang.cbrn.device.socket;

import com.dangkang.cbrn.bean.ModelDeviceBean;
import com.dangkang.cbrn.event.ItemEvent;
import com.dangkang.cbrn.socket.AbstractDevice;
import com.dangkang.cbrn.socket.SocketServer;
import com.dangkang.core.utils.L;

import org.greenrobot.eventbus.EventBus;

import kotlin.text.Charsets;

public class SocketConverter {
    public SocketConverter(){

    }
    public void data(byte[] data, String ip) {
        AbstractDevice abstractDevice = SocketServer.Companion.getInstance().findDeviceByIp(ip);
        if (abstractDevice !=null){
            abstractDevice.write(new byte[]{0x01});
        }
        if (data.length == 3) {
            /*指令回复*/
            byte cmdType = data[1];
            if (cmdType == 0x01) {
                /*开始回复，同步开始*/
                L.e("同步开始");
            } else if (cmdType == 0x00) {
                L.e("配置成功");
            } else if (cmdType == 0x02) {
                L.e("停止测量");
            }
        } else if (data.length == 4) {
            L.e("消杀");
        } else {
            if (data[0] == 0x0F) {
                if (data[1] == 0x01) {
                    int power = data[2];
                    String deviceID = new String(new byte[]{data[4],data[5],data[6],data[7],data[8],data[9]},Charsets.US_ASCII);
                    ModelDeviceBean modelDeviceBean = new ModelDeviceBean(1,power,"WIFI",deviceID);
//                    EventBus.getDefault().post(new ItemEvent());
                }

            }
        }
    }

    public byte[] cmdStart() {
        L.e("开始指令下发");
        return new byte[]{(byte) 0x0F, 0x01, (byte) 0x0E};
    }

    public byte[] cmdStop() {
        L.e("停止指令下发");

        return new byte[]{(byte) 0x0F, 0x02, (byte) 0x0E};
    }

}
