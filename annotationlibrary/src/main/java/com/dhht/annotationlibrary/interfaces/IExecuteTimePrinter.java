package com.dhht.annotationlibrary.interfaces;

import com.dhht.annotation.ExecuteTime;
import com.dhht.annotationlibrary.bean.MethodInfo;

/**
 * 打印类
 *
 * @author HanPei
 * @date 2019/5/9  下午4:37
 */
public interface IExecuteTimePrinter {

    /**
     * 方法执行完毕
     *
     * @param executeTime 实际使用时间
     * @param expectTime  预期使用时间
     * @param methodInfo  方法相关信息
     */
    void executeBack(long executeTime, ExecuteTime expectTime, MethodInfo methodInfo);

}
