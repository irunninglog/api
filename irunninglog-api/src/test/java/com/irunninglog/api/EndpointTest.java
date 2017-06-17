package com.irunninglog.api;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class EndpointTest {

    @Test
    public void ping() {
        assertEquals("6678d445-030f-4aad-b360-6304588c07b6", Endpoint.PING.getAddress());
        assertEquals("/api/ping", Endpoint.PING.getPath());
        assertEquals(AccessControl.ALL, Endpoint.PING.getControl());
        assertEquals(EndpointMethod.GET, Endpoint.PING.getMethod());
    }

    @Test
    public void tostring() {
        assertNotNull(Endpoint.PING.toString());
    }

}
