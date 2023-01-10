package com.dangkang.cbrn.dialog;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.blankj.utilcode.util.AdaptScreenUtils;
import com.blankj.utilcode.util.ConvertUtils;
import com.dangkang.cbrn.R;

import java.util.List;

import razerdp.basepopup.BasePopupWindow;
import razerdp.util.animation.AlphaConfig;
import razerdp.util.animation.AnimationHelper;
import razerdp.util.animation.TranslationConfig;

/**
 * @author:Administrator
 * @date:2023/1/9
 */
public class DataSelectWindow extends BasePopupWindow {
    private final List<String > mArrayStrings;
    private final String mSelValue;
    public int gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
    private final OnValueSelected mSelected;
    public BasePopupWindow.GravityMode gravityMode = BasePopupWindow.GravityMode.RELATIVE_TO_ANCHOR;
    private final int width;
    /**
     *@description 辐射 类型选择 多选一规则
     *@param  arrayStrings 给定的字符串集合
     *@return DataSelectWindow
     *@author tangerine
     *@time 2023/1/9 17:48
     *
     *
     */
    public DataSelectWindow(Context context, List<String> arrayStrings,OnValueSelected selected,String selectedValue,int width) {
        super(context);
        this.mArrayStrings = arrayStrings;
        setPopupGravity(gravityMode, gravity);//设置吸附规则
        hideKeyboardOnShow(true);
        setBlurBackgroundEnable(false); //背景模糊
        setOverlayMask(false);//是否允许蒙层叠加
        setBackgroundColor(Color.TRANSPARENT);//背景颜色设置 透明图层
        this.mSelected = selected;
        this.mSelValue = selectedValue;
        /**/
        this.width = width;
        /*此处SetContentView必须要在最后才进行视图绑定*/
        setContentView(R.layout.dialog_selecter); //设置主要图层
    }

    @Override
    public void onViewCreated(@NonNull View contentView) {
        super.onViewCreated(contentView);
        RadioGroup radioGroup = findViewById(R.id.selector);
        ScrollView.LayoutParams layoutParams = new ScrollView.LayoutParams(width, ViewGroup.LayoutParams.WRAP_CONTENT);
        radioGroup.setLayoutParams(layoutParams);

        ColorStateList colorStateList = new ColorStateList(new int[][]
                {{-android.R.attr.state_enabled },{android.R.attr.state_enabled}},
                new int[]{ContextCompat.getColor(getContext(),R.color.blue),ContextCompat.getColor(getContext(),R.color.blue)});
        RadioGroup.LayoutParams buttonParams = new RadioGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        RadioGroup.LayoutParams lineParams = new RadioGroup.LayoutParams(ViewGroup.MarginLayoutParams.MATCH_PARENT,2);

        for (int i = 0  ; i < mArrayStrings.size(); i ++) {
            RadioButton radioButton = new RadioButton(getContext());
            radioButton.setPadding(10,4,10,4);
            radioButton.setText(mArrayStrings.get(i));
            radioButton.setTextSize(18);
            radioButton.setTextColor(Color.WHITE);
            radioButton.setGravity(Gravity.CENTER);
            radioButton.setLayoutParams(buttonParams);
            radioButton.setButtonTintList(colorStateList);
            radioButton.setTag(i);
            radioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked){
                        mSelected.valueSelected(mArrayStrings.get((Integer) buttonView.getTag()));
                    }
                }
            });
            //先后顺序不可以改变
            radioGroup.addView(radioButton);
            if (i  != mArrayStrings.size() - 1){
                View view = new View(getContext());
                view.setLayoutParams(lineParams);
                view.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.navigation));
                radioGroup.addView(view);
            }
            if (!TextUtils.isEmpty(mSelValue)){
                if (mSelValue.equals(mArrayStrings.get(i))){
                    radioGroup.check(radioButton.getId());
                }
            }

        }
    }
    public interface OnValueSelected{
        void valueSelected(String value);
    }

    @Override
    protected Animation onCreateShowAnimation(int width, int height) {
        return AnimationHelper.asAnimation().withTranslation(TranslationConfig.FROM_TOP).toShow();
    }

    @Override
    protected Animation onCreateDismissAnimation(int width, int height) {
        return AnimationHelper.asAnimation().withTranslation(TranslationConfig.TO_TOP).toDismiss();
    }
}
