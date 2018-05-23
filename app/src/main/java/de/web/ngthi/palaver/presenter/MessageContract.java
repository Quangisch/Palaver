package de.web.ngthi.palaver.presenter;

public interface MessageContract {

    interface View {
        void notifyDataSetChanged();
        void scrollDown();

        void onDestroy();
    }

    interface Presenter extends BaseContract.Presenter<View> {
        void onBindRepositoryRowViewAtPosition(MessageViewable holder, int position);
        int getRepositoriesRowsCount();
        int getRepositoriesRowsType(int position);
        void onMessageSend(String message);
        String getFriendName();
    }

    interface MessageViewable {
        void bind(String messageContent, String dateTime);
    }
}
