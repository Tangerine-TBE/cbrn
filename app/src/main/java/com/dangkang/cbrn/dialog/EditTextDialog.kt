package com.dangkang.cbrn.dialog

import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.renderscript.ScriptGroup.Input
import android.text.TextUtils
import android.view.Gravity
import android.view.LayoutInflater
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatDialog
import com.blankj.utilcode.util.ScreenUtils
import com.dangkang.cbrn.databinding.DialogTypeEditBinding
import com.dangkang.cbrn.databinding.DialogWorkBackBinding
import com.dangkang.cbrn.fragment.main.setting.BiologicsFragment
import com.dangkang.cbrn.utils.ToastUtil
import me.yokeyword.fragmentation.SupportActivity

class EditTextDialog(context: Context, theme: Int, onItemSelected: OnItemSelected) :
    AppCompatDialog(context, theme) {
    private var mBinding: DialogTypeEditBinding? = null
    private var onItemSelected: OnItemSelected? = null
    private val imm: InputMethodManager =
        context.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
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
            imm.hideSoftInputFromWindow(mBinding?.tv2?.windowToken,InputMethodManager.HIDE_IMPLICIT_ONLY)
            dismiss()
        }
        mBinding!!.btnCancel.setOnClickListener {
            val value = mBinding?.tv2?.text.toString()
            if (!TextUtils.isEmpty(value)){
                onItemSelected.onSaveItem(mBinding?.tv2?.text.toString())
            }else{
                ToastUtil.showToast("输入的内容不能为空!")
            }
            imm.hideSoftInputFromWindow(mBinding?.tv2?.windowToken,InputMethodManager.HIDE_IMPLICIT_ONLY)
            dismiss()

        }
    }

    interface OnItemSelected {
        fun onSaveItem(value: String)
    }

    override fun show() {
        super.show()
        mBinding?.tv2?.text?.clear()
        mBinding?.tv2?.requestFocus()
        imm.showSoftInput(mBinding?.tv2, InputMethodManager.SHOW_IMPLICIT);
    }

}