package com.coodev.module_api;

import android.app.Application;
import android.content.Context;

public class ModuleBus {

    /**
     * 整体模块初始化，类似于每个模块的application
     *
     * @param application Application参数
     */
    public static void init(Application application) {
        InitCenter.init(application);
    }

    /**
     * 模块初始化，类似View初始化，用于界面显示
     *
     * @param context Context
     */
    public static void initModuleView(Context context) {
        ModuleCenter.init(context);
    }
}
