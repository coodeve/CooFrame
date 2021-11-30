package com.coodev.module_annotation.module;

import com.coodev.module_annotation.annotation.ModuleUnit;
import com.coodev.module_annotation.enums.LayoutLevel;

public class ModuleMeta {
    public String template;
    public String moduleName;
    public String title;
    public LayoutLevel layoutLevel;
    public int extraLevel;

    public ModuleMeta(String template, String moduleName, String title, int layoutLevel, int extraLevel) {
        this.template = template;
        this.moduleName = moduleName;
        this.title = title;
        setLayoutLevel(layoutLevel);
        this.extraLevel = extraLevel;
    }

    /**
     * for annotation processor
     *
     * @param moduleUnit see {@link ModuleUnit}
     * @param moduleName name of module
     */
    public ModuleMeta(ModuleUnit moduleUnit, String moduleName) {
        this.moduleName = moduleName;
        this.template = moduleUnit.template();
        this.title = moduleUnit.title();
        this.layoutLevel = moduleUnit.layoutLevel();
        this.extraLevel = moduleUnit.extraLevel();
    }

    private void setLayoutLevel(int layoutLevelParams) {
        for (LayoutLevel level : LayoutLevel.values()) {
            if (layoutLevelParams == level.ordinal()) {
                layoutLevel = level;
                break;
            }
        }
    }

    @Override
    public String toString() {
        return "ModuleMeta{" +
                "template='" + template + '\'' +
                ", moduleName='" + moduleName + '\'' +
                ", title='" + title + '\'' +
                ", layoutLevel=" + layoutLevel +
                ", extraLevel=" + extraLevel +
                '}';
    }
}
