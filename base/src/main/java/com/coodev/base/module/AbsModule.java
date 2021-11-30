package com.coodev.base.module;

import android.content.res.Configuration;
import android.os.Bundle;

/**
 * 模块
 */
public abstract class AbsModule {
    public abstract void init(ModuleContext moduleContext);

    public abstract boolean onCreate(ModuleContext moduleContext, Bundle extend);

    public abstract void onSaveInstanceState(Bundle outState);

    public abstract void onStart();

    public abstract void onResume();

    public abstract void onPause();

    public abstract void onStop();

    public abstract void onConfigurationChanged(Configuration configuration);

    public abstract void onDestroy();

    public abstract void detachView();

    public abstract void setVisible(boolean visible);

}
