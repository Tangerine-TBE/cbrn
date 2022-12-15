package com.dangkang.core.application;

import android.app.Application;

public class BaseApplication extends Application {
    public static Application APPLICATION;
    @Override
    public void onCreate() {
        super.onCreate();
        APPLICATION = this;
    }
}
