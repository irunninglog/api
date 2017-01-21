package com.irunninglog.service;

import com.irunninglog.security.AccessControl;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class EndpointTest {

    @Test
    public void ping() {
        assertEquals("6678d445-030f-4aad-b360-6304588c07b6", Endpoint.Ping.getAddress());
        assertEquals("/ping", Endpoint.Ping.getPath());
        assertEquals(AccessControl.AllowAnonymous, Endpoint.Ping.getControl());
        assertEquals(EndpointMethod.GET, Endpoint.Ping.getMethod());
    }

    @Test
    public void tostring() {
        assertNotNull(Endpoint.Ping.toString());
    }

}
