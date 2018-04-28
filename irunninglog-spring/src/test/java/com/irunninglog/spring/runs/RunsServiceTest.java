package com.irunninglog.spring.runs;

import com.irunninglog.api.factory.IFactory;
import com.irunninglog.api.runs.IRun;
import com.irunninglog.api.runs.IRunsService;
import com.irunninglog.api.security.AuthnException;
import com.irunninglog.api.security.IUser;
import com.irunninglog.spring.AbstractTest;
import com.irunninglog.strava.IStravaService;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.context.ApplicationContext;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;

public class RunsServiceTest extends AbstractTest {

    private static final String TIME = ZonedDateTime.now().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);

    private IFactory factory;
    private IRunsService service;

    @Override
    protected void afterBefore(ApplicationContext applicationContext) {
        super.afterBefore(applicationContext);

        factory = applicationContext.getBean(IFactory.class);
        service = applicationContext.getBean(IRunsService.class);

        IStravaService stravaService = applicationContext.getBean(IStravaService.class);

        IRun run = factory.get(IRun.class);
        run.setId(1);
        run.setName("name");
        run.setStartTime(TIME);
        run.setDistance("1");
        run.setDuration(3600);
        run.setShoes("shoes_id");

        Mockito.when(stravaService.create(any(IUser.class), any(IRun.class))).thenReturn(run);
        Mockito.when(stravaService.update(any(IUser.class), any(IRun.class))).thenReturn(run);
    }

    @Test
    public void create() throws AuthnException {
        IRun before = factory.get(IRun.class);
        before.setId(-1);
        before.setName("name");
        before.setStartTime(TIME);
        before.setDistance("1");
        before.setDuration(3600);
        before.setShoes("shoes_id");

        IRun after = service.create(factory.get(IUser.class), before);

        assertEquals(1, after.getId());
        assertEquals(before.getName(), after.getName());
        assertEquals(before.getStartTime(), after.getStartTime());
        assertEquals(before.getDistance(), after.getDistance());
        assertEquals(before.getDuration(), after.getDuration());
        assertEquals(before.getShoes(), after.getShoes());
    }

    @Test
    public void update() throws AuthnException {
        IRun before = factory.get(IRun.class);
        before.setId(-1);
        before.setName("name");
        before.setStartTime(TIME);
        before.setDistance("1");
        before.setDuration(3600);
        before.setShoes("shoes_id");

        IRun after = service.update(factory.get(IUser.class), before);

        assertEquals(1, after.getId());
        assertEquals(before.getName(), after.getName());
        assertEquals(before.getStartTime(), after.getStartTime());
        assertEquals(before.getDistance(), after.getDistance());
        assertEquals(before.getDuration(), after.getDuration());
        assertEquals(before.getShoes(), after.getShoes());
    }

}
