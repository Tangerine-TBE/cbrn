package com.dangkang.cbrn.socket;

import java.io.InputStream;
import java.io.OutputStream;

public interface SocketCallBack {
        void disconnect(int type,String ip);
        void receiverByte(byte[] data,String ip);
        void receiverString(String data, String ip);
        void heartBeat(OutputStream outputStream);
}
