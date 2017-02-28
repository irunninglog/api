package com.irunninglog.spring.ping;

import com.irunninglog.api.ping.IPingService;
import com.irunninglog.spring.AbstractTest;
import org.junit.Test;
import org.springframework.context.ApplicationContext;

import static org.junit.Assert.assertNotNull;

public class PingServiceTest extends AbstractTest {

    private IPingService service;

    @Override
    protected void afterBefore(ApplicationContext applicationContext) {
        super.afterBefore(applicationContext);

        service = applicationContext.getBean(IPingService.class);
    }

    @Test
    public void ping() {
        assertNotNull(service.ping().getTimestamp());
    }

}
