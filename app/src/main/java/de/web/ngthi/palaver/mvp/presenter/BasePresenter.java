package de.web.ngthi.palaver.mvp.presenter;

import android.support.annotation.NonNull;
import android.util.Log;

import de.web.ngthi.palaver.mvp.contracts.BaseContract;
import de.web.ngthi.palaver.repository.IRepository;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.plugins.RxJavaPlugins;

public abstract class BasePresenter<V extends BaseContract.View> implements BaseContract.Presenter<V> {

    private V view;
    private CompositeDisposable disposables;
    private IRepository repository;

    BasePresenter(V view, IRepository repository) {
        subscribe(view);
        this.disposables = new CompositeDisposable();
        this.repository = repository;
        RxJavaPlugins.setErrorHandler(error -> {
            Log.e("===RXJAVAPLUGINS===", error.getMessage(), error);
            getView().showNetworkError();
        });
    }

    public V getView() {
        return view;
    }

    public void start() {

    }

    public IRepository getRepository() {
        return repository;
    }

    public void stop() {
        disposables.clear();
    }

    public void subscribe(@NonNull V view) {
        this.view = view;
    }

    public void unsubscribe() {
        view = null;
    }

    public void addDisposable(Disposable disposable) {
        try {
            this.disposables.add(disposable);
        } catch (io.reactivex.exceptions.UndeliverableException e) {
            showNetworkError(e);
            if(disposable != null)
                disposable.dispose();
        }
    }

    public void showNetworkError(Throwable throwable) {
        Log.d(getClass().getSimpleName()+"==", throwable.getMessage());
        getView().showNetworkError();

    }

    public void dispose() {
        stop();
        unsubscribe();
    }

}
