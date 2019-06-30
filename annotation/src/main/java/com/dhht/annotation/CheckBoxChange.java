package com.dhht.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Tyhj
 * @date 2019/6/30
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.CLASS)
public @interface CheckBoxChange {
    /**
     * 控件ID，默认为变量名
     *
     * @return
     */
    int value() default -1;
}
