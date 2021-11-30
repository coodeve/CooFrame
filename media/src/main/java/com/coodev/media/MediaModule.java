package com.coodev.media;

import android.content.res.Configuration;
import android.os.Bundle;

import com.coodev.base.module.AbsModule;
import com.coodev.base.module.ModuleContext;
import com.coodev.module_annotation.annotation.ModuleUnit;
import com.coodev.module_annotation.enums.LayoutLevel;

/**
 * 多媒体模块
 */
@ModuleUnit(template = "video", layoutLevel = LayoutLevel.VERY_LOW)
public class MediaModule extends AbsModule {

    @Override
    public void init(ModuleContext moduleContext) {
        
    }

    @Override
    public boolean onCreate(ModuleContext moduleContext, Bundle extend) {
        return false;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

    }

    @Override
    public void onStart() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onConfigurationChanged(Configuration configuration) {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void detachView() {

    }

    @Override
    public void setVisible(boolean visible) {

    }
}