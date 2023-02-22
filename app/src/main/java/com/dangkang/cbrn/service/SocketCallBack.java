package com.dangkang.cbrn.service;

public interface SocketCallBack {
        void disconnect();
        void receiver(String data);
}
