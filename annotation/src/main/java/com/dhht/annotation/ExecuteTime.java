package com.dhht.annotation;

/**
 * 方法运行时间监听类
 */
public @interface ExecuteTime {
    /**
     * 设置运行的时间，如果超过该时间
     *
     * @return
     */
    int expectTime() default -1;

    /**
     * 方法标志
     *
     * @return
     */
    String tag() default "";
}
