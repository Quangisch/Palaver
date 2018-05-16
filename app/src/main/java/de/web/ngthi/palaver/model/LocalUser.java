package de.web.ngthi.palaver.model;

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
        contacts.put(friend, new LinkedList<Message>());
    }

    public boolean hasFriend(User friend) {
        return contacts.containsKey(friend);
    }

    public void removeFriend(User friend) {
        contacts.remove(friend);
    }

    public Set<User> getFriends() {
        return contacts.keySet();
    }

    public void addMessage(Message m) {
        User other = m.getRecipient().equals(this) ? m.getSender() : m.getRecipient();
        if(!contacts.containsKey(other))
            contacts.put(other, new LinkedList<Message>());
        contacts.get(other).add(m);
    }

    public List<Message> getMessages(User friend) {
        return contacts.get(friend);
    }

}
