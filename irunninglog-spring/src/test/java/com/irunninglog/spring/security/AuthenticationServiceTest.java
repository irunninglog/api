package com.irunninglog.spring.security;

import com.irunninglog.api.security.AuthnException;
import com.irunninglog.api.security.IAuthenticationService;
import com.irunninglog.spring.AbstractTest;
import org.junit.Test;
import org.springframework.context.ApplicationContext;

import static org.junit.Assert.*;

public class AuthenticationServiceTest extends AbstractTest {

    private IAuthenticationService authenticationService;

    @Override
    public void afterBefore(ApplicationContext context) {
        authenticationService = context.getBean(IAuthenticationService.class);
    }

    @Test
    public void failTokenNull() throws SecurityException {
        try {
            assertNull(authenticationService.authenticate(null));

            fail("Should have thrown");
        } catch (AuthnException ex) {
            assertTrue(Boolean.TRUE);
        }
    }

    @Test
    public void failTokenFormat() throws SecurityException {
        try {
            authenticationService.authenticate("Basic invalid");

            fail("Should have thrown");
        } catch (AuthnException ex) {
            assertEquals("Invalid token format", ex.getMessage());
        }
    }

    @Test
    public void failTokenVerification() throws SecurityException {
        try {
            authenticationService.authenticate("Bearer invalid");

            fail("Should have thrown");
        } catch (AuthnException ex) {
            assertEquals("Token verification failed", ex.getMessage());
        }
    }

}
