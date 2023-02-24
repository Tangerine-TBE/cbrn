package com.dangkang.cbrn.socket;

import java.io.InputStream;
import java.io.OutputStream;

public interface SocketCallBack {
        void disconnect(int type,String ip);
        void receiver(String data,String ip);
        void write(OutputStream outputStream);
}
