package com.dangkang.cbrn.application;

import com.dangkang.cbrn.application.config.Config;
import com.dangkang.core.application.BaseApplication;

public class CBRNApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        Config.init(this)
                .withDao(this)
                .withSp(this)
                .Configure();
    }
}
