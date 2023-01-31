package com.dangkang.cbrn.fragment.main.workSpace

import android.os.Bundle
import androidx.viewbinding.ViewBinding
import com.dangkang.cbrn.databinding.FragmentChemicalWsBinding
import com.dangkang.cbrn.databinding.FragmentRadiationWsBinding
import com.dangkang.cbrn.databinding.FragmentWorkSpaceBinding
import com.dangkang.core.fragment.BaseFragment

class RadiationWSFragment:BaseFragment<ViewBinding>() {

    override fun setBindingView(): ViewBinding {
        binding  = FragmentRadiationWsBinding.inflate(layoutInflater)
        return initView(binding as FragmentRadiationWsBinding)
    }
    private fun initView(viewBinding: FragmentRadiationWsBinding): FragmentRadiationWsBinding {
        return viewBinding
    }

}