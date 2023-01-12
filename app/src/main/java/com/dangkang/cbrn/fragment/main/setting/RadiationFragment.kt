package com.dangkang.cbrn.fragment.main.setting

import android.annotation.SuppressLint
import android.text.TextUtils
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.blankj.utilcode.util.ConvertUtils
import com.blankj.utilcode.util.ScreenUtils
import com.dangkang.cbrn.R
import com.dangkang.cbrn.adapter.RadiationAdapter
import com.dangkang.cbrn.dao.DaoTool
import com.dangkang.cbrn.databinding.FragmentSettingRadiationBinding
import com.dangkang.cbrn.db.TaintInfo
import com.dangkang.cbrn.dialog.DataSelectWindow
import com.dangkang.core.fragment.BaseFragment
import com.dangkang.core.utils.L
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class RadiationFragment : BaseFragment<ViewBinding>() {
    private var adapter:RadiationAdapter? = null
    override fun setBindingView(): ViewBinding {
        binding = FragmentSettingRadiationBinding.inflate(layoutInflater)
        return initView(binding as FragmentSettingRadiationBinding)
    }
    private fun initView(binding: FragmentSettingRadiationBinding): ViewBinding {
        val pageSnapHelper = PagerSnapHelper()
        binding.recyclerView.apply {
            val gridLayoutManager = GridLayoutManager(_mActivity, 2)
            layoutManager = gridLayoutManager
            queryRadiationData(binding)
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    hideSoftInput()
                }
            })
            pageSnapHelper.attachToRecyclerView(this)
        }
        binding.add.setOnClickListener {
            queryRadiationData(binding)
        }
        binding.measurement.setOnClickListener{
            DataSelectWindow(_mActivity,
                resources.getStringArray(R.array.radiation_measurement).toMutableList()
            , { value ->
                    if(!TextUtils.isEmpty(value)){
                        binding.measurement.text = value
                    }
                },binding.measurement.text.toString(),ConvertUtils.dp2px(200f)).showPopupWindow(binding.measurement)
        }
        return binding
    }
    @SuppressLint("CheckResult")
    private fun queryRadiationData(binding: FragmentSettingRadiationBinding){
        Observable.create<MutableList<TaintInfo>>() {
            val list = DaoTool.queryRadiationTaintInfo()
            it.onNext(list)
        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe({
            if (adapter == null){
                adapter = RadiationAdapter(it,_mActivity)
                binding.recyclerView.adapter = adapter
            }else{
                val taintInfo = TaintInfo()
                taintInfo.type = 1
                taintInfo.taint_dis = "200"
                taintInfo.taint_loc = "未知"
                taintInfo.taint_max = "300"
                taintInfo.taint_num = 1144
                taintInfo.taint_sim = resources.getStringArray(R.array.radiation_sim)[0]
                taintInfo.taint_unit = resources.getStringArray(R.array.radiation_unit)[0]
                taintInfo.create_time = System.currentTimeMillis() / 1000
                adapter!!.addItem(taintInfo)
                binding.recyclerView.scrollToPosition(0)
            }
        }, { L.e(it.message) })
    }

    fun getRadiationInfo():List<TaintInfo>{
        return adapter?.data() as List<TaintInfo>
    }

}