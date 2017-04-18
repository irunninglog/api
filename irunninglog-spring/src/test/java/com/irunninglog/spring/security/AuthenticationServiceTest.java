package com.irunninglog.spring.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.irunninglog.api.security.AuthnException;
import com.irunninglog.api.security.IAuthenticationService;
import com.irunninglog.api.security.IUser;
import com.irunninglog.spring.AbstractTest;
import org.junit.Test;
import org.springframework.context.ApplicationContext;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import static org.junit.Assert.*;

public class AuthenticationServiceTest extends AbstractTest {

    private IAuthenticationService authenticationService;

    @Override
    public void afterBefore(ApplicationContext context) {
        authenticationService = context.getBean(IAuthenticationService.class);

        saveProfile("allan@irunninglog.com", "password");
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

    @Test
    public void successMyProfile() throws UnsupportedEncodingException, AuthnException {
        Algorithm algorithm = Algorithm.HMAC256("secret");
        String token = JWT.create()
                .withIssuer("issuer")
                .withSubject("allan@irunninglog.com")
                .sign(algorithm);

        IUser user = authenticationService.authenticate("Bearer " + token);
        assertEquals("allan@irunninglog.com", user.getUsername());
        assertEquals(1, user.getAuthorities().size());
        assertEquals("MYPROFILE", user.getAuthorities().iterator().next());
    }

    @Test
    public void expiredToken() throws AuthnException, UnsupportedEncodingException {
        Algorithm algorithm = Algorithm.HMAC256("secret");
        String token = JWT.create()
                .withExpiresAt(new Date(System.currentTimeMillis() - 1000))
                .withIssuer("issuer")
                .withSubject("allan@irunninglog.com")
                .sign(algorithm);

        try {
            authenticationService.authenticate("Bearer " + token);

            fail("Should have thrown");
        } catch (AuthnException ex) {
            assertTrue(Boolean.TRUE);
        }
    }

}
