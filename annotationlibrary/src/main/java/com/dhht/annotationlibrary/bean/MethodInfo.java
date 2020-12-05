package com.dhht.annotationlibrary.bean;

/**
 * 方法信息
 */
public class MethodInfo {

    /**
     * 类名
     */
    private String className;

    /**
     * 方法名
     */
    private String methodName;

    /**
     * 线程名
     */
    private String threadName;


    public MethodInfo(String className, String methodName, String threadName) {
        this.className = className;
        this.methodName = methodName;
        this.threadName = threadName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getThreadName() {
        return threadName;
    }

    public void setThreadName(String threadName) {
        this.threadName = threadName;
    }


    @Override
    public String toString() {
        return "MethodInfo{" +
                "className='" + className + '\'' +
                ", methodName='" + methodName + '\'' +
                ", threadName='" + threadName + '\'' +
                '}';
    }
}
