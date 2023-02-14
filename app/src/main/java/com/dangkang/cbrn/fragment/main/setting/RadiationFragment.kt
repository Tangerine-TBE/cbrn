package com.dangkang.cbrn.fragment.main.setting

import android.annotation.SuppressLint
import android.text.TextUtils
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.blankj.utilcode.util.ConvertUtils
import com.dangkang.Constant
import com.dangkang.cbrn.R
import com.dangkang.cbrn.adapter.setting.RadiationAdapter
import com.dangkang.cbrn.adapter.setting.RadiationTypeAdapter
import com.dangkang.cbrn.dao.DaoTool
import com.dangkang.cbrn.databinding.FragmentSettingRadiationBinding
import com.dangkang.cbrn.db.TaintInfo
import com.dangkang.cbrn.db.TypeInfo
import com.dangkang.cbrn.dialog.DataSelectWindow
import com.dangkang.cbrn.dialog.EditTextDialog
import com.dangkang.cbrn.fragment.main.workSpace.WorkSpaceFragment
import com.dangkang.cbrn.utils.SPUtil
import com.dangkang.cbrn.utils.ToastUtil
import com.dangkang.cbrn.weight.BiologicsDecoration
import com.dangkang.core.fragment.BaseFragment
import com.dangkang.core.utils.L
import com.google.android.flexbox.FlexboxLayoutManager
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.lang.reflect.Type

class RadiationFragment : BaseFragment<ViewBinding>(), RadiationTypeAdapter.OnItemClickListener {
    private var adapter: RadiationAdapter? = null
    private var typeAdapter: RadiationTypeAdapter? = null
    private var editTextDialog: EditTextDialog? = null
    override fun setBindingView(): ViewBinding {
        binding = FragmentSettingRadiationBinding.inflate(layoutInflater)
        return initView(binding as FragmentSettingRadiationBinding)
    }

    override fun onBackPressedSupport(): Boolean {
        val fragment = findFragment(SettingFragment::class.java)
        return if (fragment != null) {
            fragment.onBackPressedSupport()
            true
        } else {
            false
        }
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
        binding.typeRecyclerView.apply {
            val flexboxLayoutManager = FlexboxLayoutManager(_mActivity)
            layoutManager = flexboxLayoutManager
            pageSnapHelper.attachToRecyclerView(this)
            addItemDecoration(BiologicsDecoration())
            queryRadiationType(binding)
        }
        binding.add.setOnClickListener {
            queryRadiationData(binding)
        }
        binding.measurement.setOnClickListener {
            DataSelectWindow(
                _mActivity,
                resources.getStringArray(R.array.radiation_measurement).toMutableList(),
                { value ->
                    if (!TextUtils.isEmpty(value)) {
                        binding.measurement.text = value
                        SPUtil.getInstance().putString(Constant.MEASUREMENT, value)
                        adapter?.setUniType(value)
                    }
                },
                binding.measurement.text.toString(),
                ConvertUtils.dp2px(200f),
                false
            ).showPopupWindow(binding.measurement)
        }
        val textValue = SPUtil.getInstance().getString(Constant.MEASUREMENT)
        if (!TextUtils.isEmpty(textValue)) {
            binding.measurement.text = textValue
        }
        return binding
    }

    @SuppressLint("CheckResult")
    private fun queryRadiationType(binding: FragmentSettingRadiationBinding) {
        Observable.create<ArrayList<TypeInfo>> {
            val strings = mutableListOf(resources.getStringArray(R.array.radiation_type))[0]
            val listDefault: ArrayList<TypeInfo> = ArrayList()
            for (i in strings.indices) {
                val typeInfo: TypeInfo = if (i == 0) {
                    TypeInfo(0, strings[i])
                } else {
                    TypeInfo(1, strings[i])
                }
                listDefault.add(typeInfo)
            }
            val list = DaoTool.queryAllTypeInfo(2)
            if (list != null && list.isNotEmpty()) {
                listDefault.addAll(1, list)
            }

            it.onNext(listDefault)
        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe({
            if (typeAdapter == null) {
                typeAdapter = RadiationTypeAdapter(it, _mActivity, this)
                binding.typeRecyclerView.adapter = typeAdapter
            }
        }, {
            ToastUtil.showToast(it.message)
        })
    }

    @SuppressLint("CheckResult")
    private fun queryRadiationData(binding: FragmentSettingRadiationBinding) {
        Observable.create<MutableList<TaintInfo>>() {
            val list = DaoTool.queryRadiationTaintInfo()
            it.onNext(list)
        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe({
            val value = binding.measurement.text.toString()

            if (adapter == null) {
                adapter = RadiationAdapter(
                    it, _mActivity,value
                )
                binding.recyclerView.adapter = adapter
            } else {
                val taintInfo = TaintInfo()
                taintInfo.type = 1
                taintInfo.taint_dis = resources.getStringArray(R.array.radiation_type)[1]
                taintInfo.taint_loc = ""
                taintInfo.taint_max = "300"
                taintInfo.taint_num = adapter?.itemCount!! + 1
                taintInfo.taint_sim = resources.getStringArray(R.array.radiation_sim)[0]
                taintInfo.taint_sim_dis = resources.getStringArray(R.array.radiation_sim_dis)[0]
                if (value == Constant.MEASUREMENT_UNIT_1){
                    taintInfo.taint_unit = resources.getStringArray(R.array.radiation_unit_1)[0]
                }else{
                    taintInfo.taint_unit = resources.getStringArray(R.array.radiation_unit_2)[0]
                }
                taintInfo.create_time = System.currentTimeMillis() / 1000
                adapter!!.addItem(taintInfo)
                binding.recyclerView.scrollToPosition(0)
            }
        }, {
            L.e(it.message)
        })
    }

    fun getRadiationInfo(): List<TaintInfo> {
        return adapter?.data() as List<TaintInfo>
    }

    override fun onItemClicked(value: Int) {
        if (value == 0) {
            if (editTextDialog == null) {
                editTextDialog = EditTextDialog(
                    _mActivity,
                    R.style.DialogStyle,
                    object : EditTextDialog.OnItemSelected {
                        override fun onSaveItem(value: String) {
                            if (typeAdapter != null) {
                                typeAdapter?.addItem(value)
                            }
                        }
                    }, "核素编辑"
                )
            }
            editTextDialog!!.show()
        }
    }

}