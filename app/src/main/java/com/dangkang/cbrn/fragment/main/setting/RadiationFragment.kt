package com.dangkang.cbrn.fragment.main.setting

import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.viewbinding.ViewBinding
import com.dangkang.cbrn.adapter.RadiationAdapter
import com.dangkang.cbrn.dao.DaoTool
import com.dangkang.cbrn.databinding.FragmentSettingRadiationBinding
import com.dangkang.cbrn.databinding.FragmentSettingsBinding
import com.dangkang.cbrn.db.TaintInfo
import com.dangkang.core.fragment.BaseFragment

class RadiationFragment : BaseFragment<ViewBinding>() {
    private val list: MutableList<TaintInfo> = DaoTool.queryTaintInfo()

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
        binding.recyclerView.apply {
            val gridLayoutManager = GridLayoutManager(_mActivity, 3)
            layoutManager = gridLayoutManager
            adapter = RadiationAdapter(list)
        }
        binding.add.setOnClickListener{
            val taintInfo :TaintInfo



        }
        return binding
    }
}