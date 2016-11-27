package com.irunninglog.security;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class UserTest {

    @Test
    public void noargs() {
        User user = new User();
        user.setId(2);
        user.setUsername("junit@irunninglog.com");

        assertEquals(2, user.getId());
        assertEquals("junit@irunninglog.com", user.getUsername());
        assertTrue(user.getAuthorities().isEmpty());
    }

    @Test
    public void args() {
        User user = new User(1, "junit@irunninglog.com", "foo");

        assertEquals(1, user.getId());
        assertEquals("junit@irunninglog.com", user.getUsername());
        assertFalse(user.getAuthorities().isEmpty());
    }

    @Test
    public void tostring() {
        String s = new User().toString();

        assertTrue(s.contains("id"));
        assertTrue(s.contains("username"));
        assertTrue(s.contains("authorities"));
    }

}
