package com.dangkang.cbrn.fragment.main.workSpace

import android.opengl.Visibility
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewbinding.ViewBinding
import com.dangkang.cbrn.R
import com.dangkang.cbrn.databinding.FragmentMainBinding
import com.dangkang.cbrn.databinding.FragmentWorkSpaceBinding
import com.dangkang.cbrn.fragment.main.TeacherFragment
import com.dangkang.cbrn.fragment.main.setting.ChemicalFragment
import com.dangkang.core.fragment.BaseFragment

class WorkSpaceFragment : BaseFragment<ViewBinding>() ,View.OnClickListener {


    companion object {
        fun newInstance(): BaseFragment<ViewBinding> {
            return WorkSpaceFragment()
        }
    }

    override fun setBindingView(): ViewBinding {
        binding = FragmentWorkSpaceBinding.inflate(layoutInflater)
        return initView(binding as FragmentWorkSpaceBinding)
    }

    private fun initView(fragmentMainBinding: FragmentWorkSpaceBinding): ViewBinding {

        fragmentMainBinding.titleBar.title.text = "实时操作"
        fragmentMainBinding.titleBar.connectInfo.text = "未连接模块..."
        fragmentMainBinding.titleBar.back.setOnClickListener {
            pop()
        }
        fragmentMainBinding.titleBar.cancel.setOnClickListener {
            _mActivity.finish()
        }
        fragmentMainBinding.titleBar.connectSir.setOnClickListener(this)
        fragmentMainBinding.biologics.setOnClickListener (this)
        fragmentMainBinding.chemical.setOnClickListener(this)
        fragmentMainBinding.radiation.setOnClickListener(this)
        fragmentMainBinding.titleBar.connectSir.visibility = View.VISIBLE
        onClick(fragmentMainBinding.radiation)
        return fragmentMainBinding
    }
    private var selectedItem:View? = null
    override fun onClick(v: View?) {
        var selectedFragment: BaseFragment<ViewBinding>? = null
        when(v?.id){

            R.id.radiation ->{
                selectedFragment = RadiationWSFragment()
            }
            R.id.biologics ->{
                selectedFragment = BiologicsWSFragment()
            }
            R.id.chemical ->{
                selectedFragment = ChemicalWSFragment()
            }
            R.id.connect_sir ->{
                selectedFragment = SocketBindingFragment()
            }
        }
        v?.isSelected = true
        selectedItem?.isSelected = false
        selectedItem = v
        loadRootFragment(R.id.fragment_replace,selectedFragment,false,false)

    }
}