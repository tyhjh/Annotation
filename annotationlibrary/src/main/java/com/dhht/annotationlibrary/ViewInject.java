package com.dhht.annotationlibrary;

/**
 *
 * @author zhy
 * @date 16/4/22
 */
public interface ViewInject<T> {
    void inject(T t, Object source);
}
