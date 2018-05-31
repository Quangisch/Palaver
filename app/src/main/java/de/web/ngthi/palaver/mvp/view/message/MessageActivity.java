package de.web.ngthi.palaver.mvp.view.message;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import org.joda.time.IllegalFieldValueException;

import java.util.Calendar;

import de.web.ngthi.palaver.PalaverApplication;
import de.web.ngthi.palaver.R;
import de.web.ngthi.palaver.mvp.contracts.MessageContract;
import de.web.ngthi.palaver.mvp.presenter.MessagePresenter;
import de.web.ngthi.palaver.mvp.view.friends.FriendsActivity;

public class MessageActivity extends AppCompatActivity implements MessageContract.View, View.OnClickListener {

    private final String TAG = "=="+getClass().getSimpleName()+"==";

    public MessageContract.Presenter presenter;
    private Button sendButton;
    private EditText messageField;
    private RecyclerView.Adapter mMessageAdapter;
    private RecyclerView mMessageRecycler;
    private SwipeRefreshLayout swipeLayout;
    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme_NoActionBar);
        super.onCreate(savedInstanceState);
        Log.d(TAG, "=====onCreate=====");
        setContentView(R.layout.activity_message);

        PalaverApplication application = (PalaverApplication) getApplication();
        String friendName = getIntent().getStringExtra(getString(R.string.intent_friend_message));
        presenter = new MessagePresenter(this, application.getRepository(), friendName);

        Toolbar toolbar = findViewById(R.id.toolbar_message);
        toolbar.setTitle(friendName);
        toolbar.setNavigationOnClickListener(new BackListener());
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        messageField = findViewById(R.id.edittext_message_sendtext);
        sendButton = findViewById(R.id.button_message_send);
        sendButton.setOnClickListener(this);

        mMessageAdapter = new MessageListAdapter(presenter);
        mMessageRecycler = findViewById(R.id.recyclerview_message);
        mMessageRecycler.setAdapter(mMessageAdapter);
        mMessageRecycler.addOnLayoutChangeListener((v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom) -> {
            if ( bottom < oldBottom)
                mMessageRecycler.postDelayed(this::scrollDown, 100);
        });
        mMessageRecycler.setLayoutManager(new LinearLayoutManager(this));

        swipeLayout = findViewById(R.id.swiperefresh_message);
        swipeLayout.setOnRefreshListener(() -> {
            Log.d(TAG, "refresh swipe");
            swipeLayout.setRefreshing(true);
            presenter.onSwipeRefreshStart();
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
                if(presenter.hasOffsetFilter()) {
                    presenter.onRemoveFilterOffset();
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
    public void onDestroy() {
        super.onDestroy();
        presenter.dispose();
    }

    @Override
    public void onRestart() {
        super.onRestart();
        notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        if(v == sendButton) {
            presenter.onMessageSend(messageField.getText().toString());
            messageField.setText("");
        }
    }

    @Override
    public void notifyDataSetChanged() {
        mMessageAdapter.notifyDataSetChanged();
        scrollDown();
    }

    private void scrollDown() {
        int lastMessageIndex = Math.max(presenter.getRepositoriesRowsCount() - 1, 0);
        mMessageRecycler.smoothScrollToPosition(lastMessageIndex);
    }

    @Override
    public void onSwipeRefreshEnd() {
        if(swipeLayout != null)
            swipeLayout.setRefreshing(false);
    }

    @Override
    public void showNetworkError() {
        onSwipeRefreshEnd();
        makeSnack(R.string.error_network_message);
    }

    private void makeSnack(int resId) {
        Snackbar.make(mMessageRecycler, getString(resId), Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressed() {
        presenter.stop();
        new BackListener().onClick(null);
    }

    private class BackListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MessageActivity.this, FriendsActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
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
                        presenter.onAddFilterOffset(year, monthOfYear, dayOfMonth, hourOfDay, minute);
                        menu.findItem(R.id.action_message_filter)
                                .setTitle(getString(R.string.action_message_filter_activated) + ": " + presenter.getOffsetString());},
                    currentCalendar.get(Calendar.HOUR_OF_DAY),
                    currentCalendar.get(Calendar.MINUTE),
                    true)
                .show();
    }

}
