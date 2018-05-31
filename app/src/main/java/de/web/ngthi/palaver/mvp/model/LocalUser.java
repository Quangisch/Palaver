package de.web.ngthi.palaver.mvp.model;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class LocalUser extends User {

    private String password;
    private Map<User, List<Message>> contacts;

    public LocalUser(String username, String password) {
        super(username);
        this.password = password;
        this.contacts = new HashMap<>();
    }

    public boolean isLocalUser() {
        return true;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void addFriend(User friend) {
        contacts.put(friend, new LinkedList<>());
    }

    public void addFriends(Collection<User> friends) {
        for(User u : friends)
            addFriend(u);
    }

    private boolean hasFriend(User friend) {
        return contacts.containsKey(friend);
    }

    public void removeFriend(User friend) {
        contacts.remove(friend);
    }

    public Set<User> getFriends() {
        return contacts.keySet();
    }

    public List<User> getSortedFriendList() {
        List<User> friendList = Arrays.asList(contacts.keySet().toArray(new User[contacts.keySet().size()]));
        Collections.sort(friendList);
        return friendList;
    }

    public void addMessage(String friend, Message m) {
        addMessages(friend, Collections.singletonList(m));
    }

    public void addMessages(String friend, Collection<Message> messages) {
        User u = new User(friend);
        if(hasFriend(u)) {
            if(!contacts.containsKey(u))
                contacts.put(u, new LinkedList<>());
            contacts.get(u).addAll(messages);
        }
    }

    public List<Message> getSortedMessages(User friend) {
        List<Message> messages = new LinkedList<>();
        if(hasFriend(friend)) {
            messages.addAll(contacts.get(friend));
            Collections.sort(messages);
        }
        return messages;
    }

    public String toString() {
        return String.format("%s,%s - %d", getUsername(), getPassword(), getFriends().size());
    }

}
