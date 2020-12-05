package com.dhht.annotationlibrary;

import android.app.Activity;
import android.view.View;

import com.dhht.annotationlibrary.view.AvoidShake;


/**
 * @author tyhj
 */
public class CatAnnotation {
    private static final String SUFFIX = "$$CatViewInject";


    public static void injectView(Activity activity) {
        CatViewInject proxyActivity = findProxyActivity(activity);
        if (proxyActivity == null) {
            return;
        }
        proxyActivity.inject(activity, activity);
    }



    /**
     * 默认点击间隔时间
     *
     * @param clickIntervalTime
     */
    public static void setClickIntervalTime(int clickIntervalTime) {
        AvoidShake.setClickIntervalTime(clickIntervalTime);
    }


    public static void injectView(Object object, View view) {
        CatViewInject proxyActivity = findProxyActivity(object);
        if (proxyActivity == null) {
            return;
        }
        proxyActivity.inject(object, view);
    }

    /**
     * 根据使用注解的类和约定的命名规则，反射获取注解生成的类
     *
     * @param object
     * @return
     */
    private static CatViewInject findProxyActivity(Object object) {
        try {
            Class clazz = object.getClass();
            Class injectorClazz = Class.forName(clazz.getName() + SUFFIX);
            return (CatViewInject) injectorClazz.newInstance();
        } catch (Exception e) {
            return null;
        }

    }
}
