package com.dangkang.cbrn.fragment.main.workSpace

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewbinding.ViewBinding
import com.dangkang.cbrn.adapter.workspace.ModelSelectAdapter
import com.dangkang.cbrn.bean.ModelDeviceBean
import com.dangkang.cbrn.databinding.FragmentSocketBingBinding
import com.dangkang.cbrn.event.ItemEvent
import com.dangkang.cbrn.weight.SimplePaddingDecoration
import com.dangkang.core.fragment.BaseFragment
import com.dangkang.core.utils.L
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

open class ModelSelectFragment : BaseFragment<ViewBinding>(),ModelSelectAdapter.OnModelSelected {
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
        EventBus.getDefault().register(this)
        return initView(binding as FragmentSocketBingBinding)
    }

    override fun onDestroyView() {
        EventBus.getDefault().unregister(this)
        super.onDestroyView()
    }

    private fun initView(viewBinding: FragmentSocketBingBinding): FragmentSocketBingBinding {
        viewBinding.recyclerView.apply {
            /*获取设备的抽象*/
            /*抽象有设备对应的mac 设备名称 设备型号 设备当前电量*/
            /*一个设备代表两个item 化学和核辐射*/
            val modelDeviceInfo = ModelDeviceBean(3,0,"蓝牙","无")
            val list = listOf(modelDeviceInfo)
            adapter = ModelSelectAdapter(list,_mActivity,this@ModelSelectFragment)
            layoutManager = LinearLayoutManager(_mActivity)
            addItemDecoration(SimplePaddingDecoration(_mActivity))
        }
        return viewBinding
    }

    override fun onSelected(modelDeviceBean: ModelDeviceBean?) {
        if (modelDeviceBean?.model == 3){
            val workSpaceFragment = findFragment(WorkSpaceFragment::class.java)
            EventBus.getDefault().post(EventBus())
        }
    }
    @Subscribe(threadMode = ThreadMode.BACKGROUND)
   open fun changeEvent(eventBus: EventBus){
        L.e("changed")
    }


}