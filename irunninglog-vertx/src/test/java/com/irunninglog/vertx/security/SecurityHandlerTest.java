package com.irunninglog.vertx.security;

import com.irunninglog.api.security.AuthnException;
import com.irunninglog.vertx.AbstractTest;
import io.vertx.ext.unit.TestContext;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SecurityHandlerTest extends AbstractTest {

    @Test
    public void testUnauthorizedFromAuthnException(TestContext context) throws AuthnException {
        throwOnAuthentication(new AuthnException(""));

        assertEquals(401, get(context, "/api/profile"));
    }

    @Test
    public void testUnauthorizedFromNullUser(TestContext context) throws AuthnException {
        returnFromAuthentication(null);

        assertEquals(401, get(context, "/api/profile"));
    }

    @Test
    public void testNotFound(TestContext context) throws AuthnException {
        assertEquals(404, get(context, "/foo"));
    }

}
