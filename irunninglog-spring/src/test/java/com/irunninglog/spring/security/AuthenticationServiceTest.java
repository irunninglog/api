package com.irunninglog.spring.security;

import com.irunninglog.api.Endpoint;
import com.irunninglog.api.security.*;
import com.irunninglog.api.security.SecurityException;
import com.irunninglog.spring.AbstractTest;
import com.irunninglog.spring.profile.ProfileEntity;
import org.junit.Test;
import org.springframework.context.ApplicationContext;

import java.util.Base64;

import static org.junit.Assert.*;

public class AuthenticationServiceTest extends AbstractTest {

    private IAuthenticationService authenticationService;

    private ProfileEntity myprofile;
    private ProfileEntity admin;
    private ProfileEntity none;

    @Override
    public void afterBefore(ApplicationContext context) {
        authenticationService = context.getBean(IAuthenticationService.class);

        myprofile = saveProfile("allan@irunninglog.com", "password", "MYPROFILE");
        admin = saveProfile("admin@irunninglog.com", "password", "ADMIN");
        none = saveProfile("none@irunninglog.com", "password");
    }

    @Test
    public void success() throws com.irunninglog.api.security.SecurityException {
        IUser user = authenticationService.authenticate(Endpoint.DASHBOARD_GET,
                "/profiles/" + myprofile.getId(),
                "Basic YWxsYW5AaXJ1bm5pbmdsb2cuY29tOnBhc3N3b3Jk");
        assertEquals("allan@irunninglog.com", user.getUsername());
        assertEquals(1, user.getAuthorities().size());
    }

    @Test
    public void notFound() throws SecurityException {
        try {
            authenticationService.authenticate(Endpoint.DASHBOARD_GET,
                    null,
                    "Basic bm9ib2R5QGlydW5uaW5nbG9nLmNvbTpwYXNzd29yZA==");

            fail("Should have thrown");
        } catch (AuthnException ex) {
            assertTrue(ex.getMessage().contains("not found"));
        }
    }

    @Test
    public void wrongPassword() throws SecurityException {
        try {
            authenticationService.authenticate(Endpoint.DASHBOARD_GET,
                    null,
                    "Basic YWxsYW5AaXJ1bm5pbmdsb2cuY29tOndyb25n");

            fail("Should have thrown");
        } catch (AuthnException ex) {
            assertTrue(ex.getMessage().contains("don't match"));
        }
    }

    @Test
    public void decodeFailure() throws SecurityException {
        try {
            authenticationService.authenticate(Endpoint.DASHBOARD_GET,
                    null,
                    "Basic oops!");

            fail("Should have thrown");
        } catch (AuthnException ex) {
            assertTrue(ex.getMessage().contains("decode"));
        }
    }

    @Test
    public void denyAll() throws SecurityException {
        try {
            authenticationService.authenticate(Endpoint.FORBIDDEN,
                    null,
                    "Basic YWxsYW5AaXJ1bm5pbmdsb2cuY29tOnBhc3N3b3Jk");

            fail("Should have thrown");
        } catch (AuthzException ex) {
            assertTrue(ex.getMessage().contains("Not authorized for endpoint"));
        }
    }

    @Test
    public void allowAnonymous() throws SecurityException {
        assertNull(authenticationService.authenticate(Endpoint.PING, "/ping", null));
    }

    @Test
    public void allowAll() throws SecurityException {
        assertNotNull(authenticationService.authenticate(Endpoint.LOGIN,
                "/authn",
                "Basic YWxsYW5AaXJ1bm5pbmdsb2cuY29tOnBhc3N3b3Jk"));
    }

    @Test
    public void canViewMyProfile() throws SecurityException {
        IUser user = authenticationService.authenticate(Endpoint.PROFILE_GET,
                "/profiles/" + myprofile.getId(),
                "Basic YWxsYW5AaXJ1bm5pbmdsb2cuY29tOnBhc3N3b3Jk");

        assertNotNull(user);
    }

    @Test
    public void admin() throws SecurityException {
        IUser user = authenticationService.authenticate(Endpoint.PROFILE_GET,
                "/profiles/" + myprofile.getId() + "" + admin.getId(),
                "Basic YWRtaW5AaXJ1bm5pbmdsb2cuY29tOnBhc3N3b3Jk");

        assertNotNull(user);
    }

