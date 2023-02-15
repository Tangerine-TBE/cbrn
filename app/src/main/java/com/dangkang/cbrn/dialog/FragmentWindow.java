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
import com.dangkang.cbrn.dao.DaoTool;
import com.dangkang.cbrn.db.ChemicalInfo;
import com.dangkang.cbrn.weight.SimplePaddingDecoration;
import com.dangkang.core.utils.GsonUtil;
import com.dangkang.core.utils.L;

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
        mDisposable = Observable.create((ObservableOnSubscribe<List<ChemicalInfo>>) emitter -> {
            List<ChemicalInfo> values = DaoTool.queryAllChemicalInfo();
            emitter.onNext(values);
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<List<ChemicalInfo>>() {
            @Override
            public void accept(List<ChemicalInfo> cwaBeans) {
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
