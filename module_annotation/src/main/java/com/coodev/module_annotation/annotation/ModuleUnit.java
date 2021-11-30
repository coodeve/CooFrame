package com.coodev.module_annotation.annotation;

import com.coodev.module_annotation.enums.LayoutLevel;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface ModuleUnit {
    String TITLE_DEFAULT = "Unknown";
    String TEMPLATE_DEFAULT = "normal";

    String template() default TEMPLATE_DEFAULT;

    String title() default TITLE_DEFAULT;// 业务名称

    LayoutLevel layoutLevel() default LayoutLevel.NORMAL;// 层级

    int extraLevel() default 0;// 层级内排序

}
