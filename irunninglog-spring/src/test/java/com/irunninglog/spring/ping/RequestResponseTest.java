package com.irunninglog.spring.ping;

import com.irunninglog.api.ping.IPingRequest;
import com.irunninglog.api.ping.IPingResponse;
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
        IPingRequest request = applicationContext.getBean(IPingRequest.class);
        request.setOffset(600);

        assertEquals(600, request.getOffset());
    }

    @Test
    public void response() {
        IPingResponse response = applicationContext.getBean(IPingResponse.class);

        assertNull(response.getBody());
    }

}
