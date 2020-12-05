package com.dhht.annotationlibrary.utils;

import com.dhht.annotation.CustomAnnotation;
import com.dhht.annotation.ExecuteTime;
import com.dhht.annotationlibrary.bean.MethodInfo;
import com.dhht.annotationlibrary.interfaces.IExecuteListener;
import com.dhht.annotationlibrary.interfaces.IExecuteTimePrinter;

import java.util.HashSet;

/**
 * @author HanPei
 * @date 2019/4/16  上午9:07
 */
public class ExecuteManager {

    /**
     * 方法监听类集合
     */
    private HashSet<IExecuteListener> executeListeners = new HashSet<>();

    /**
     * 方法执行时间监听类
     */
    private IExecuteTimePrinter printer;


    /**
     * 方法运行时间返回
     *
     * @param time
     * @param executeTime
     * @param methodInfo
     */
    public void executeTimeBack(long time, ExecuteTime executeTime, MethodInfo methodInfo) {
        if (printer != null) {
            printer.executeBack(time, executeTime, methodInfo);
        }
    }


    /**
     * 方法执行前
     *
     * @param methodInfo
     * @param annotation
     */
    public void executeBefore(CustomAnnotation annotation, MethodInfo methodInfo) {
        for (IExecuteListener listener:executeListeners){
            listener.before(annotation,methodInfo);
        }
    }

    /**
     * 方法执行后
     *
     * @param methodInfo
     * @param annotation
     */
    public void executeAfter(CustomAnnotation annotation, MethodInfo methodInfo) {
        for (IExecuteListener listener:executeListeners){
            listener.after(annotation,methodInfo);
        }
    }


    /**
     * 添加方法执行监听类
     *
     * @param listener
     */
    public void addExecuteListener(IExecuteListener listener) {
        if (listener != null) {
            executeListeners.add(listener);
        }
    }

    /**
     * 移除方法执行监听类
     *
     * @param listener
     */
    public void removeExecuteListener(IExecuteListener listener) {
        if (listener != null) {
            executeListeners.remove(listener);
        }
    }

    /**
     * 移除所有监听类
     */
    public void removeAllExecuteListener() {
        executeListeners.clear();
    }


    public static ExecuteManager getInstance() {
        return Holder.executeManager;
    }


    private ExecuteManager() {
    }

    private static final class Holder {
        private static ExecuteManager executeManager = new ExecuteManager();
    }


    public IExecuteTimePrinter getPrinter() {
        return printer;
    }

    public void setPrinter(IExecuteTimePrinter printer) {
        this.printer = printer;
    }

}
