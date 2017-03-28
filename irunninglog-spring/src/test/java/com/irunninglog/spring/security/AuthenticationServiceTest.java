package com.irunninglog.spring.security;

import com.irunninglog.api.security.AuthnException;
import com.irunninglog.api.security.IAuthenticationService;
import com.irunninglog.api.security.IUser;
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
    public void success() throws AuthnException {
        IUser user = authenticationService.authenticate("Basic YWxsYW5AaXJ1bm5pbmdsb2cuY29tOnBhc3N3b3Jk");
        assertEquals("allan@irunninglog.com", user.getUsername());
        assertEquals(1, user.getAuthorities().size());
    }

    @Test
    public void notFound() throws SecurityException {
        try {
            authenticationService.authenticate("Basic bm9ib2R5QGlydW5uaW5nbG9nLmNvbTpwYXNzd29yZA==");

            fail("Should have thrown");
        } catch (AuthnException ex) {
            assertTrue(ex.getMessage().contains("not found"));
        }
    }

    @Test
    public void wrongPassword() throws SecurityException {
        try {
            authenticationService.authenticate("Basic YWxsYW5AaXJ1bm5pbmdsb2cuY29tOndyb25n");

            fail("Should have thrown");
        } catch (AuthnException ex) {
            assertTrue(ex.getMessage().contains("don't match"));
        }
    }

    @Test
    public void decodeFailure() throws SecurityException {
        try {
            authenticationService.authenticate("Basic oops!");

            fail("Should have thrown");
        } catch (AuthnException ex) {
            assertTrue(ex.getMessage().contains("decode"));
        }
    }

    @Test
    public void allowAnonymous() throws SecurityException {
        try {
            assertNull(authenticationService.authenticate(null));

            fail("Should have thrown");
        } catch (AuthnException ex) {
            assertTrue(Boolean.TRUE);
        }
    }

    @Test
    public void allowAll() throws AuthnException {
        assertNotNull(authenticationService.authenticate("Basic YWxsYW5AaXJ1bm5pbmdsb2cuY29tOnBhc3N3b3Jk"));
    }

    @Test
    public void canViewMyProfile() throws AuthnException {
        IUser user = authenticationService.authenticate("Basic YWxsYW5AaXJ1bm5pbmdsb2cuY29tOnBhc3N3b3Jk");

        assertEquals(myprofile.getId(), user.getId());
    }

    @Test
    public void admin() throws AuthnException {
        IUser user = authenticationService.authenticate("Basic YWRtaW5AaXJ1bm5pbmdsb2cuY29tOnBhc3N3b3Jk");

        assertEquals(admin.getId(), user.getId());
    }

    @Test
    public void none() throws AuthnException {
        IUser user = authenticationService.authenticate("Basic " + Base64.getEncoder().encodeToString("none@irunninglog.com:password".getBytes()));

        assertEquals(none.getId(), user.getId());
    }

    @Test
    public void token() throws AuthnException {
        IUser user = authenticationService.authenticate("Basic YWxsYW5AaXJ1bm5pbmdsb2cuY29tOnBhc3N3b3Jk");

        assertNotNull(authenticationService.token(user));
    }

    @Test
    public void tokenSuccess() throws AuthnException {
        IUser basic = authenticationService.authenticate("Basic YWxsYW5AaXJ1bm5pbmdsb2cuY29tOnBhc3N3b3Jk");

        IUser token = authenticationService.authenticate("Token " + authenticationService.token(basic));
        assertEquals("allan@irunninglog.com", token.getUsername());
        assertEquals(1, token.getAuthorities().size());
    }

    @Test
    public void tokenExpired() throws AuthnException {
        IUser basic = authenticationService.authenticate("Basic YWxsYW5AaXJ1bm5pbmdsb2cuY29tOnBhc3N3b3Jk");

        String original = authenticationService.token(basic);
        String [] tokens = new String(Base64.getDecoder().decode(original)).split(":");
        String invalid = Long.MIN_VALUE + ":" + tokens[1] + ":" + tokens[2];
        String newToken = Base64.getEncoder().encodeToString(invalid.getBytes());

        try {
            authenticationService.authenticate("Token " + newToken);

            fail("Should have thrown");
        } catch (AuthnException ex) {
            assertTrue(ex.getMessage().contains("expired"));
        }
    }

    @Test
    public void tokenInvalidUser() throws AuthnException {
        IUser basic = authenticationService.authenticate("Basic YWxsYW5AaXJ1bm5pbmdsb2cuY29tOnBhc3N3b3Jk");

        String original = authenticationService.token(basic);
        String [] tokens = new String(Base64.getDecoder().decode(original)).split(":");
        String invalid = tokens[0] + ":" + "invalid" + ":" + tokens[2];
        String newToken = Base64.getEncoder().encodeToString(invalid.getBytes());

        try {
            authenticationService.authenticate("Token " + newToken);

            fail("Should have thrown");
        } catch (AuthnException ex) {
            assertTrue(ex.getMessage().contains("not found"));
        }
    }

    @Test
    public void tokenBadSignature() throws AuthnException {
        IUser basic = authenticationService.authenticate("Basic YWxsYW5AaXJ1bm5pbmdsb2cuY29tOnBhc3N3b3Jk");

        String original = authenticationService.token(basic);
        String [] tokens = new String(Base64.getDecoder().decode(original)).split(":");
        String invalid = tokens[0] + ":" + tokens[1] + ":" + "foo";
        String newToken = Base64.getEncoder().encodeToString(invalid.getBytes());

        try {
            authenticationService.authenticate("Token " + newToken);

            fail("Should have thrown");
        } catch (AuthnException ex) {
            assertTrue(ex.getMessage().contains("don't match"));
        }
    }

    @Test
    public void tokenInvalid() throws SecurityException {
        try {
            authenticationService.authenticate("Token oops!");

            fail("Should have thrown");
        } catch (AuthnException ex) {
            assertTrue(ex.getMessage().contains("decode"));
        }
    }

}
