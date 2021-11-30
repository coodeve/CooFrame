package com.coodev.base.bus;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;


/**
 * 使用{@link LocalBroadcastManager}作为组件间通讯工具
 * 使用{@link BroadcastReceiver} 接收通知
 * 优点：可靠，效率高
 * 缺点：中途不可控，无法干预
 */
public class IMCLocalBroadcast implements InterModuleCommunication.IMC<IMCLocalBroadcast.Receiver, Intent> {

    public static class Receiver {
        BroadcastReceiver mReceiver;
        IntentFilter mIntentFilter;
    }

    private final LocalBroadcastManager mInstance;
    private final Context mContext;

    public IMCLocalBroadcast(Context context) {
        mContext = context;
        mInstance = LocalBroadcastManager.getInstance(mContext);
    }


    @Override
    public void register(Receiver receiver) {
        mInstance.registerReceiver(receiver.mReceiver, receiver.mIntentFilter);
    }

    @Override
    public void unRegister(Receiver receiver) {
        mInstance.unregisterReceiver(receiver.mReceiver);
    }

    @Override
    public void notify(Intent intent) {
        mInstance.sendBroadcast(intent);
    }

}