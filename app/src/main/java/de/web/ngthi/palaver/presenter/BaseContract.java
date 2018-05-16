package de.web.ngthi.palaver.presenter;

import android.support.annotation.NonNull;

import io.reactivex.disposables.Disposable;

public interface BaseContract {

    interface Presenter<V> {
        V getView();
        void start();
        void stop();
        void subscribe(@NonNull V view);
        void unsubscribe();
        void addDisposable(Disposable disposable);
        void dispose();
    }
}
