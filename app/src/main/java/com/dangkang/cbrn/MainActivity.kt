package com.dangkang.cbrn

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputBinding
import com.dangkang.cbrn.databinding.ActivityMainBinding
import com.dangkang.core.activity.BaseActivity
import com.dangkang.core.activity.fragment.BaseFragment

class MainActivity : BaseActivity() {
    override fun setRootFragment(): BaseFragment {
        return MainFragment.newInstance()
    }

    override fun setStatusBar() {
        //暂时不关联主题
    }

}