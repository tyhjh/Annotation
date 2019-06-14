package com.dhht.annotationlibrary.view;

import android.view.View;

import com.dhht.annotation.Background;
import com.dhht.annotation.RefreshView;
import com.dhht.annotation.UiThread;
import com.dhht.annotationlibrary.R;

/**
 * @author HanPei
 * @date 2019/5/30  下午7:34
 */
public class AvoidShake {

    /**
     * 默认点击间隔时间
     */
    private static int sClickIntervalTime = 300;

    /**
     * 方法已不推荐使用，改方法使用线程切换的方式会增加CPU开销
     *
     * @param view
     * @param clickIntervalTime 时间单位为毫秒
     */
    @Background
    @Deprecated
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
