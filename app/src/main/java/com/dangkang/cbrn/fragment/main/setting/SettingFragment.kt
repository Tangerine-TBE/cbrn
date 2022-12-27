package com.dangkang.cbrn.fragment.main.setting

import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.dangkang.cbrn.databinding.FragmentSettingsBinding
import com.dangkang.core.fragment.BaseFragment


class SettingFragment : BaseFragment<ViewBinding>() {
    private val titleList = arrayListOf("核辐射","化学制剂","生物制剂")

    companion object{
        fun newInstance(): BaseFragment<ViewBinding> {
            return SettingFragment()
        }
    }

    override fun setBindingView() : ViewBinding  {
        binding  = FragmentSettingsBinding.inflate(layoutInflater)
        return initView(binding as FragmentSettingsBinding)
    }
    private fun initView(viewBinding: FragmentSettingsBinding): ViewBinding {
        viewBinding.viewPager.apply {
            offscreenPageLimit = ViewPager2.OFFSCREEN_PAGE_LIMIT_DEFAULT
            adapter = object : FragmentStateAdapter(
                parentFragmentManager,
                lifecycle
            ) {
                override fun createFragment(position: Int): Fragment {
                    //FragmentStateAdapter内部自己会管理已实例化的fragment对象。
                    // 所以不需要考虑复用的问题
                    return when(position){
                        0 -> RadiationFragment.newInstance()
                        1 -> ChemicalFragment.newInstance()
                        2 -> BiologicsFragment.newInstance()
                        else -> RadiationFragment.newInstance()
                    }
                }

                override fun getItemCount(): Int {
                    return titleList.size
                }
            }
        }
        return viewBinding
    }

}