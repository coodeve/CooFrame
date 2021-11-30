package com.coodev.base.bus;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * 使用{@link EventBus}作为组件间通讯工具
 * <p>
 * 使用如下方式接收通知
 *
 * @Subscribe(threadMode = ThreadMode.MAIN)
 * public void onEvent(Object object) {
 * <p>
 * }
 *
 * 优点：可以动态设置事件处理线程和优先级
 * 缺点：事件类型增多，会导致事件类太多，增加维护成本，且跳转间无直接关联，不易阅读和查找响应事件的位置
 *
 */
public class IMCEventBus implements InterModuleCommunication.IMC<Object, Object> {

    public IMCEventBus() {
    }

    @Override
    public void register(Object t) {
        EventBus.getDefault().register(t);
    }

    @Override
    public void unRegister(Object t) {
        EventBus.getDefault().unregister(t);
    }

    @Override
    public void notify(Object object) {
        EventBus.getDefault().post(object);
    }

    /**
     * 监听
     * @param info 数据
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Object info) {

    }
}