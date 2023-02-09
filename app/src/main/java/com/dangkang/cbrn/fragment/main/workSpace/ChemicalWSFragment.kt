package com.dangkang.cbrn.fragment.main.workSpace

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.viewbinding.ViewBinding
import com.dangkang.cbrn.R
import com.dangkang.cbrn.adapter.workspace.ChemicalWSAdapter
import com.dangkang.cbrn.adapter.workspace.RadiationWSAdapter
import com.dangkang.cbrn.dao.DaoTool
import com.dangkang.cbrn.databinding.FragmentBiologicsWsBinding
import com.dangkang.cbrn.databinding.FragmentChemicalWsBinding
import com.dangkang.cbrn.db.TaintInfo
import com.dangkang.cbrn.utils.ToastUtil
import com.dangkang.core.fragment.BaseFragment
import com.dangkang.core.utils.L
import com.google.android.material.tabs.TabLayout
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ChemicalWSFragment:BaseFragment<ViewBinding>() {
    private val titleList = arrayListOf<String>("简易","详细")
    private var chemicalWSAdapter:ChemicalWSAdapter? =null
    private var fragmentChemicalWsBinding:FragmentChemicalWsBinding? = null
    private var mCurrentType = 0;
    override fun setBindingView(): ViewBinding {
        binding  = FragmentChemicalWsBinding.inflate(layoutInflater)
        fragmentChemicalWsBinding = binding as FragmentChemicalWsBinding
        return fragmentChemicalWsBinding as FragmentChemicalWsBinding
    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        fragmentChemicalWsBinding?.let { initView(it) }
        super.onLazyInitView(savedInstanceState)
    }


    @SuppressLint("CheckResult")
    private fun initData(fragmentChemicalWsBinding:FragmentChemicalWsBinding){


        Observable.create<List<TaintInfo>> {
            val lists = DaoTool.queryChemicalTaintInfo()
            if (lists != null && lists.isNotEmpty()) {
                it.onNext(lists)
            } else {
                it.onError(Throwable("没有配置信息!"))
            }

        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe({
            /*懒加载 + 异步策略 初始化数据加载 防止卡顿*/
            chemicalWSAdapter = ChemicalWSAdapter(it,mCurrentType)
            fragmentChemicalWsBinding.recyclerView.apply {
                adapter = chemicalWSAdapter
                layoutManager = GridLayoutManager(_mActivity, 2)
            }
        },{
            ToastUtil.showCenterToast(it.message)
        })
    }

    override fun onResume() {
        super.onResume()
        fragmentChemicalWsBinding?.let { initData(it) }
    }
    @SuppressLint("CheckResult")
    private fun initView(viewBinding: FragmentChemicalWsBinding): FragmentChemicalWsBinding {
        /*建议写进懒加载中*/
        /*这个初始化流程不可以改变 0.*/
        viewBinding.recyclerView.apply {
            val pageSnapHelper = PagerSnapHelper()
            pageSnapHelper.attachToRecyclerView(this)
        }

        /*这个初始化流程不可以改变 1.*/
        viewBinding.tabLayout.apply {
            addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    val textView = tab?.customView as TextView
                    textView.setTextColor(Color.WHITE)
                    textView.setBackgroundColor(ContextCompat.getColor(_mActivity, R.color.blue))
                    val value = textView.text
                    for (i in 1 until titleList.size + 1) {
                        if (titleList[i - 1] == value) {
                            mCurrentType = i
                            chemicalWSAdapter?.setViewType(i)
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