package de.web.ngthi.palaver.mvp.model;

import org.hamcrest.CoreMatchers;
import org.joda.time.DateTime;
import org.junit.Test;

import static org.junit.Assert.*;

public class MessageTest {

    @Test
    public void compareTo() {
        Message m1 = new Message(null, null, null, new DateTime(1), null);
        Message m2 = new Message(null, null, null, new DateTime(2), null);

        assertThat(m1.compareTo(m2), CoreMatchers.is(-1));
        assertThat(m2.compareTo(m1), CoreMatchers.is(1));
    }
}