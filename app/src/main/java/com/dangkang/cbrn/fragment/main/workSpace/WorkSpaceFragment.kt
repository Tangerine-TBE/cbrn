package com.dangkang.cbrn.fragment.main.workSpace

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewbinding.ViewBinding
import com.dangkang.cbrn.databinding.FragmentMainBinding
import com.dangkang.cbrn.databinding.FragmentWorkSpaceBinding
import com.dangkang.cbrn.fragment.main.TeacherFragment
import com.dangkang.core.fragment.BaseFragment

class WorkSpaceFragment :BaseFragment<ViewBinding>(){




    companion object{
        fun newInstance(): BaseFragment<ViewBinding> {
            return WorkSpaceFragment()
        }
    }

    override fun setBindingView() : ViewBinding  {
        binding  = FragmentWorkSpaceBinding.inflate(layoutInflater)
        return initView(binding as FragmentWorkSpaceBinding)
    }

    private fun initView(fragmentMainBinding: FragmentWorkSpaceBinding): ViewBinding {

            fragmentMainBinding.titleBar.title.text="实时操作"
            fragmentMainBinding.titleBar.back.setOnClickListener{
                pop()
            }
            fragmentMainBinding.titleBar.connectInfo.text = "未连接模块"
            fragmentMainBinding.titleBar.cancel.setOnClickListener{
                _mActivity.finish()
            }
            fragmentMainBinding.biologics.setOnClickListener{

            }
            fragmentMainBinding.chemical.setOnClickListener{

            }
            fragmentMainBinding.radiation.setOnClickListener{

            }

            return  fragmentMainBinding
    }
}