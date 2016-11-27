package com.irunninglog.security;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AuthnRequestTest {

    @Test
    public void sanity() {
        AuthnRequest request = new AuthnRequest();
        request.setPassword("password");
        request.setPath("/foo");
        request.setUsername("junit@irunninglog.com");

        assertEquals("password", request.getPassword());
        assertEquals("/foo", request.getPath());
        assertEquals("junit@irunninglog.com", request.getUsername());
    }

}
