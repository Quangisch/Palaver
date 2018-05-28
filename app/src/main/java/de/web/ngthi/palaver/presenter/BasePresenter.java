package de.web.ngthi.palaver.presenter;

import android.support.annotation.NonNull;

import de.web.ngthi.palaver.repository.IRepository;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class BasePresenter<V> implements BaseContract.Presenter<V> {

    private V view;
    private CompositeDisposable disposables;
    private IRepository repository;

    public BasePresenter(V view, IRepository repository) {
        subscribe(view);
        this.disposables = new CompositeDisposable();
        this.repository = repository;
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
