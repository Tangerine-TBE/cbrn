package com.dangkang.cbrn.application.config;

import static com.dangkang.cbrn.application.config.ConfigureType.APPLICATION;
import static com.dangkang.cbrn.application.config.ConfigureType.CONFIGURE_READ;

import android.app.Application;
import android.content.Context;
import android.util.ArrayMap;

import com.dangkang.Constant;
import com.dangkang.cbrn.R;
import com.dangkang.cbrn.dao.DaoTool;
import com.dangkang.cbrn.utils.SPUtil;

/**
 * * DATE: 2022/10/10
 * * Author:tangerine
 * * Description:
 **/
public class Configurator {
    private static final ArrayMap<String, Object> CONFIGS = new ArrayMap<>();

    private static class Holder {
        private static final Configurator INSTANCE = new Configurator();
    }

    static Configurator getInstance() {
        return Holder.INSTANCE;
    }

    private Configurator() {
        CONFIGS.put(CONFIGURE_READ.name(), false);
    }

    final <A> A getConfiguration(Enum<ConfigureType> key) {
        checkConfiguration();
        return (A) CONFIGS.get(key.name());
    }

    private void checkConfiguration() {
        final boolean isReady = Boolean.TRUE.equals(CONFIGS.get(CONFIGURE_READ.name()));
        if (!isReady) {
            throw new RuntimeException("Configuration is not ready yet!");
        }

    }


    public final Configurator withSp(Context context) {
        SPUtil.init(context);
        String value = SPUtil.getInstance().getString(Constant.MEASUREMENT);
        if (value == null || value.isEmpty()){
            SPUtil.getInstance().putString(Constant.MEASUREMENT,Constant.MEASUREMENT_UNIT_1);
            SPUtil.getInstance().putString(Constant.ENVIRONMENT_VALUE,context.getResources().getStringArray(R.array.radiation_sim_dis)[0]);
            SPUtil.getInstance().putString(Constant.ENVIRONMENT_VALUE_UNIT,context.getResources().getStringArray(R.array.radiation_unit_1)[0]);
        }
        return this;
    }

    public final Configurator withDao(Context context) {
        DaoTool.init(context);
        return this;
    }

    public final Configurator withApplication(Application application) {
        CONFIGS.put(APPLICATION.name(), application);
        return this;
    }

    final ArrayMap<String, Object> getConfigs() {
        return CONFIGS;
    }

    public final void Configure() {
        CONFIGS.put(CONFIGURE_READ.name(), true);
    }
}
