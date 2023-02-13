package com.dangkang.cbrn.dialog

import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.renderscript.ScriptGroup.Input
import android.view.Gravity
import android.view.LayoutInflater
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatDialog
import com.blankj.utilcode.util.ScreenUtils
import com.dangkang.cbrn.databinding.DialogTypeEditBinding
import com.dangkang.cbrn.databinding.DialogWorkBackBinding
import com.dangkang.cbrn.fragment.main.setting.BiologicsFragment
import me.yokeyword.fragmentation.SupportActivity

class EditTextDialog(context: Context, theme: Int, onItemSelected: OnItemSelected) :
    AppCompatDialog(context, theme) {
    private var mBinding: DialogTypeEditBinding? = null
    private var onItemSelected: OnItemSelected? = null

    init {
        mBinding = DialogTypeEditBinding.inflate(LayoutInflater.from(context))
        this.onItemSelected = onItemSelected
        setContentView(mBinding!!.root)
        val deviceWidth = ScreenUtils.getScreenWidth()
        val deviceHeight = ScreenUtils.getScreenHeight()
        if (window != null) {
            val layoutParams: WindowManager.LayoutParams = window!!.attributes
            layoutParams.width = deviceWidth / 3
            layoutParams.height = deviceHeight / 3
            layoutParams.gravity = Gravity.CENTER
        }
        setCanceledOnTouchOutside(false)
        mBinding!!.btnSave.setOnClickListener {
            dismiss()
        }
        mBinding!!.btnCancel.setOnClickListener {
            dismiss()
            onItemSelected.onSaveItem(mBinding?.tv2?.text.toString())
        }
    }

    interface OnItemSelected {
        fun onSaveItem(value: String)
    }

    override fun show() {
        super.show()
        mBinding?.tv2?.text?.clear()
        mBinding?.tv2?.requestFocus()
        val imm: InputMethodManager =
            context.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(mBinding?.tv2, InputMethodManager.SHOW_IMPLICIT);

    }

}