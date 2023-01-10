package com.dangkang.cbrn.dialog

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.WindowManager
import androidx.appcompat.app.AppCompatDialog
import com.blankj.utilcode.util.ScreenUtils
import com.dangkang.cbrn.databinding.DialogSettingBackBinding

/**
 *@author:Administrator
 *@date:2023/1/10
 */
class SettingBackDialog(context: Context, theme: Int,onItemSelected: OnItemSelected) : AppCompatDialog(context, theme) {
    private var mBinding :DialogSettingBackBinding? = null
    private var onItemSelected:OnItemSelected?=null
    init{
        mBinding = DialogSettingBackBinding.inflate(LayoutInflater.from(context))
        this.onItemSelected = onItemSelected
        setContentView(mBinding!!.root)
        val deviceWidth  =ScreenUtils.getScreenWidth()
        val deviceHeight = ScreenUtils.getScreenHeight()
        if (window != null){
            val layoutParams: WindowManager.LayoutParams = window!!.attributes
            layoutParams.width = deviceWidth / 3
            layoutParams.height = deviceHeight / 3
            layoutParams.gravity = Gravity.CENTER
        }
        setCanceledOnTouchOutside(false)
        mBinding!!.btnSave.setOnClickListener{
            dismiss()
            onItemSelected.onSaveItem()
        }
        mBinding!!.btnUnSave.setOnClickListener{
            dismiss()
            onItemSelected.onUnSaveItem()

        }
        mBinding!!.btnCancel.setOnClickListener{
            dismiss()
        }
    }
    interface OnItemSelected{
        fun onSaveItem()
        fun onUnSaveItem()
    }

}