package com.dangkang.cbrn.fragment.splash

import android.content.Intent
import android.os.Bundle
import androidx.viewbinding.ViewBinding
import com.dangkang.cbrn.activity.MainActivity
import com.dangkang.cbrn.databinding.FragmentSplashBinding
import com.dangkang.core.fragment.BaseFragment
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator

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

    override fun onResume() {
        super.onResume()
    }
    private fun toMain(){
        val intent = Intent(_mActivity,MainActivity::class.java)
        startActivity(intent)
        _mActivity.finish()
    }
}