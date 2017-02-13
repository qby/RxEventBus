package com.qibenyu;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.TypeElement;


public class ProxyInfo {
    private String packageName;
    private String targetClassName;
    private String proxyClassName;
    private TypeElement typeElement;

//    private List<OnceMethod> methods;

    public static final String PROXY = "PROXY";

    ProxyInfo(String packageName, String className) {
        this.packageName = packageName;
        this.targetClassName = className;
        this.proxyClassName = className + "$$" + PROXY;
    }

    String getProxyClassFullName() {
        return packageName + "." + proxyClassName;
    }

    String generateJavaCode() {

        StringBuilder builder = new StringBuilder();
        builder.append("// Generated code from OnceClick. Do not modify!\n");
        builder.append("package ").append(packageName).append(";\n\n");

        builder.append("import android.view.View;\n");
        builder.append("import com.lizhaoxuan.onceclick.Finder;\n");
        builder.append("import com.lizhaoxuan.onceclick.AbstractInjector;\n");
        builder.append('\n');

        builder.append("public class ").append(proxyClassName);
        builder.append("<T extends ").append(getTargetClassName()).append(">");
        builder.append(" implements AbstractInjector<T>");
        builder.append(" {\n");

        generateInjectMethod(builder);
        builder.append('\n');

        builder.append("}\n");
        return builder.toString();

    }

    private String getTargetClassName() {
        return targetClassName.replace("$", ".");
    }

    private void generateInjectMethod(StringBuilder builder) {

        builder.append("public long intervalTime; \n");

        builder.append("  @Override ")
                .append("public void setIntervalTime(long time) {\n")
                .append("intervalTime = time;\n     } \n");
        builder.append("  @Override ")
                .append("public void inject(final Finder finder, final T target, Object source) {\n");
        builder.append("View view;");

    }

    TypeElement getTypeElement() {
        return typeElement;
    }

    void setTypeElement(TypeElement typeElement) {
        this.typeElement = typeElement;
    }

}
