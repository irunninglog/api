package com.irunninglog.spring.security.impl;

import com.irunninglog.security.AuthzException;
import com.irunninglog.security.IAuthorizationService;
import com.irunninglog.security.User;
import com.irunninglog.spring.AbstractTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

@SuppressWarnings("SpringJavaAutowiredMembersInspection")
public class AuthorizationServiceTest extends AbstractTest {

    @Autowired
    private IAuthorizationService authorizationService;

    @Test
    public void myprofile() throws AuthzException {
        User user = new User().setId(1);
        user.getAuthorities().add("MYPROFILE");
        assertSame(user, authorizationService.authorize(user, "/profiles/1/foo"));
    }

    @Test
    public void admin() throws AuthzException {
        User user = new User().setId(1);
        user.getAuthorities().add("ADMIN");
        assertSame(user, authorizationService.authorize(user, "/foobar"));
    }

    @Test
    public void unauthorized() {
        User user = new User().setId(2);
        try {
            authorizationService.authorize(user, "/profiles/1/foo");
            fail("Should have thrown");
        } catch (AuthzException ex) {
            assertTrue(true);
        }
    }

}
