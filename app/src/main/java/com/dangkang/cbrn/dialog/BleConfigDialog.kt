package com.dangkang.cbrn.dialog

import android.app.Dialog
import android.bluetooth.BluetoothGatt
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.view.Gravity
import android.view.LayoutInflater
import android.view.Window
import com.alibaba.fastjson.JSONObject
import com.clj.fastble.BleManager
import com.clj.fastble.callback.BleGattCallback
import com.clj.fastble.callback.BleWriteCallback
import com.clj.fastble.data.BleDevice
import com.clj.fastble.exception.BleException
import com.dangkang.cbrn.R
import com.dangkang.cbrn.dao.DaoTool
import com.dangkang.cbrn.databinding.DialogBleConfigBinding
import com.dangkang.cbrn.db.DeviceInfo
import com.dangkang.cbrn.device.ble.BiologicalDevice
import com.dangkang.cbrn.utils.DimenUtil
import java.lang.ref.WeakReference

class BleConfigDialog(context: Context,mCanceledListener:OnCanceledListener) {

    private var mDialog: Dialog? = null
    private var mBind: DialogBleConfigBinding? = null
    private var myHandler: MyHandler? = null
    private var mCanceledListener:OnCanceledListener? = null

    init {
        myHandler = MyHandler(this)
        this.mDialog = Dialog(context, R.style.DialogStyle).apply {
            setCancelable(false)
            mBind = DialogBleConfigBinding.inflate(LayoutInflater.from(context))
            //        取屏幕长宽高



            setContentView(mBind!!.root)
        }
        val deviceWidth: Int = DimenUtil.getScreenWidth()
        val deviceHeight: Int = DimenUtil.getScreenHeight()
        val dialogWindow: Window = mDialog?.window!!
        val layoutParams = dialogWindow.attributes
        layoutParams.width = deviceWidth / 3 * 2
        layoutParams.height = deviceHeight /  3 * 2
        layoutParams.gravity = Gravity.CENTER
        this.mCanceledListener = mCanceledListener
    }

    private fun initView() {
        Handler(Looper.myLooper()!!).postDelayed({
            Thread {
                val deviceInfo = DaoTool.queryAllDeviceInfo()
                //发送总数
                if (deviceInfo.isEmpty()) {
                    return@Thread
                }
                val jsonObject = JSONObject()
                jsonObject["max_device"] = deviceInfo.size
                myHandler?.obtainMessage(1, jsonObject.toJSONString())?.sendToTarget()
                val index = 0
                dfs(deviceInfo, index)
            }.start()
        }, 1000)
    }
    public fun showDialog(){
        mDialog?.show()
        initView()
    }
    private fun cancelDialog() {
        if (mDialog != null) {
            if (mDialog!!.isShowing) {
                mDialog!!.cancel()
                mDialog!!.dismiss()
                mDialog = null
                if (myHandler != null) {
                    myHandler!!.removeCallbacksAndMessages(null)
                    myHandler = null
                }
            }
        }
    }

