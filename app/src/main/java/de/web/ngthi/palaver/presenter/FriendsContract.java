package de.web.ngthi.palaver.presenter;

import java.util.List;

import de.web.ngthi.palaver.model.Message;
import de.web.ngthi.palaver.view.friends.FriendsViewHolder;

public interface FriendsContract {

    interface View {
        void onFriendClick(String friend);
        void notifyDataSetChanged();
        void showUnkownFriendError();
        void showDuplicateFriendError();
        void showWrongOldPassword();
        void showWrongPasswordRepeat();
        void showPasswordTooShort();
        void showPasswordTooLong();
        void showChangedPassword();

        void onDestroy();
    }

    interface Presenter extends BaseContract.Presenter<View> {
        void onBindRepositoryRowViewAtPosition(FriendsViewable holder, int position);
        int getRepositoriesRowsCount();

        void onFriendClick(String friend);
        void onAddFriend(String friend);
        void onRemoveFriend(List<Integer> friendIndex);
        String[] getFriends();
        void onChangePassword(String oldPassword, String newPassword, String newPasswordRepeat);

    }

    interface FriendsViewable {
        void bind(String friendName, String message, String dateTime);
    }

}
