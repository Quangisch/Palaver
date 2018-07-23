package de.web.ngthi.palaver.mvp.model;

import static org.hamcrest.Matchers.is;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserTest {

    @Test
    public void compareTo() {
        String username = "user";

        User a = new User(username);
        User b = new User(username);
        User c = new User(username+"x");

        assertThat(a.equals(b), is(true));
        assertThat(b.equals(a), is(true));
        assertThat(a.equals(c), is(false));
    }
}