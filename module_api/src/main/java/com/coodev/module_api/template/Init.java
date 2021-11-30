package com.coodev.module_api.template;

import com.coodev.module_annotation.module.InitMeta;

import java.util.List;

public interface Init {
    void loadInto(List<InitMeta> initMetaList);
}
