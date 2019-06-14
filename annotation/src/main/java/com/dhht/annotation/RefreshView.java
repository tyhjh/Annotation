package com.dhht.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author HanPei
 * @date 2019/6/14  下午3:23
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.CLASS)
public @interface RefreshView {
    /**
     * SwipeRefreshLayout控件ID，默认为变量名
     *
     * @return
     */
    int value() default -1;


    /**
     * 颜色设置
     *
     * @return
     */
    int[] colors() default {};
}
