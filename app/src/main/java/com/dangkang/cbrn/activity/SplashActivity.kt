package com.dangkang.cbrn.activity

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import androidx.viewbinding.ViewBinding
import com.dangkang.cbrn.R
import com.dangkang.cbrn.fragment.splash.WelComeFragment
import com.dangkang.core.activity.BaseActivity
import com.dangkang.core.fragment.BaseFragment
import com.gyf.barlibrary.BarHide
import com.gyf.barlibrary.ImmersionBar

@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseActivity(){
    override fun setRootFragment(): BaseFragment<ViewBinding> {

        return WelComeFragment.newInstance()
    }

    override fun onBackPressedSupport() {
        finish()
    }

    override fun setStatusBar() {
        ImmersionBar.with(this)
            .statusBarColor(R.color.navigation)
            .statusBarDarkFont(false)
            .navigationBarColor(R.color.navigation)
            .barColor(R.color.navigation)
            .init()
    }
}