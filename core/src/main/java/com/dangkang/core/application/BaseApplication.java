package com.dangkang.core.application;

import android.app.Application;
import android.content.Context;
import android.os.Build;

import androidx.multidex.MultiDex;

import com.dangkang.core.utils.SPUtil;

public class BaseApplication extends Application {
    public static Application APPLICATION;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        APPLICATION = this;
        //MultiDex分包方法 必须最先初始化
        if (Build.VERSION_CODES.LOLLIPOP>Build.VERSION.SDK_INT) {
            MultiDex.install(this);
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();

        SPUtil.init(this);
    }
}
