package com.qibenyu.rxeventbus;

import com.qibenyu.rxbus.RxBus;

import org.greenrobot.eventbus.EventBus;


public class OrderEvent {

    public final static String MAKEORDERACTION = "order";
    public final static String UNSUBSCRIBE = "unsubscribe";

    private String orderAction;

    public String getOrderAction() {
        return orderAction;
    }

    public OrderEvent(String orderAction) {
        this.orderAction = orderAction;

        EventBus.getDefault().post(new AccountEvent(AccountEvent.LOGIN));
        RxBus.getInstance().post(new AccountEvent(AccountEvent.LOGIN));
    }
}
