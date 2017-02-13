package com.qibenyu.rxeventbus;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by lenovo on 2017/2/1.
 */

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
    }
}
