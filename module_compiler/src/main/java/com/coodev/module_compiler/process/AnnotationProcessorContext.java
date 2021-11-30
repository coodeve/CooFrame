package com.coodev.module_compiler.process;

import com.coodev.module_compiler.util.Logger;

import java.util.Map;

import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

public class AnnotationProcessorContext {
    private final Elements mElements;
    private final Filer mFiler;
    private final Messager mMessage;
    private Map<String, String> mOptions;
    private Types mTypeUtils;
    private Logger mLogger;

    public AnnotationProcessorContext(ProcessingEnvironment processingEnvironment) {
        mElements = processingEnvironment.getElementUtils();
        mFiler = processingEnvironment.getFiler();
        mTypeUtils = processingEnvironment.getTypeUtils();
        mMessage = processingEnvironment.getMessager();
        mOptions = processingEnvironment.getOptions();
    }

    public Filer getFiler() {
        return mFiler;
    }

    public Elements getElements() {
        return mElements;
    }

    public Logger getLogger() {
        if (mLogger == null) {
            mLogger = new Logger(mMessage);
        }
        return mLogger;
    }
}
