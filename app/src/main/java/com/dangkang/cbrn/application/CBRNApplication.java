package com.dangkang.cbrn.application;

import android.os.Handler;
import android.os.Looper;

import com.dangkang.core.application.BaseApplication;

public class CBRNApplication extends BaseApplication {
    public static Handler handler;

    @Override
    public void onCreate() {
        super.onCreate();
        handler = new Handler(Looper.getMainLooper());
    }
}
