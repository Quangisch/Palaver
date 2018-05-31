package de.web.ngthi.palaver.mvp.contract;

import android.support.annotation.NonNull;

import io.reactivex.disposables.Disposable;

public interface BaseContract {

    interface View {
        void onDestroy();
        void onStop();

        void showNetworkError();
    }

    interface Presenter<V extends View > {
        V getView();
        void onStart();
        void onStop();
        void subscribe(@NonNull V view);
        void unsubscribe();
        void addDisposable(Disposable disposable);
        void dispose();
    }
}
