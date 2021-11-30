package com.coodev.module_compiler.process;

import com.coodev.module_annotation.annotation.ModuleInit;
import com.coodev.module_annotation.annotation.ModuleUnit;
import com.coodev.module_compiler.handle.ModuleInitHandle;
import com.coodev.module_compiler.handle.ModuleUnitHandle;
import com.google.auto.service.AutoService;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;

@AutoService(Processor.class)
public class ModuleProcessor extends AbstractProcessor {

    private AnnotationProcessorContext mProcessorContext;

    private final List<AnnotationHandle> mAnnotationHandles = new ArrayList<>();

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        mProcessorContext = new AnnotationProcessorContext(processingEnvironment);
        mAnnotationHandles.addAll(assembleHandles());
        mProcessorContext.getLogger().info(ModuleProcessor.class.getSimpleName() + " Init.");
    }

    private Collection<? extends AnnotationHandle> assembleHandles() {
        return Lists.newArrayList(new ModuleUnitHandle(), new ModuleInitHandle());
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        LinkedHashSet<String> supportAnnotationTypes = Sets.newLinkedHashSet();
        supportAnnotationTypes.add(ModuleUnit.class.getCanonicalName());
        supportAnnotationTypes.add(ModuleInit.class.getCanonicalName());
        return supportAnnotationTypes;
    }

    @Override
    public Set<String> getSupportedOptions() {
        return super.getSupportedOptions();
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        for (AnnotationHandle annotationHandle : mAnnotationHandles) {
            annotationHandle.attachProcessorEvn(mProcessorContext);
            annotationHandle.handleAction(set, roundEnvironment);
        }
        return false;
    }
}
