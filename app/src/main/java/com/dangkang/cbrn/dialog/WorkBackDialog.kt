package com.dangkang.cbrn.dialog

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.WindowManager
import androidx.appcompat.app.AppCompatDialog
import com.blankj.utilcode.util.ScreenUtils
import com.dangkang.cbrn.databinding.DialogWorkBackBinding

class WorkBackDialog (context: Context, theme: Int, onItemSelected: OnItemSelected) : AppCompatDialog(context, theme) {
    private var mBinding : DialogWorkBackBinding? = null
    private var onItemSelected:OnItemSelected?=null
    init{
        mBinding = DialogWorkBackBinding.inflate(LayoutInflater.from(context))
        this.onItemSelected = onItemSelected
        setContentView(mBinding!!.root)
        val deviceWidth  = ScreenUtils.getScreenWidth()
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
        }
        mBinding!!.btnCancel.setOnClickListener{
            dismiss()
            onItemSelected.onSaveItem()
        }
    }
    interface OnItemSelected{
        fun onSaveItem()
    }


}