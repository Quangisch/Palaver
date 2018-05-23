package de.web.ngthi.palaver.view.friends;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import javax.inject.Inject;

import de.web.ngthi.palaver.PalaverApplication;
import de.web.ngthi.palaver.R;
import de.web.ngthi.palaver.di.DaggerAppComponent;
import de.web.ngthi.palaver.presenter.FriendsContract;
import de.web.ngthi.palaver.presenter.FriendsPresenter;
import de.web.ngthi.palaver.view.message.MessageActivity;

public class FriendsActivity extends AppCompatActivity implements FriendsContract.View {

    @Inject
    public PalaverApplication application;
    private Toolbar toolbar;
    private FriendsContract.Presenter presenter;
    private RecyclerView.Adapter friendsAdapter;
    private RecyclerView friendsRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);
        DaggerAppComponent.create().inject(this);

        presenter = new FriendsPresenter(this);

        toolbar = findViewById(R.id.toolbar_friends);
//        setSupportActionBar(toolbar);

        friendsAdapter = new FriendsListAdapter(presenter);
        friendsRecycler = findViewById(R.id.recyclerview_friends);
        friendsRecycler.setAdapter(friendsAdapter);
        friendsRecycler.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    public void onFriendClick(String friend) {
        Intent intent = new Intent(this, MessageActivity.class);
        intent.putExtra(getString(R.string.intent_friend_message), friend);
        startActivity(intent);
    }

    @Override
    public void notifyDataSetChanged() {
        Log.d(getClass().getSimpleName(), "notifyDataSetChanged");
        friendsAdapter.notifyDataSetChanged();
    }

    @Override
    public void showUnkownFriendError() {
        //TODO
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.dispose();
    }
}
