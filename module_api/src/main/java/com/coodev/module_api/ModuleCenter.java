package com.coodev.module_api;

import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;

import com.coodev.module_annotation.module.ModuleMeta;
import com.coodev.module_api.template.IModuleUnit;
import com.coodev.module_api.util.ClassUtils;
import com.coodev.module_api.util.ModuleUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModuleCenter {
    public static final String TAG = "ModuleCenter";
    /**
     * module 信息，即注解相关信息
     */
    private final static List<ModuleMeta> group = new ArrayList<>();

    /**
     * key为template，value为相关module信息
     */
    private final static Map<String, List<ModuleMeta>> sortGroup = new HashMap<>();

    public static void init(Context context) {
        try {
            List<String> fileNameByPackageName =
                    ClassUtils.getFileNameByPackageName(context, ModuleUtil.FACADE_PACKAGE);
            for (String className : fileNameByPackageName) {
                Log.i(TAG, "init: className = [" + className + "]");
                if (className.startsWith(ModuleUtil.ADDRESS_OF_MODULEUNIT)) {
                    IModuleUnit moduleUnit = (IModuleUnit) Class.forName(className).newInstance();
                    moduleUnit.loadInto(group);
                }
            }

            Log.i(TAG, "init: group = " + group.toString());

            sortTemplate(group);

        } catch (PackageManager.NameNotFoundException | IOException
                | ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
    }

    private static void sortTemplate(List<ModuleMeta> group) {
        for (ModuleMeta meta : group) {
            List<ModuleMeta> metaList = null;
            if ((metaList = sortGroup.get(meta.template)) == null) {
                metaList = new ArrayList<>();
                sortGroup.put(meta.template, metaList);
            }

            // 分类对应template中，ModuleMeta的优先级，优先级高的放置到前面
            int index = 0;
            for (int i = 0; i < metaList.size(); i++) {
                if (isPriority(meta, metaList.get(i))) {
                    index = i;
                }
            }

            if (index == 0) {
                metaList.add(meta);
            } else {
                metaList.add(index, meta);
            }


        }
    }


    private static boolean isPriority(ModuleMeta meta, ModuleMeta moduleMeta) {
        int compare = meta.layoutLevel.compareTo(moduleMeta.layoutLevel);
        if (compare == 0) {
            return meta.extraLevel < moduleMeta.extraLevel;
        }

        return compare < 0;
    }


    public static List<ModuleMeta> getModuleList(String template) {
        if (sortGroup.containsKey(template)) {
            return sortGroup.get(template);
        }
        return null;
    }

}
