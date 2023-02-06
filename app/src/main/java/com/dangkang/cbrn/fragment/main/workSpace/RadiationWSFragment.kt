package com.dangkang.cbrn.fragment.main.workSpace

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.widget.TableLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.viewbinding.ViewBinding
import com.dangkang.cbrn.R
import com.dangkang.cbrn.databinding.FragmentChemicalWsBinding
import com.dangkang.cbrn.databinding.FragmentRadiationWsBinding
import com.dangkang.cbrn.databinding.FragmentWorkSpaceBinding
import com.dangkang.core.fragment.BaseFragment
import com.dangkang.core.utils.SPUtil
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener

class RadiationWSFragment : BaseFragment<ViewBinding>() {
    private val titleList = arrayListOf("简易", "详细")
    override fun setBindingView(): ViewBinding {
        binding = FragmentRadiationWsBinding.inflate(layoutInflater)
        return initView(binding as FragmentRadiationWsBinding)
    }

    private fun initView(viewBinding: FragmentRadiationWsBinding): FragmentRadiationWsBinding {
        /*建议写进懒加载中*/
        /*这个初始化流程不可以改变 1.*/
        viewBinding.tabLayout.apply {
            addOnTabSelectedListener(object : OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    val textView = tab?.customView as TextView
                    textView.setTextColor(Color.WHITE)
                    textView.setBackgroundColor(ContextCompat.getColor(_mActivity, R.color.blue))
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {
                    val textView = tab?.customView as TextView
                    textView.setTextColor(
                        ContextCompat.getColor(
                            _mActivity, R.color.un_select_color
                        )
                    )
                    textView.setBackgroundColor(ContextCompat.getColor(_mActivity, R.color.status))
                }

                override fun onTabReselected(tab: TabLayout.Tab?) {

                }
            })
        }
        /*这个初始化流程不可以改变 2.*/
        for (i in 0 until titleList.size) {
            val tab = viewBinding.tabLayout.newTab()
            val textView = TextView(_mActivity)
            textView.width = 300
            textView.height = 100
            textView.gravity = Gravity.CENTER
            textView.text = titleList[i]
            textView.setTextColor(ContextCompat.getColor(_mActivity, R.color.white))
            tab.customView = textView
            var isSelected = false
            if (i == 0) {
                isSelected = true
            }
            viewBinding.tabLayout.addTab(tab, isSelected)
        }
        /*这个初始化流程不可以改变 3.*/
        return viewBinding
    }

}