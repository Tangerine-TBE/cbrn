package com.dangkang.cbrn.fragment.main.setting

import android.annotation.SuppressLint
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.dangkang.Constant
import com.dangkang.cbrn.R
import com.dangkang.cbrn.adapter.setting.ChemicalAdapter
import com.dangkang.cbrn.dao.DaoTool
import com.dangkang.cbrn.databinding.FragmentSettingChemicalBinding
import com.dangkang.cbrn.db.TaintInfo
import com.dangkang.cbrn.dialog.FragmentWindow
import com.dangkang.core.fragment.BaseFragment
import com.dangkang.core.utils.L
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ChemicalFragment :BaseFragment<ViewBinding>() {
    private var adapter: ChemicalAdapter? = null
    override fun setBindingView() : ViewBinding {
        binding  = FragmentSettingChemicalBinding.inflate(layoutInflater)
        return initView(binding as FragmentSettingChemicalBinding)
    }
    override fun onBackPressedSupport(): Boolean {
        val fragment  = findFragment(SettingFragment::class.java)
        return if (fragment != null){
            fragment.onBackPressedSupport()
            true
        }else{
            false
        }
    }
    private fun initView(binding: FragmentSettingChemicalBinding): ViewBinding {
        val pageSnapHelper = PagerSnapHelper()
        binding.recyclerView.apply {
            val gridLayoutManager = GridLayoutManager(_mActivity, 2)
            layoutManager = gridLayoutManager
            queryChemicalData(binding)
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    hideSoftInput()
                }
            })
            pageSnapHelper.attachToRecyclerView(this)
        }
        binding.add.setOnClickListener {
            queryChemicalData(binding)
        }
        binding.layout1.setOnClickListener{
            FragmentWindow(_mActivity).showPopupWindow(binding.root)
        }
        return binding
    }
    @SuppressLint("CheckResult")
    private fun queryChemicalData(binding: FragmentSettingChemicalBinding){
        Observable.create<MutableList<TaintInfo>>() {
            val list = DaoTool.queryChemicalTaintInfo()
            it.onNext(list)
        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe({
            if (adapter == null){
                adapter = ChemicalAdapter(
                    it,
                    _mActivity
                )
                binding.recyclerView.adapter = adapter
            }else{
                if (adapter!!.data().isEmpty()){
                    val taintInfo = TaintInfo()
                    taintInfo.type = 2
                    taintInfo.taint_dis = resources.getStringArray(R.array.chemical_type)[0]
                    taintInfo.taint_loc = ""
                    taintInfo.taint_max = "300"
                    taintInfo.taint_num = adapter?.itemCount!! + 1
                    taintInfo.taint_sim = resources.getStringArray(R.array.radiation_sim)[0]
                    taintInfo.taint_sim_dis = resources.getStringArray(R.array.radiation_sim_dis)[0]
                    taintInfo.taint_unit = DaoTool.queryUnitFromChemicalInfo(taintInfo.taint_dis)
                    taintInfo.create_time = System.currentTimeMillis() / 1000
                    adapter!!.addItem(taintInfo)
                    binding.recyclerView.scrollToPosition(0)
                }

            }
        }, { L.e(it.message) })
    }

    fun getRadiationInfo():List<TaintInfo>{
        return adapter?.data() as List<TaintInfo>
    }
}