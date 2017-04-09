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

import static org.junit.Assert.*;

public class AuthenticationServiceTest extends AbstractTest {

    private IAuthenticationService authenticationService;

    @Override
    public void afterBefore(ApplicationContext context) {
        authenticationService = context.getBean(IAuthenticationService.class);

        saveProfile("allan@irunninglog.com", "password", "MYPROFILE");
        saveProfile("admin@irunninglog.com", "password", "ADMIN");
        saveProfile("none@irunninglog.com", "password");
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
    public void failUserNotFound() throws UnsupportedEncodingException {
        try {
            Algorithm algorithm = Algorithm.HMAC256("secret");
            String token = JWT.create()
                    .withIssuer("issuer")
                    .withSubject("invalid")
                    .sign(algorithm);

            authenticationService.authenticate("Bearer " + token);

            fail("Should have thrown");
        } catch (AuthnException ex) {
            assertEquals("User not found", ex.getMessage());
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
    public void successAdmin() throws UnsupportedEncodingException, AuthnException {
        Algorithm algorithm = Algorithm.HMAC256("secret");
        String token = JWT.create()
                .withIssuer("issuer")
                .withSubject("admin@irunninglog.com")
                .sign(algorithm);

        IUser user = authenticationService.authenticate("Bearer " + token);
        assertEquals("admin@irunninglog.com", user.getUsername());
        assertEquals(1, user.getAuthorities().size());
        assertEquals("ADMIN", user.getAuthorities().iterator().next());
    }

    @Test
    public void successNone() throws UnsupportedEncodingException, AuthnException {
        Algorithm algorithm = Algorithm.HMAC256("secret");
        String token = JWT.create()
                .withIssuer("issuer")
                .withSubject("none@irunninglog.com")
                .sign(algorithm);

        IUser user = authenticationService.authenticate("Bearer " + token);
        assertEquals("none@irunninglog.com", user.getUsername());
        assertEquals(0, user.getAuthorities().size());
    }

}
