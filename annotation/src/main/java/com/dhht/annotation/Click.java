package com.dhht.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author HanPei
 * @date 2019/3/27  上午11:49
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.CLASS)
public @interface Click {

    /**
     * 控件ID，默认为变量名
     *
     * @return
     */
    int value() default -1;

    /**
     * 点击间隔
     *
     * @return
     */
    int interval() default -1;
}
