package com.irunninglog.spring;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {com.irunninglog.spring.context.ContextConfiguration.class})
public abstract class AbstractTest {

    static {
        System.setProperty("env", "application.properties");
    }

}
