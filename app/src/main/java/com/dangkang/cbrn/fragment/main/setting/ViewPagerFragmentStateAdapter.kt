package com.dangkang.cbrn.fragment.main.setting

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerFragmentStateAdapter(fragmentManager: FragmentManager,lifecycle:Lifecycle) : FragmentStateAdapter(fragmentManager,lifecycle) {

    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when (position){
            0 -> RadiationFragment.newInstance()
            1 -> RadiationFragment.newInstance()
            2 -> RadiationFragment.newInstance()
            else -> RadiationFragment.newInstance()
        }
    }

}