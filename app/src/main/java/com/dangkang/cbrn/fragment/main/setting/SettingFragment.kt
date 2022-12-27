package com.dangkang.cbrn.fragment.main.setting

import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewbinding.ViewBinding
import androidx.viewpager2.widget.ViewPager2
import com.dangkang.cbrn.R
import com.dangkang.cbrn.databinding.FragmentSettingsBinding
import com.dangkang.core.fragment.BaseFragment
import com.google.android.material.tabs.TabLayout


class SettingFragment : BaseFragment<ViewBinding>() {
    private val titleList = arrayListOf("核辐射","化学制剂","生物制剂")
    private val fragments: MutableList<Fragment> = ArrayList()

    companion object{
        fun newInstance(): BaseFragment<ViewBinding> {
            return SettingFragment()
        }
    }

    override fun setBindingView() : ViewBinding  {
        binding  = FragmentSettingsBinding.inflate(layoutInflater)
        fragments.add(ChemicalFragment.newInstance())
        fragments.add(RadiationFragment.newInstance())
        fragments.add(BiologicsFragment.newInstance())
        return initView(binding as FragmentSettingsBinding)
    }
    private fun initView(viewBinding: FragmentSettingsBinding): ViewBinding {
        viewBinding.viewPager.apply {
            offscreenPageLimit = ViewPager2.OFFSCREEN_PAGE_LIMIT_DEFAULT
            adapter = FragmentAdapter(_mActivity.supportFragmentManager,FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT,fragments)
        }
        viewBinding.tabLayout.setupWithViewPager(viewBinding.viewPager)
        initTabLayout(viewBinding)
        return viewBinding
    }
    private fun initTabLayout(viewBinding: FragmentSettingsBinding) {
        viewBinding.tabLayout .setSelectedTabIndicator(0)
        val tabCount: Int =  viewBinding.tabLayout.tabCount
        for (i in 0 until tabCount) {
            val tabChild: TabLayout.Tab = viewBinding.tabLayout.getTabAt(i)!!
            val view: View =
                LayoutInflater.from(_mActivity).inflate(R.layout.item_main_tab,  viewBinding.tabLayout, false)
            val textView = view.findViewById<TextView>(R.id.text)
            textView.text = titleList[i]
            if (i == 0) {
                textView.setTextColor(resources.getColor(R.color.blue))
            } else {
                textView.setTextColor(resources.getColor(R.color.navigation))
            }
            tabChild.customView = view
        }
    }
    private fun resumeTabStatus() {
        val tabChild: TabLayout.Tab = mTabLayout.getTabAt(mCurrentPosition)!!
        val view = tabChild.customView!!
        val imageView = view.findViewById<ImageView>(R.id.image)
        val textView = view.findViewById<TextView>(R.id.text)
        textView.setTextColor(resources.getColor(R.color.unl_text_main_color))
        imageView.setImageResource(tableUnSelectedIcon.get(mCurrentPosition))
    }

    private fun resumeTabStatus(position: Int) {
        val tabChild: TabLayout.Tab = mTabLayout.getTabAt(position)!!
        val view = tabChild.customView!!
        val imageView = view.findViewById<ImageView>(R.id.image)
        val textView = view.findViewById<TextView>(R.id.text)
        textView.setTextColor(resources.getColor(R.color.sel_text_main_color))
        imageView.setImageResource(tableSelectedIcon.get(position))
    }

}