    @Test
    public void none() throws SecurityException {
        try {
            authenticationService.authenticate(Endpoint.PROFILE_GET,
                    "/profiles/" + none.getId(),
                    "Basic bm9uZUBpcnVubmluZ2xvZy5jb206cGFzc3dvcmQ=");

            fail("Should have thrown");
        } catch (AuthzException ex) {
            ex.printStackTrace();
        }
    }

    @Test
    public void token() throws SecurityException {
        IUser user = authenticationService.authenticate(Endpoint.DASHBOARD_GET,
                "/profiles/" + myprofile.getId(),
                "Basic YWxsYW5AaXJ1bm5pbmdsb2cuY29tOnBhc3N3b3Jk");

        assertNotNull(authenticationService.token(user));
    }

    @Test
    public void tokenSuccess() throws SecurityException {
        IUser basic = authenticationService.authenticate(Endpoint.DASHBOARD_GET,
                "/profiles/" + myprofile.getId(),
                "Basic YWxsYW5AaXJ1bm5pbmdsb2cuY29tOnBhc3N3b3Jk");

        IUser token = authenticationService.authenticate(Endpoint.DASHBOARD_GET,
                "/profiles/" + myprofile.getId(),
                "Token " + authenticationService.token(basic));
        assertEquals("allan@irunninglog.com", token.getUsername());
        assertEquals(1, token.getAuthorities().size());
    }

    @Test
    public void tokenExpired() throws SecurityException {
        IUser basic = authenticationService.authenticate(Endpoint.DASHBOARD_GET,
                "/profiles/" + myprofile.getId(),
                "Basic YWxsYW5AaXJ1bm5pbmdsb2cuY29tOnBhc3N3b3Jk");

        String original = authenticationService.token(basic);
        String [] tokens = new String(Base64.getDecoder().decode(original)).split(":");
        String invalid = Long.MIN_VALUE + ":" + tokens[1] + ":" + tokens[2];
        String newToken = Base64.getEncoder().encodeToString(invalid.getBytes());

        try {
            authenticationService.authenticate(Endpoint.DASHBOARD_GET,
                    "/profiles/" + myprofile.getId(),
                    "Token " + newToken);

            fail("Should have thrown");
        } catch (AuthnException ex) {
            assertTrue(ex.getMessage().contains("expired"));
        }
    }

    @Test
    public void tokenInvalidUser() throws SecurityException {
        IUser basic = authenticationService.authenticate(Endpoint.DASHBOARD_GET,
                "/profiles/" + myprofile.getId(),
                "Basic YWxsYW5AaXJ1bm5pbmdsb2cuY29tOnBhc3N3b3Jk");

        String original = authenticationService.token(basic);
        String [] tokens = new String(Base64.getDecoder().decode(original)).split(":");
        String invalid = tokens[0] + ":" + "invalid" + ":" + tokens[2];
        String newToken = Base64.getEncoder().encodeToString(invalid.getBytes());

        try {
            authenticationService.authenticate(Endpoint.DASHBOARD_GET,
                    "/profiles/" + myprofile.getId(),
                    "Token " + newToken);

            fail("Should have thrown");
        } catch (AuthnException ex) {
            assertTrue(ex.getMessage().contains("not found"));
        }
    }

    @Test
    public void tokenBadSignature() throws SecurityException {
        IUser basic = authenticationService.authenticate(Endpoint.DASHBOARD_GET,
                "/profiles/" + myprofile.getId(),
                "Basic YWxsYW5AaXJ1bm5pbmdsb2cuY29tOnBhc3N3b3Jk");

        String original = authenticationService.token(basic);
        String [] tokens = new String(Base64.getDecoder().decode(original)).split(":");
        String invalid = tokens[0] + ":" + tokens[1] + ":" + "foo";
        String newToken = Base64.getEncoder().encodeToString(invalid.getBytes());

        try {
            authenticationService.authenticate(Endpoint.DASHBOARD_GET,
                    "/profiles/" + myprofile.getId(),
                    "Token " + newToken);

            fail("Should have thrown");
        } catch (AuthnException ex) {
            assertTrue(ex.getMessage().contains("don't match"));
        }
    }

    @Test
    public void tokenInvalid() throws SecurityException {
        try {
            authenticationService.authenticate(Endpoint.DASHBOARD_GET,
                    null,
                    "Token oops!");

            fail("Should have thrown");
        } catch (AuthnException ex) {
            assertTrue(ex.getMessage().contains("decode"));
        }
    }

}
