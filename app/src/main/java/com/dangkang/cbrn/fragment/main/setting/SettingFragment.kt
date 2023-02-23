package com.dangkang.cbrn.fragment.main.setting

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.TextView
import androidx.viewbinding.ViewBinding
import androidx.viewpager2.widget.ViewPager2
import com.dangkang.cbrn.R
import com.dangkang.cbrn.databinding.FragmentSettingsBinding
import com.dangkang.cbrn.dialog.SettingBackDialog
import com.dangkang.core.fragment.BaseFragment
import com.google.android.material.tabs.TabLayoutMediator


class SettingFragment : BaseFragment<ViewBinding>() {
    private val titleList = arrayListOf("核辐射", "化学制剂", "生物制剂")
    private var settingBackDialog:SettingBackDialog ?= null
    private  var fragmentSettingsBinding: FragmentSettingsBinding?= null
    private var viewPagerFragmentStateAdapter:ViewPagerFragmentStateAdapter? = null

    override fun onDestroy() {
        viewPagerFragmentStateAdapter?.destroyAllItem()
        super.onDestroy()
    }

    override fun setBindingView(): ViewBinding {
        binding = FragmentSettingsBinding.inflate(layoutInflater)
        fragmentSettingsBinding = binding as FragmentSettingsBinding
        return fragmentSettingsBinding as FragmentSettingsBinding
    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        initView(fragmentSettingsBinding as FragmentSettingsBinding)
    }

    override fun onBackPressedSupport(): Boolean {
        if (settingBackDialog == null){
            settingBackDialog = SettingBackDialog(_mActivity,R.style.DialogStyle,object :SettingBackDialog.OnItemSelected{
                override fun onSaveItem() {
                    viewPagerFragmentStateAdapter!!.save(this@SettingFragment)
                }

                override fun onUnSaveItem() {
                    pop()
                }
            })
        }
        settingBackDialog!!.show()
        return true
    }
    private fun initView(viewBinding: FragmentSettingsBinding): ViewBinding {
        viewBinding.viewPager.apply {
            viewPagerFragmentStateAdapter = ViewPagerFragmentStateAdapter(_mActivity.supportFragmentManager, lifecycle)
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    val tabCount  =viewBinding.tabLayout.tabCount
                  for (i in 0 until tabCount){
                      val tab = viewBinding.tabLayout.getTabAt(i)
                      val textView :TextView= tab?.customView as TextView
                      if (tab.position == position){
                          textView.setTextColor(Color.WHITE)
                          textView.setBackgroundColor(resources.getColor(R.color.blue))
                      }else{
                          textView.setTextColor(resources.getColor(R.color.un_select_color))
                          textView.setBackgroundColor(resources.getColor(R.color.status))
                      }
                  }
                }
            })
            adapter = viewPagerFragmentStateAdapter
            isUserInputEnabled = false
        }
        viewBinding.viewPager.offscreenPageLimit = 3
        TabLayoutMediator(viewBinding.tabLayout,
            viewBinding.viewPager,
            true
        ) { tab, position ->

            val textView = TextView(_mActivity)
            textView.text= titleList[position]
            textView.width = 300
            textView.height = 100
            textView.gravity = Gravity.CENTER
            textView.setTextColor(resources.getColor(R.color.white))
            tab.customView = textView
        }.attach()
        viewBinding.titleBar.back.setOnClickListener{
            if (settingBackDialog == null){
                settingBackDialog = SettingBackDialog(_mActivity,R.style.DialogStyle,object :SettingBackDialog.OnItemSelected{
                    override fun onSaveItem() {
                        viewPagerFragmentStateAdapter?.save(this@SettingFragment)
                    }

                    override fun onUnSaveItem() {
                        pop()
                    }
                })
            }
            settingBackDialog!!.show()

        }
        viewBinding.titleBar.cancel.setOnClickListener{
            _mActivity.finish()
        }
        viewBinding.titleBar.title.text = "设置"
        return viewBinding
    }


}