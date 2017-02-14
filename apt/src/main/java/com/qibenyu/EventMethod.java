package com.qibenyu;

/**
 * Created by qibenyu on 17-2-14.
 */

public class EventMethod {
    private String methodName;
    private String parameterType;

    public EventMethod(String methodName, String parameterType) {
        this.methodName = methodName;
        this.parameterType = parameterType;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getParameterType() {
        return parameterType;
    }

    public void setParameterType(String parameterType) {
        this.parameterType = parameterType;
    }
}
