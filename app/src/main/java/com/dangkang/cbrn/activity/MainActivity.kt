package com.dangkang.cbrn.activity

import android.Manifest
import android.os.Bundle
import androidx.viewbinding.ViewBinding
import com.clj.fastble.BleManager
import com.dangkang.cbrn.R
import com.dangkang.cbrn.fragment.splash.SplashFragment
import com.dangkang.core.activity.BaseActivity
import com.dangkang.core.fragment.BaseFragment
import com.dangkang.core.utils.L
import com.gyf.barlibrary.ImmersionBar
import pub.devrel.easypermissions.EasyPermissions

class MainActivity: BaseActivity(){
    override fun setRootFragment(): BaseFragment<ViewBinding> {
        return SplashFragment.newInstance()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        BleManager.getInstance().init(application)
        checkPermissions()
    }

    override fun onStart() {
        super.onStart()
        L.e("onStart")
    }


    private fun checkPermissions() {
        if (!EasyPermissions.hasPermissions(this, Manifest.permission.ACCESS_FINE_LOCATION)){
            EasyPermissions.requestPermissions(this, "当前需要一个非常重要的权限申请   ",
                1, Manifest.permission.ACCESS_FINE_LOCATION
            )
        }
    }



    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode,permissions,grantResults,this)
    }
    override fun setStatusBar() {
        ImmersionBar.with(this)
            .statusBarColor(R.color.navigation)
            .statusBarDarkFont(false)
            .navigationBarColor(R.color.navigation)
            .barColor(R.color.navigation)
            .init()
    }


}