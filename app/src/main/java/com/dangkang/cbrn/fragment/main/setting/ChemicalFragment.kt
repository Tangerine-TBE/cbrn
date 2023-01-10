package com.dangkang.cbrn.fragment.main.setting

import android.view.View
import androidx.viewbinding.ViewBinding
import com.dangkang.cbrn.databinding.FragmentSettingChemicalBinding
import com.dangkang.cbrn.databinding.FragmentSettingRadiationBinding
import com.dangkang.core.fragment.BaseFragment

class ChemicalFragment :BaseFragment<ViewBinding>() {
    companion object{
        fun newInstance(): BaseFragment<ViewBinding> {
            return ChemicalFragment()
        }
    }

    override fun setBindingView() : ViewBinding {
        binding  = FragmentSettingChemicalBinding.inflate(layoutInflater)
        return initView(binding as FragmentSettingChemicalBinding)
    }

    private fun initView(binding: FragmentSettingChemicalBinding): ViewBinding {

        return binding
    }
}