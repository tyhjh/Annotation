package com.dhht.annotationlibrary;

public interface CatViewInject<T> {
    /**
     * 提供给生成的代码去绑定id用的
     *
     * @param t
     * @param source
     */
    void inject(T t, Object source);
}
