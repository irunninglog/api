package com.irunninglog.strava.impl;

import com.irunninglog.strava.IStravaRun;
import org.junit.Test;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import static org.junit.Assert.*;

public class StravaRunTest extends AbstractStravaTest implements ApplicationContextAware {

    private ApplicationContext context;

    @Test
    public void testPrototype() {
        IStravaRun run1 = context.getBean(IStravaRun.class);
        IStravaRun run2 = context.getBean(IStravaRun.class);

        assertFalse(run1 == run2);
    }

    @Test
    public void testGettersSetters() {
        IStravaRun run = context.getBean(IStravaRun.class)
                .setId(1)
                .setDistance(1)
                .setShoes("shoes")
                .setStartTime(null)
                .setStartTimeLocal(null)
                .setTimezone(null);

        assertEquals(1, run.getId());
        assertEquals(1, run.getDistance(), getMargin());
        assertEquals("shoes", run.getShoes());
        assertNull(run.getStartTime());
        assertNull(run.getStartTimeLocal());
        assertNull(run.getTimezone());
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

}
