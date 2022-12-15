package com.dangkang.cbrn

import androidx.viewbinding.ViewBinding
import com.dangkang.cbrn.databinding.ActivityMainBinding
import com.dangkang.core.fragment.BaseFragment

class MainFragment : BaseFragment<ViewBinding>() {
    companion object{
        fun newInstance(): BaseFragment<ViewBinding> {
            return MainFragment()
        }
    }
    override fun setBindingView() : ActivityMainBinding  {
        binding  = ActivityMainBinding.inflate(layoutInflater)
        return binding as ActivityMainBinding
    }
}