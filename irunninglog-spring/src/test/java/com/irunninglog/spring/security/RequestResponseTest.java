package com.irunninglog.spring.security;

import com.irunninglog.api.security.ILoginRequest;
import com.irunninglog.api.security.ILoginResponse;
import com.irunninglog.api.security.IUser;
import com.irunninglog.spring.AbstractTest;
import org.junit.Test;
import org.springframework.context.ApplicationContext;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class RequestResponseTest extends AbstractTest {

    private ApplicationContext applicationContext;

    @Override
    protected void afterBefore(ApplicationContext applicationContext) {
        super.afterBefore(applicationContext);

        this.applicationContext = applicationContext;
    }

    @Test
    public void loginRequest() {
        IUser user = applicationContext.getBean(IUser.class);
        ILoginRequest request = applicationContext.getBean(ILoginRequest.class);
        //noinspection unchecked
        request.setUser(user);

        assertEquals(user, request.getUser());
    }

    @Test
    public void loginResponse() {
        ILoginResponse response = applicationContext.getBean(ILoginResponse.class);
        assertNull(response.getBody());
    }

}
