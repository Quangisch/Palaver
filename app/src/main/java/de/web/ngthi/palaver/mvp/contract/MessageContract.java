package de.web.ngthi.palaver.mvp.contract;

public interface MessageContract {

    interface View extends BaseContract.View {
        void notifyDataSetChanged();
        void onSwipeRefreshEnd();
    }

    interface Presenter extends BaseContract.Presenter<View> {
        void onBindRepositoryRowViewAtPosition(MessageViewable holder, int position);
        int getRepositoriesRowsCount();
        int getRepositoriesRowsType(int position);
        void onMessageSend(String message);
        void onSwipeRefreshStart();
        void onAddFilterOffset(final int year, final int month, final int day, final int hour, final int minute);
        void onRemoveFilterOffset();
        boolean hasOffsetFilter();
        String getOffsetString();
    }

    interface MessageViewable {
        void bind(String messageContent, String dateTime);
        void bindPending(String messageContent);
        void bindFailed(String messageContent);
    }
}
