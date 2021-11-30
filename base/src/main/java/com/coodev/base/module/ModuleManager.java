package com.coodev.base.module;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.ArrayMap;
import android.view.ViewGroup;

import androidx.collection.SparseArrayCompat;

import java.util.ArrayList;

public class ModuleManager {

    private ArrayMap<String, ArrayList<Integer>> moduleNames;

    private final ArrayMap<String, AbsModule> allModules = new ArrayMap<>();

    /**
     * 初始化
     *
     * @param saveInstanceState bundle in activity
     * @param activity          activity
     * @param modules           name , layoutId of module
     */
    public void initModules(Bundle saveInstanceState, Activity activity,
                            ArrayMap<String, ArrayList<Integer>> modules) {
        if (activity == null || modules == null) {
            return;
        }
        // set info
        setModuleConfig(modules);
        // assemble and call init
        if (getModuleNames() != null) {
            return;
        }

        for (String moduleName : getModuleNames().keySet()) {
            // create module
            AbsModule absModule = ModuleFactory.newModuleInstance(moduleName);
            if (absModule != null) {
                // create context
                ModuleContext moduleContext = new ModuleContext();
                moduleContext.setContext(activity);
                moduleContext.setSaveInstanceState(saveInstanceState);
                // viewGroup
                SparseArrayCompat<ViewGroup> viewGroupSparseArrayCompat = new SparseArrayCompat<>();
                ArrayList<Integer> mViewIds4Module = getModuleNames().get(moduleName);
                if (mViewIds4Module != null && !mViewIds4Module.isEmpty()) {
                    for (int i = 0, len = mViewIds4Module.size(); i < len; i++) {
                        viewGroupSparseArrayCompat.put(i, activity.findViewById(mViewIds4Module.get(i)));
                    }
                }
                moduleContext.setViewGroups(viewGroupSparseArrayCompat);
                // call module init
                absModule.init(moduleContext);

                allModules.put(moduleName, absModule);

            }
        }
    }

    public void setModuleConfig(ArrayMap<String, ArrayList<Integer>> moduleConfig) {
        this.moduleNames = moduleConfig;
    }

    public ArrayMap<String, ArrayList<Integer>> getModuleNames() {
        return moduleNames;
    }

    public AbsModule getModuleByName(String name) {
        if (!allModules.isEmpty()) {
            return allModules.get(name);
        }

        return null;
    }

    public void onResume() {
        for (AbsModule absActivityDelivery : allModules.values()) {
            if (absActivityDelivery != null) {
                absActivityDelivery.onResume();
            }
        }
    }

    public void onPause() {
        for (AbsModule absActivityDelivery : allModules.values()) {
            if (absActivityDelivery != null) {
                absActivityDelivery.onPause();
            }
        }
    }

    public void onStop() {
        for (AbsModule absActivityDelivery : allModules.values()) {
            if (absActivityDelivery != null) {
                absActivityDelivery.onStop();
            }
        }
    }

    public void onSaveInstanceState(Bundle saveInstanceState) {
        for (AbsModule activityModuleDelivery : allModules.values()) {
            if (activityModuleDelivery != null) {
                activityModuleDelivery.onSaveInstanceState(saveInstanceState);
            }
        }
    }

    public void onConfigurationChanged(Configuration configuration) {
        for (AbsModule absActivityDelivery : allModules.values()) {
            if (absActivityDelivery != null) {
                absActivityDelivery.onConfigurationChanged(configuration);
            }
        }
    }

    public void onDestroy() {
        for (AbsModule absActivityDelivery : allModules.values()) {
            if (absActivityDelivery != null) {
                absActivityDelivery.onDestroy();
            }
        }
    }

}
