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
                radiationFragment = RadiationFragment.newInstance()
                return radiationFragment as BaseFragment<ViewBinding>
            }
            1 -> {
                chemicalFragment = ChemicalFragment.newInstance()
                return chemicalFragment as BaseFragment<ViewBinding>
            }
            2 -> {
                biologicsFragment = BiologicsFragment.newInstance()
                return biologicsFragment as BaseFragment<ViewBinding>
            }
            else -> {
                radiationFragment = RadiationFragment.newInstance()
                return radiationFragment as BaseFragment<ViewBinding>
            }
        }
    }

    fun save() {
        DaoTool.updateTaintInfo(
            (radiationFragment as RadiationFragment).getRadiationInfo()
        )
    }

}