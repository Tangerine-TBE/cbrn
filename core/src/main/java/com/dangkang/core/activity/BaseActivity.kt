package com.dangkang.core.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.PersistableBundle
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.ContentFrameLayout
import androidx.viewbinding.ViewBinding
import com.dangkang.core.R
import com.dangkang.core.fragment.BaseFragment
import me.yokeyword.fragmentation.SupportActivity
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.DefaultNoAnimator
import me.yokeyword.fragmentation.anim.DefaultVerticalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator

abstract class BaseActivity : SupportActivity() {
    abstract fun setRootFragment(): BaseFragment<ViewBinding>

    //强制更改的沉浸式
    abstract fun setStatusBar()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initContainer(savedInstanceState)

    }

    //    fun setStatusBar(){
//
//    }
    @SuppressLint("RestrictedApi")
    private fun initContainer(savedInstanceState: Bundle?) {
        val container = ContentFrameLayout(this)
        container.id = R.id.fragment_container
        container.fitsSystemWindows = true
        setContentView(container)
        setStatusBar()
        if (savedInstanceState == null) {
            loadRootFragment(R.id.fragment_container, setRootFragment(), true, true)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        System.gc()
        System.runFinalization()
    }

    override fun onCreateFragmentAnimator(): FragmentAnimator {
        return DefaultHorizontalAnimator()
    }


}