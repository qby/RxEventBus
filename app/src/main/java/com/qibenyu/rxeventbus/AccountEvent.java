package com.qibenyu.rxeventbus;

/**
 * Created by lenovo on 2017/2/1.
 */

public class AccountEvent {

    public final static String LOGIN = "login";
    public final static String LOGOUT = "logout";

    private String action;
//    private String testAction;

    public AccountEvent(String action) {
        this.action = action;
    }

    public String getAction() {
        return action;
    }
}
