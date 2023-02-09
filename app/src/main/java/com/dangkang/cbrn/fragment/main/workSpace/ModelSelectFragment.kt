package com.dangkang.cbrn.fragment.main.workSpace

import androidx.viewbinding.ViewBinding
import com.dangkang.cbrn.databinding.FragmentSocketBingBinding
import com.dangkang.core.fragment.BaseFragment

class ModelSelectFragment :BaseFragment<ViewBinding>() {
    override fun onBackPressedSupport(): Boolean {
        val fragment  = findFragment(WorkSpaceFragment::class.java)
        return if (fragment != null){
            fragment.onBackPressedSupport()
            true
        }else{
            false
        }
    }
    override fun setBindingView(): ViewBinding {
        binding  = FragmentSocketBingBinding.inflate(layoutInflater)
        return initView(binding as FragmentSocketBingBinding)
    }
    private fun initView(viewBinding: FragmentSocketBingBinding): FragmentSocketBingBinding {
        return viewBinding
    }
}