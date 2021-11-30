package com.coodev.module_compiler.process;

import java.util.Set;

import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.TypeElement;

public interface AnnotationHandle {
    void attachProcessorEvn(AnnotationProcessorContext env);

    void handleAction(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment);
}
