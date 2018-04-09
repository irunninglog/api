package com.irunninglog.spring.runs;

import com.irunninglog.api.runs.IRun;
import com.irunninglog.spring.AbstractTest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import static org.junit.Assert.*;

public class RunTest extends AbstractTest implements ApplicationContextAware {

    private ApplicationContext context;

    @Override
    protected void afterBefore(ApplicationContext applicationContext) {
        super.afterBefore(applicationContext);

        this.context = applicationContext;
    }

    @Test
    public void testPrototype() {
        IRun run1 = context.getBean(IRun.class);
        IRun run2 = context.getBean(IRun.class);

        assertFalse(run1 == run2);
    }

    @Test
    public void testGettersSetters() {
        IRun run = context.getBean(IRun.class)
                .setId(1)
                .setDistance(1)
                .setShoes("shoes")
                .setStartTime(null)
                .setStartTimeLocal(null)
                .setTimezone(null);

        assertEquals(1, run.getId());
        Assert.assertEquals(1, run.getDistance(), 1E-9);
        assertEquals("shoes", run.getShoes());
        assertNull(run.getStartTime());
        assertNull(run.getStartTimeLocal());
        assertNull(run.getTimezone());
    }

}
