package com.dangkang.cbrn.fragment.main.workSpace

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Service
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothGatt
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
import com.clj.fastble.callback.BleGattCallback
import com.clj.fastble.callback.BleScanCallback
import com.clj.fastble.data.BleDevice
import com.clj.fastble.exception.BleException
import com.clj.fastble.scan.BleScanRuleConfig
import com.dangkang.cbrn.adapter.workspace.BiologicsWSAdapter
import com.dangkang.cbrn.adapter.workspace.BiologicsWSAdapter.OnIconClickListener
import com.dangkang.cbrn.dao.DaoTool
import com.dangkang.cbrn.databinding.FragmentBiologicsWsBinding
import com.dangkang.cbrn.device.ble.BiologicalDevice
import com.dangkang.core.fragment.BaseFragment

class BiologicsWSFragment : BaseFragment<ViewBinding>(),OnIconClickListener {
    private var systemListener: SystemListener = SystemListener()
    private var mScanStop = false
    private var biologicsWsAdapter: BiologicsWSAdapter? = null
    override fun setBindingView(): ViewBinding {
        binding = FragmentBiologicsWsBinding.inflate(layoutInflater)
        return initView(binding as FragmentBiologicsWsBinding)
    }

    private fun initView(viewBinding: FragmentBiologicsWsBinding): FragmentBiologicsWsBinding {
        biologicsWsAdapter =
            BiologicsWSAdapter(
                _mActivity,
                this
            )
        val devices = DaoTool.queryAllDeviceInfo()
        for (item in devices.indices){
            biologicsWsAdapter?.addItem(devices[item])
        }
        viewBinding.recyclerView.adapter = biologicsWsAdapter
        viewBinding.recyclerView.layoutManager = GridLayoutManager(_mActivity, 2)
        val pagerSnapHelper = PagerSnapHelper()
        pagerSnapHelper.attachToRecyclerView(viewBinding.recyclerView)
        return viewBinding
    }
    override fun onBackPressedSupport(): Boolean {
        val fragment  = findFragment(WorkSpaceFragment::class.java)
        return if (fragment != null){
            fragment.onBackPressedSupport()
            true
        }else{
            false
        }
    }
    override fun onItemClicked() {
        //通过ble蓝牙发送指令
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
            } else if (LocationManager.PROVIDERS_CHANGED_ACTION == action) {
                val lm = context?.getSystemService(Service.LOCATION_SERVICE) as LocationManager
                val isEnabled: Boolean = lm.isProviderEnabled(LocationManager.GPS_PROVIDER)
                if (!isEnabled) {
                    AlertDialog.Builder(context).setTitle("提示").setMessage("当前手机扫描需要打开定位功能")
                        .setNegativeButton(
                            "取消"
                        ) { _, _ -> pop() }.setPositiveButton(
                            "前往设置"
                        ) { _, _ ->
                            val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                            startActivityForResult(intent, 1)
                        }.setCancelable(false).show()
                }
            }
            Toast.makeText(context, currentState, Toast.LENGTH_SHORT).show()
        }

    }

    private fun setScanRule() {
        val scanRuleConfig =
            BleScanRuleConfig.Builder().setAutoConnect(false) // 连接时的autoConnect参数，可选，默认false
                .setScanTimeOut(10000) // 扫描超时时间，可选，默认10秒
                .build()
        BleManager.getInstance().initScanRule(scanRuleConfig)
    }

    private fun startScan() {
        /*修改逻辑为将数据库中设置好的蓝牙设备全部取出*/
        BleManager.getInstance().scan(object : BleScanCallback() {
            override fun onScanStarted(success: Boolean) {
            }

            override fun onLeScan(bleDevice: BleDevice) {
            }

            @SuppressLint("SetTextI18n")
            override fun onScanning(bleDevice: BleDevice) {
                /*1.先从环境中筛选设备
                * 2.将获取到的其中设备 对数据库已经进行设置存储的设备进行获取
                * 3.将设置信息配对上
                * 4.*/
                if (mScanStop) {
                    return
                }
                val deviceBrand = bleDevice.name
                if (TextUtils.isEmpty(deviceBrand)) {
                    return
                }
                val deviceInfo = DaoTool.queryDeviceInfo(deviceBrand)
                if (deviceInfo != null) {
                    //匹配到对应设备
                    val status  = BiologicalDevice().formatData(bleDevice.scanRecord,0)
                    biologicsWsAdapter?.changeItemStatus(deviceInfo,status)
                }
            }

            override fun onScanFinished(scanResultList: List<BleDevice>) {
                if (!mScanStop) {
                    startScan()
                }
            }
        })
    }

    private fun startWork() {
        if (!checkGPSIsOpen()) {
            AlertDialog.Builder(_mActivity).setTitle("提示").setMessage("当前手机扫描需要打开定位功能")
                .setNegativeButton(
                    "取消"
                ) { _, _ -> pop() }.setPositiveButton(
                    "前往设置"
                ) { _, _ ->
                    val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                    startActivityForResult(intent, 1)
                }.setCancelable(false).show()
        } else {
            setScanRule()
            startScan()
        }
    }

    private fun checkGPSIsOpen(): Boolean {
        val locationManager =
            _mActivity.getSystemService(Context.LOCATION_SERVICE) as LocationManager?
                ?: return false
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }

    override fun onPause() {
        super.onPause()
        _mActivity.unregisterReceiver(systemListener)
    }

    override fun onResume() {
        super.onResume()
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
            systemListener, intentFilter
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
}