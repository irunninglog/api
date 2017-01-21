package com.irunninglog.security;

import com.irunninglog.service.Endpoint;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AuthnRequestTest {

    @Test
    public void sanity() {
        AuthnRequest request = new AuthnRequest();
        request.setToken("foo");
        request.setEndpoint(Endpoint.Ping);
        request.setPath("/path");

        assertEquals("foo", request.getToken());
        assertEquals(Endpoint.Ping, request.getEndpoint());
        assertEquals("/path", request.getPath());
    }

}
