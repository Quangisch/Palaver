package de.web.ngthi.palaver.mvp.presenter;

import android.support.annotation.NonNull;
import android.util.Log;

import de.web.ngthi.palaver.mvp.contract.BaseContract;
import de.web.ngthi.palaver.repository.IRepository;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.SingleTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;

public abstract class BasePresenter<V extends BaseContract.View> implements BaseContract.Presenter<V> {

    private V view;
    private CompositeDisposable disposables;
    private IRepository repository;

    BasePresenter(V view, IRepository repository) {
        subscribe(view);
        this.disposables = new CompositeDisposable();
        this.repository = repository;
        RxJavaPlugins.setErrorHandler(error -> {
            Log.e("==RXJAVAPLUGINS.ERROR==", error.getMessage(), error);
            try {
                if(getView() != null)
                    getView().showNetworkError();
            } catch(Exception e) {
                Log.e("==VIEW.EXCEPTION==", e.getMessage(), e);
            }

        });
    }

    public V getView() {
        return view;
    }

    public void onStart() {

    }

    public IRepository getRepository() {
        return repository;
    }

    public void onStop() {
        Log.d(getClass().getSimpleName(), "clear disposables");
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
        Log.e(this.getClass().getSimpleName(), throwable.getMessage());
        getView().showNetworkError();
    }

    public void dispose() {
        onStop();
        unsubscribe();
    }

    protected <T> SingleTransformer<T, T> applySchedulers() {
        return new SingleTransformer<T, T>() {
            @Override
            public SingleSource<T> apply(Single<T> observable) {
                return observable
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .compose(applyLoader());
            }
        };
    }

    protected <T> SingleTransformer<T, T> applyLoader() {
        return new SingleTransformer<T, T>() {
            @Override
            public SingleSource<T> apply(Single<T> observable) {
                return observable
                        .doOnSubscribe(d -> getView().startLoading())
                        .doAfterTerminate(() -> getView().endLoading());
            }
        };
    }

}
