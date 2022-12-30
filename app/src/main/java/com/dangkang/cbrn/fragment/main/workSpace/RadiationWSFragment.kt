package com.dangkang.cbrn.fragment.main.workSpace

import androidx.viewbinding.ViewBinding
import com.dangkang.cbrn.databinding.FragmentChemicalWsBinding
import com.dangkang.cbrn.databinding.FragmentRadiationWsBinding
import com.dangkang.cbrn.databinding.FragmentWorkSpaceBinding
import com.dangkang.core.fragment.BaseFragment

class RadiationWSFragment:BaseFragment<ViewBinding>() {
    companion object{
        fun newInstance(): BaseFragment<ViewBinding> {
            return RadiationWSFragment()
        }
    }
    override fun setBindingView(): ViewBinding {
        binding  = FragmentRadiationWsBinding.inflate(layoutInflater)
        return initView(binding as FragmentRadiationWsBinding)
    }
    private fun initView(viewBinding: FragmentRadiationWsBinding): FragmentRadiationWsBinding {
        return viewBinding
    }

}