package com.dangkang.cbrn.fragment.main.workSpace

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.viewbinding.ViewBinding
import androidx.viewpager2.widget.ViewPager2
import com.dangkang.cbrn.R
import com.dangkang.cbrn.databinding.FragmentWorkSpaceBinding
import com.dangkang.cbrn.fragment.main.setting.ViewPagerFragmentStateAdapter
import com.dangkang.core.fragment.BaseFragment

class WorkSpaceFragment : BaseFragment<ViewBinding>() ,View.OnClickListener {
    private var viewPagerWsStateAdapter:ViewPagerWsStateAdapter? = null
    private var fragmentWorkSpaceBinding :FragmentWorkSpaceBinding? = null
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
            pop()
        }
        fragmentMainBinding.titleBar.cancel.setOnClickListener {
            _mActivity.finish()
        }
        fragmentMainBinding.titleBar.connectSir.setOnClickListener(this)
        fragmentMainBinding.biologics.setOnClickListener (this)
        fragmentMainBinding.chemical.setOnClickListener(this)
        fragmentMainBinding.radiation.setOnClickListener(this)
        fragmentMainBinding.titleBar.connectSir.visibility = View.VISIBLE
        fragmentMainBinding.fragmentReplace.apply {
            viewPagerWsStateAdapter = ViewPagerWsStateAdapter(_mActivity.supportFragmentManager, lifecycle)
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
    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        onClick(fragmentWorkSpaceBinding?.radiation)
    }
    private var selectedItem:View? = null
    override fun onClick(v: View?) {
        if (selectedItem == v){
            return
        }
        if (v == fragmentWorkSpaceBinding?.titleBar?.connectSir){
             fragmentWorkSpaceBinding!!.fragmentReplace.currentItem = 3
        }else{
            fragmentWorkSpaceBinding?.menuList?.indexOfChild(v)
                ?.let { fragmentWorkSpaceBinding!!.fragmentReplace.currentItem = it }
        }
        v?.isSelected = true
        selectedItem?.isSelected = false
        selectedItem = v
    }
}