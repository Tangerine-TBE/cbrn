package com.dangkang.cbrn.fragment.splash

import androidx.viewbinding.ViewBinding
import com.dangkang.cbrn.databinding.FragmentSplashBinding
import com.dangkang.cbrn.fragment.main.TeacherFragment
import com.dangkang.core.fragment.BaseFragment

class SplashFragment : BaseFragment<ViewBinding>() {
    companion object {
        fun newInstance(): BaseFragment<ViewBinding> {
            return SplashFragment()
        }
    }

    override fun setBindingView(): ViewBinding {
        binding = FragmentSplashBinding.inflate(layoutInflater)
        return initView(binding as FragmentSplashBinding)
    }

    private fun initView(viewBinding: FragmentSplashBinding): ViewBinding {
        viewBinding.mainEntry.setOnClickListener {
            toTeacherSelect()
        }
        viewBinding.titleBar.back.setOnClickListener {
            _mActivity.finish()
        }
        viewBinding.titleBar.cancel.setOnClickListener {
            _mActivity.finish()
        }
        return viewBinding
    }

    private fun toTeacherSelect() {
        start(TeacherFragment.newInstance())

    }
}