package de.web.ngthi.palaver.mvp.view.message;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.web.ngthi.palaver.PalaverApplication;
import de.web.ngthi.palaver.R;
import de.web.ngthi.palaver.mvp.contract.MessageContract;
import de.web.ngthi.palaver.mvp.presenter.MessagePresenter;
import de.web.ngthi.palaver.mvp.view.BaseActivity;
import de.web.ngthi.palaver.mvp.view.friends.FriendsActivity;

public class MessageActivity extends BaseActivity<MessageContract.Presenter> implements MessageContract.View {

    private final String TAG = MessageActivity.class.getSimpleName();

    @BindView(R.id.button_message_send) Button sendButton;
    @BindView(R.id.edittext_message_sendtext) EditText messageField;
    @BindView(R.id.recyclerview_message) RecyclerView mMessageRecycler;
    @BindView(R.id.swiperefresh_message) SwipeRefreshLayout swipeLayout;

    private RecyclerView.Adapter mMessageAdapter;
    private Menu menu;

    public MessageActivity() {
        Log.d(TAG, "==============constructor==============");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme_NoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        ButterKnife.bind(this);

        String friendName = getIntent().getStringExtra(getString(R.string.intent_friend_message));
        PalaverApplication application = (PalaverApplication) getApplication();
        setPresenter(new MessagePresenter(this, application.getRepository(), friendName));

        getToolbar().setTitle(friendName);
        getToolbar().setNavigationOnClickListener(view -> onClickBack());
        if(getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        mMessageAdapter = new MessageListAdapter(getPresenter());
        mMessageRecycler.setAdapter(mMessageAdapter);
        mMessageRecycler.addOnLayoutChangeListener((v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom) -> {
            if (bottom < oldBottom)
                mMessageRecycler.postDelayed(this::scrollDown, 100);
        });
        mMessageRecycler.setLayoutManager(new LinearLayoutManager(this));

        swipeLayout.setOnRefreshListener(() -> {
            Log.d(TAG, "refresh swipe");
            swipeLayout.setRefreshing(true);
            getPresenter().onSwipeRefreshStart();
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        this.menu = menu;
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.message_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_message_filter:
                if(getPresenter().hasOffsetFilter()) {
                    getPresenter().onRemoveFilterOffset();
                    item.setTitle(getString(R.string.action_message_filter_deactivated));
                } else {
                    showDateTimePicker();
                }
                return true;
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if(isLoading())
            endLoading();
        else {
            getPresenter().onStop();
            onClickBack();
        }
    }

    @Override
    public void notifyDataSetChanged() {
        mMessageAdapter.notifyDataSetChanged();
        scrollDown();
    }

    private void scrollDown() {
        int lastMessageIndex = Math.max(getPresenter().getRepositoriesRowsCount() - 1, 0);
        mMessageRecycler.smoothScrollToPosition(lastMessageIndex);
    }

    @Override
    public void onSwipeRefreshEnd() {
        if(swipeLayout != null)
            swipeLayout.setRefreshing(false);
    }
    private void showDateTimePicker() {
        final Calendar currentCalendar = Calendar.getInstance();
        new DatePickerDialog(
                this,
                (DatePicker view, int year, int monthOfYear, int dayOfMonth) ->
                        showTimePicker(currentCalendar, year, monthOfYear + 1, dayOfMonth), //monthOfYear + 1: JodaTime 1-12, Calendar 0-11
                currentCalendar.get(Calendar.YEAR),
                currentCalendar.get(Calendar.MONTH),
                currentCalendar.get(Calendar.DAY_OF_MONTH))
                .show();
    }

    private void showTimePicker(final Calendar currentCalendar, final int year, final int monthOfYear, final int dayOfMonth) {
        new TimePickerDialog(
                    this,
                    (TimePicker view, int hourOfDay,int minute) -> {
                        Log.d(TAG, String.format("showDateTimePicker: %d, %d, %d %d:%d", year, monthOfYear, dayOfMonth, hourOfDay, minute));
                        getPresenter().onAddFilterOffset(year, monthOfYear, dayOfMonth, hourOfDay, minute);
                        menu.findItem(R.id.action_message_filter)
                                .setTitle(getString(R.string.action_message_filter_activated) + ": " + getPresenter().getOffsetString());},
                    currentCalendar.get(Calendar.HOUR_OF_DAY),
                    currentCalendar.get(Calendar.MINUTE),
                    true)
                .show();
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.button_message_send)
    public void onClickSend() {
        getPresenter().onMessageSend(messageField.getText().toString());
        messageField.setText("");
    }

    @OnClick()
    public void onClickBack() {
        Intent intent = new Intent(MessageActivity.this, FriendsActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
