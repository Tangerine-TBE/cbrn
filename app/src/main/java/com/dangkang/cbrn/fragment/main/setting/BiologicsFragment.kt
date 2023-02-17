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
import com.dangkang.cbrn.adapter.setting.BiologicsDeviceAdapter
import com.dangkang.cbrn.adapter.setting.BiologicsTypeAdapter
import com.dangkang.cbrn.dao.DaoTool
import com.dangkang.cbrn.databinding.FragmentSettingsBiologicsBinding
import com.dangkang.cbrn.db.DeviceInfo
import com.dangkang.cbrn.db.TypeInfo
import com.dangkang.cbrn.dialog.EditTextDialog
import com.dangkang.cbrn.utils.ToastUtil
import com.dangkang.cbrn.weight.BiologicsDecoration
import com.dangkang.core.fragment.BaseFragment
import com.dangkang.core.utils.L
import com.google.android.flexbox.FlexboxLayoutManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*
import java.util.regex.Pattern
import kotlin.collections.ArrayList

class BiologicsFragment : BaseFragment<ViewBinding>(), BiologicsTypeAdapter.OnItemClickListener {
    private var mScanStop = false
    private var editTextDialog: EditTextDialog? = null

    /*全程生命周期需要:*/
    /*1.动态监听蓝牙启动或关闭广播以达到业务流畅*/
    /*2.动态监听位置定位启动或关闭广播以达到业务流畅*/
    /*初始化生命周期需要:*/
    /*1.检测权限
    * 2.检测蓝牙是否开启
    * 3.检测位置服务是否开启
    * 4.打开蓝牙扫描（视图可见开启蓝牙扫描 视图不可见关闭蓝牙扫描）*/
    private var biologicsAdapter: BiologicsAdapter? = null
    private var biologicsTypeAdapter: BiologicsTypeAdapter? = null
    private var biologicsDeviceAdapter: BiologicsDeviceAdapter? = null
    override fun setBindingView(): ViewBinding {
        binding = FragmentSettingsBiologicsBinding.inflate(layoutInflater)
        return initView(binding as FragmentSettingsBiologicsBinding)
    }

