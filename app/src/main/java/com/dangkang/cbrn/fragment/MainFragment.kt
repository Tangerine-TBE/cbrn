package com.dangkang.cbrn.fragment

import androidx.viewbinding.ViewBinding
import com.dangkang.cbrn.databinding.FragmentMainBinding
import com.dangkang.core.fragment.BaseFragment

class MainFragment : BaseFragment<ViewBinding>() {
    companion object{
        fun newInstance(): BaseFragment<ViewBinding> {
            return MainFragment()
        }
    }
    override fun setBindingView() : FragmentMainBinding  {
        binding  = FragmentMainBinding.inflate(layoutInflater)
        return binding as FragmentMainBinding
    }
}