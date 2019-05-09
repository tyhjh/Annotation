package com.dhht.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * recycleView加载更多
 *
 * @author HanPei
 * @date 2019/5/9  下午4:37
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.CLASS)
public @interface RecyclerMore {
    int value() default -1;
}
