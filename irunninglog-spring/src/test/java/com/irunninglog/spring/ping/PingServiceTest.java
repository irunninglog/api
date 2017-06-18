package com.irunninglog.spring.ping;

import com.irunninglog.api.ping.IPing;
import com.irunninglog.api.ping.IPingService;
import com.irunninglog.spring.AbstractTest;
import org.junit.Test;
import org.springframework.context.ApplicationContext;

import static org.junit.Assert.assertNotNull;

public class PingServiceTest extends AbstractTest {

    private IPingService pingService;

    @Override
    protected void afterBefore(ApplicationContext applicationContext) {
        super.afterBefore(applicationContext);

        this.pingService = applicationContext.getBean(IPingService.class);
    }

    @Test
    public void ping() {
        IPing ping = pingService.ping();
        assertNotNull(ping);
        assertNotNull(ping.getTimestamp());
    }

}
