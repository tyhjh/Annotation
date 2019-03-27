package com.dhht.annotationlibrary;

public interface ViewInject<T> {
    /**
     * 提供给生成的代码去绑定id用的
     *
     * @param t
     * @param source
     */
    void inject(T t, Object source);
}
