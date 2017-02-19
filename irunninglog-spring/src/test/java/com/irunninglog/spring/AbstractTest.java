package com.irunninglog.spring;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {com.irunninglog.spring.context.ContextConfiguration.class})
public abstract class AbstractTest implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    static {
        System.setProperty("env", "application.properties");
    }

    @Before
    public void before() {
        afterBefore(applicationContext);
    }

    protected void afterBefore(ApplicationContext applicationContext) {
        // Empty for subclasses
    }

    @Override
    public final void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

}
