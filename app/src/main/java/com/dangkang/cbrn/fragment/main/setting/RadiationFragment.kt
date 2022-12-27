package com.dangkang.cbrn.fragment.main.setting

import androidx.viewbinding.ViewBinding
import com.dangkang.cbrn.databinding.FragmentSettingRadiationBinding
import com.dangkang.cbrn.databinding.FragmentSettingsBinding
import com.dangkang.core.fragment.BaseFragment

class RadiationFragment :BaseFragment<ViewBinding>() {
    companion object{
        fun newInstance(): BaseFragment<ViewBinding> {
            return RadiationFragment()
        }
    }

    override fun setBindingView() : ViewBinding  {
        binding  = FragmentSettingRadiationBinding.inflate(layoutInflater)
        return initView(binding as FragmentSettingRadiationBinding)
    }

    private fun initView(binding: FragmentSettingRadiationBinding): ViewBinding {



        return binding
    }
}