package com.dangkang.cbrn.fragment.main

import androidx.viewbinding.ViewBinding
import com.dangkang.cbrn.databinding.FragmentMainBinding
import com.dangkang.core.fragment.BaseFragment

class TeacherFragment : BaseFragment<ViewBinding>() {
    companion object{
        fun newInstance(): BaseFragment<ViewBinding> {
            return TeacherFragment()
        }
    }
    override fun setBindingView() : ViewBinding  {
        binding  = FragmentMainBinding.inflate(layoutInflater)
        return initView(binding as FragmentMainBinding)
    }
    private fun initView(viewBinding: FragmentMainBinding): ViewBinding {
        viewBinding.titleBar.title.text = "导师端软件"
        viewBinding.titleBar.back.setOnClickListener{
            pop()
        }
        viewBinding.titleBar.cancel.setOnClickListener{
            _mActivity.finish()
        }
        viewBinding.setting.setOnClickListener{
            toSetting()
        }
        viewBinding.workSpace.setOnClickListener{
            toWorkSpace()
        }

        return viewBinding
    }
    private fun  toSetting(){

    }
    private fun  toWorkSpace(){

    }
}