package de.web.ngthi.palaver.mvp.view;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import butterknife.BindView;
import de.web.ngthi.palaver.R;
import de.web.ngthi.palaver.mvp.contract.BaseContract;

public abstract class BaseActivity<P extends BaseContract.Presenter> extends AppCompatActivity implements BaseContract.View {

    private P presenter;
    @BindView(R.id.progressbar) ProgressBar progressBar;
    @BindView(R.id.layout_inner) ViewGroup viewGroup;
    private boolean loading;

    public void startLoading() {
        if(!loading) {
            loading = true;
            if(progressBar != null)
                progressBar.setVisibility(View.VISIBLE);
            if(viewGroup != null)
                viewGroup.setVisibility(View.GONE);
        }
    }

    public void endLoading() {
        if(loading) {
            loading = false;
            if(progressBar != null)
                progressBar.setVisibility(View.GONE);
            if(viewGroup != null)
                viewGroup.setVisibility(View.VISIBLE);
        }
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
}
