package com.dangkang.cbrn.fragment.main.workSpace

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewbinding.ViewBinding
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dangkang.core.fragment.BaseFragment

/**
 *@author:Administrator
 *@date:2023/1/31
 */
class ViewPagerWsStateAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    private var radiationFragment: BaseFragment<ViewBinding>? = null
    private var chemicalFragment: BaseFragment<ViewBinding>? = null
    private var biologicsFragment: BaseFragment<ViewBinding>? = null
    private var modelSelectFragment:BaseFragment<ViewBinding>? = null
    override fun getItemCount(): Int {
        return 4
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                radiationFragment = RadiationWSFragment()
                return radiationFragment as BaseFragment<ViewBinding>
            }
            1 -> {
                chemicalFragment = ChemicalWSFragment()
                return chemicalFragment as BaseFragment<ViewBinding>
            }
            2 -> {
                biologicsFragment = BiologicsWSFragment()
                return biologicsFragment as BaseFragment<ViewBinding>
            }
            else -> {
                modelSelectFragment = ModelSelectFragment()
                return modelSelectFragment as BaseFragment<ViewBinding>
            }
        }
    }
    fun destroyAllItem(){
        radiationFragment?.onDestroy()
        chemicalFragment?.onDestroy()
        biologicsFragment?.onDestroy()
        modelSelectFragment?.onDestroy()
    }
}