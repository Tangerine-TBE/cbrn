package com.dangkang.core.ble;

import android.Manifest;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothProfile;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import com.dangkang.core.application.BaseApplication;
import com.dangkang.core.utils.L;
import com.dangkang.core.utils.StringUtil;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public abstract class BLEBaseConnection {
    private Disposable mDisposable;
    private int EXPECTED_STATE; //
    private BluetoothDevice mDevice;
    private IBluetoothListener mIBluetoothListener;
    private BluetoothGatt mGatt;
    private BluetoothGattCallback callback;
    private final String TAG ;
    /*我再想UUID 对于底层逻辑来说太过于灵活，所以直接不要把UUID写死*/
    /*但又不想使用者在对写入UUID 读取UUID 服务UUID在多个地方做过多的写入操作*/
    BLEBaseConnection(BluetoothDevice device, IBluetoothListener iBluetoothListener) {
        mDevice = device;
        mIBluetoothListener = iBluetoothListener;
        callback = makeGattCallback();
        TAG = device.getAddress();
    }

    public final void connect() {
        EXPECTED_STATE = BluetoothProfile.STATE_CONNECTED;
        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
        mDisposable = Observable.interval(2000, TimeUnit.MILLISECONDS).subscribe(new Consumer<Long>() {
            @Override
            public void accept(Long aLong) throws Exception {
                mGatt = mDevice.connectGatt(BaseApplication.APPLICATION, false, callback, BluetoothDevice.TRANSPORT_LE);
            }
        });


    }
    private BluetoothGattCallback makeGattCallback() {
        return new BluetoothGattCallback() {
            /**
             * @date 2019/10/10
             * @author: TanBoen
             * @description: 当蓝牙连接状态发生改变
             */
            @Override
            public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
                super.onConnectionStateChange(gatt, status, newState);
                if (status == BluetoothGatt.GATT_SUCCESS) {
                    switch (newState) {
                        case BluetoothProfile.STATE_CONNECTED:
                            if (EXPECTED_STATE == BluetoothProfile.STATE_DISCONNECTED) {
                                L.d(TAG, "已连接但期望离线");
                                refreshDevicesCache();
                                gatt.close();
                                disconnect();
                                disconnected();
                                return;
                            }
                            L.d(TAG, "已连接");
                            connected();
                            gatt.discoverServices();
                            break;
                        case BluetoothProfile.STATE_DISCONNECTED:
                            disconnected();
                            if (EXPECTED_STATE == BluetoothProfile.STATE_CONNECTED) {
                                L.d(TAG, "已离线但期望连接");
                                refreshDevicesCache();
                                gatt.close();
                                unregisterChrNotifies();
                                connect();
                            }
                            L.d(TAG, "已离线");
                            break;
                    }
                } else if (status == 133) {
                    L.d(TAG, "连接状态133");
                    disconnected();
                    if (EXPECTED_STATE == BluetoothProfile.STATE_CONNECTED) {
                        L.d(TAG, "已超时再尝试连接");
                        refreshDevicesCache();
                        gatt.close();
                        unregisterChrNotifies();

                        disconnect();
                        connect();
                    }
                } else if (status == 8) {
                    L.d(TAG, "连接状态8");
                    if (EXPECTED_STATE == BluetoothProfile.STATE_CONNECTED) {
                        L.d(TAG, "已超时再尝试连接");
                        refreshDevicesCache();
                        gatt.close();
                        unregisterChrNotifies();

                        disconnect();
                        connect();
                    }
                } else if (status == 19) {
                    if (newState == 0) {
                        L.d(TAG, "连接状态19");
                        if (EXPECTED_STATE == BluetoothProfile.STATE_CONNECTED) {
                            L.d(TAG, "已超时再尝试连接");
                            refreshDevicesCache();
                            gatt.close();
                            unregisterChrNotifies();

                            disconnect();
                            connect();
                        }
                    }
                }
            }

            @Override
            public void onServicesDiscovered(BluetoothGatt gatt, int status) {
                super.onServicesDiscovered(gatt, status);
                if (status == BluetoothGatt.GATT_SUCCESS) {
                    L.d(TAG, "发现服务");
                    readyToWrite();
                }
            }

            @Override
            public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic chr, int status) {
                L.d(TAG, "读取数据回调" + StringUtil.byte2HexStr(chr.getValue()));
                byte[] raw = chr.getValue();
                receivedData(raw);

            }

            @Override
            public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic chr, int status) {
                L.d(TAG, "写入数据回调" + StringUtil.byte2HexStr(chr.getValue()));
            }

            @Override
            public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic chr) {
                byte[] raw = chr.getValue();
                try {
                    L.d(TAG, "特性变动 " + chr.getUuid() + " 值 [" + StringUtil.byte2HexStr(raw) + "] " + new String(raw, "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                receivedData(raw);
            }
        };

    }

    public final void write(UUID svc, UUID wri, byte[] any) {
        if (mGatt != null) {
            BluetoothGattService service = mGatt.getService(svc);
            if (service != null) {
                BluetoothGattCharacteristic characteristic = service.getCharacteristic(wri);
                if (characteristic != null) {
                    characteristic.setValue(any);
                    boolean result = mGatt.writeCharacteristic(characteristic);
                    L.d(TAG + wri, result + "----" + StringUtil.byte2HexStr(any));
                }
            }
        }
    }
    public void disconnect() {
        EXPECTED_STATE = BluetoothProfile.STATE_DISCONNECTED;
        unregisterChrNotifies();
        disconnected();
        if (mGatt != null) {
            refreshDevicesCache();
            mGatt.close();
            mGatt.disconnect();
            mGatt = null;
        }
    }
    public final void registerChrNotify(UUID svc, UUID chr) {
        if (mGatt == null) {
            L.d(TAG, "gatt = null");
            return;
        }
        BluetoothGattService service = mGatt.getService(svc);
        if (service == null) {
            L.d(TAG, "service = null");
            return;
        }
        BluetoothGattCharacteristic ch = service.getCharacteristic(chr);
        boolean can = mGatt.setCharacteristicNotification(ch, true);
        L.d(TAG, "subscribe " + ch.getUuid() + " success=" + can);
        for (BluetoothGattDescriptor descriptor : ch.getDescriptors()) {
            descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
            boolean success = mGatt.writeDescriptor(descriptor);
            Log.d(TAG, "writeDesc " + descriptor.getUuid() + " success==" + success);
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void    unregisterChrNotifies(UUID svc, UUID chr){
        BluetoothGattCharacteristic ch = mGatt.getService(svc).getCharacteristic(chr);
        mGatt.setCharacteristicNotification(ch, false);
        for (BluetoothGattDescriptor descriptor : ch.getDescriptors()) {
            descriptor.setValue(BluetoothGattDescriptor.DISABLE_NOTIFICATION_VALUE);
            mGatt.writeDescriptor(descriptor);
        }
    }

    /**
     * Date: 2019/11/4
     * Author : tangerine
     * Description:  释放蓝牙缓存
     */
    private boolean refreshDevicesCache() {
        if (mGatt != null) {
            try {
                Method method = mGatt.getClass().getMethod("refresh", new Class[0]);
                if (method != null) {
                    boolean boost = (Boolean) method.invoke(
                            mGatt, new Object[0]);
                    return boost;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return false;
    }
    protected abstract void disconnected();

    protected abstract void connected();

    protected abstract void readyToWrite();

    protected abstract void receivedData(byte[] raw);
}
