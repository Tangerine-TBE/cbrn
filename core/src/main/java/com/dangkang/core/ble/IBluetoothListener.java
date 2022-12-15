package com.dangkang.core.ble;

public interface IBluetoothListener {
    public void connected();

    public void disconnected();

    public void receivedData(byte[] data);

    public void connectTimeout();

    public void readyToWriteData();
}
