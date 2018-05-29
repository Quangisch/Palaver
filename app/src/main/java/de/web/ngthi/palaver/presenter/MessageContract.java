package de.web.ngthi.palaver.presenter;

public interface MessageContract {

    interface View extends BaseContract.View {
        void notifyDataSetChanged();
        void scrollDown();
        void onSwipeRefreshEnd();
    }

    interface Presenter extends BaseContract.Presenter<View> {
        void onBindRepositoryRowViewAtPosition(MessageViewable holder, int position);
        int getRepositoriesRowsCount();
        int getRepositoriesRowsType(int position);
        void onMessageSend(String message);
        void onSwipeRefreshStart();
    }

    interface MessageViewable {
        void bind(String messageContent, String dateTime);
    }
}
