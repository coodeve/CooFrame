package com.coodev.base.init;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.coodev.module_api.ModuleBus;

public class BaseApplication extends Application {
    public static final String TAG = "BaseApplication";
    private static Application sApplication;
    private final static DefaultActivityLifecycleCallback sActivityLifecycleCallback = new DefaultActivityLifecycleCallback();

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sApplication = this;
        registerActivityLifecycleCallbacks(sActivityLifecycleCallback);
        ModuleBus.init(sApplication);
    }

    public static Application getApplication() {
        return sApplication;
    }

    public static Activity getTopActivity() {
        return sActivityLifecycleCallback.getTopActivity();
    }


}
