package com.coodev.module_api.template;

import android.app.Application;

public interface AppInit {
    /**
     * 初始化，Application的onCreate中同步
     *
     * @param app application
     */
    void onInit(Application app);

    /**
     * 延迟初始化，会在idle时进行初始化
     *
     * @param app application
     */
    void onInitLazy(Application app);
}
