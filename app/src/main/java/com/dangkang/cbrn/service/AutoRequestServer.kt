package com.dangkang.cbrn.service

import android.app.Service
import android.bluetooth.BluetoothGatt
import android.content.Intent
import android.os.IBinder
import com.clj.fastble.BleManager
import com.clj.fastble.callback.BleGattCallback
import com.clj.fastble.callback.BleWriteCallback
import com.clj.fastble.data.BleDevice
import com.clj.fastble.exception.BleException
import com.dangkang.cbrn.dao.DaoTool
import com.dangkang.cbrn.db.DeviceInfo
import com.dangkang.core.utils.L

class AutoRequestServer : Service() {


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        /*1.try to connect the index 0f 0 device */
        Thread{
            val deviceInfo = DaoTool.queryAllDeviceInfo()
            val index = 0
            dfs(deviceInfo, index)
        }.start()

        return START_NOT_STICKY
    }

    private fun dfs(deviceInfo: List<DeviceInfo>, index: Int) {

        BleManager.getInstance().connect(deviceInfo[0].bleDevice, object : BleGattCallback() {
            override fun onStartConnect() {
            }

            override fun onConnectFail(bleDevice: BleDevice?, exception: BleException?) {
            }

            override fun onConnectSuccess(
                bleDevice: BleDevice?, gatt: BluetoothGatt?, status: Int
            ) {
                val serUUID = gatt?.services?.get(2)?.uuid.toString()//可用Service特征
                val wriUUID =
                    gatt?.services?.get(2)?.characteristics?.get(1)?.uuid.toString()//可用写入特征
                val resultCmd = byteArrayOf(0x23)
                BleManager.getInstance()
                    .write(bleDevice, serUUID, wriUUID, resultCmd, object : BleWriteCallback() {
                        override fun onWriteSuccess(
                            current: Int, total: Int, justWrite: ByteArray?
                        ) {
                            L.e("写入ok")
                            BleManager.getInstance().disconnectAllDevice()
                            val next = index + 1
                            if (next == deviceInfo.size){
                                stopSelf()
                                return
                            }
                            dfs(deviceInfo, next)
                        }

                        override fun onWriteFailure(exception: BleException?) {
                            L.e(exception.toString())
                        }
                    })
            }

            override fun onDisConnected(
                isActiveDisConnected: Boolean, device: BleDevice?, gatt: BluetoothGatt?, status: Int
            ) {
            }
        })


    }


    override fun onCreate() {
        super.onCreate()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}