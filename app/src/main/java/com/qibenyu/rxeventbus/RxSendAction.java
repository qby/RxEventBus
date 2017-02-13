package com.qibenyu.rxeventbus;

import android.util.Log;

import com.qibenyu.rxbus.RxBus;

import rx.Subscriber;

/**
 * Created by lenovo on 2017/1/31.
 */

public class RxSendAction {

    private static final String TAG = "RxSendAction";

    public void sendAccountChangeEvent() {
        RxBus.getInstance().post(new AccountEvent(AccountEvent.LOGIN));
    }

    public void sendOrderEvent() {
        RxBus.getInstance().post(new OrderEvent(OrderEvent.MAKEORDERACTION));
    }

    private void testregister() {
        RxBus.getInstance()
                .toObserverable(AccountEvent.class)
                .subscribe(new Subscriber<AccountEvent>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(AccountEvent event) {

                    }
                });
    }

    private void update() {

    }

    public void sayHello() {
        Log.d(TAG, "sayHello: hello");
    }
}
