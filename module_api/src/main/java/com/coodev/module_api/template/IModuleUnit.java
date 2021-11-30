package com.coodev.module_api.template;

import com.coodev.module_annotation.module.ModuleMeta;

import java.util.List;

public interface IModuleUnit {
    void loadInto(List<ModuleMeta> metaSet);
}