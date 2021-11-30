package com.coodev.base.module;

public class ModuleFactory {
    public static AbsModule newModuleInstance(String moduleName) {
        if (moduleName == null || "".equals(moduleName)) {
            return null;
        }

        try {
            Class<? extends AbsModule> moduleClazz =
                    (Class<? extends AbsModule>) Class.forName(moduleName);
            if (moduleClazz != null) {
                return (AbsModule) moduleClazz.newInstance();
            }
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }

        return null;
    }
}
