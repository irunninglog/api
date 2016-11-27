package com.irunninglog.security;

import com.irunninglog.service.ResponseStatus;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AuthnResponseTest {

    @Test
    public void sanity() {
        User user = new User();
        AuthnResponse response = new AuthnResponse().setBody(user).setStatus(ResponseStatus.Ok);

        assertEquals(user, response.getBody());
        assertEquals(ResponseStatus.Ok, response.getStatus());
    }

}
