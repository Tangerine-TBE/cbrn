package com.dangkang.cbrn.fragment.main.workSpace

import androidx.viewbinding.ViewBinding
import com.dangkang.cbrn.bean.ModelDeviceBean
import com.dangkang.cbrn.databinding.FragmentSocketBingBinding
import com.dangkang.cbrn.service.AbstractDevice
import com.dangkang.cbrn.service.SocketServer
import com.dangkang.core.fragment.BaseFragment

class ModelSelectFragment : BaseFragment<ViewBinding>() {
    override fun onBackPressedSupport(): Boolean {
        val fragment = findFragment(WorkSpaceFragment::class.java)
        return if (fragment != null) {
            fragment.onBackPressedSupport()
            true
        } else {
            false
        }
    }

    override fun setBindingView(): ViewBinding {
        binding = FragmentSocketBingBinding.inflate(layoutInflater)
        return initView(binding as FragmentSocketBingBinding)
    }

    private fun initView(viewBinding: FragmentSocketBingBinding): FragmentSocketBingBinding {
        viewBinding.recyclerView.apply {
            /*获取设备的抽象*/
            /*抽象有设备对应的mac 设备名称 设备型号 设备当前电量*/
            /*一个设备代表两个item 化学和核辐射*/
            val list: ArrayList<AbstractDevice> = SocketServer.instance.devicesCache()
            //获取设备个
            val modelDevices = ArrayList<ModelDeviceBean>()
            if (list.isNotEmpty()) {
                for (i in 0 until list.size) {
                    val ip = list[i].ip
                    val power = list[i].power
                    val modelDevice1 = ModelDeviceBean(1, power, "WIFI", ip)
                    val modelDevice2 = ModelDeviceBean(2, power, "WIFI", ip)
                    modelDevices.add(modelDevice1)
                    modelDevices.add(modelDevice2)
                }
            }
            modelDevices.add(ModelDeviceBean(3, 0, "蓝牙", ""))


        }
        return viewBinding
    }
}