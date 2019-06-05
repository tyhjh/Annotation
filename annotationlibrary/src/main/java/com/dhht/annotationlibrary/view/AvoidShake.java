package com.dhht.annotationlibrary.view;

import android.view.View;

import com.dhht.annotation.Background;
import com.dhht.annotation.UiThread;

/**
 * @author HanPei
 * @date 2019/5/30  下午7:34
 */
public class AvoidShake {


    /**
     * 默认点击间隔时间
     */
    private static int sClickIntervalTime = 200;

    /**
     * 设置防抖动
     *
     * @param view
     * @param clickIntervalTime 时间单位为毫秒
     */
    @Background
    public static void avoidViewShake(View view, int clickIntervalTime) {
        try {
            if (view != null) {
                view.setClickable(false);
            }
            if (clickIntervalTime < 0) {
                Thread.sleep(sClickIntervalTime);
            } else {
                Thread.sleep(clickIntervalTime);
            }
            recoverView(view);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @UiThread
    private static void recoverView(View view) {
        System.out.println("设置防抖动UiThread：" + Thread.currentThread().getName());
        if (view != null) {
            view.setClickable(true);
        }
    }


    public static int getClickIntervalTime() {
        return sClickIntervalTime;
    }

    /**
     * 默认点击间隔时间
     *
     * @param clickIntervalTime
     */
    public static void setClickIntervalTime(int clickIntervalTime) {
        sClickIntervalTime = clickIntervalTime;
    }
}
