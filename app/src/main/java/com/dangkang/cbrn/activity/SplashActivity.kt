package com.dangkang.cbrn.activity

import androidx.viewbinding.ViewBinding
import com.dangkang.cbrn.fragment.splash.WelComeFragment
import com.dangkang.core.activity.BaseActivity
import com.dangkang.core.fragment.BaseFragment

class SplashActivity : BaseActivity(){
    override fun setRootFragment(): BaseFragment<ViewBinding> {

        return WelComeFragment.newInstance()
    }

    override fun setStatusBar() {
    }
}