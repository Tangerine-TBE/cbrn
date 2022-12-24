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
            .statusBarColor(android.R.color.transparent)
            .statusBarDarkFont(true)
            .hideBar(BarHide.FLAG_HIDE_BAR)
            .navigationBarColor(R.color.white)
            .init()
    }
}