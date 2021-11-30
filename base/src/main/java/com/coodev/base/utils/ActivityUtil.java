package com.coodev.base.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

public class ActivityUtil {
    /**
     * activity跳转
     *
     * @param context context
     * @param intent  跳转Activity的intent
     */
    public static void startActivity(Context context, Intent intent) {
        if (context != null && intent != null && intent.resolveActivity(context.getPackageManager()) != null) {
            if (!(context instanceof Activity)) {
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }
            context.startActivity(intent);
            return;
        }

        throw new IllegalArgumentException("params may be null");

    }

    /**
     * 获取选择器，主要用于多应用相应时候弹出系统界面的选择弹窗
     *
     * @param intent       intent
     * @param chooserTitle 选择器标题
     * @return Intent
     */
    public static Intent getChooserIntent(Intent intent, String chooserTitle) {
        return Intent.createChooser(intent, chooserTitle);
    }
}
