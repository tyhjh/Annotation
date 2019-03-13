package com.dhht.annotation.util;

import android.content.Context;

/**
 * @author HanPei
 * @date 2019/3/13  上午11:48
 */
public class ResourceUtil {

    /**
     * 获取资源文件的id
     *
     * @param context
     * @param resName
     * @return
     */
    public static int getId(Context context, String resName) {
        return context.getResources().getIdentifier(resName, "id", context.getPackageName());
    }

    /**
     * 获取资源文件中string的id
     *
     * @param context
     * @param resName
     * @return
     */
    public static int getStringId(Context context, String resName) {
        return context.getResources().getIdentifier(resName, "string", context.getPackageName());
    }


    /**
     * 获取资源文件drable的id
     *
     * @param context
     * @param resName
     * @return
     */
    public static int getDrableId(Context context, String resName) {
        return context.getResources().getIdentifier(resName, "drable", context.getPackageName());
    }


    /**
     * 获取资源文件layout的id
     *
     * @param context
     * @param resName
     * @return
     */
    public static int getLayoutId(Context context, String resName) {
        return context.getResources().getIdentifier(resName, "layout", context.getPackageName());
    }


    /**
     * 获取资源文件style的id
     *
     * @param context
     * @param resName
     * @return
     */
    public static int getStyleId(Context context, String resName) {
        return context.getResources().getIdentifier(resName, "style", context.getPackageName());
    }

    /**
     * 获取资源文件color的id
     *
     * @param context
     * @param resName
     * @return
     */
    public static int getColorId(Context context, String resName) {
        return context.getResources().getIdentifier(resName, "color", context.getPackageName());
    }

    /**
     * 获取资源文件dimen的id
     *
     * @param context
     * @param resName
     * @return
     */
    public static int getDimenId(Context context, String resName) {
        return context.getResources().getIdentifier(resName, "dimen", context.getPackageName());
    }

    /**
     * 获取资源文件ainm的id
     *
     * @param context
     * @param resName
     * @return
     */
    public static int getAnimId(Context context, String resName) {
        return context.getResources().getIdentifier(resName, "anim", context.getPackageName());
    }

    /**
     * 获取资源文件menu的id
     */
    public static int getMenuId(Context context, String resName) {
        return context.getResources().getIdentifier(resName, "menu", context.getPackageName());
    }

}
