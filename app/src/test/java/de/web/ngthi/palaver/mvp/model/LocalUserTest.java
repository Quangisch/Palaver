package de.web.ngthi.palaver.mvp.model;

import org.hamcrest.CoreMatchers;
import org.hamcrest.Matchers;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

public class LocalUserTest {

    private LocalUser local;

    @Before
    public void setUp() {
        local = new LocalUser("local", "password");
    }

    @Test
    public void addFriend() {
        User a = new User("a");
        local.addFriend(a);

        assertThat(local.getFriends().contains(a), CoreMatchers.is(true));
    }

    @Test
    public void addFriends() {
        List<User> users = new LinkedList<>();
        User b = new User("b");
        User c = new User("c");
        users.add(b);
        users.add(c);
        local.addFriends(users);

        assertThat(local.getFriends().contains(b), CoreMatchers.is(true));
        assertThat(local.getFriends().contains(c), CoreMatchers.is(true));
    }

    @Test
    public void removeFriend() {
        User d = new User("d");
        local.addFriend(d);
        local.removeFriend(d);

        assertThat(local.getFriends().contains(d), CoreMatchers.is(false));
    }


    @Test
    public void getSortedFriendList() {
        User e = new User("e");
        User f = new User("f");
        local.addFriend(e);
        local.addFriend(f);
        List<User> friends = local.getSortedFriendList();
        int eIndex = -1;
        int fIndex = -1;
        for(int i = 0; i < friends.size(); i++) {
            if(friends.get(i).equals(e))
                eIndex = i;
            if(friends.get(i).equals(f))
                fIndex = i;
        }

        assertThat(eIndex, CoreMatchers.is(CoreMatchers.not(-1)));
        assertThat(fIndex, CoreMatchers.is(CoreMatchers.not(-1)));
        assertThat(fIndex, Matchers.greaterThan(eIndex));
    }

    @Test
    public void getSortedMessages() {
        User g = new User("g");
        String message = "message";
        DateTime date1 = new DateTime(1);
        DateTime date2 = new DateTime(2);
        Message m1 = new Message(g, local, message, date1, Message.Status.SENT);
        Message m2 = new Message(g, local, message, date2, Message.Status.SENT);

        local.addFriend(g);
        local.addMessage(g.getUsername(), m1);
        local.addMessage(g.getUsername(), m2);

        List<Message> messages = local.getSortedMessages(g);
        int m1Index = -1;
        int m2Index = -1;
        for(int i = 0; i < messages.size(); i++) {
            if(messages.get(i).equals(m1))
                m1Index = i;
            if(messages.get(i).equals(m2))
                m2Index = i;
        }

        assertThat(m1Index, CoreMatchers.is(CoreMatchers.not(-1)));
        assertThat(m2Index, CoreMatchers.is(CoreMatchers.not(-1)));
        assertThat(m2Index, Matchers.greaterThan(m1Index));
    }
}