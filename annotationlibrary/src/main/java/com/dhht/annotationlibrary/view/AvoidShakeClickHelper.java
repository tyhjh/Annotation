package com.dhht.annotationlibrary.view;

import android.view.View;

/**
 * @author HanPei
 * @date 2019/6/5  下午2:53
 */
public class AvoidShakeClickHelper implements View.OnClickListener {

    private long lastClickTime = 0;
    private long mClickIntervalTime = -1;
    AvoidShakeListener mAvoidShakeListener;


    public AvoidShakeClickHelper(long clickIntervalTime, AvoidShakeListener avoidShakeListener) {
        mAvoidShakeListener = avoidShakeListener;
        mClickIntervalTime = clickIntervalTime;
        if (mClickIntervalTime < 0) {
            mClickIntervalTime = AvoidShake.getClickIntervalTime();
        }
    }



    public AvoidShakeClickHelper(AvoidShakeListener avoidShakeListener) {
        mAvoidShakeListener = avoidShakeListener;
        if (mClickIntervalTime < 0) {
            mClickIntervalTime = AvoidShake.getClickIntervalTime();
        }
    }


    @Override
    public void onClick(View v) {
        if (System.currentTimeMillis() - lastClickTime > mClickIntervalTime) {
            if (mAvoidShakeListener != null) {
                mAvoidShakeListener.onClick(v);
            }
            lastClickTime = System.currentTimeMillis();
        }
    }


}
