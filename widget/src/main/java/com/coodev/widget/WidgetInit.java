package com.coodev.widget;

import android.app.Application;
import android.util.Log;

import com.coodev.module_annotation.annotation.ModuleInit;
import com.coodev.module_api.template.AppInit;


/**
 * application 初始化
 */
@ModuleInit
public class WidgetInit implements AppInit {
    public static final String TAG = WidgetInit.class.getSimpleName();

    @Override
    public void onInit(Application app) {
        Log.i(TAG, "onInit: ");
    }

    @Override
    public void onInitLazy(Application app) {
        Log.i(TAG, "onInit: ");
    }
}
