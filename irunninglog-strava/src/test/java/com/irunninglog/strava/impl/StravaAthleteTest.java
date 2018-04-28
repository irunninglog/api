package com.irunninglog.strava.impl;

import com.irunninglog.strava.IStravaAthlete;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {Config.class})
public class StravaAthleteTest implements ApplicationContextAware {

    private ApplicationContext context;

    @Test
    public void testPrototype() {
        IStravaAthlete athlete1 = context.getBean(IStravaAthlete.class);
        IStravaAthlete athlete2 = context.getBean(IStravaAthlete.class);

        assertFalse(athlete1 == athlete2);
    }

    @Test
    public void testGettersAndSetters() {
        IStravaAthlete athlete = context.getBean(IStravaAthlete.class);
        athlete.setId(1);
        athlete.setEmail("allan@irunninglog.com");
        athlete.setFirstname("Allan");
        athlete.setLastname("Lewis");
        athlete.setAvatar("avatar.png");

        assertEquals(1L, athlete.getId());
        assertEquals("Allan", athlete.getFirstname());
        assertEquals("Lewis", athlete.getLastname());
        assertEquals("allan@irunninglog.com", athlete.getEmail());
        assertEquals("avatar.png", athlete.getAvatar());
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

}
