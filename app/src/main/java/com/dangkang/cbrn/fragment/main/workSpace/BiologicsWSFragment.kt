package com.dangkang.cbrn.fragment.main.workSpace

import androidx.viewbinding.ViewBinding
import com.dangkang.cbrn.databinding.FragmentBiologicsWsBinding
import com.dangkang.cbrn.databinding.FragmentWorkSpaceBinding
import com.dangkang.core.fragment.BaseFragment

class BiologicsWSFragment :BaseFragment<ViewBinding>() {
    companion object{
        fun newInstance(): BaseFragment<ViewBinding> {
            return BiologicsWSFragment()
        }
    }

    override fun setBindingView(): ViewBinding {
        binding  = FragmentBiologicsWsBinding.inflate(layoutInflater)
        return initView(binding as FragmentBiologicsWsBinding)
    }
    private fun initView(viewBinding: FragmentBiologicsWsBinding):FragmentBiologicsWsBinding{
        return viewBinding
    }
}