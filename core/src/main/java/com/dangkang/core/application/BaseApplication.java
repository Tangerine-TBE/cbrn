package com.dangkang.core.application;

import android.app.Application;
import android.content.Context;
import android.os.Build;

import androidx.multidex.MultiDex;

public class BaseApplication extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        if (Build.VERSION_CODES.LOLLIPOP>Build.VERSION.SDK_INT) {
            MultiDex.install(this);
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
