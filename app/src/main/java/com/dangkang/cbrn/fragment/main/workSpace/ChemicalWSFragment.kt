package com.dangkang.cbrn.fragment.main.workSpace

import android.os.Bundle
import androidx.viewbinding.ViewBinding
import com.dangkang.cbrn.databinding.FragmentBiologicsWsBinding
import com.dangkang.cbrn.databinding.FragmentChemicalWsBinding
import com.dangkang.core.fragment.BaseFragment

class ChemicalWSFragment:BaseFragment<ViewBinding>() {
    private var fragmentChemicalWsBinding:FragmentChemicalWsBinding? = null
    override fun setBindingView(): ViewBinding {
        binding  = FragmentChemicalWsBinding.inflate(layoutInflater)
        fragmentChemicalWsBinding = binding as FragmentChemicalWsBinding
        return fragmentChemicalWsBinding as FragmentChemicalWsBinding
    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        fragmentChemicalWsBinding?.let { initView(it) }
        super.onLazyInitView(savedInstanceState)
    }
    private fun initView(viewBinding: FragmentChemicalWsBinding): FragmentChemicalWsBinding {
        return viewBinding
    }
}