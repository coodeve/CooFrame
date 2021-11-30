package com.coodev.module_api;

import android.app.Application;
import android.content.pm.PackageManager;
import android.os.MessageQueue;
import android.util.Log;

import com.coodev.module_annotation.module.InitMeta;
import com.coodev.module_api.template.AppInit;
import com.coodev.module_api.template.Init;
import com.coodev.module_api.util.ClassUtils;
import com.coodev.module_api.util.HandlerUtil;
import com.coodev.module_api.util.ModuleUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class InitCenter {
    public static final String TAG = "InitCenter";
    private final static List<InitMeta> mInitMetaList = new ArrayList<>(4);

    public static void init(Application app) {
        try {
            List<String> fileNameByPackageName =
                    ClassUtils.getFileNameByPackageName(app, ModuleUtil.FACADE_PACKAGE);
            for (String className : fileNameByPackageName) {
                Log.i(TAG, "init: className = [" + className + "]");
                if (className.startsWith(ModuleUtil.ADDRESS_OF_INIT)) {
                    Init appInit = (Init) Class.forName(className).newInstance();
                    appInit.loadInto(mInitMetaList);
                }
            }
        } catch (PackageManager.NameNotFoundException | IOException
                | ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }


        // 初始化加载
        try {
            dispatchInit(app);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private static void dispatchInit(Application app)
            throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        for (InitMeta initMeta : mInitMetaList) {
            AppInit appInit = (AppInit) Class.forName(initMeta.getInitPath()).newInstance();
            appInit.onInit(app);
            HandlerUtil.addIdleHandler(new InitIdleHandler(appInit, app));
        }
    }

    private static class InitIdleHandler implements MessageQueue.IdleHandler {
        private final AppInit mAppInit;
        private final Application mApplication;

        public InitIdleHandler(AppInit appInit, Application application) {
            mAppInit = appInit;
            mApplication = application;
        }

        @Override
        public boolean queueIdle() {
            if (mAppInit != null) {
                mAppInit.onInitLazy(mApplication);
            }
            return false;
        }
    }

}
