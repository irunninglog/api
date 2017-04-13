package com.irunninglog.api;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class EndpointTest {

    @Test
    public void ping() {
        assertEquals("6678d445-030f-4aad-b360-6304588c07b6", Endpoint.PING.getAddress());
        assertEquals("/ping", Endpoint.PING.getPath());
        assertEquals(AccessControl.ANONYMOUS, Endpoint.PING.getControl());
        assertEquals(EndpointMethod.GET, Endpoint.PING.getMethod());
    }

    @Test
    public void identity() {
        assertEquals("407a8fc3-ce60-4076-81d6-a7432dd7defb", Endpoint.IDENTITY.getAddress());
        assertEquals("/identity", Endpoint.IDENTITY.getPath());
        assertEquals(AccessControl.ALLOW, Endpoint.IDENTITY.getControl());
        assertEquals(EndpointMethod.POST, Endpoint.IDENTITY.getMethod());
    }

    @Test
    public void tostring() {
        assertNotNull(Endpoint.PING.toString());
    }

}
