package com.coodev.base.bus;

/**
 * IMC 组件间通讯工具集
 */
public class InterModuleCommunication {
    interface IMC<K,V> {
        void register(K t);

        void unRegister(K t);

        void notify(V v);
    }

}
