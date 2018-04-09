package com.irunninglog.spring;

import com.irunninglog.api.runs.IRun;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SpringConfig.class, TestConfig.class})
public abstract class AbstractTest implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Before
    public final void before() {
        afterBefore(applicationContext);
    }

    protected void afterBefore(ApplicationContext applicationContext) {
        // Empty for subclasses
    }

    @After
    public final void after() {

    }

    @Override
    public final void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    protected final IRun run(LocalDateTime localDateTime) {
        return run(localDateTime, 0);
    }

    protected final IRun run(LocalDateTime localDateTime, float distance) {
        return new IRun() {
            @Override
            public int getId() {
                return 0;
            }

            @Override
            public IRun setId(int id) {
                return null;
            }

            @Override
            public ZonedDateTime getStartTime() {
                return null;
            }

            @Override
            public IRun setStartTime(ZonedDateTime startTime) {
                return null;
            }

            @Override
            public LocalDateTime getStartTimeLocal() {
                return localDateTime;
            }

            @Override
            public IRun setStartTimeLocal(LocalDateTime startTimeLocal) {
                return null;
            }

            @Override
            public String getTimezone() {
                return null;
            }

            @Override
            public IRun setTimezone(String timezone) {
                return null;
            }

            @Override
            public float getDistance() {
                return distance;
            }

            @Override
            public IRun setDistance(float distance) {
                return null;
            }

            @Override
            public String getShoes() {
                return null;
            }

            @Override
            public IRun setShoes(String gear) {
                return null;
            }
        };
    }

}
