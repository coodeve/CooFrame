package com.coodev.base.utils;

import android.os.Handler;
import android.os.Looper;
import android.os.MessageQueue;

public class HandlerUtil {
    private static Handler sHandler;

    public static Handler getMainHandler() {
        if (sHandler == null) {
            sHandler = new Handler(Looper.getMainLooper());
        }
        return sHandler;
    }

    public static Looper getMainLooper() {
        return Looper.getMainLooper();
    }

    public static void addIdleHandler(MessageQueue.IdleHandler idleHandler) {
        getMainLooper().getQueue().addIdleHandler(idleHandler);
    }
}
