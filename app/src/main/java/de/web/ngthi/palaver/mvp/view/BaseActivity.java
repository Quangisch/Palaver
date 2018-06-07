package de.web.ngthi.palaver.mvp.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.web.ngthi.palaver.PalaverApplication;
import de.web.ngthi.palaver.R;
import de.web.ngthi.palaver.mvp.contract.BaseContract;

public abstract class BaseActivity<P extends BaseContract.Presenter> extends AppCompatActivity implements BaseContract.View {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.progressbar) ProgressBar progressBar;
    @BindView(R.id.layout_inner) ViewGroup viewGroup;

    private PalaverApplication application;
    private P presenter;
    private boolean loading;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        application = (PalaverApplication) getApplication();
    }

    @Override
    public void setContentView(int resId) {
        super.setContentView(resId);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
    }

    @Override
    public void onDestroy() {
        onStop();
        super.onDestroy();
    }

    @Override
    public void onStop() {
        presenter.dispose();
        super.onStop();
    }

    @Override
    public void showNetworkError() {
        endLoading();
        Toast.makeText(this, R.string.error_network_message, Toast.LENGTH_LONG).show();
    }

//    @SuppressWarnings("unchecked")
    @Override
    public void onRestart() {
        presenter.subscribe(this);
        notifyDataSetChanged();
        super.onRestart();
    }

    @Override
    public void onBackPressed() {
        if(isLoading()) {
            endLoading();
        } else {
            presenter.onStop();
            super.onBackPressed();
        }
    }

    public void makeSnack(int resId) {
        Snackbar.make(viewGroup, resId, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev){
        if(loading)
            return true; //consume
        return super.dispatchTouchEvent(ev); //delegate
    }

    public void startLoading() {
        if(!loading) {
            loading = true;
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    public void endLoading() {
        loading = false;
        progressBar.setVisibility(View.GONE);
    }

    protected boolean isLoading() {
        return loading;
    }

    protected abstract void notifyDataSetChanged();

    public void setPresenter(P presenter) {
        this.presenter = presenter;
    }

    public P getPresenter() {
        return presenter;
    }

    public Toolbar getToolbar() {
        return toolbar;
    }

    public PalaverApplication getPalaverApplication() {
        return application;
    }
}
