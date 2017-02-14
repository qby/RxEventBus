package com.qibenyu.rxeventbus;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.qibenyu.OnReceivedRxEvent;

/**
 * Created by qibenyu on 17-2-14.
 */

public class SecondActivity extends AppCompatActivity {

    private static final String TAG = "SecondActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }


    @OnReceivedRxEvent(OrderEvent.class)
    public void onOrderEvent(OrderEvent event) {
        Log.d(TAG, "onOrderEvent: ");
    }
}
