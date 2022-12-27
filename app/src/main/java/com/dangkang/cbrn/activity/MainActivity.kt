package com.dangkang.cbrn.activity

import androidx.viewbinding.ViewBinding
import com.dangkang.cbrn.R
import com.dangkang.cbrn.fragment.main.TeacherFragment
import com.dangkang.core.activity.BaseActivity
import com.dangkang.core.fragment.BaseFragment
import com.gyf.barlibrary.ImmersionBar

class MainActivity: BaseActivity(){
    override fun setRootFragment(): BaseFragment<ViewBinding> {
        return TeacherFragment.newInstance()
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