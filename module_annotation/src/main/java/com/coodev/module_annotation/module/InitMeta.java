package com.coodev.module_annotation.module;

public class InitMeta {
    /**
     * 初始化所调用的类的路径
     */
    private String initPath;

    public InitMeta(String initPath) {
        this.initPath = initPath;
    }

    public String getInitPath() {
        return initPath;
    }
}
