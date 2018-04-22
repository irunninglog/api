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

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

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
        return applicationContext.getBean(IRun.class).setDistance(BigDecimal.valueOf(distance).toPlainString()).setStartTime(localDateTime.atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
    }

}
