package com.dangkang.core.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewbinding.ViewBinding
import androidx.viewpager.widget.PagerAdapter
import com.dangkang.core.utils.L
import me.yokeyword.fragmentation.SupportFragment

abstract class BaseFragment <VB:ViewBinding>: SupportFragment() {
    protected  open  var binding:VB?=null
    protected open val mBinding get()= binding!!
    abstract fun setBindingView(): VB?
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        L.e("${javaClass.name} onCreateView")
        binding = setBindingView()
        return binding?.root
    }

    override fun onResume() {
        super.onResume()
        L.e("${javaClass.name} onResume")
    }

    override fun onPause() {
        super.onPause()
        L.e("${javaClass.name} onPause")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        L.e("${javaClass.name} onDestroyView")
    }
    open fun initialize() {
    }
    override fun onDestroy() {
        super.onDestroy()
        binding=null
    }
}