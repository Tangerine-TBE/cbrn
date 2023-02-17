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
import com.dangkang.cbrn.device.ble.BiologicalDevice
import com.dangkang.core.utils.L

class AutoRequestServer : Service() {


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        /*1.try to connect the index 0f 0 device */
        /*这里的代码目前是后台无感，没有做收集异常处理
        * 1.需要有感的，阻止用户操作的
        * 2.需要有异常信息收集的*/
        Thread {
            val deviceInfo = DaoTool.queryAllDeviceInfo()
            val index = 0
            dfs(deviceInfo, index)
        }.start()

        return START_NOT_STICKY
    }

    private fun dfs(deviceInfo: List<DeviceInfo>, index: Int) {
        BleManager.getInstance().connect(deviceInfo[index].bleDevice, object : BleGattCallback() {
            override fun onStartConnect() {}
            override fun onConnectFail(bleDevice: BleDevice?, exception: BleException?) {
                val next = index + 1
                /*收集连接错误*/
                if (next == deviceInfo.size) {
                    stopSelf()
                    return
                }
                dfs(deviceInfo, next)
            }

            override fun onConnectSuccess(
                bleDevice: BleDevice?, gatt: BluetoothGatt?, status: Int
            ) {
                val serUUID = gatt?.services?.get(2)?.uuid.toString()//可用Service特征
                val wriUUID =
                    gatt?.services?.get(2)?.characteristics?.get(1)?.uuid.toString()//可用写入特征
                val type: Int = if (deviceInfo[index].result.equals("阴性")){
                    1
                }else if (deviceInfo[index].result.equals("阳性")){
                    2
                }else if (deviceInfo[index].result.equals("弱阳")){
                    3
                }else{
                    4
                }
                val resultCmd = BiologicalDevice().getData(
                    1,
                    type)
                BleManager.getInstance()
                    .write(bleDevice, serUUID, wriUUID, resultCmd, object : BleWriteCallback() {
                        override fun onWriteSuccess(
                            current: Int, total: Int, justWrite: ByteArray?
                        ) {
                            L.e("写入ok")
                            BleManager.getInstance().disconnect(bleDevice)
                            val next = index + 1
                            if (next == deviceInfo.size) {
                                stopSelf()
                                return
                            }
                            dfs(deviceInfo, next)
                        }

                        override fun onWriteFailure(exception: BleException?) {
                            /*收集写入错误*/
                            val next = index + 1
                            if (next == deviceInfo.size) {
                                stopSelf()
                                return
                            }
                            dfs(deviceInfo, next)
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