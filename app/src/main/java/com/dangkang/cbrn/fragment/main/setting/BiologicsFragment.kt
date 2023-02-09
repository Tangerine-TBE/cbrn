package com.dangkang.cbrn.fragment.main.setting

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Service
import android.bluetooth.BluetoothAdapter
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.location.LocationManager
import android.provider.Settings
import android.text.TextUtils
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.viewbinding.ViewBinding
import com.clj.fastble.BleManager
import com.clj.fastble.callback.BleScanCallback
import com.clj.fastble.data.BleDevice
import com.clj.fastble.scan.BleScanRuleConfig
import com.dangkang.cbrn.R
import com.dangkang.cbrn.adapter.setting.BiologicsAdapter
import com.dangkang.cbrn.dao.DaoTool
import com.dangkang.cbrn.databinding.FragmentSettingsBiologicsBinding
import com.dangkang.cbrn.db.DeviceInfo
import com.dangkang.core.fragment.BaseFragment

class BiologicsFragment : BaseFragment<ViewBinding>() {
    /*全程生命周期需要:*/
    /*1.动态监听蓝牙启动或关闭广播以达到业务流畅*/
    /*2.动态监听位置定位启动或关闭广播以达到业务流畅*/
    /*初始化生命周期需要:*/
    /*1.检测权限
    * 2.检测蓝牙是否开启
    * 3.检测位置服务是否开启
    * 4.打开蓝牙扫描（视图可见开启蓝牙扫描 视图不可见关闭蓝牙扫描）*/
    private var biologicsAdapter: BiologicsAdapter?= null
    private var mScanStop = false
    override fun setBindingView(): ViewBinding {
        binding = FragmentSettingsBiologicsBinding.inflate(layoutInflater)


        return initView(binding as FragmentSettingsBiologicsBinding)
    }
    override fun onBackPressedSupport(): Boolean {
        findFragment(SettingFragment::class.java).onBackPressedSupport()
        return true
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
                if (mScanStop){
                    return
                }
                val deviceBrand = bleDevice.name
                if (TextUtils.isEmpty(deviceBrand)){
                    return
                }
                var deviceInfo :DeviceInfo?= DaoTool.queryDeviceInfo(deviceBrand)
                if (deviceInfo == null){
                    deviceInfo  = DeviceInfo()
                    deviceInfo.brand = deviceBrand
                    deviceInfo.result = resources.getStringArray(R.array.biglogics_result)[0] //附加默认值
                    deviceInfo.type = resources.getStringArray(R.array.biglogics_type)[0] //附加默认值
                }
                for (item in biologicsAdapter?.data()!!){
                    if (deviceInfo.brand.equals(item.brand)){
                        return
                    }
                }
               val text =  (binding as FragmentSettingsBiologicsBinding).deviceNameSet.text
                (binding as FragmentSettingsBiologicsBinding).deviceNameSet.text = "${deviceInfo.brand}  $text"
                biologicsAdapter?.addItem(deviceInfo)
                (binding as FragmentSettingsBiologicsBinding).recyclerView.scrollToPosition(0)
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



    override fun onResume() {
        /*为什么要在onResume上做这些？因为不想当前fra会在隐藏或者不被用户看到的时候
        做一些用户交互，当转入当前fra 被用户看到时，才进行这部份逻辑，我觉得相当不错的体验*/

        /*全程生命周期需要:*/
        /*1.动态监听蓝牙启动或关闭广播以达到业务流畅*/
        /*2.动态监听位置定位启动或关闭广播以达到业务流畅*/
        /*1.检测权限*/
        super.onResume()
        mScanStop = false
        val intentFilter = IntentFilter()
        intentFilter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED)
        intentFilter.addAction(LocationManager.PROVIDERS_CHANGED_ACTION)
        _mActivity.registerReceiver(
            systemListener,intentFilter
        )
        /*初始化生命周期需要:*/
        /**
         * 2.检测蓝牙是否开启
         * 3.检测位置服务是否开启
         * 4.打开蓝牙扫描（视图可见开启蓝牙扫描 视图不可见关闭蓝牙扫描）*/
        val bluetoothAdapter: BluetoothAdapter? = BluetoothAdapter.getDefaultAdapter()
        if (!bluetoothAdapter?.isEnabled!!) {
            Toast.makeText(_mActivity, "请先打开蓝牙", Toast.LENGTH_LONG).show()
            return
        }
        startWork()
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
     @Deprecated("Deprecated in Java")
     override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {
            if (checkGPSIsOpen()) {
                setScanRule()
                startScan()
            }
        }
    }
    private fun checkGPSIsOpen(): Boolean {
        val locationManager = _mActivity.getSystemService(Context.LOCATION_SERVICE) as LocationManager?
            ?: return false
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }
    /**
     * onPause用于切换视图的停止*/
    override fun onPause() {
        mScanStop = true
        BleManager.getInstance().cancelScan()
        super.onPause()
    }
    /**
     * onDestroy用于销毁视图的停止*/
    override fun onDestroy() {
        super.onDestroy()
        try {
            BleManager.getInstance().cancelScan()
            mScanStop = true
            _mActivity.unregisterReceiver(systemListener)
        }catch (e:java.lang.Exception){
            e.printStackTrace()
        }

    }



    private fun initView(binding: FragmentSettingsBiologicsBinding): ViewBinding {
        biologicsAdapter =
            BiologicsAdapter(_mActivity)
        binding.recyclerView.adapter = biologicsAdapter
        binding.recyclerView.layoutManager = GridLayoutManager(_mActivity,2)
        val pagerSnapHelper = PagerSnapHelper()
        pagerSnapHelper.attachToRecyclerView( binding.recyclerView)
        return binding
    }
    fun getRadiationInfo():List<DeviceInfo>{
        return biologicsAdapter?.data() as List<DeviceInfo>
    }

    private var systemListener: SystemListener = SystemListener()
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