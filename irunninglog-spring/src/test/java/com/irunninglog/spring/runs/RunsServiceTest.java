package com.irunninglog.spring.runs;

import com.irunninglog.api.runs.IRun;
import com.irunninglog.api.runs.IRunsService;
import com.irunninglog.api.security.IUser;
import com.irunninglog.spring.AbstractTest;
import org.junit.Test;
import org.springframework.context.ApplicationContext;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class RunsServiceTest extends AbstractTest {

    private IRunsService service;

    @Override
    protected void afterBefore(ApplicationContext applicationContext) throws Exception {
        super.afterBefore(applicationContext);

        service = applicationContext.getBean(IRunsService.class);
    }

    @Test
    public void create() {
        try {
            service.create(factory.get(IUser.class), factory.get(IRun.class));
            fail("Should have failed");
        } catch (UnsupportedOperationException ex) {
            assertTrue(Boolean.TRUE);
        }
    }

    @Test
    public void update() {
        try {
            service.update(factory.get(IUser.class), factory.get(IRun.class));
            fail("Should have failed");
        } catch (UnsupportedOperationException ex) {
            assertTrue(Boolean.TRUE);
        }
    }

}
