package com.dhht.annotationlibrary.interfaces;

import com.dhht.annotation.CustomAnnotation;
import com.dhht.annotationlibrary.bean.MethodInfo;

/**
 * 方法运行监听
 */
public interface IExecuteListener {

    /**
     * 运行前
     */
    void before(CustomAnnotation annotation, MethodInfo methodInfo);

    /**
     * 运行后
     */
    void after(CustomAnnotation annotation, MethodInfo methodInfo);

}
