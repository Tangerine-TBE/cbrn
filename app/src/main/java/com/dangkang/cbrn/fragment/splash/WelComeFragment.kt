package com.dangkang.cbrn.fragment.splash

import android.content.Intent
import android.os.Handler
import android.os.Looper
import androidx.viewbinding.ViewBinding
import com.dangkang.cbrn.activity.MainActivity
import com.dangkang.cbrn.activity.SplashActivity
import com.dangkang.cbrn.databinding.FragmentWelcomeBinding
import com.dangkang.core.fragment.BaseFragment
import me.yokeyword.fragmentation.ISupportFragment.LaunchMode
import me.yokeyword.fragmentation.ISupportFragment.SINGLETASK
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.DefaultVerticalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator

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

    override fun onResume() {
        super.onResume()
        Handler(Looper.getMainLooper()).postDelayed({
          startActivity(Intent(_mActivity,MainActivity::class.java))
            _mActivity.finish()
        },2000)
    }
}