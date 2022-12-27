package com.dangkang.cbrn.fragment.main.setting

import androidx.viewbinding.ViewBinding
import com.dangkang.cbrn.databinding.FragmentSettingChemicalBinding
import com.dangkang.cbrn.databinding.FragmentSettingsBiologicsBinding
import com.dangkang.core.fragment.BaseFragment

class BiologicsFragment :BaseFragment<ViewBinding>() {
    companion object{
        fun newInstance(): BaseFragment<ViewBinding> {
            return BiologicsFragment()
        }
    }

    override fun setBindingView() : ViewBinding {
        binding  = FragmentSettingsBiologicsBinding.inflate(layoutInflater)
        return initView(binding as FragmentSettingsBiologicsBinding)
    }

    private fun initView(binding: FragmentSettingsBiologicsBinding): ViewBinding {

        return binding
    }
}