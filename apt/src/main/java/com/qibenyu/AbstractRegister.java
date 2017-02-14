package com.qibenyu;


public interface AbstractRegister<T> {

    void init();

    void register(Object subscribe);

    void unregister(Object subscribe);
}
