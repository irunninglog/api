package com.irunninglog.spring;

import com.irunninglog.api.factory.IFactory;
import com.irunninglog.api.runs.IRun;
import com.irunninglog.api.security.IUser;
import com.irunninglog.spring.strava.StravaMockRestTemplate;
import com.irunninglog.spring.strava.StravaService;
import com.irunninglog.spring.strava.StravaSessionCache;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SpringConfig.class, TestConfig.class})
public abstract class AbstractTest implements ApplicationContextAware {

    private ApplicationContext applicationContext;
    protected StravaService stravaService;
    protected StravaMockRestTemplate restTemplate;
    protected IFactory factory;

    @Before
    public final void before() throws Exception {
        restTemplate = (StravaMockRestTemplate) applicationContext.getBean(RestTemplate.class);
        factory = applicationContext.getBean(IFactory.class);
        StravaSessionCache cache = applicationContext.getBean(StravaSessionCache.class);

        cache.clear();
        restTemplate.clear();

        stravaService = applicationContext.getBean(StravaService.class);

        afterBefore(applicationContext);
    }

    protected void waitForRuns(IUser user) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                if (!stravaService.runs(user).isEmpty()) {
                    timer.cancel();
                    latch.countDown();
                }
            }

        }, 10);

        latch.await(1, TimeUnit.SECONDS);
    }

    protected void waitForShoes(IUser user) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                if (!stravaService.shoes(user).isEmpty()) {
                    timer.cancel();
                    latch.countDown();
                }
            }

        }, 10);

        latch.await(1, TimeUnit.SECONDS);
    }

    protected void afterBefore(ApplicationContext applicationContext) throws Exception {
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

    protected final IRun run(String startTime) {
        return applicationContext.getBean(IRun.class).setDistance(BigDecimal.valueOf((float) 16093.44).toPlainString()).setStartTime(startTime);
    }

    protected final IRun run(LocalDateTime localDateTime, float distance) {
        return applicationContext.getBean(IRun.class).setDistance(BigDecimal.valueOf(distance).toPlainString()).setStartTime(localDateTime.atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
    }

}
