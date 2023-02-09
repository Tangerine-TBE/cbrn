package com.dangkang.cbrn.fragment.main.workSpace

import androidx.viewbinding.ViewBinding
import com.dangkang.cbrn.databinding.FragmentSocketBingBinding
import com.dangkang.core.fragment.BaseFragment

class ModelSelectFragment :BaseFragment<ViewBinding>() {
    override fun onBackPressedSupport(): Boolean {
        findFragment(WorkSpaceFragment::class.java).onBackPressedSupport()
        return true
    }
    override fun setBindingView(): ViewBinding {
        binding  = FragmentSocketBingBinding.inflate(layoutInflater)
        return initView(binding as FragmentSocketBingBinding)
    }
    private fun initView(viewBinding: FragmentSocketBingBinding): FragmentSocketBingBinding {
        return viewBinding
    }
}