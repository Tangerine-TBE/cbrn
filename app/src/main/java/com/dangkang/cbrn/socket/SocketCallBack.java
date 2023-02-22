package com.dangkang.cbrn.socket;

public interface SocketCallBack {
        void disconnect();
        void receiver(String data);
}
