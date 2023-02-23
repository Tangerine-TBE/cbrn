package com.dangkang.cbrn.fragment.main.setting

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewbinding.ViewBinding
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dangkang.cbrn.dao.DaoTool
import com.dangkang.cbrn.db.TaintInfo
import com.dangkang.cbrn.utils.ToastUtil
import com.dangkang.core.fragment.BaseFragment
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.regex.Pattern

class ViewPagerFragmentStateAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    private var radiationFragment: BaseFragment<ViewBinding>? = null
    private var chemicalFragment: BaseFragment<ViewBinding>? = null
    private var biologicsFragment: BaseFragment<ViewBinding>? = null
    private var mDisposable: Disposable? = null
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                radiationFragment = RadiationFragment()
                return radiationFragment as BaseFragment<ViewBinding>
            }
            1 -> {
                chemicalFragment = ChemicalFragment()
                return chemicalFragment as BaseFragment<ViewBinding>
            }
            2 -> {
                biologicsFragment = BiologicsFragment()
                return biologicsFragment as BaseFragment<ViewBinding>
            }
            else -> {
                radiationFragment = RadiationFragment()
                return radiationFragment as BaseFragment<ViewBinding>
            }
        }
    }

    fun destroyAllItem() {
        radiationFragment?.onDestroy()
        chemicalFragment?.onDestroy()
        biologicsFragment?.onDestroy()
        if (mDisposable != null) {
            if (!mDisposable!!.isDisposed) {
                mDisposable!!.dispose()
            }
        }
    }


    fun save(settingFragment: SettingFragment) {
        /*检测*/
        val pattern1 = Pattern.compile("^BL+[0-9]+[0-9]+[0-9]+[0-9]+[0-9]")
        val pattern2 = Pattern.compile("^RF+[0-9]+[0-9]+[0-9]+[0-9]+[0-9]")
        var hasValue = true
        mDisposable = Observable.create<Boolean> {
            val radiationTaintInfoList = (radiationFragment as RadiationFragment).getRadiationInfo()
            val chemicalTaintInfoList = (chemicalFragment as ChemicalFragment).getRadiationInfo()
            if (radiationTaintInfoList.isNotEmpty()) {
                for (item in radiationTaintInfoList) {
                    val value = item.taint_num
                    val matcher1 = pattern1.matcher(value)
                    val matcher2 = pattern2.matcher(value)
                    if (!matcher1.matches() || !matcher2.matches()) {
                        item.normal = true
                        hasValue = false
                    }else{
                        item.normal = false
                    }
                }
            }
            if (chemicalTaintInfoList.isNotEmpty()) {
                for (item in chemicalTaintInfoList) {
                    val value = item.taint_num
                    val matcher1 = pattern1.matcher(value)
                    val matcher2 = pattern2.matcher(value)
                    if (!matcher1.matches() || !matcher2.matches()) {
                        item.normal = true
                        hasValue = false
                    }else{
                        item.normal = false
                    }
                }
            }
            if (hasValue) {
                DaoTool.updateTaintInfo(
                    (radiationFragment as RadiationFragment).getRadiationInfo(), 1
                )
                DaoTool.updateTaintInfo(
                    (chemicalFragment as ChemicalFragment).getRadiationInfo(), 2
                )
                DaoTool.updateDeviceInfo(
                    (biologicsFragment as BiologicsFragment).getRadiationInfo()
                )
            }
            it.onNext(hasValue)

        }.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe {

            if (hasValue) {
                ToastUtil.showCenterToast("保存成功")
                settingFragment.pop()
            } else {
                ToastUtil.showCenterToast("保存失败")
                (radiationFragment as RadiationFragment).adapter!!.notifyDataSetChanged()
                (chemicalFragment as ChemicalFragment).adapter!!.notifyDataSetChanged()
            }

        }

    }

}