package com.irunninglog.spring.runs;

import com.irunninglog.api.runs.IRun;
import com.irunninglog.spring.AbstractTest;
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
                .setDuration(3600)
                .setDistance("1")
                .setShoes("shoes")
                .setName("name")
                .setStartTime(null);

        assertEquals(1, run.getId());
        assertEquals("1", run.getDistance());
        assertEquals("shoes", run.getShoes());
        assertEquals("name", run.getName());
        assertEquals(3600, run.getDuration());
        assertNull(run.getStartTime());
    }

}
