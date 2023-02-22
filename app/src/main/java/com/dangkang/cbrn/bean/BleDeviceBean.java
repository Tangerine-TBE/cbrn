package com.dangkang.cbrn.bean;

import android.bluetooth.BluetoothDevice;
import android.os.Parcel;

import com.clj.fastble.data.BleDevice;

public class BleDeviceBean extends BleDevice {
    public boolean isSelected;
    public BleDeviceBean(BluetoothDevice device) {
        super(device);
    }

    public BleDeviceBean(BluetoothDevice device, int rssi, byte[] scanRecord, long timestampNanos) {
        super(device, rssi, scanRecord, timestampNanos);
    }

    public BleDeviceBean(Parcel in) {
        super(in);
    }
}
