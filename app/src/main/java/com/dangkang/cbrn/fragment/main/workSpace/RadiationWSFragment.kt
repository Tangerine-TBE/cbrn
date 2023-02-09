package com.dangkang.cbrn.fragment.main.workSpace

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.viewbinding.ViewBinding
import com.dangkang.cbrn.R
import com.dangkang.cbrn.adapter.workspace.RadiationWSAdapter
import com.dangkang.cbrn.dao.DaoTool
import com.dangkang.cbrn.databinding.FragmentRadiationWsBinding
import com.dangkang.cbrn.db.TaintInfo
import com.dangkang.cbrn.utils.ToastUtil
import com.dangkang.core.fragment.BaseFragment
import com.dangkang.core.utils.L
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers

class RadiationWSFragment : BaseFragment<ViewBinding>() {
    private val titleList = arrayListOf("简易", "详细")
    private var radiationWSAdapter: RadiationWSAdapter? = null
    private var mCurrentType = 0;
    private var fragmentRadiationWsBinding: FragmentRadiationWsBinding? = null
    override fun setBindingView(): ViewBinding {
        binding = FragmentRadiationWsBinding.inflate(layoutInflater)
        fragmentRadiationWsBinding = binding as FragmentRadiationWsBinding
        return fragmentRadiationWsBinding as FragmentRadiationWsBinding
    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        fragmentRadiationWsBinding?.let { initView(it) }
        super.onLazyInitView(savedInstanceState)
    }
    @SuppressLint("CheckResult")
    private fun initData(viewBinding: FragmentRadiationWsBinding){
        /*这个初始化流程不可以改变 0.*/

        Observable.create<List<TaintInfo>> {
            val lists = DaoTool.queryRadiationTaintInfo()
            if (lists != null && lists.isNotEmpty()) {
                it.onNext(lists)
            } else {
                it.onError(Throwable("没有配置信息!"))
            }
        }.subscribeOn(io.reactivex.schedulers.Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe({
                /*懒加载 + 异步策略 初始化数据加载 防止卡顿*/
                radiationWSAdapter = RadiationWSAdapter(it,mCurrentType)
                viewBinding.recyclerView.apply {
                    adapter = radiationWSAdapter
                    layoutManager = GridLayoutManager(_mActivity, 2)

                }
            }, {
                ToastUtil.showCenterToast(it.message)
            })
    }

    override fun onResume() {
        super.onResume()
        fragmentRadiationWsBinding?.let { initData(it) }
    }

    @SuppressLint("CheckResult")
    private fun initView(viewBinding: FragmentRadiationWsBinding): FragmentRadiationWsBinding {
        /*建议写进懒加载中*/
        viewBinding.recyclerView.apply {
            val pageSnapHelper = PagerSnapHelper()
            pageSnapHelper.attachToRecyclerView(this)
        }
        /*这个初始化流程不可以改变 1.*/
        viewBinding.tabLayout.apply {
            addOnTabSelectedListener(object : OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    val textView = tab?.customView as TextView
                    textView.setTextColor(Color.WHITE)
                    textView.setBackgroundColor(ContextCompat.getColor(_mActivity, R.color.blue))
                    val value = textView.text
                    for (i in 1 until titleList.size + 1) {
                        if (titleList[i - 1] == value) {
                            mCurrentType = i
                            radiationWSAdapter?.setViewType(i)
                        }
                    }
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {
                    val textView = tab?.customView as TextView
                    textView.setTextColor(
                        ContextCompat.getColor(
                            _mActivity, R.color.un_select_color
                        )
                    )
                    textView.setBackgroundColor(ContextCompat.getColor(_mActivity, R.color.status))
                }

                override fun onTabReselected(tab: TabLayout.Tab?) {

                }
            })
        }
        /*这个初始化流程不可以改变 2.*/
        for (i in 0 until titleList.size) {
            val tab = viewBinding.tabLayout.newTab()
            val textView = TextView(_mActivity)
            textView.width = 300
            textView.height = 100
            textView.gravity = Gravity.CENTER
            textView.text = titleList[i]
            textView.setTextColor(ContextCompat.getColor(_mActivity, R.color.white))
            tab.customView = textView
            var isSelected = false
            if (i == 0) {
                isSelected = true
            }
            viewBinding.tabLayout.addTab(tab, isSelected)
        }
        /*这个初始化流程不可以改变 3.*/
        return viewBinding
    }

}