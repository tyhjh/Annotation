package com.dhht.annotationlibrary.aspect;


import com.dhht.annotation.Background;
import com.dhht.annotation.UiThread;

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


    private <T extends Annotation> T getMethodAnnotation(ProceedingJoinPoint joinPoint, Class<T> clazz) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        return method.getAnnotation(clazz);
    }

}
