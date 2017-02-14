package com.qibenyu;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.TypeElement;


public class ProxyInfo {
    private String packageName;
    private String targetClassName;
    private String proxyClassName;
    private TypeElement typeElement;

    private List<EventMethod> methods;

    public static final String PROXY = "Action";

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
        builder.append("// Generated code from RxEventBus. Do not modify!\n");
        builder.append("package ").append(packageName).append(";\n\n")
                .append("import com.qibenyu.AbstractRegister;\n")
                .append("import rx.subscriptions.CompositeSubscription;\n")
                .append("import rx.Subscriber;\n")
                .append("import com.qibenyu.rxbus.RxBus;\n")
                .append("public class ").append(proxyClassName)
                .append(" implements AbstractRegister")
                .append(" {\n")
                .append("   private CompositeSubscription mSubscriptions;\n");


        generateInit(builder);
        generateRegisterMethod(builder);
        generateUnregisterMethod(builder);
        builder.append('\n');

        builder.append("}\n");
        return builder.toString();

    }

    private void generateInit(StringBuilder builder) {
        builder.append("    @Override \n")
                .append("   public void init()").append(" {mSubscriptions = new CompositeSubscription();}\n");
    }

    private void generateRegisterMethod(StringBuilder builder) {
        builder.append("    @Override \n")
                .append("   public void register(final Object obj)").append(" {\n")
                .append("");
        for (EventMethod method : getMethods()) {
            builder.append("       RxBus.getInstance()\n")
                    .append("           .toObserverable(").append(method.getParameterType()).append(".class)\n")
                    .append("           .subscribe(new Subscriber<"+ method.getParameterType() +">() {\n")
                    .append("               @Override\n")
                    .append("               public void onCompleted() {}\n")
                    .append("               @Override\n")
                    .append("               public void onError(Throwable throwable) {}\n")
                    .append("               @Override\n")
                    .append("               public void onNext("+ method.getParameterType() +" event) {\n")
                    .append("                   ((" + targetClassName + ")obj)." + method.getMethodName()+"(event);\n")
                    .append("               }\n")
                    .append("      });\n");
        }
        builder.append("   }\n");
    }

    private void generateUnregisterMethod(StringBuilder builder) {
        builder.append("    @Override \n")
                .append("   public void unregister(Object object)").append(" {\n")
                .append("      if(mSubscriptions != null) {\n")
                .append("          mSubscriptions.clear();\n")
                .append("      }\n")
                .append("   }\n");
    }

    TypeElement getTypeElement() {
        return typeElement;
    }

    void setTypeElement(TypeElement typeElement) {
        this.typeElement = typeElement;
    }

    void addMethod(EventMethod method) {
        if (methods == null) {
            methods = new ArrayList<>();
        }
        methods.add(method);
    }

    List<EventMethod> getMethods() {
        return methods == null ? new ArrayList<EventMethod>() : methods;
    }
}
