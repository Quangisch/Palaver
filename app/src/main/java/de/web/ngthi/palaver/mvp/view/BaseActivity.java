package de.web.ngthi.palaver.mvp.view;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import butterknife.BindView;
import de.web.ngthi.palaver.R;
import de.web.ngthi.palaver.mvp.contract.BaseContract;

public abstract class BaseActivity<P extends BaseContract.Presenter> extends AppCompatActivity implements BaseContract.View {

    private P presenter;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.progressbar) ProgressBar progressBar;
    @BindView(R.id.layout_inner) ViewGroup viewGroup;
    private boolean loading;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

    @SuppressWarnings("unchecked")
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

    public void startLoading() {
        if(!loading) {
            loading = true;
            progressBar.setVisibility(View.VISIBLE);

        }
    }

    public void endLoading() {
        if(loading) {
            loading = false;
            progressBar.setVisibility(View.GONE);
        }
    }

    protected boolean isLoading() {
        return loading;
    }

    protected abstract void notifyDataSetChanged();

    public P getPresenter() {
        return presenter;
    }

    public void setPresenter(P presenter) {
        this.presenter = presenter;
    }

    public Toolbar getToolbar() {
        return toolbar;
    }
}