    private fun dfs(deviceInfo: List<DeviceInfo>, index: Int) {

        val currentDevice = deviceInfo[index]
        val jsonObject = JSONObject()
        jsonObject.clear()
        jsonObject["currentDevice"] = currentDevice.brand
        jsonObject["current_msg"] = "${currentDevice.brand}->正在连接"
        myHandler?.obtainMessage(4, jsonObject.toJSONString())?.sendToTarget()
        BleManager.getInstance().connect(currentDevice.bleDevice, object : BleGattCallback() {
            override fun onStartConnect() {}
            override fun onConnectFail(bleDevice: BleDevice?, exception: BleException?) {
                jsonObject.clear()
                jsonObject["currentIndex"] = index+1
                jsonObject["currentDevice"] = currentDevice.brand
                jsonObject["current_msg"] = "${currentDevice.brand}->连接错误->${exception.toString()}"
                myHandler?.obtainMessage(3, jsonObject.toJSONString())?.sendToTarget()
                val next = index + 1
                /*收集连接错误*/
                if (next == deviceInfo.size) {
                    myHandler?.obtainMessage(5)?.sendToTarget()
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
                val type: Int = if (currentDevice.result.equals("阴性")) {
                    1
                } else if (currentDevice.result.equals("阳性")) {
                    2
                } else if (currentDevice.result.equals("弱阳")) {
                    3
                } else {
                    4
                }
                val resultCmd = BiologicalDevice().getData(
                    1, type
                )
                BleManager.getInstance()
                    .write(bleDevice, serUUID, wriUUID, resultCmd, object : BleWriteCallback() {
                        override fun onWriteSuccess(
                            current: Int, total: Int, justWrite: ByteArray?
                        ) {
                            BleManager.getInstance().disconnect(bleDevice)
                            jsonObject.clear()
                            jsonObject["currentIndex"] = index+1
                            jsonObject["rst_devices"] = deviceInfo.size-index-1
                            myHandler?.obtainMessage(2, jsonObject.toJSONString())?.sendToTarget()
                            val next = index + 1
                            if (next == deviceInfo.size) {
                                myHandler?.obtainMessage(5)?.sendToTarget()
                                return
                            }
                            dfs(deviceInfo, next)
                        }

                        override fun onWriteFailure(exception: BleException?) {
                            /*收集写入错误*/
                            jsonObject.clear()
                            jsonObject["currentIndex"] = index+1
                            jsonObject["currentDevice"] = currentDevice.brand
                            jsonObject["current_msg"] =
                                "${currentDevice.brand}->写入错误->${exception.toString()}"
                            myHandler?.obtainMessage(3, jsonObject.toJSONString())?.sendToTarget()
                            val next = index + 1
                            if (next == deviceInfo.size) {
                                myHandler?.obtainMessage(5)?.sendToTarget()
                                return
                            }
                            dfs(deviceInfo, next)
                        }
                    })
            }

            override fun onDisConnected(
                isActiveDisConnected: Boolean, device: BleDevice?, gatt: BluetoothGatt?, status: Int
            ) {
            }
        })
    }
    public interface OnCanceledListener{
        fun cancel(connectDevices:Int)
    }

    class MyHandler(bleConfigDialog: BleConfigDialog) : Handler(Looper.myLooper()!!) {
        private var weakReference: WeakReference<BleConfigDialog>? = null

        init {
            weakReference = WeakReference(bleConfigDialog)
        }

        override fun handleMessage(msg: Message) {
            val bleConfigDialog = weakReference?.get() ?: return
            when (msg.what) {
                1 -> {
                    //总数
                    val jsonObject = JSONObject.parseObject(msg.obj.toString())
                    bleConfigDialog.mBind?.progress?.max = jsonObject.getInteger("max_device")

                }
                2 -> {
                    //当前第几个设备
                    val jsonObject = JSONObject.parseObject(msg.obj.toString())
                    bleConfigDialog.mBind?.progress?.progress = jsonObject.getInteger("currentIndex")
                    bleConfigDialog.mBind?.deviceSetNum?.text =  jsonObject.getInteger("rst_devices").toString()
                }
                3 -> {
                    val jsonObject = JSONObject.parseObject(msg.obj.toString())
                    bleConfigDialog.mBind?.progress?.progress = jsonObject.getInteger("currentIndex")
                    bleConfigDialog.mBind?.tvText?.text = jsonObject.getString("current_msg")
                }
                4->{
                    val jsonObject = JSONObject.parseObject(msg.obj.toString())
                    bleConfigDialog.mBind?.currentDevice?.text=jsonObject.getString("currentDevice")
                }
                5->{
                    postDelayed({
                        bleConfigDialog.cancelDialog()
                        bleConfigDialog.mCanceledListener
                    },1000)
                }
            }
        }

    }

}