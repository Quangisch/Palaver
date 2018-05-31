package de.web.ngthi.palaver.mvp.view.friends;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.web.ngthi.palaver.R;
import de.web.ngthi.palaver.mvp.contract.FriendsContract;

public class FriendsListAdapter extends RecyclerView.Adapter<FriendsViewHolder> {

    private FriendsContract.Presenter presenter;

    FriendsListAdapter(FriendsContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @NonNull
    @Override
    public FriendsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View friendsView = LayoutInflater.from(parent.getContext()).inflate(R.layout.friend_view, parent, false);
        return new FriendsViewHolder(friendsView, presenter);
    }

    @Override
    public void onBindViewHolder(@NonNull FriendsViewHolder holder, int position) {
        presenter.onBindRepositoryRowViewAtPosition(holder, position);
    }

    @Override
    public int getItemCount() {
        return presenter.getRepositoriesRowsCount();
    }
}
