package com.dangkang.cbrn.fragment.splash

import androidx.viewbinding.ViewBinding
import com.dangkang.cbrn.databinding.FragmentWelcomeBinding
import com.dangkang.core.fragment.BaseFragment

class WelComeFragment : BaseFragment<ViewBinding>() {
    companion object{
        fun newInstance(): BaseFragment<ViewBinding> {
            return WelComeFragment()
        }
    }
    override fun setBindingView(): ViewBinding {
        binding  = FragmentWelcomeBinding.inflate(layoutInflater)
        return binding as FragmentWelcomeBinding
    }
}