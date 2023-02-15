package com.dangkang.cbrn.fragment.main.workSpace

import android.app.AlertDialog
import android.app.Service
import android.bluetooth.BluetoothAdapter
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.Toast
import androidx.viewbinding.ViewBinding
import com.clj.fastble.BleManager
import com.clj.fastble.scan.BleScanRuleConfig
import com.dangkang.cbrn.R
import com.dangkang.cbrn.databinding.FragmentWorkSpaceBinding
import com.dangkang.cbrn.dialog.SettingBackDialog
import com.dangkang.cbrn.dialog.WorkBackDialog
import com.dangkang.core.fragment.BaseFragment
import com.dangkang.core.utils.L

class WorkSpaceFragment : BaseFragment<ViewBinding>(), View.OnClickListener {
    private var viewPagerWsStateAdapter: ViewPagerWsStateAdapter? = null
    private var fragmentWorkSpaceBinding: FragmentWorkSpaceBinding? = null
    private var workBackDialog: WorkBackDialog? = null
    private var systemListener: SystemListener = SystemListener()
    private var mScanStop = false

    companion object {
        fun newInstance(): BaseFragment<ViewBinding> {
            return WorkSpaceFragment()
        }
    }

    override fun setBindingView(): ViewBinding {
        fragmentWorkSpaceBinding = FragmentWorkSpaceBinding.inflate(layoutInflater)
        binding = fragmentWorkSpaceBinding
        return initView(binding as FragmentWorkSpaceBinding)
    }

    private fun initView(fragmentMainBinding: FragmentWorkSpaceBinding): ViewBinding {

        fragmentMainBinding.titleBar.title.text = "实时操作"
        fragmentMainBinding.titleBar.connectInfo.text = "未连接模块..."
        fragmentMainBinding.titleBar.back.setOnClickListener {
            if (workBackDialog == null) {
                workBackDialog = WorkBackDialog(_mActivity,
                    R.style.DialogStyle,
                    object : WorkBackDialog.OnItemSelected {
                        override fun onSaveItem() {
                            pop()
                        }

                    })
            }
            workBackDialog!!.show()
        }
        fragmentMainBinding.titleBar.cancel.setOnClickListener {
            _mActivity.finish()
        }
        fragmentMainBinding.titleBar.connectSir.setOnClickListener(this)
        fragmentMainBinding.biologics.setOnClickListener(this)
        fragmentMainBinding.chemical.setOnClickListener(this)
        fragmentMainBinding.radiation.setOnClickListener(this)
        fragmentMainBinding.titleBar.connectSir.visibility = View.VISIBLE
        fragmentMainBinding.fragmentReplace.apply {
            viewPagerWsStateAdapter =
                ViewPagerWsStateAdapter(_mActivity.supportFragmentManager, lifecycle)
            adapter = viewPagerWsStateAdapter
            isUserInputEnabled = false
            offscreenPageLimit = 4
        }
        return fragmentMainBinding
    }

    override fun onDestroy() {
        viewPagerWsStateAdapter?.destroyAllItem()
        super.onDestroy()
    }

    override fun onBackPressedSupport(): Boolean {
        if (workBackDialog == null) {
            workBackDialog = WorkBackDialog(_mActivity,
                R.style.DialogStyle,
                object : WorkBackDialog.OnItemSelected {
                    override fun onSaveItem() {
                        pop()
                    }

                })
        }
        workBackDialog!!.show()
        return true
    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        onClick(fragmentWorkSpaceBinding?.radiation)
    }

    private var selectedItem: View? = null
    override fun onClick(v: View?) {
        if (selectedItem == v) {
            return
        }
        if (v == fragmentWorkSpaceBinding?.titleBar?.connectSir) {
            fragmentWorkSpaceBinding!!.fragmentReplace.currentItem = 3
        } else {
            fragmentWorkSpaceBinding?.menuList?.indexOfChild(v)
                ?.let { fragmentWorkSpaceBinding!!.fragmentReplace.currentItem = it }
        }
        v?.isSelected = true
        selectedItem?.isSelected = false
        selectedItem = v
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

//        BleManager.getInstance().scan(object : BleScanCallback() {
//            override fun onScanStarted(success: Boolean) {
//            }
//
//            override fun onLeScan(bleDevice: BleDevice) {
//            }
//
//            @SuppressLint("SetTextI18n")
//            override fun onScanning(bleDevice: BleDevice) {
//                /*1.先从环境中筛选设备
//                * 2.将获取到的其中设备 对数据库已经进行设置存储的设备进行获取
//                * 3.将设置信息配对上
//                * 4.*/
//                if (mScanStop) {
//                    return
//                }
//                val deviceBrand = bleDevice.name
//                if (TextUtils.isEmpty(deviceBrand)) {
//                    return
//                }
//                val deviceInfo = DaoTool.queryDeviceInfo(deviceBrand)
//                if (deviceInfo != null) {
////                    deviceInfo.status = BiologicalDevice().formatData(bleDevice.scanRecord) as Int
//                    biologicsWsAdapter?.addItem(deviceInfo)
//                }
//            }
//
//            override fun onScanFinished(scanResultList: List<BleDevice>) {
//                if (!mScanStop) {
//                    startScan()
//                }
//            }
//        })
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