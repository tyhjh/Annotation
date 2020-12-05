package com.dhht.annotation;

/**
 * 自定义注解
 */
public @interface CustomAnnotation {

    /**
     * 方法标记
     *
     * @return
     */
    String tag() default "";

    /**
     * 参数1
     *
     * @return
     */
    int var1() default 0;

    /**
     * 参数2
     *
     * @return
     */
    String var2() default "";

    /**
     * 参数3
     *
     * @return
     */
    boolean var3() default false;
}
