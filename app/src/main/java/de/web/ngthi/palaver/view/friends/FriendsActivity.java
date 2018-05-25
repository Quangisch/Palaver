package de.web.ngthi.palaver.view.friends;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import java.util.List;

import de.web.ngthi.palaver.PalaverApplication;
import de.web.ngthi.palaver.R;
import de.web.ngthi.palaver.presenter.FriendsContract;
import de.web.ngthi.palaver.presenter.FriendsPresenter;
import de.web.ngthi.palaver.view.login.LoginActivity;
import de.web.ngthi.palaver.view.message.MessageActivity;

public class FriendsActivity extends AppCompatActivity
        implements FriendsContract.View,
        AddFriendDialogFragment.InputListener,
        RemoveFriendsDialogFragment.InputListener,
        ChangePasswordDialogFragment.InputListener{

    public PalaverApplication application;
    private FriendsContract.Presenter presenter;
    private RecyclerView.Adapter friendsAdapter;
    private ChangePasswordDialogFragment changeDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);
        application = (PalaverApplication) getApplication();

        presenter = new FriendsPresenter(this);

        Toolbar toolbar = findViewById(R.id.toolbar_friends);
        toolbar.setTitle(R.string.app_name);
        setSupportActionBar(toolbar);

        //RecyclerView
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        friendsAdapter = new FriendsListAdapter(presenter);
        RecyclerView friendsRecycler = findViewById(R.id.recyclerview_friends);
        friendsRecycler.setAdapter(friendsAdapter);
        friendsRecycler.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(friendsRecycler.getContext(),
                layoutManager.getOrientation());
        friendsRecycler.addItemDecoration(dividerItemDecoration);
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
                            Intent intent = new Intent(this, LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
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
    public void notifyDataSetChanged() {
        Log.d(getClass().getSimpleName(), "notifyDataSetChanged");
        friendsAdapter.notifyDataSetChanged();
    }

    private void mackSnack(int resId) {
        View view = findViewById(R.id.coordinatorlayout_friends);
        if(changeDialog != null && changeDialog.getView() != null)
            view = changeDialog.getView();
        Snackbar.make(view, resId, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showUnkownFriendError() {
        mackSnack(R.string.friends_error_unknownFriend);
    }

    @Override
    public void showDuplicateFriendError() {
        mackSnack(R.string.friends_error_duplicateFriend);
    }

    @Override
    public void showWrongOldPassword() {
        mackSnack(R.string.friends_error_wrongOldPassword);
    }

    @Override
    public void showWrongPasswordRepeat() {
        mackSnack(R.string.friends_error_wrongPasswordRepeat);
    }

    @Override
    public void showPasswordTooShort() {
        mackSnack(R.string.global_error_passwordShort);
    }

    @Override
    public void showPasswordTooLong() {
        mackSnack(R.string.global_error_passwordLong);
    }

    @Override
    public void showChangedPassword() {
        mackSnack(R.string.action_friends_changePassword);
        changeDialog.dismiss();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.dispose();
    }

    @Override
    public void onAddDialogPositiveButton(String friend) {
        presenter.onAddFriend(friend);
    }

    @Override
    public void onAddDialogNegativeButton() {

    }

    @Override
    public void onRemoveDialogPositiveButton(List<Integer> selectedFriendsIndex) {
        presenter.onRemoveFriend(selectedFriendsIndex);
    }

    @Override
    public void onRemoveDialogNegativeButton() {

    }

    @Override
    public String[] getFriends() {
        return presenter.getFriends();
    }


    @Override
    public void onChangeDialogPositiveButton(String oldPassword, String newPassword, String newPasswordRepeat) {
        presenter.onChangePassword(oldPassword, newPassword, newPasswordRepeat);
    }

    @Override
    public void onChangeDialogNegativeButton() {

    }
}
