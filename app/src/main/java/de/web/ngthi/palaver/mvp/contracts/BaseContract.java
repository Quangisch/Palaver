package de.web.ngthi.palaver.mvp.contracts;

import android.support.annotation.NonNull;

import io.reactivex.disposables.Disposable;

public interface BaseContract {

    interface View {
        void showNetworkError();
        void onDestroy();
    }

    interface Presenter<V extends View > {
        V getView();
        void start();
        void stop();
        void subscribe(@NonNull V view);
        void unsubscribe();
        void addDisposable(Disposable disposable);
        void dispose();
    }
}
