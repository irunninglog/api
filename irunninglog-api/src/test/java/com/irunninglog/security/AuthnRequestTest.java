package com.irunninglog.security;

import com.irunninglog.service.Endpoint;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AuthnRequestTest {

    @Test
    public void sanity() {
        AuthnRequest request = new AuthnRequest();
        request.setPassword("password");
        request.setEndpoint(Endpoint.Ping);
        request.setUsername("junit@irunninglog.com");
        request.setPath("/path");

        assertEquals("password", request.getPassword());
        assertEquals(Endpoint.Ping, request.getEndpoint());
        assertEquals("junit@irunninglog.com", request.getUsername());
        assertEquals("/path", request.getPath());
    }

}
