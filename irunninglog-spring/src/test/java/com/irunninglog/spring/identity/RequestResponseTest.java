package com.irunninglog.spring.identity;

import com.irunninglog.api.identity.IIdentityRequest;
import com.irunninglog.api.identity.IIdentityResponse;
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
    public void request() {
        IIdentityRequest request = applicationContext.getBean(IIdentityRequest.class);
        request.setUsername("foo");

        assertEquals("foo", request.getUsername());
    }

    @Test
    public void response() {
        IIdentityResponse response = applicationContext.getBean(IIdentityResponse.class);

        assertNull(response.getBody());
    }

}
