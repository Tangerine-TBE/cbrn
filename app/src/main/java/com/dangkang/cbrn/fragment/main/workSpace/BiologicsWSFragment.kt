package com.dangkang.cbrn.fragment.main.workSpace

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Service
import android.bluetooth.BluetoothAdapter
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.text.TextUtils
import android.widget.Toast
import androidx.viewbinding.ViewBinding
import com.clj.fastble.BleManager
import com.clj.fastble.callback.BleScanCallback
import com.clj.fastble.data.BleDevice
import com.clj.fastble.scan.BleScanRuleConfig
import com.dangkang.cbrn.R
import com.dangkang.cbrn.dao.DaoTool
import com.dangkang.cbrn.databinding.FragmentBiologicsWsBinding
import com.dangkang.cbrn.databinding.FragmentSettingsBiologicsBinding
import com.dangkang.cbrn.databinding.FragmentWorkSpaceBinding
import com.dangkang.cbrn.db.DeviceInfo
import com.dangkang.core.fragment.BaseFragment

class BiologicsWSFragment :BaseFragment<ViewBinding>() {
    /*这里的流程和设置流程差不多*/
    companion object {
        fun newInstance(): BaseFragment<ViewBinding> {
            return BiologicsWSFragment()
        }
    }

    private var mScanStop = false
    private var fragmentBiologicsWsBinding:FragmentBiologicsWsBinding? = null
    override fun setBindingView(): ViewBinding {
        binding  = FragmentBiologicsWsBinding.inflate(layoutInflater)
        return initView(binding as FragmentBiologicsWsBinding)
    }
    private fun initView(viewBinding: FragmentBiologicsWsBinding):FragmentBiologicsWsBinding{
        fragmentBiologicsWsBinding = viewBinding
        return viewBinding
    }

    override fun onResume() {
        super.onResume()
    }
    private fun checkGPSIsOpen(): Boolean {
        val locationManager = _mActivity.getSystemService(Context.LOCATION_SERVICE) as LocationManager?
            ?: return false
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }
    private fun  startWork(){
        if (!checkGPSIsOpen()){
            AlertDialog.Builder(_mActivity)
                .setTitle("提示")
                .setMessage("当前手机扫描需要打开定位功能")
                .setNegativeButton("取消"
                ) { _, _ -> pop() }
                .setPositiveButton("前往设置"
                ) { _, _ ->
                    val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                    startActivityForResult(intent, 1)
                }
                .setCancelable(false)
                .show()
        }else{
            setScanRule()
            startScan()
        }
    }
    private fun startScan() {
        BleManager.getInstance().scan(object : BleScanCallback() {
            override fun onScanStarted(success: Boolean) {
            }

            override fun onLeScan(bleDevice: BleDevice) {
            }

            @SuppressLint("SetTextI18n")
            override fun onScanning(bleDevice: BleDevice) {
                //主要是这里添加设备
                //背景环境：蓝牙查找回来的设备信息，我默认只需要带一个试剂盒Id就行
                //1.数据库查找这个设备 如果没有设备 添加进数列组，给它一个默认的类型值和结果值
                //2.如果数据库找到了这个设备，那么就用数据中已经设置好的设备信息
                //3.添加进数据列表中
                //4.添加的时候要看看是否有重复添加的现象，去重！
                //根据名字（名字=试剂盒id）数据库查找
            }

            override fun onScanFinished(scanResultList: List<BleDevice>) {
                if (!mScanStop){
                    startScan()
                }
            }
        })
    }

    private fun setScanRule(){
        val scanRuleConfig = BleScanRuleConfig.Builder()
            .setAutoConnect(false) // 连接时的autoConnect参数，可选，默认false
            .setScanTimeOut(10000) // 扫描超时时间，可选，默认10秒
            .build()
        BleManager.getInstance().initScanRule(scanRuleConfig)
    }
    inner class SystemListener : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val action = intent?.action;
            var currentState = ""
            if (BluetoothAdapter.ACTION_STATE_CHANGED == action) {
                when (intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, -1)) {
                    BluetoothAdapter.STATE_TURNING_ON, BluetoothAdapter.STATE_ON -> {
                        currentState = "蓝牙已打开"
                        startWork()
                    }
                    BluetoothAdapter.STATE_TURNING_OFF, BluetoothAdapter.STATE_OFF -> {
                        currentState = "蓝牙已断开"
                    }
                }
            }else if (LocationManager.PROVIDERS_CHANGED_ACTION == action){
                val lm =  context?.getSystemService(Service.LOCATION_SERVICE) as LocationManager
                val isEnabled: Boolean = lm.isProviderEnabled(LocationManager.GPS_PROVIDER)
                if (!isEnabled){
                    AlertDialog.Builder(context)
                        .setTitle("提示")
                        .setMessage("当前手机扫描需要打开定位功能")
                        .setNegativeButton("取消"
                        ) { _, _ -> pop() }
                        .setPositiveButton("前往设置"
                        ) { _, _ ->
                            val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                            startActivityForResult(intent, 1)
                        }
                        .setCancelable(false)
                        .show()
                }
            }
            Toast.makeText(context, currentState, Toast.LENGTH_SHORT).show()
        }

    }

}