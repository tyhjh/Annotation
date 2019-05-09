package com.dhht.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author HanPei
 * @date 2019/3/12  下午5:34
 */
@Target(ElementType.FIELD)
@Inherited
@Retention(RetentionPolicy.CLASS)
public @interface ViewById {
    int value() default -1;
}
