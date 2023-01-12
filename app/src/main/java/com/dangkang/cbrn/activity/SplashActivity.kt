package com.dangkang.cbrn.activity

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.bluetooth.BluetoothAdapter
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.viewbinding.ViewBinding
import com.clj.fastble.BleManager
import com.dangkang.cbrn.R
import com.dangkang.cbrn.fragment.splash.WelComeFragment
import com.dangkang.core.activity.BaseActivity
import com.dangkang.core.fragment.BaseFragment
import com.dangkang.core.utils.L
import com.gyf.barlibrary.BarHide
import com.gyf.barlibrary.ImmersionBar
import pub.devrel.easypermissions.EasyPermissions

@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseActivity(){
    override fun setRootFragment(): BaseFragment<ViewBinding> {

        return WelComeFragment.newInstance()
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