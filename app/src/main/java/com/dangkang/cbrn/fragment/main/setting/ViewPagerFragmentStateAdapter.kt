package com.dangkang.cbrn.fragment.main.setting

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewbinding.ViewBinding
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dangkang.cbrn.dao.DaoTool
import com.dangkang.core.fragment.BaseFragment

class ViewPagerFragmentStateAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    private var radiationFragment: BaseFragment<ViewBinding>? = null
    private var chemicalFragment: BaseFragment<ViewBinding>? = null
    private var biologicsFragment: BaseFragment<ViewBinding>? = null
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
    fun destroyAllItem(){
        radiationFragment?.onDestroy()
        chemicalFragment?.onDestroy()
        biologicsFragment?.onDestroy()

    }



    fun save() {
        DaoTool.updateTaintInfo(
            (radiationFragment as RadiationFragment).getRadiationInfo(), 1)
        DaoTool.updateTaintInfo(
            (chemicalFragment as ChemicalFragment).getRadiationInfo(), 2)
        DaoTool.updateDeviceInfo(
            (biologicsFragment as BiologicsFragment).getRadiationInfo()
        )
    }

}