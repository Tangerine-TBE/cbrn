package com.dangkang.cbrn

import androidx.viewbinding.ViewBinding
import com.dangkang.cbrn.databinding.ActivityMainBinding
import com.dangkang.core.activity.BaseActivity
import com.dangkang.core.fragment.BaseFragment

class MainActivity : BaseActivity() {
    override fun setRootFragment(): BaseFragment<ViewBinding> {
        return MainFragment.newInstance()
    }

    override fun setStatusBar() {
        //暂时不关联主题
    }

}