    override fun onBackPressedSupport(): Boolean {
        val fragment = findFragment(SettingFragment::class.java)
        return if (fragment != null) {
            fragment.onBackPressedSupport()
            true
        } else {
            false
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
                if (mScanStop) {
                    return
                }
                val deviceBrand = bleDevice.name
                if (TextUtils.isEmpty(deviceBrand)) {
                    return
                }
                L.e(bleDevice.name)
                val pattern = Pattern.compile("^S+[0-9]+[0-9]+[0-9]+[0-9]")
                val matcher = pattern.matcher(deviceBrand)
                if (!matcher.matches()) {
                    return
                }

                /**增加蓝牙扫描规则*/
                for (item in biologicsDeviceAdapter?.data()!!) {
                    if (deviceBrand.equals(item.name)) {
                        return
                    }
                }
                biologicsDeviceAdapter?.addItem(bleDevice)
            }

            override fun onScanFinished(scanResultList: List<BleDevice>) {
                if (!mScanStop) {
                    startScan()
                }
            }
        })
    }


    private fun setScanRule() {
        val scanRuleConfig =
            BleScanRuleConfig.Builder().setAutoConnect(false) // 连接时的autoConnect参数，可选，默认false
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
            systemListener, intentFilter
        )
        /*初始化生命周期需要:*/
        /**
         * 2.检测蓝牙是否开启
         * 3.检测位置服务是否开启
         * 4.打开蓝牙扫描（视图可见开启蓝牙扫描 视图不可见关闭蓝牙扫描）*/
        val bluetoothAdapter: BluetoothAdapter? = BluetoothAdapter.getDefaultAdapter()
        if (bluetoothAdapter != null) {
            if (!bluetoothAdapter.isEnabled) {
                Toast.makeText(_mActivity, "请先打开蓝牙", Toast.LENGTH_LONG).show()
                return
            }
            startWork()
        }

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
        val locationManager =
            _mActivity.getSystemService(Context.LOCATION_SERVICE) as LocationManager?
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
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }


    @SuppressLint("CheckResult")
    private fun initView(binding: FragmentSettingsBiologicsBinding): ViewBinding {
        initData(binding)
        io.reactivex.Observable.create<ArrayList<DeviceInfo>> {

            it.onNext(DaoTool.queryAllDeviceInfo() as ArrayList<DeviceInfo>)
        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe({
            biologicsDeviceAdapter = BiologicsDeviceAdapter()
            biologicsAdapter = BiologicsAdapter(_mActivity, biologicsDeviceAdapter, it)
            binding.recyclerView.adapter = biologicsAdapter
            binding.recyclerView.layoutManager = GridLayoutManager(_mActivity, 2)
            binding.deviceRecyclerView.adapter = biologicsDeviceAdapter
            binding.deviceRecyclerView.layoutManager = FlexboxLayoutManager(_mActivity)
            binding.deviceRecyclerView.addItemDecoration(BiologicsDecoration())
            binding.add.setOnClickListener {
                val deviceInfo = DeviceInfo()
                deviceInfo.brand = "未知设备"
                deviceInfo.type = resources.getStringArray(R.array.biglogics_type)[1]
                deviceInfo.result = resources.getStringArray(R.array.biglogics_result)[0]
                deviceInfo.location = ""
                biologicsAdapter!!.addItem(deviceInfo)
                binding.recyclerView.scrollToPosition(0)
            }
            val pagerSnapHelper = PagerSnapHelper()
            val deviceSnapHelper = PagerSnapHelper();
            deviceSnapHelper.attachToRecyclerView(binding.deviceRecyclerView)
            pagerSnapHelper.attachToRecyclerView(binding.recyclerView)
        }, { L.e(it.message) })

        return binding
    }

    fun getRadiationInfo(): List<DeviceInfo> {
        return biologicsAdapter?.data() as List<DeviceInfo>
    }

    @SuppressLint("CheckResult")
    private fun initData(viewBinding: FragmentSettingsBiologicsBinding) {
        /**查询数据
         * 1.BiologicsTypeAdapter
         * 2.BiologicsDeviceAdapter
         * 3.BiologicsResultAdapter
         *
         * 1.BiologicsType's default data query and the dynamic data query ,the dynamic data should save in the database
         * 2.BiologicsResult 's default data query
         * 3.BiologicsDevice should load from ble scanner it's the same way with recyclerView scanner
         * */
        /* load in the thread to save the application running */
        /*RxJava2 join*/
        io.reactivex.Observable.create<List<TypeInfo>> {
            val strings = mutableListOf(resources.getStringArray(R.array.biglogics_type))[0]
            val listDefault: ArrayList<TypeInfo> = kotlin.collections.ArrayList()
            for (i in strings.indices) {
                val typeInfo: TypeInfo = if (i == 0) {
                    TypeInfo(0, strings[i])
                } else {
                    TypeInfo(1, strings[i])
                }
                listDefault.add(typeInfo)
            }
            val listDynamic = DaoTool.queryAllTypeInfo(1)
            if (listDynamic != null && listDynamic.isNotEmpty()) {
                //这里的添加要进行额外的处理
                //0 header 不进行额外处理
                //从1开始进行添加知道dynamic数组添加完毕 kotlin ArrayList == java ArrayList
                //最后添加default数组
                listDefault.addAll(1, listDynamic)
//                listDefault = listDefault.plus(listDynamic) as ArrayList<TypeInfo>
            }
            it.onNext(listDefault.toList())
        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe({
            biologicsTypeAdapter = BiologicsTypeAdapter(it, context, this)
            viewBinding.typeRecyclerView.adapter = biologicsTypeAdapter
            viewBinding.typeRecyclerView.layoutManager = FlexboxLayoutManager(_mActivity).apply {
                val pagerSnapHelper = PagerSnapHelper()
                pagerSnapHelper.attachToRecyclerView(viewBinding.typeRecyclerView)
            }
            viewBinding.typeRecyclerView.addItemDecoration(BiologicsDecoration())
        }, {
            ToastUtil.showCenterToast(it.message)
        })

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

    override fun onItemClicked(value: Int) {
        if (value == 0) {
            if (editTextDialog == null) {
                editTextDialog = EditTextDialog(
                    _mActivity, R.style.DialogStyle, object : EditTextDialog.OnItemSelected {
                        override fun onSaveItem(value: String) {
                            if (biologicsTypeAdapter != null) {
                                biologicsTypeAdapter?.addItem(value)
                            }
                        }
                    }, "制剂编辑"
                )
            }
            editTextDialog!!.show()
        }
    }
}