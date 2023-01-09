package com.dangkang.cbrn.fragment.main.setting

import android.annotation.SuppressLint
import android.text.TextUtils
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.viewbinding.ViewBinding
import com.dangkang.cbrn.R
import com.dangkang.cbrn.adapter.RadiationAdapter
import com.dangkang.cbrn.dao.DaoTool
import com.dangkang.cbrn.databinding.FragmentSettingRadiationBinding
import com.dangkang.cbrn.db.TaintInfo
import com.dangkang.cbrn.dialog.DataSelectWindow
import com.dangkang.cbrn.dialog.DataSelectWindow.OnValueSelected
import com.dangkang.core.fragment.BaseFragment
import com.dangkang.core.utils.L
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class RadiationFragment : BaseFragment<ViewBinding>() {
    private var adapter:RadiationAdapter? = null
    companion object {
        fun newInstance(): BaseFragment<ViewBinding> {
            return RadiationFragment()
        }
    }

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
            pageSnapHelper.attachToRecyclerView(this)
        }
        binding.add.setOnClickListener {
            DaoTool.addTaintInfoTest()
            queryRadiationData(binding)
        }
        binding.measurement.setOnClickListener{
            DataSelectWindow(_mActivity,
                resources.getStringArray(R.array.radiation_measurement).toMutableList()
            , { value ->
                    if(!TextUtils.isEmpty(value)){
                        binding.measurement.text = value
                    }
                },binding.measurement.text.toString()).showPopupWindow(binding.measurement)
        }
        return binding
    }
    @SuppressLint("CheckResult")
    private fun queryRadiationData(binding: FragmentSettingRadiationBinding){
        Observable.create<MutableList<TaintInfo>>() {
            val list = DaoTool.queryTaintInfo()
            it.onNext(list)
        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe({
            if (adapter == null){
                adapter = RadiationAdapter(it)
                binding.recyclerView.adapter = adapter
            }else{
                adapter!!.addItems(it)
                binding.recyclerView.scrollToPosition(0)
            }
        }, { L.e(it.message) })
    }

}