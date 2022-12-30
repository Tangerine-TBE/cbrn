package com.dangkang.cbrn.fragment.main.workSpace

import androidx.viewbinding.ViewBinding
import com.dangkang.cbrn.databinding.FragmentRadiationWsBinding
import com.dangkang.cbrn.databinding.FragmentSocketBingBinding
import com.dangkang.core.fragment.BaseFragment

class SocketBindingFragment :BaseFragment<ViewBinding>() {
    companion object{
        fun newInstance(): BaseFragment<ViewBinding> {
            return SocketBindingFragment()
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