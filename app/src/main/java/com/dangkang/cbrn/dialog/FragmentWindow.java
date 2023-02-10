package com.dangkang.cbrn.dialog;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dangkang.cbrn.R;
import com.dangkang.cbrn.adapter.setting.ChemicalCwaAdapter;
import com.dangkang.cbrn.bean.CwaBean;
import com.dangkang.cbrn.weight.SimplePaddingDecoration;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import razerdp.basepopup.BasePopupWindow;

/**
 * @author:Administrator
 * @date:2023/1/11
 */
public class FragmentWindow extends BasePopupWindow {
    private Disposable mDisposable;
    public int gravity = Gravity.TOP | Gravity.CENTER;
    public BasePopupWindow.GravityMode gravityMode = GravityMode.ALIGN_TO_ANCHOR_SIDE;
    public FragmentWindow(Context context) {
        super(context);
        setPopupGravity(gravityMode, gravity);//设置吸附规则
        hideKeyboardOnShow(true);
        setOutSideDismiss(false);
        setBlurBackgroundEnable(false); //背景模糊
        setOverlayMask(false);//是否允许蒙层叠加
        setBackgroundColor(Color.TRANSPARENT);//背景颜色设置 透明图层
        setContentView(R.layout.fragment_cwa); //设置主要图层
    }

    @Override
    public void onViewCreated(@NonNull View contentView) {
        super.onViewCreated(contentView);
        initView();
    }
    private void initView() {
        RecyclerView recyclerView =  findViewById(R.id.recyclerView);
        findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        mDisposable = Observable.create((ObservableOnSubscribe<List<CwaBean>>) emitter -> {
            ArrayList<CwaBean> values = new ArrayList<>();
            values.add(new CwaBean("神经", R.mipmap.icon_frame1, "沙林", "0.1", "0.2", "0.3", "mg/m^3"));
            values.add(new CwaBean("神经", R.mipmap.icon_frame1, "塔崩", "0.1", "0.2", "0.3", "mg/m^3"));
            values.add(new CwaBean("神经", R.mipmap.icon_frame1, "梭曼", "0.1", "0.2", "0.3", "mg/m^3"));
            values.add(new CwaBean("神经", R.mipmap.icon_frame1, "尔沙林", "0.1", "0.2", "0.3", "mg/m^3"));
            values.add(new CwaBean("神经", R.mipmap.icon_frame1, "VX", "0.1", "0.2", "0.3", "mg/m^3"));
            values.add(new CwaBean("皮肤", R.mipmap.icon_frame1, "硫芥", "0.5", "0.6", "0.7", "mg/m^3"));
            values.add(new CwaBean("皮肤", R.mipmap.icon_frame1, "路易斯气", "2", "2.5", "3", "mg/m^3"));
            values.add(new CwaBean("皮肤", R.mipmap.icon_frame1, "氮芥", "5", "5.5", "6", "mg/m^3"));
            values.add(new CwaBean("血液", R.mipmap.icon_frame1, "氰胺酸", "50", "55", "60", "mg/m^3"));
            values.add(new CwaBean("血液", R.mipmap.icon_frame1, "氯化氢", "50", "55", "60", "mg/m^3"));
            values.add(new CwaBean("有毒工业品", R.mipmap.frame, "氯", "10", "15", "20", "ppm"));
            values.add(new CwaBean("有毒工业品", R.mipmap.frame, "氨", "100", "150", "200", "ppm"));
            values.add(new CwaBean("有毒工业品", R.mipmap.frame, "有害的浓度和化学混合物", "-", "-", "-", "mg/m^3"));
            emitter.onNext(values);
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<List<CwaBean>>() {
            @Override
            public void accept(List<CwaBean> cwaBeans) {
                ChemicalCwaAdapter chemicalCwaAdapter = new ChemicalCwaAdapter(getContext(),cwaBeans);
                recyclerView .setAdapter(chemicalCwaAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerView.addItemDecoration(new SimplePaddingDecoration(getContext()));
            }
        });
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mDisposable != null) {
            if (!mDisposable.isDisposed()) {
                mDisposable.dispose();
            }
        }
    }
}
