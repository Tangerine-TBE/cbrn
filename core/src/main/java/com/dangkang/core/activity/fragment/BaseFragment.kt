package com.dangkang.core.activity.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import me.yokeyword.fragmentation.SupportFragment

abstract class BaseFragment : SupportFragment() {
    abstract  fun setBindingView(): ViewBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return setBindingView().root
    }
}