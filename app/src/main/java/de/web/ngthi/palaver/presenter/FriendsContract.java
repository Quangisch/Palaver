package de.web.ngthi.palaver.presenter;

public interface FriendsContract {

    interface View {
        void updateDataSet();
        void showUnkownFriendError();
    }

    interface Presenter extends BaseContract.Presenter<View> {
        void onFriendClick(String friend);
        void onAddFriend(String friend);
        void onRemoveFriend(String friend);
    }

}
