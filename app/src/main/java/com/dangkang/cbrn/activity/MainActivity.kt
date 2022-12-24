package com.dangkang.cbrn.activity

import androidx.viewbinding.ViewBinding
import com.dangkang.cbrn.R
import com.dangkang.cbrn.fragment.main.MainFragment
import com.dangkang.core.activity.BaseActivity
import com.dangkang.core.fragment.BaseFragment
import com.gyf.barlibrary.BarHide
import com.gyf.barlibrary.ImmersionBar

class MainActivity: BaseActivity(){
    override fun setRootFragment(): BaseFragment<ViewBinding> {
        return MainFragment.newInstance()
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