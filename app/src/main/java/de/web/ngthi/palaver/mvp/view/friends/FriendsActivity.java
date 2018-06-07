package de.web.ngthi.palaver.mvp.view.friends;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.List;

import butterknife.BindView;
import de.web.ngthi.palaver.R;
import de.web.ngthi.palaver.mvp.contract.FriendsContract;
import de.web.ngthi.palaver.mvp.presenter.FriendsPresenter;
import de.web.ngthi.palaver.mvp.view.BaseActivity;
import de.web.ngthi.palaver.mvp.view.login.LoginActivity;
import de.web.ngthi.palaver.mvp.view.message.MessageActivity;

public class FriendsActivity extends BaseActivity<FriendsContract.Presenter>
        implements FriendsContract.View,
        AddFriendDialogFragment.InputListener,
        RemoveFriendsDialogFragment.InputListener,
        ChangePasswordDialogFragment.InputListener{

    private static final String TAG = FriendsActivity.class.getSimpleName();


    @BindView(R.id.recyclerview_friends) RecyclerView friendsRecycler;
    @BindView(R.id.swiperefresh_friends) SwipeRefreshLayout swipeLayout;

    private RecyclerView.Adapter friendsAdapter;
    private ChangePasswordDialogFragment changeDialog;

    public FriendsActivity() {
        Log.d(TAG, "==============constructor==============");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);

        //TODO dependencyInjection
        setPresenter(new FriendsPresenter(this, getPalaverApplication().getRepository(),
                getPalaverApplication().getLocalUsername(), getPalaverApplication().getLocalPassword()));

        getToolbar().setTitle(R.string.app_name);

        //RecyclerView
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        friendsAdapter = new FriendsListAdapter(getPresenter());
        friendsRecycler.setAdapter(friendsAdapter);
        friendsRecycler.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(friendsRecycler.getContext(),
                layoutManager.getOrientation());
        friendsRecycler.addItemDecoration(dividerItemDecoration);

        swipeLayout.setOnRefreshListener(() -> {
            Log.d(TAG, "swipe refresh");
            swipeLayout.setRefreshing(true);
            getPresenter().onSwipeRefreshStart();
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.friends_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_friends_add:
                AddFriendDialogFragment addDialog = new AddFriendDialogFragment();
                addDialog.show(getSupportFragmentManager(), getString(R.string.action_friends_addFriend));
                return true;
            case R.id.action_friends_remove:
                RemoveFriendsDialogFragment removeDialog = new RemoveFriendsDialogFragment();
                removeDialog.show(getSupportFragmentManager(), getString(R.string.action_friends_removeFriend));
                return true;
            case R.id.action_friends_changePassword:
                changeDialog = new ChangePasswordDialogFragment();
                changeDialog.show(getSupportFragmentManager(), getString(R.string.action_friends_changePassword));
                return true;
            case R.id.action_friends_logOut:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(R.string.friends_title_logOut)
                        .setMessage(R.string.friends_text_logOut)
                        .setNegativeButton(R.string.friends_button_cancel, (DialogInterface dialog, int which) -> {})
                        .setPositiveButton(R.string.friends_button_logOut, (DialogInterface dialog, int which) -> {
                            getPalaverApplication().clearLocalUserData();
                            Intent intent = new Intent(this, LoginActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                            startActivity(intent);
                            finish();
                }).create().show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onFriendClick(String friend) {
        Intent intent = new Intent(this, MessageActivity.class);
        intent.putExtra(getString(R.string.intent_friend_message), friend);
        startActivity(intent);
    }

    @Override
    public void onSwipeRefreshEnd() {
        swipeLayout.setRefreshing(false);
    }

    @Override
    public void notifyDataSetChanged() {
        Log.d(TAG, "notifyDataSetChanged");
        friendsAdapter.notifyDataSetChanged();
    }

    @Override
    public void showUnknownFriendError() {
        makeSnack(R.string.friends_error_unknownFriend);
    }

    @Override
    public void showDuplicateFriendError() {
        makeSnack(R.string.friends_error_duplicateFriend);
    }

    @Override
    public void showWrongOldPassword() {
        makeSnack(R.string.friends_error_wrongOldPassword);
    }

    @Override
    public void showWrongPasswordRepeat() {
        makeSnack(R.string.friends_error_wrongPasswordRepeat);
    }

    @Override
    public void showPasswordTooShort() {
        makeSnack(R.string.global_error_passwordShort);
    }

    @Override
    public void showChangedPassword() {
        makeSnack(R.string.friends_snack_changedPassword);
        changeDialog.dismiss();
    }

    @Override
    public void onAddDialogPositiveButton(String friend) {
        getPresenter().onAddFriend(friend);
    }

    @Override
    public void onRemoveDialogPositiveButton(List<String> selectedFriends) {
        getPresenter().onRemoveFriend(selectedFriends);
    }

    @Override
    public void onChangeDialogPositiveButton(String oldPassword, String newPassword, String newPasswordRepeat) {
        getPresenter().onChangePassword(getPalaverApplication().getLocalUsername(), oldPassword, newPassword, newPasswordRepeat);
    }

    @Override
    public String getUsername() {
        return getPalaverApplication().getLocalUsername();
    }

    @Override
    public String[] getFriends() {
        return getPresenter().getFriends();
    }

}
