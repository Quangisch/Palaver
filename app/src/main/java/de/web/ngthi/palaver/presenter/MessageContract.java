package de.web.ngthi.palaver.presenter;

import android.support.annotation.NonNull;
import android.view.ViewGroup;

import java.util.List;

import de.web.ngthi.palaver.model.Message;

public interface MessageContract {

    interface View {
        void notifyDataSetChanged();
        void scrollDown();
    }

    interface Presenter extends BaseContract.Presenter<View> {
        void onBindRepositoryRowViewAtPosition(MessageRowView holder, int position);
        int getRepositoriesRowsCount();
        int getRepositoriesRowsType(int position);
        void onMessageSend(String message);
        String getFriendName();
    }

    interface MessageRowView {
        void bind(String messageContent, String dateTime);
    }
}
