package com.coodev.module_compiler.handle;

import com.coodev.module_annotation.annotation.ModuleInit;
import com.coodev.module_annotation.module.InitMeta;
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

import java.io.IOException;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;


public class ModuleInitHandle implements AnnotationHandle {
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

        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(ModuleInit.class);
        env.getLogger().info("## @Annotation " + ModuleInit.class.getSimpleName() + " has found,Then start.");
        try {
            handle(elements);
        } catch (IOException e) {
            e.printStackTrace();
            env.getLogger().error(e);
        }

    }

    /**
     * 构建源代码：
     * <pre>
     *  public class Init$$name implements Init{
     *      @Override
     *      public void loadInto(List<InitMeta> initMetaList){
     *      initMetaList.add(new InitMeta(class path));
     *      }
     *  }
     * </pre>
     *
     * @param elements elements
     */
    private void handle(Set<? extends Element> elements) throws IOException {
        if (CollectionUtils.isEmpty(elements)) {
            return;
        }

        env.getLogger().info("## %s is found , size is %d", ModuleInit.class.getSimpleName(), elements.size());

        ClassName initMetaClazz = ClassName.get(InitMeta.class);
        ParameterizedTypeName initMetaListType = ParameterizedTypeName.get(ClassName.get(List.class), initMetaClazz);
        String varName = "initMetaList";
        ParameterSpec initMetaListParams = ParameterSpec.builder(initMetaListType, varName).build();
        MethodSpec.Builder loadInfoMethod = MethodSpec.methodBuilder(ModuleUtil.METHOD_LOAD_INTO)
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Override.class)
                .addParameter(initMetaListParams)
                .returns(TypeName.VOID);

        for (Element element : elements) {
            ClassName name = ClassName.get((TypeElement) element);
            String className = name.packageName() + "." + name.simpleName();
            env.getLogger().info("## class = [%s]", className);
            // write method body
            loadInfoMethod.addStatement(
                    "$L.add(new $T($S))",
                    varName,
                    initMetaClazz,
                    className
            );

            env.getLogger().info("## build methodInit");
            TypeSpec typeSpec = TypeSpec.classBuilder(ModuleUtil.NAME_OF_INIT + name.simpleName())
                    .addModifiers(Modifier.PUBLIC)
                    .addJavadoc(ModuleUtil.WARNING_TIPS)
                    .addSuperinterface(ClassName.get(env.getElements().getTypeElement(ModuleUtil.IINIT_UNIT)))
                    .addMethod(loadInfoMethod.build())
                    .build();

            JavaFile.builder(ModuleUtil.FACADE_PACKAGE, typeSpec)
                    .build()
                    .writeTo(env.getFiler());
        }

    }
}
