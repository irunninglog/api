package com.irunninglog.spring.profile;

import com.irunninglog.api.profile.IGetProfileRequest;
import com.irunninglog.api.profile.IGetProfileResponse;
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
        IGetProfileRequest request = applicationContext.getBean(IGetProfileRequest.class).setProfileId(2);
        assertEquals(2, request.getProfileId());
    }

    @Test
    public void response() {
        IGetProfileResponse response = applicationContext.getBean(IGetProfileResponse.class);
        assertNull(response.getBody());
    }

}
