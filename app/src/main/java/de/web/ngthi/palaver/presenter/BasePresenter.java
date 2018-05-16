package de.web.ngthi.palaver.presenter;

import android.support.annotation.NonNull;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class BasePresenter<V> implements BaseContract.Presenter<V> {

    private V view;
    private CompositeDisposable disposables;

    public BasePresenter(V view) {
        subscribe(view);
        this.disposables = new CompositeDisposable();
    }

    public V getView() {
        return view;
    }

    public void start() {

    }

    public void stop() {
        disposables.clear();
    }

    public void subscribe(V view) {
        this.view = view;
    }

    public void unsubscribe() {
        view = null;
    }

    public void addDisposable(Disposable disposable) {
        this.disposables.add(disposable);
    }

    public void dispose() {
        stop();
        unsubscribe();
    }

}
