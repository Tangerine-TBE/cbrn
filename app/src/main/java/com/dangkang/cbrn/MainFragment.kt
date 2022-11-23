package com.dangkang.cbrn

import androidx.viewbinding.ViewBinding
import com.dangkang.cbrn.databinding.ActivityMainBinding
import com.dangkang.core.activity.fragment.BaseFragment

class MainFragment : BaseFragment() {
    private lateinit var binding: ActivityMainBinding
    companion object{
        fun newInstance():BaseFragment{
            return MainFragment()
        }
    }
    override fun setBindingView() : ViewBinding  {
        binding  = ActivityMainBinding.inflate(layoutInflater)
        return binding;
    }
}