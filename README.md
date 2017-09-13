# RxEventBus

### 使用编译时注解替代RxBus注册过程

- @OnReceivedRxEvent注解为事件监听回调



```
public abstract class EventListener<T> {

    private static final String TAG = "EventListener";

    private Subscription subscription;

    public void subscribe() {

        final Class<T> entityClass =
                (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        subscription = RxBus.getInstance()
                .toObserverable(entityClass)
                .subscribe(new Subscriber<T>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "onCompleted: entityClass = " + entityClass);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        throwable.printStackTrace();
                    }

                    @Override
                    public void onNext(T event) {
                        onEventTriggr(event);
                    }
                });
    }

    public void unsubscribe() {
        subscription.unsubscribe();
    }

    public abstract void onEventTriggr(T event);

} 
```
