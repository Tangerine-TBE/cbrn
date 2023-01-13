package com.dangkang.cbrn.application;

import android.os.Handler;
import android.os.Looper;

import com.dangkang.cbrn.dao.DaoTool;
import com.dangkang.core.application.BaseApplication;
import com.dangkang.core.utils.SPUtil;

public class CBRNApplication extends BaseApplication {
    public static Handler handler;

    @Override
    public void onCreate() {
        super.onCreate();
        handler = new Handler(Looper.getMainLooper());
        DaoTool.init(this);
        SPUtil.init(this);
    }
}
