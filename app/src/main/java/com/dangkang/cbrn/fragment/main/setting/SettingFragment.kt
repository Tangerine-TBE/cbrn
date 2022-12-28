package com.dangkang.cbrn.fragment.main.setting

import android.R.id.tabs
import android.graphics.Color
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.dangkang.cbrn.R
import com.dangkang.cbrn.databinding.FragmentSettingsBinding
import com.dangkang.core.fragment.BaseFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class SettingFragment : BaseFragment<ViewBinding>() {
    private val titleList = arrayListOf("核辐射", "化学制剂", "生物制剂")
    private val fragments: MutableList<Fragment> = ArrayList()

    companion object {
        fun newInstance(): BaseFragment<ViewBinding> {
            return SettingFragment()
        }
    }

    override fun setBindingView(): ViewBinding {
        binding = FragmentSettingsBinding.inflate(layoutInflater)
        fragments.add(ChemicalFragment.newInstance())
        fragments.add(RadiationFragment.newInstance())
        fragments.add(BiologicsFragment.newInstance())
        return initView(binding as FragmentSettingsBinding)
    }

    private fun initView(viewBinding: FragmentSettingsBinding): ViewBinding {
        viewBinding.viewPager.apply {
            adapter = ViewPagerFragmentStateAdapter(_mActivity.supportFragmentManager, lifecycle)
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
        }
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
        return viewBinding
    }


}