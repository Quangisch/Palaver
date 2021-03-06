package de.web.ngthi.palaver.mvp.contract;

import java.util.List;

public interface FriendsContract {

    interface View extends BaseContract.View {
        void onFriendClick(String friend);
        void notifyDataSetChanged();
        void showUnknownFriendError();
        void showDuplicateFriendError();
        void showWrongOldPassword();
        void showWrongPasswordRepeat();
        void showPasswordTooShort();
        void showChangedPassword();

        void onSwipeRefreshEnd();
    }

    interface Presenter extends BaseContract.Presenter<View> {
        void onBindRepositoryRowViewAtPosition(FriendsViewable holder, int position);
        int getRepositoriesRowsCount();

        void onFriendClick(String friend);
        void onAddFriend(String friend);
        void onRemoveFriend(List<String> friends);
        String[] getFriends();
        void onChangePassword(String username, String oldPassword, String newPassword, String newPasswordRepeat);

        void onSwipeRefreshStart();

    }

    interface FriendsViewable {
        void bind(String friendName, String message, String dateTime);
    }

}
