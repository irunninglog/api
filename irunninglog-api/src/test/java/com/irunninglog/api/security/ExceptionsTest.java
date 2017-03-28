package com.irunninglog.api.security;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ExceptionsTest {

    @Test
    public void authn() {
        AuthnException exception = new AuthnException("foo");
        assertEquals("foo", exception.getMessage());
    }

}
