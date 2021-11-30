package com.coodev.module_compiler.handle;

import com.coodev.module_annotation.annotation.ModuleUnit;
import com.coodev.module_annotation.module.ModuleMeta;
import com.coodev.module_compiler.process.AnnotationHandle;
import com.coodev.module_compiler.process.AnnotationProcessorContext;
import com.coodev.module_compiler.util.ModuleUtil;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.map.HashedMap;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;

public class ModuleUnitHandle implements AnnotationHandle {
    private final Map<String, ModuleMeta> mMetaMap = new HashedMap<>();
    private AnnotationProcessorContext env;

    @Override
    public void attachProcessorEvn(AnnotationProcessorContext env) {
        this.env = env;
    }

    @Override
    public void handleAction(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        if (CollectionUtils.isEmpty(set)) {
            return;
        }

        Set<? extends Element> elementsAnnotatedWith = roundEnvironment.getElementsAnnotatedWith(ModuleUnit.class);
        env.getLogger().info("## @Annotation " + ModuleUnit.class.getSimpleName() + " has found. Then start ");
        try {
            handle(elementsAnnotatedWith);
        } catch (IOException e) {
            env.getLogger().error(e);
        }
    }

    private void handle(Set<? extends Element> elementsAnnotatedWith) throws IOException {
        if (CollectionUtils.isEmpty(elementsAnnotatedWith)) {
            return;
        }

        env.getLogger().info(String.format(Locale.getDefault(), "## %s found , size is %d "
                , ModuleUnit.class.getSimpleName(), elementsAnnotatedWith.size()));
        // ModuleMeta
        ClassName moduleMetaClassName = ClassName.get(ModuleMeta.class);
        // Set<ModuleMeta>
        ParameterizedTypeName inputMapTypeOfGroup = ParameterizedTypeName.get(ClassName.get(List.class), ClassName.get(ModuleMeta.class));
        String metaSetParam = "metaSet";
        // group params
        ParameterSpec metaSet = ParameterSpec.builder(inputMapTypeOfGroup, metaSetParam).build();
        // loadInfo(List<ModuleMeta> metaSet)
        MethodSpec.Builder loadInfoMethodBuild = MethodSpec.methodBuilder(ModuleUtil.METHOD_LOAD_INTO)
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Override.class)
                .addParameter(metaSet)
                .returns(TypeName.VOID);

        for (Element element : elementsAnnotatedWith) {
            // info of annotation
            ModuleUnit moduleUnit = element.getAnnotation(ModuleUnit.class);
            ClassName name = ClassName.get((TypeElement) element);
            // class name
            String address = name.packageName() + "." + name.simpleName();
            env.getLogger().info("## address = " + address + " ");
            // moduleMeta info
            ModuleMeta moduleMeta = new ModuleMeta(moduleUnit, address);
            // add map
            mMetaMap.put(element.getSimpleName().toString(), moduleMeta);

            moduleMeta.title = ModuleUnit.TITLE_DEFAULT.equals(moduleMeta.title) ? name.simpleName() : moduleMeta.title;
            env.getLogger().info("meta = " + moduleMeta.toString());
            String[] templateArr = moduleMeta.template.split(",");
            for (String template : templateArr) {
                loadInfoMethodBuild.addStatement(
                        "$L.add(new $T($S,$S,$S,$L,$L))",
                        metaSetParam,
                        moduleMetaClassName,
                        template,
                        moduleMeta.moduleName,
                        moduleMeta.title,
                        moduleMeta.layoutLevel.ordinal(),
                        moduleMeta.extraLevel);
            }

            env.getLogger().info("## build moduleUnit , moduleMeta = " + moduleMeta.toString() + "");

            TypeSpec typeSpec = TypeSpec.classBuilder(ModuleUtil.NAME_OF_MODULEUNIT + name.simpleName())
                    .addJavadoc(ModuleUtil.WARNING_TIPS)
                    .addSuperinterface(ClassName.get(env.getElements().getTypeElement(ModuleUtil.IMODULE_UNIT)))
                    .addModifiers(Modifier.PUBLIC)
                    .addMethod(loadInfoMethodBuild.build())
                    .build();
            // create java file
            JavaFile
                    .builder(ModuleUtil.FACADE_PACKAGE, typeSpec)
                    .build()
                    .writeTo(env.getFiler());

        }

    }
}
