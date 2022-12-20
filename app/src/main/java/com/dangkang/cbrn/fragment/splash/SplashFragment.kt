package com.dangkang.cbrn.fragment.splash

import androidx.viewbinding.ViewBinding
import com.dangkang.cbrn.databinding.FragmentSplashBinding
import com.dangkang.core.fragment.BaseFragment

class SplashFragment : BaseFragment<ViewBinding>()  {
    companion object{
        fun newInstance(): BaseFragment<ViewBinding> {
            return SplashFragment()
        }
    }
    override fun setBindingView(): ViewBinding {
        binding  = FragmentSplashBinding.inflate(layoutInflater)
        return binding as FragmentSplashBinding
    }
}