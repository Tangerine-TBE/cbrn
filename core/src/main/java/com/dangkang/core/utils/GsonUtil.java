package com.dangkang.core.utils;

import android.text.TextUtils;

import com.google.gson.Gson;

import java.lang.reflect.Type;

public class GsonUtil {

    private static final String TAG = "GsonUtil";
    private static final Gson sGson = new Gson();

    public static String toJson(Object obj) {
        if (obj != null) {
            try {
                return sGson.toJson(obj);
            } catch (Exception e) {
                L.e(TAG, e.toString());
            }
        }
        return "";
    }

    public static <T> T fromJson(String jsonStr, Class<T> cls) {
        T jsonObj = null;
        if (!TextUtils.isEmpty(jsonStr)) {
            try {
                jsonObj = sGson.fromJson(jsonStr, cls);
            } catch (Exception e) {
                L.e(TAG, e.toString());
            }
        }
        return jsonObj;
    }

    public static <T> T fromJson(String jsonStr, Type type) {
        T jsonObj = null;
        if (!TextUtils.isEmpty(jsonStr)) {
            try {
                jsonObj = sGson.fromJson(jsonStr, type);
            } catch (Exception e) {
                L.e(TAG, e.toString());
            }
        }
        return jsonObj;
    }

}
