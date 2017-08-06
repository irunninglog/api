package com.irunninglog.spring.security;

import com.irunninglog.spring.AbstractTest;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class UserTest extends AbstractTest {


    @Test
    public void tesToString() {
        User user = new User();
        user.setToken("foo");

        String tostring = user.toString();
        assertTrue(tostring.contains("***"));
        assertFalse(tostring.contains("foo"));
    }

}
