package com.dhht.annotationlibrary.aspect;


import android.os.SystemClock;

import com.dhht.annotation.Background;
import com.dhht.annotation.CustomAnnotation;
import com.dhht.annotation.ExecuteTime;
import com.dhht.annotation.UiThread;
import com.dhht.annotationlibrary.bean.MethodInfo;
import com.dhht.annotationlibrary.utils.ExecuteManager;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


/**
 * @author HanPei
 * @date 2019/4/16  上午9:07
 */
@Aspect
public class ThreadsAspect {

    @Around("execution(@com.dhht.annotation.Background void *(..))")
    public void doBackground(final ProceedingJoinPoint joinPoint) {
        final Background background = getMethodAnnotation(joinPoint, Background.class);
        Observable.timer(background.delay(), TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        try {
                            joinPoint.proceed();
                        } catch (Throwable throwable) {
                            throwable.printStackTrace();
                        }
                    }
                });
    }


    @Around("execution(@com.dhht.annotation.UiThread void *(..))")
    public void doUiThread(final ProceedingJoinPoint joinPoint) {
        UiThread uiThread = getMethodAnnotation(joinPoint, UiThread.class);
        Observable.timer(uiThread.delay(), TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        try {
                            joinPoint.proceed();
                        } catch (Throwable throwable) {
                            throwable.printStackTrace();
                        }
                    }
                });
    }


    @Around("execution(@com.dhht.annotation.ExecuteTime * *(..))")
    public void executeTime(final ProceedingJoinPoint joinPoint) {
        ExecuteTime executeTimeAnnotation = getMethodAnnotation(joinPoint, ExecuteTime.class);
        try {
            long time = SystemClock.uptimeMillis();
            joinPoint.proceed();
            long executeTime = SystemClock.uptimeMillis() - time;
            //获取方法
            MethodInfo methodInfo = getMethodInfo(joinPoint);
            //返回方法执行时间
            ExecuteManager.getInstance().executeTimeBack(executeTime, executeTimeAnnotation, methodInfo);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    @Around("execution(@com.dhht.annotation.CustomAnnotation * *(..))")
    public void customAnnotation(final ProceedingJoinPoint joinPoint) {
        CustomAnnotation executeTimeAnnotation = getMethodAnnotation(joinPoint, CustomAnnotation.class);
        try {
            //获取方法
            MethodInfo methodInfo = getMethodInfo(joinPoint);
            //返回方法执行开始前
            ExecuteManager.getInstance().executeBefore(executeTimeAnnotation, methodInfo);
            joinPoint.proceed();
            //返回方法执行完成
            ExecuteManager.getInstance().executeAfter(executeTimeAnnotation, methodInfo);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }


    private <T extends Annotation> T getMethodAnnotation(ProceedingJoinPoint joinPoint, Class<T> clazz) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        return method.getAnnotation(clazz);
    }


    /**
     * 获取方法信息
     *
     * @param joinPoint
     */
    private MethodInfo getMethodInfo(ProceedingJoinPoint joinPoint) {
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();
        String threadName = Thread.currentThread().getName();
        return new MethodInfo(className, methodName, threadName);
    }


}
