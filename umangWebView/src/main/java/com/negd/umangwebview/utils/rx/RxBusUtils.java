package com.negd.umangwebview.utils.rx;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class RxBusUtils {

    public RxBusUtils() {
    }

    private PublishSubject<Object> bus = PublishSubject.create();

    public void send(Object o) {
        bus.onNext(o);
    }

    public Observable<Object> toObservable() {
        return bus;
    }
}
