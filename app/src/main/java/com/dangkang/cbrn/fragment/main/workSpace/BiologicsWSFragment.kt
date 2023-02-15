package com.dangkang.cbrn.fragment.main.workSpace

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
import com.dangkang.cbrn.adapter.workspace.BiologicsWSAdapter
import com.dangkang.cbrn.adapter.workspace.BiologicsWSAdapter.OnIconClickListener
import com.dangkang.cbrn.dao.DaoTool
import com.dangkang.cbrn.databinding.FragmentBiologicsWsBinding
import com.dangkang.cbrn.device.ble.BiologicalDevice
import com.dangkang.core.fragment.BaseFragment

class BiologicsWSFragment : BaseFragment<ViewBinding>(),OnIconClickListener {

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

}