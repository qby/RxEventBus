package com.qibenyu;

/**
 * Created by lenovo on 2017/2/12.
 */

public interface AbstractRegister<T> {

    void register(Object object);

    void unregister(Event event);
}
