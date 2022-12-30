package com.dangkang.cbrn.fragment.main.workSpace

import androidx.viewbinding.ViewBinding
import com.dangkang.cbrn.databinding.FragmentBiologicsWsBinding
import com.dangkang.cbrn.databinding.FragmentChemicalWsBinding
import com.dangkang.core.fragment.BaseFragment

class ChemicalWSFragment:BaseFragment<ViewBinding>() {
    companion object{
        fun newInstance(): BaseFragment<ViewBinding> {
            return ChemicalWSFragment()
        }
    }
    override fun setBindingView(): ViewBinding {
        binding  = FragmentChemicalWsBinding.inflate(layoutInflater)
        return initView(binding as FragmentChemicalWsBinding)
    }
    private fun initView(viewBinding: FragmentChemicalWsBinding): FragmentChemicalWsBinding {
        return viewBinding
    }
}