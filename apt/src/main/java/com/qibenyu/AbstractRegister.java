package com.qibenyu;

/**
 * Created by lenovo on 2017/2/12.
 */

public interface AbstractRegister<T> {

    void init();

    void register(Object subscribe);

    void unregister(Object subscribe);
}
