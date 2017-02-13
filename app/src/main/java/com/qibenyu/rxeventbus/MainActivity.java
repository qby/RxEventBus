package com.qibenyu.rxeventbus;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.qibenyu.OnReceivedRxEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @OnReceivedRxEvent(AccountEvent.class)
    private void accountChange(AccountEvent event) {
        Log.d(TAG, "update: accountchange");
    }

    @OnReceivedRxEvent(OrderEvent.class)
    private void updateOrder(OrderEvent event) {
        Log.d(TAG, "updateOrder: orderupdate");
    }

    @Subscribe
    public void onEventMainThread(AccountEvent event) {
        Log.d(TAG, "onEventMainThread: event" + event.getAction());
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }
}
