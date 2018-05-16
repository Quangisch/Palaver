package de.web.ngthi.palaver.view.message;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import javax.inject.Inject;

import de.web.ngthi.palaver.PalaverApplication;
import de.web.ngthi.palaver.R;
import de.web.ngthi.palaver.di.DaggerAppComponent;
import de.web.ngthi.palaver.model.LocalUser;
import de.web.ngthi.palaver.model.Message;
import de.web.ngthi.palaver.presenter.MessageContract;
import de.web.ngthi.palaver.presenter.MessagePresenter;

public class MessageActivity extends Activity implements MessageContract.View, View.OnClickListener {

    @Inject
    public PalaverApplication application;
    public MessageContract.Presenter presenter;
    private Button sendButton;
    private TextView friendNameField;
    private EditText messageField;
    private MessageListAdapter mMessageAdapter;
    private RecyclerView mMessageRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        DaggerAppComponent.builder().build().inject(this);

        LocalUser localUser = ((PalaverApplication) getApplication()).getLocalUser();
        List<Message> messages = localUser.getSortedMessages(localUser.getSortedFriendList().get(0));
        presenter = new MessagePresenter(this, messages);

        friendNameField = findViewById(R.id.textview_message_friendname);
        friendNameField.setText(presenter.getFriendName());
        messageField = findViewById(R.id.edittext_message_sendtext);
        sendButton = findViewById(R.id.button_message_send);
        sendButton.setOnClickListener(this);

        mMessageAdapter = new MessageListAdapter(presenter);
        mMessageRecycler = findViewById(R.id.recyclerview_message);
        mMessageRecycler.setAdapter(mMessageAdapter);
        mMessageRecycler.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onDestroy() {
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
        mMessageRecycler.smoothScrollToPosition(presenter.getRepositoriesRowsCount() - 1);
    }
}
