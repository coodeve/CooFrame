package com.coodev.media;

import android.app.Application;
import android.util.Log;

import com.coodev.module_api.template.AppInit;
import com.coodev.module_annotation.annotation.ModuleInit;

/**
 * application 初始化
 */
@ModuleInit
public class MediaInit implements AppInit {

    public static final String TAG = "MediaInit";

    @Override
    public void onInit(Application app) {
        Log.i(TAG, "onInit: ");
    }

    @Override
    public void onInitLazy(Application app) {
        Log.i(TAG, "onInitLazy: ");
    }
}
