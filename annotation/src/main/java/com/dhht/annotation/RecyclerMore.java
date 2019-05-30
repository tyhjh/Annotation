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
    /**
     * 控件ID
     * 默认为方法名
     *
     * @return
     */
    int value() default -1;

    /**
     * 每页加载数量，当前item数量少于pageSize不触发方法
     *
     * @return
     */
    int pageSize() default -1;
}
