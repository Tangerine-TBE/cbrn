package com.dangkang.cbrn.fragment.main.workSpace

import android.os.Bundle
import android.view.View
import androidx.viewbinding.ViewBinding
import com.dangkang.cbrn.R
import com.dangkang.cbrn.databinding.FragmentWorkSpaceBinding
import com.dangkang.cbrn.dialog.SettingBackDialog
import com.dangkang.cbrn.dialog.WorkBackDialog
import com.dangkang.core.fragment.BaseFragment
import com.dangkang.core.utils.L

class WorkSpaceFragment : BaseFragment<ViewBinding>(), View.OnClickListener {
    private var viewPagerWsStateAdapter: ViewPagerWsStateAdapter? = null
    private var fragmentWorkSpaceBinding: FragmentWorkSpaceBinding? = null
    private var workBackDialog: WorkBackDialog? = null

    companion object {
        fun newInstance(): BaseFragment<ViewBinding> {
            return WorkSpaceFragment()
        }
    }

    override fun setBindingView(): ViewBinding {
        fragmentWorkSpaceBinding = FragmentWorkSpaceBinding.inflate(layoutInflater)
        binding = fragmentWorkSpaceBinding
        return initView(binding as FragmentWorkSpaceBinding)
    }

    private fun initView(fragmentMainBinding: FragmentWorkSpaceBinding): ViewBinding {

        fragmentMainBinding.titleBar.title.text = "实时操作"
        fragmentMainBinding.titleBar.connectInfo.text = "未连接模块..."
        fragmentMainBinding.titleBar.back.setOnClickListener {
            if (workBackDialog == null) {
                workBackDialog = WorkBackDialog(
                    _mActivity,
                    R.style.DialogStyle,
                    object : WorkBackDialog.OnItemSelected {
                        override fun onSaveItem() {
                            pop()
                        }

                    })
            }
            workBackDialog!!.show()
        }
        fragmentMainBinding.titleBar.cancel.setOnClickListener {
            _mActivity.finish()
        }
        fragmentMainBinding.titleBar.connectSir.setOnClickListener(this)
        fragmentMainBinding.biologics.setOnClickListener(this)
        fragmentMainBinding.chemical.setOnClickListener(this)
        fragmentMainBinding.radiation.setOnClickListener(this)
        fragmentMainBinding.titleBar.connectSir.visibility = View.VISIBLE
        fragmentMainBinding.fragmentReplace.apply {
            viewPagerWsStateAdapter =
                ViewPagerWsStateAdapter(_mActivity.supportFragmentManager, lifecycle)
            adapter = viewPagerWsStateAdapter
            isUserInputEnabled = false
            offscreenPageLimit = 4
        }
        return fragmentMainBinding
    }

    override fun onDestroy() {
        viewPagerWsStateAdapter?.destroyAllItem()
        super.onDestroy()
    }

    override fun onBackPressedSupport(): Boolean {
        if (workBackDialog == null) {
            workBackDialog = WorkBackDialog(
                _mActivity,
                R.style.DialogStyle,
                object : WorkBackDialog.OnItemSelected {
                    override fun onSaveItem() {
                        pop()
                    }

                })
        }
        workBackDialog!!.show()
        return true
    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        onClick(fragmentWorkSpaceBinding?.radiation)
    }

    private var selectedItem: View? = null
    override fun onClick(v: View?) {
        if (selectedItem == v) {
            return
        }
        if (v == fragmentWorkSpaceBinding?.titleBar?.connectSir) {
            fragmentWorkSpaceBinding!!.fragmentReplace.currentItem = 3
        } else {
            fragmentWorkSpaceBinding?.menuList?.indexOfChild(v)
                ?.let { fragmentWorkSpaceBinding!!.fragmentReplace.currentItem = it }
        }
        v?.isSelected = true
        selectedItem?.isSelected = false
        selectedItem = v
    }
}