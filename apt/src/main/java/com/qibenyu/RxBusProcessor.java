package com.qibenyu;


import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.MirroredTypeException;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

/**
 * Created by lenovo on 2017/1/27.
 */
@SupportedSourceVersion(SourceVersion.RELEASE_6)
public class RxBusProcessor extends AbstractProcessor {

    private Messager messager;
    private Elements elementUtils;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        messager = processingEnv.getMessager();
        elementUtils = processingEnv.getElementUtils();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new LinkedHashSet<>();
        types.add(OnReceivedRxEvent.class.getCanonicalName());
        return types;
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Map<String, Generator> proxyMap = getProxyMap(roundEnv);

        for (String key : proxyMap.keySet()) {
            Generator generator = proxyMap.get(key);
            writeCode(generator);
        }
        return true;
    }

    private Map<String, Generator> getProxyMap(RoundEnvironment roundEnv) {
        Map<String, Generator> proxyMap = new HashMap<>();
        for (Element element : roundEnv.getElementsAnnotatedWith(OnReceivedRxEvent.class)) {

            ExecutableElement executableElement = (ExecutableElement) element;
            TypeElement classElement = (TypeElement) element
                    .getEnclosingElement();

            PackageElement packageElement = elementUtils.getPackageOf(classElement);

            String fullClassName = classElement.getQualifiedName().toString();
            String className = classElement.getSimpleName().toString();
            String packageName = packageElement.getQualifiedName().toString();
            String methodName = executableElement.getSimpleName().toString();

            getMethodParameterTypes(executableElement);
            print("fullClassName: " + fullClassName +
                    ",  methodName: " + methodName +
                    ", className = " + className +
                    ", packageName = " + packageName +
                    ", value = " + getMethodParameterTypes(executableElement));

            EventMethod method = new EventMethod(methodName, getMethodParameterTypes(executableElement));

            Generator generator = proxyMap.get(fullClassName);
            if (generator != null) {
                generator.addMethod(method);
            } else {
                generator = new Generator(packageName, className);
                generator.setTypeElement(classElement);
                generator.addMethod(method);
                proxyMap.put(fullClassName, generator);
            }
        }
        return proxyMap;
    }

    /**
     * 取得方法参数类型列表
     */
    private String getMethodParameterTypes(ExecutableElement executableElement) {
        OnReceivedRxEvent event = executableElement.getAnnotation(OnReceivedRxEvent.class);

        TypeMirror value = null;
        if (event != null) {
            try {
                event.value();
            } catch (MirroredTypeException mte) {
                value = mte.getTypeMirror();
            }
        }
        return value.toString();
    }

    private void writeCode(Generator generator) {
        try {
            JavaFileObject jfo = processingEnv.getFiler().createSourceFile(
                    generator.getProxyClassFullName(),
                    generator.getTypeElement());
            Writer writer = jfo.openWriter();
            writer.write(generator.generateJavaCode());
            writer.flush();
            writer.close();
        } catch (IOException e) {
            error(generator.getTypeElement(),
                    "Unable to write injector for type %s: %s",
                    generator.getTypeElement(), e.getMessage());
        } catch (Exception e) {
            error(generator.getTypeElement(),
                    "Unable to write injector for type %s: %s",
                    generator.getTypeElement(), e.getMessage());
        }
    }

    private void print(String message) {
        messager.printMessage(Diagnostic.Kind.NOTE, message);
    }

    private void error(Element element, String message, Object... args) {

        if (args.length > 0) {
            message = String.format(message, args);
        }
        messager.printMessage(Diagnostic.Kind.ERROR, message, element);
    }
}
