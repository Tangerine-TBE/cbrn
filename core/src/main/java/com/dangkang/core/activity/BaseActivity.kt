package com.dangkang.core.activity

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.os.IBinder
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.widget.ContentFrameLayout
import androidx.viewbinding.ViewBinding
import com.dangkang.core.R
import com.dangkang.core.fragment.BaseFragment
import me.yokeyword.fragmentation.SupportActivity
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
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

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if(ev!!.action == MotionEvent.ACTION_DOWN){
            val view =  currentFocus
            if (isShouldHideKeyboard(view,ev)){
                hideKeyboard(view!!.windowToken)
            }
        }
        return super.dispatchTouchEvent(ev)
    }
    private  fun hideKeyboard(token: IBinder?) {
        if (token != null) {
            val im: InputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS)
        }
    }
    private  fun isShouldHideKeyboard(v: View?, event: MotionEvent): Boolean {
        if (v != null && v is EditText) {
            val l = intArrayOf(0, 0)
            v.getLocationInWindow(l)
            val left = l[0]
            val top = l[1]
            val bottom: Int = top + v.getHeight()
            val right: Int = left + v.getWidth()
            return !(event.x > left && event.x < right && event.y > top && event.y < bottom)
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditText上，和用户用轨迹球选择其他的焦点
        return false
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