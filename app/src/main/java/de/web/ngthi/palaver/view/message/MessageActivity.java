package de.web.ngthi.palaver.view.message;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import javax.inject.Inject;

import de.web.ngthi.palaver.MockupData;
import de.web.ngthi.palaver.PalaverApplication;
import de.web.ngthi.palaver.R;
import de.web.ngthi.palaver.di.DaggerAppComponent;
import de.web.ngthi.palaver.model.LocalUser;
import de.web.ngthi.palaver.presenter.MessageContract;
import de.web.ngthi.palaver.presenter.MessagePresenter;

public class MessageActivity extends AppCompatActivity implements MessageContract.View, View.OnClickListener {

    @Inject
    public PalaverApplication application;
    public MessageContract.Presenter presenter;
    private Button sendButton;
    private EditText messageField;
    private RecyclerView.Adapter mMessageAdapter;
    private RecyclerView mMessageRecycler;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        DaggerAppComponent.create().inject(this);

//        LocalUser localUser = MockupData.getLocalUser();
//        String friendName = localUser.getSortedFriendList().get(0).getUsername();
        String friendName = getIntent().getStringExtra(getString(R.string.intent_friend_message));
        presenter = new MessagePresenter(this, friendName);

        toolbar = findViewById(R.id.toolbar_message);
        toolbar.setTitle(presenter.getFriendName());
//        setSupportActionBar(toolbar);

        messageField = findViewById(R.id.edittext_message_sendtext);
        sendButton = findViewById(R.id.button_message_send);
        sendButton.setOnClickListener(this);

        mMessageAdapter = new MessageListAdapter(presenter);
        mMessageRecycler = findViewById(R.id.recyclerview_message);
        mMessageRecycler.setAdapter(mMessageAdapter);
        mMessageRecycler.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.dispose();
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
    }

    @Override
    public void scrollDown() {
        int lastMessageIndex = Math.max(presenter.getRepositoriesRowsCount() - 1, 0);
        mMessageRecycler.smoothScrollToPosition(lastMessageIndex);
    }
}
