package com.dangkang.cbrn.fragment.main.setting

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.viewbinding.ViewBinding
import com.dangkang.cbrn.adapter.RadiationAdapter
import com.dangkang.cbrn.dao.DaoTool
import com.dangkang.cbrn.databinding.FragmentSettingRadiationBinding
import com.dangkang.cbrn.db.TaintInfo
import com.dangkang.core.fragment.BaseFragment
import com.dangkang.core.utils.L
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class RadiationFragment : BaseFragment<ViewBinding>() {
    companion object {
        fun newInstance(): BaseFragment<ViewBinding> {
            return RadiationFragment()
        }
    }

    override fun setBindingView(): ViewBinding {
        binding = FragmentSettingRadiationBinding.inflate(layoutInflater)
        return initView(binding as FragmentSettingRadiationBinding)
    }

    @SuppressLint("CheckResult")
    private fun initView(binding: FragmentSettingRadiationBinding): ViewBinding {
        binding.recyclerView.apply {
            val gridLayoutManager = GridLayoutManager(_mActivity, 2)
            layoutManager = gridLayoutManager
            Observable.create<MutableList<TaintInfo>>() {
                val list = DaoTool.queryTaintInfo()
                it.onNext(list)
            }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe({
                adapter = RadiationAdapter(it)
            }, { L.e(it.message) })

        }
        binding.add.setOnClickListener {
            (binding.recyclerView.adapter as RadiationAdapter).addItem(DaoTool.addTaintInfoTest())
        }
        return binding
    }

}