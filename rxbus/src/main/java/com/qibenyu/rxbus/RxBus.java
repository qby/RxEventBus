package com.qibenyu.rxbus;


import com.qibenyu.AbstractRegister;
import com.qibenyu.Event;

import java.util.LinkedHashMap;
import java.util.Map;

import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

/**
 * Created by lenovo on 2017/1/27.
 */

public class RxBus {

    private final Subject<Object, Object> _bus;

    private static class RxBusHolder {
        private static final RxBus instance = new RxBus();
    }

    private RxBus() {
        _bus = new SerializedSubject<>(PublishSubject.create());
    }

    public static synchronized RxBus getInstance() {
        return RxBusHolder.instance;
    }

    public void post(Object o) {
        _bus.onNext(o);
    }


    public <T> Observable<T> toObserverable(Class<T> eventType) {
        return _bus.ofType(eventType);
    }

    public void register(Object subscribe) {
        AbstractRegister<Object> register = findRegister(subscribe);
        register.register(subscribe);
    }

    public void unregister(Object subscribe) {
        AbstractRegister<Object> register = findRegister(subscribe);
        register.unregister(subscribe);
    }

    private static final Map<Class<?>, AbstractRegister<Object>> REGISTERS = new LinkedHashMap<Class<?>, AbstractRegister<Object>>();

    private static AbstractRegister<Object> findRegister(Object activity) {
        Class<?> clazz = activity.getClass();
        AbstractRegister<Object> injector = REGISTERS.get(clazz);
        if (injector == null) {
            try {
                Class injectorClazz = Class.forName(clazz.getName() + "$$"
                        + "Action");
                injector = (AbstractRegister<Object>) injectorClazz
                        .newInstance();
                injector.init();
                REGISTERS.put(clazz, injector);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return injector;
    }
}
