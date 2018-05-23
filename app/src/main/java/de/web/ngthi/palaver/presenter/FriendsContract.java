package de.web.ngthi.palaver.presenter;

import de.web.ngthi.palaver.view.friends.FriendsViewHolder;

public interface FriendsContract {

    interface View {
        void onFriendClick(String friend);
        void notifyDataSetChanged();
        void showUnkownFriendError();

        void onDestroy();
    }

    interface Presenter extends BaseContract.Presenter<View> {
        void onBindRepositoryRowViewAtPosition(FriendsViewable holder, int position);
        int getRepositoriesRowsCount();

        void onFriendClick(String friend);
        void onAddFriend(String friend);
        void onRemoveFriend(String friend);
    }

    interface FriendsViewable {
        void bind(String friendName, String message, String dateTime);
    }

}
